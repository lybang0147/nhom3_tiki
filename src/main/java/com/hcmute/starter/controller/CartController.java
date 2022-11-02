package com.hcmute.starter.controller;

import com.hcmute.starter.handler.MethodArgumentNotValidException;
import com.hcmute.starter.model.entity.CartEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.CartRequest.AddItemCartRequest;
import com.hcmute.starter.model.payload.request.CartRequest.SubtractItemRequest;
import com.hcmute.starter.model.payload.response.CartItem.CartItemResponse;
import com.hcmute.starter.repository.CartItemRepository;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.CartService;
import com.hcmute.starter.service.ProductService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    private static final Logger LOGGER = LogManager.getLogger(CartController.class);

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getUserCart(HttpServletRequest request) throws Error {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            SuccessResponse response = new SuccessResponse();
            if (user == null) {
                response.setMessage("Can't find user with id provided");
                response.setSuccess(false);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            CartEntity cart = cartService.getCartByUid(user);
            if (cart == null) {
                CartEntity newCart = new CartEntity((double) 0, true, user);
                cartService.saveCart(newCart);
                response.setMessage("You have nothing in your cart (Add cart)");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (cart.getCartItem().isEmpty()) {
                response.setMessage("You have nothing in your cart");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Your Cart");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                List<CartItemEntity> cartItemEntityList = cart.getCartItem();
                List<CartItemResponse> cartItemResponseList = new ArrayList<>();
                for (CartItemEntity cartItem : cartItemEntityList){
                    cartItemResponseList.add(cartService.cartItemResponse(cartItem));
                }
                response.getData().put("Cart",cartItemResponseList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            throw new BadCredentialsException("access token is missing");
        }
    }

    @PutMapping("")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SuccessResponse> addItemToCart(@RequestBody @Valid AddItemCartRequest addItemCartRequest, HttpServletRequest request, BindingResult errors) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            try {
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
                SuccessResponse response = new SuccessResponse();
                CartEntity cart = cartService.getCartByUid(user);
                ProductEntity product = productService.findById(UUID.fromString(addItemCartRequest.getProductId()));
                if (product == null) {
                    response.setMessage("Can't find product with id provided");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                CartItemEntity cartItem = cartService.getCartItemByPidAndCid(product, cart);
                try {
                    if (cartItem == null) {
                        if (addItemCartRequest.getQuantity() <= product.getInventory()) {
                            cartItem = new CartItemEntity(cart, product, addItemCartRequest.getQuantity());
                            cart.getCartItem().add(cartItem);
                            cartService.saveCart(cart);
                            response.setSuccess(true);
                            response.setStatus(HttpStatus.OK.value());
                            response.setMessage("Add item to cart success");
                            response.getData().put("Item", cartItem.getProduct().getName() + " " + cartItem.getQuantity());
                            return new ResponseEntity<>(response, HttpStatus.OK);
                        }
                    } else {
                        if (cartItem.getQuantity() + addItemCartRequest.getQuantity() <= product.getInventory()) {
                            cartItem.setQuantity(cartItem.getQuantity() + addItemCartRequest.getQuantity());
                            cartService.saveCartItem(cartItem);
                            response.setSuccess(true);
                            response.setStatus(HttpStatus.OK.value());
                            response.setMessage("Increase item amount success");
                            response.getData().put("Item", cartItem.getProduct().getName() + " " + cartItem.getQuantity());
                            return new ResponseEntity<>(response, HttpStatus.OK);
                        }
                    }
                }
                catch (Exception e)
                {
                    response.setStatus(HttpStatus.CONFLICT.value());
                    response.setSuccess(false);
                    response.setMessage("Item duplicate");
                    response.getData().put("Message",e.getMessage());
                    return new ResponseEntity<>(response,HttpStatus.CONFLICT);
                }
                response.setMessage("Invalid amount");
                response.setSuccess(false);
                response.setStatus(HttpStatus.CONFLICT.value());
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
        }
        else {
            throw new BadCredentialsException("access token is missing");
        }
    }
    @PutMapping("/sub")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<SuccessResponse> subItem(@RequestBody @Valid SubtractItemRequest subtractItemRequest,HttpServletRequest request, BindingResult errors) throws Exception
    {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (errors.hasErrors()) {
            throw new MethodArgumentNotValidException(errors);
        }
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            try {
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
                SuccessResponse response = new SuccessResponse();
                CartEntity cart = cartService.getCartByUid(user);
                CartItemEntity cartItem = cartService.getItemByIdAndCart(subtractItemRequest.getId(), cart);
                if (cartItem == null) {
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setMessage("Can't find cart item");
                    return new ResponseEntity<SuccessResponse>(response, HttpStatus.NOT_FOUND);
                }
                if (subtractItemRequest.getQuantity() >= cartItem.getQuantity()) {
                    cartService.deleteCartItem(cartItem.getId());
                    response.setSuccess(true);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Item has been deleted");
                    return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() - subtractItemRequest.getQuantity());
                    cartService.saveCartItem(cartItem);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Decrease item amount success");
                    response.getData().put("newQuantity", cartItem.getQuantity());
                    return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
                }
            } catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
        }
        else
        {
            throw new BadCredentialsException("Access token is missing!");
        }
    }
}
