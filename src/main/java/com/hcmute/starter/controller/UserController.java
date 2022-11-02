package com.hcmute.starter.controller;

import com.hcmute.starter.handler.HttpMessageNotReadableException;
import com.hcmute.starter.handler.MethodArgumentNotValidException;
import com.hcmute.starter.mapping.UserMapping;
import com.hcmute.starter.mapping.UserNotificationMapping;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.UserNotificationEntity;
import com.hcmute.starter.model.entity.VoucherEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Authentication.ReActiveRequest;
import com.hcmute.starter.model.payload.request.UserRequest.*;
import com.hcmute.starter.model.payload.response.Notification.NotificationResponse;
import com.hcmute.starter.model.payload.response.ProductResponse;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ImageStorageService imageStorageService;
    private final ProductService productService;
    private final UserNotificationService userNotificationService;
    private final EmailService  emailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    //Get user by id
    //This request is: http://localhost:8080/api/user/{id}
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getUserByID(@PathVariable("id")String id) {
        UserEntity user=userService.findById(UUID.fromString(id));
        SuccessResponse response=new SuccessResponse();
        if(user==null){
            response.setMessage("User not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        else{
            response.setMessage("Get user success");
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.getData().put("user",user);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }

    }
    //This request is: http://localhost:8080/api/user/profile
    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse> getUserProfile(HttpServletRequest request) throws Exception{
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user.getId().toString().length()<1){
                throw new BadCredentialsException("User not found");
            }
            else{
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Get user success");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                response.getData().put("user",user);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


    }
    //register user API
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerAccount(@RequestBody @Valid AddNewUserRequest request) {
        UserEntity user= UserMapping.registerToEntity(request);
        if(userService.existsByPhone(user.getPhone())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        SuccessResponse response=new SuccessResponse();
        try{
            userService.saveUser(user,"USER");
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("add user successful");
            response.setSuccess(true);
            response.getData().put("phone",user.getPhone());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    //upload user's avatar API
    @PostMapping("/profile/uploadAvatar")
    public ResponseEntity<SuccessResponse> uploadAvatar(HttpServletRequest request, @RequestPart MultipartFile file) throws Exception{
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                if(!imageStorageService.isImageFile(file)){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("The file is not an image");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
                    return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
                String url = imageStorageService.saveAvatar(file,user.getPhone());
                if(url.equals("")){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("Upload Avatar Failure");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                user.setImg(url);
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Upload Avatar successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //password change API
    @PutMapping("/profile/changePassword")
    public ResponseEntity<SuccessResponse> changePassword(HttpServletRequest req, @RequestBody @Valid ChangePasswordRequest request) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is  expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                if(!passwordEncoder.matches(request.getOldPassword(),user.getPassword())){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("Old password is incorrect");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                if(!request.getNewPassword().equals(request.getConfirmPassword())){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("New password is not the same as confirm password");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Change password successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //change user info API
    @PutMapping("/profile/changeInfo")
    public ResponseEntity<SuccessResponse> changeInfo(HttpServletRequest req, @RequestBody @Valid ChangeInfoRequest request) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                user.setFullName(request.getFullName());
                user.setGender(request.getGender());
                user.setNickName(request.getNickName());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(request.getBirthDay()+" 00:00", formatter);
                user.setBirth_day(dateTime);
                user.setCountry(countryService.findById(request.getCountry()));
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Change info successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/profile/changeEmail")
    public ResponseEntity<SuccessResponse> changeEmail(HttpServletRequest req, @RequestBody @Valid ChangeEmailRequest request) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                if(userService.findByEmail(request.getEmail())!=null){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("Email is already used");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                user.setEmail(request.getEmail());
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Change email successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/profile/changePhone")
    public ResponseEntity<SuccessResponse> changePhone(HttpServletRequest req, @RequestBody ChangePhoneRequest request) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                if(userService.findByPhone(request.getPhone())!=null ){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("Phone is already used");
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
                }
                user.setPhone(request.getPhone());
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Change phone successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/wishlist")
    public ResponseEntity<SuccessResponse> getWishlist(HttpServletRequest req) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null){
                throw new BadCredentialsException("User not found");
            }
            else{
                List<ProductResponse> productResponseList = new ArrayList<>();
                for(ProductEntity product : user.getFavoriteProducts()){
                    productResponseList.add(productService.productResponse(product));
                }
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Get wishlist successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                response.getData().put("listProduct",productResponseList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    @PostMapping("/wishlist/add")
    public ResponseEntity<SuccessResponse> addToWishList(HttpServletRequest req,@RequestParam UUID productId) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                ProductEntity product = productService.findById(productId);
                if(product == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(), "Product Not Found",null),HttpStatus.NOT_FOUND);
                user.getFavoriteProducts().add(product);
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Add wishlist successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/wishlist/remove")
    public ResponseEntity<SuccessResponse> removeToWishList(HttpServletRequest req,@RequestParam UUID productId) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                ProductEntity product = productService.findById(productId);
                if(product == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(), "Product Not Found",null),HttpStatus.NOT_FOUND);
                user.getFavoriteProducts().remove(product);
                userService.saveInfo(user);
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Remove wishlist successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/wishlist/check")
    public ResponseEntity<SuccessResponse> checkWishList(HttpServletRequest req,@RequestParam UUID productId) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                ProductEntity product = productService.findById(productId);
                if(product == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(), "Product Not Found",null),HttpStatus.NOT_FOUND);
                if(user.getFavoriteProducts().contains(product)){
                    SuccessResponse response=new SuccessResponse();
                    response.setMessage("Query wishlist successfully");
                    response.setSuccess(true);
                    response.setStatus(HttpStatus.OK.value());
                    List<UUID> listProduct = new ArrayList<>();
                    listProduct.add(product.getId());
                    response.getData().put("product",listProduct);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                SuccessResponse response=new SuccessResponse();
                response.setMessage("Query wishlist successfully");
                response.setSuccess(true);
                response.setStatus(HttpStatus.OK.value());
                response.getData().put("product",new ArrayList<>());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/notification")
    public ResponseEntity<SuccessResponse> getNotifications(HttpServletRequest req,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "oder") String type) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                List<UserNotificationEntity> list = userNotificationService.findNotificationByUser(user,page,size,type);
                List<NotificationResponse> notificationResponseList = new ArrayList<>();
                for (UserNotificationEntity userNotificationEntity : list){
                    UserNotificationMapping mapping = new UserNotificationMapping();
                    notificationResponseList.add(mapping.entityToResponse(userNotificationEntity));
                }
                Map<String,Object> data = new HashMap<>();
                data.put("listNotifications",notificationResponseList);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(), "List Notifications",data),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/notification")
    public ResponseEntity<SuccessResponse> deleteNotifications(HttpServletRequest req,@RequestParam int id) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                userNotificationService.delete(id);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(), "Delete successfully",null),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/notification")
    public ResponseEntity<SuccessResponse> updateNotifications(HttpServletRequest req,@RequestParam int id) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                UserNotificationEntity notification = userNotificationService.findNotificationById(id);
                notification.setStatus(0);
                userNotificationService.saveNotification(notification);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(), "Update successfully",null),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/voucher")
    public ResponseEntity<SuccessResponse> getVouchers(HttpServletRequest req,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception{
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if(jwtUtils.validateExpiredToken(accessToken)){
                throw new BadCredentialsException("access token is expired");
            }
            UserEntity user=userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
            if(user==null)
                throw new BadCredentialsException("User not found");
            else{
                Set<VoucherEntity> list = user.getVoucherEntities();
                Map<String,Object> data = new HashMap<>();
                data.put("listVoucher",list);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(), "List Notifications",data),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
