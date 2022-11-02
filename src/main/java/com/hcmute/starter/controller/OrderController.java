package com.hcmute.starter.controller;


import com.hcmute.starter.handler.HttpMessageNotReadableException;
import com.hcmute.starter.handler.MethodArgumentNotValidException;
import com.hcmute.starter.mapping.OrderMapping;
import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.entity.userAddress.AddressEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Order.AddOrderRequest;
import com.hcmute.starter.model.payload.response.ErrorResponseMap;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    public static final String SUCCESS_URL = "/api/order/pay/success";
    public static final String CANCEL_URL = "/api/order/pay/cancel";

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    private final PaypalService paypalService;

    private final VoucherService voucherService;

    private final DiscountProgramService discountProgramService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OrderMapping orderMapping;

    private static final Logger LOGGER = LogManager.getLogger(AddressController.class);

    @PutMapping("")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SuccessResponse> saveOrder(@RequestBody AddOrderRequest request, BindingResult errors, HttpServletRequest httpServletRequest) throws Exception {
        SuccessResponse response = new SuccessResponse();
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            if (request == null) {
                LOGGER.info("Inside addIssuer, adding: " + request.toString());
                throw new HttpMessageNotReadableException("Missing field");
            }

           try {
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
                OrderEntity order = orderMapping.ModelToEntity(request);
//                DiscountProgramEntity discountProgram = discountProgramService.findByDiscountId(request.getId());

                order.setUserOrder(user);
                CartEntity cart = cartService.getCartByUid(user);

               if (request.getCartItem().length==0)
               {
                   response.setStatus(HttpStatus.NO_CONTENT.value());
                   response.setSuccess(false);
                   response.setMessage("Vui lòng chọn hàng để thanh toán");
               }else{
                   processCartItem(request,cart,user);
               }
                order.setCartOrder(cart);
                List<AddressEntity> list = user.getAddress();

                for (AddressEntity address1 : list) {
                    if (order.getAddressOrder().getId() == address1.getId()) {

                        VoucherEntity voucher = voucherService.findById(request.getVoucher());

                        double totalOrder = PayMoney(cart,order.getShipOrder(),voucher,request);

                        cart.setTotal(totalOrder-order.getShipOrder().getShipPrice());
                        order.setTotal(totalOrder);
                        if (order.getPaymentOrder().getPaymentName().equals("paypal"))
                        {
                            try {
                                orderService.save(order);
                            }
                            catch (Exception e)
                            {
                                response.setStatus(HttpStatus.CONFLICT.value());
                                response.setSuccess(false);
                                response.setMessage("Product out of iventory");
                                response.getData().put("Message",e.getMessage());
                                return new ResponseEntity<>(response,HttpStatus.CONFLICT);

                            }
                            String link = paypalPayment(order);
                            if (link!=null)
                            {
                                response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                                response.setSuccess(true);
                                response.setMessage("Vui lòng bấm link để hoàn thành thanh toán");
                                response.getData().put("linkPayment",link);
                                return new ResponseEntity<SuccessResponse>(response, HttpStatus.TEMPORARY_REDIRECT);
                            }
                        }
                        try {
                            orderService.save(order);
                        }
                        catch (Exception e)
                        {
                            response.setStatus(HttpStatus.CONFLICT.value());
                            response.setSuccess(false);
                            response.setMessage("Product out of iventory");
                            response.getData().put("Message",e.getMessage());
                            return new ResponseEntity<>(response,HttpStatus.CONFLICT);

                        }
                        response.setStatus(HttpStatus.OK.value());
                        response.setSuccess(true);
                        response.setMessage("Add order success");
                        response.getData().put("Order", order);
                        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
                    }
                }
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Address Not Found");
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.NOT_FOUND);
            }catch (Exception e){
                throw new Exception(e.getMessage() + "\nAdd Order fail");
            }
        }
        else throw new BadCredentialsException("access token is missing");
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> getOrderByUser(HttpServletRequest httpServletRequest) throws Exception {
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            try {
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
                if (user == null) {
                    return SendErrorValid("User", "Người dùng không hợp lệ", "Lỗi!!!!!");
                }
                List<OrderEntity> listOrder = user.getOrder();
                if(listOrder.isEmpty()){
                    return SendErrorValid("Order", "Người dùng chưa có đơn hàng", "Thông báo");
                }
                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Thông tin đơn hàng");
                response.setSuccess(true);
                response.getData().put("orderList", listOrder);
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
            }catch (Exception e){
                throw new Exception(e.getMessage() + "\n Get Order Fail");
            }
        }
        else
        {
            throw new BadCredentialsException("access token is missing!!!");
        }
    }
    @GetMapping("/pay/success/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> paypalSuccess(@PathVariable("id") int id,@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId)
    {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                SuccessResponse response = new SuccessResponse();
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Thanh toán thành công");
                response.getData().put("Order",orderService.findById(id));
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        orderService.delete(id);
        SuccessResponse response = new SuccessResponse();
        response.setSuccess(false);
        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
        response.setMessage("Thanh toán thất bại");
        return new ResponseEntity<>(response,HttpStatus.FAILED_DEPENDENCY);
    }

    @GetMapping("/pay/cancel/{id}")
    public ResponseEntity<SuccessResponse> paypalCancel(@PathVariable("id") int id) {
        orderService.delete(id);
        SuccessResponse response = new SuccessResponse();
        response.setSuccess(false);
        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
        response.setMessage("Thanh toán thất bại (cancel)");
        return new ResponseEntity<>(response,HttpStatus.FAILED_DEPENDENCY);
    }
    private ResponseEntity SendErrorValid(String field, String message,String title){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        Map<String,String> temp =new HashMap<>();
        errorResponseMap.setMessage(title);
        temp.put(field,message);
        errorResponseMap.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseMap.setDetails(temp);
        return ResponseEntity
                .badRequest()
                .body(errorResponseMap);
    }

    private double PayMoney(CartEntity cart,ShipEntity ship, VoucherEntity voucher,AddOrderRequest request) {
        double totalOrderProduct = PriceProduct(cart,request);

        double totalOrder = 0.0;
        double voucherValue=0.0;


        Date today = new Date();

        if(voucher==null){
            voucherValue = 0.0;
        }
        else if(voucher.getFromDate().compareTo(today)<0 && voucher.getToDate().compareTo(today)>0){
            if (voucher.getValue().contains("%")) {
                double value = Double.parseDouble(voucher.getValue().substring(0,voucher.getValue().length()-1));
                voucherValue = totalOrderProduct * value / 100;
            }else {
                voucherValue = Double.parseDouble(voucher.getValue());
            }
        }
        totalOrder = totalOrderProduct + ship.getShipPrice() - voucherValue;
        return totalOrder;
    }

    private void processCartItem(AddOrderRequest request, CartEntity cart, UserEntity user){
        List<CartItemEntity> listPick = new ArrayList<CartItemEntity>();
        List<CartItemEntity> listLeft = new ArrayList<CartItemEntity>();


        for (int i : request.getCartItem())
        {
            CartItemEntity cartItem =cartService.getItemByIdAndCart(i,cart);
            listPick.add(cartItem);
            cart.getCartItem().remove(cartItem);
        }

        listLeft = cart.getCartItem();
        cart.setCartItem(listPick);
        cart.setStatus(false);
        CartEntity newCart = new CartEntity(0.0,true,user);
        newCart.setCartItem(listLeft);
        for(CartItemEntity cartItem : listLeft)
        {
            cartItem.setCart(newCart);
//            cartService.saveCartItem(cartItem);
        }
        cartService.saveCart(newCart);
    }
    private String paypalPayment(OrderEntity order)
    {
        try {
            orderService.save(order);
            Payment paypalPayment = paypalService.createPayment(order, "USD", "paypal",
                    "sale", "https://nhom3-tiki.herokuapp.com" + CANCEL_URL+"/"+order.getOrderId(),
                    "https://nhom3-tiki.herokuapp.com" + SUCCESS_URL +"/"+order.getOrderId());
            for (Links link : paypalPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }
        }
        catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return null;
    }
    private double PriceProduct(CartEntity cart,AddOrderRequest request){
        double totalOrderProduct = 0.0;
        Date today = new Date();

        for (CartItemEntity item : cart.getCartItem()) {
            DiscountProgramEntity discountProgram = discountProgramService.findByIdAndProductBrand(request.getDiscountId(), item.getProduct().getProductBrand());
            if(request.getDiscountId()==null){
                totalOrderProduct += item.getProduct().getPrice() * item.getQuantity();
                item.getProduct().setInventory(item.getProduct().getInventory()-item.getQuantity());
                item.getProduct().setSellAmount(item.getQuantity()+ item.getProduct().getSellAmount());
            }else if (discountProgram != null && discountProgram.getFromDate().compareTo(today) < 0 && discountProgram.getToDate().compareTo(today) > 0) {
                double discount = item.getProduct().getPrice() * discountProgram.getPercent() / 100;
                totalOrderProduct += (item.getProduct().getPrice() - discount) * item.getQuantity();
                item.getProduct().setInventory(item.getProduct().getInventory() - item.getQuantity());
                item.getProduct().setSellAmount(item.getQuantity() + item.getProduct().getSellAmount());
            }
        }
        return totalOrderProduct;
    }
}
