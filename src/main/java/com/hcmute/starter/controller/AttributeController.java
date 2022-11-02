package com.hcmute.starter.controller;

import com.hcmute.starter.mapping.ProductMapping;
import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Attribute.AttributeOptionRequest;
import com.hcmute.starter.model.payload.request.Attribute.AttributeRequest;
import com.hcmute.starter.model.payload.request.Attribute.UpdateAttributeOptionRequest;
import com.hcmute.starter.model.payload.request.Attribute.UpdateAttributeRequest;
import com.hcmute.starter.model.payload.request.ProductRequest.AddAttributeRequest;
import com.hcmute.starter.model.payload.response.Category.CategoryAttributeResponse;
import com.hcmute.starter.model.payload.response.Category.ListCategoryAttributeResponse;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.AttributeService;
import com.hcmute.starter.service.CategoryService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AttributeController {
    private final AttributeService attributeService;
    @Autowired
    JwtUtils jwtUtils;
    private final UserService userService;

    @GetMapping("/attribute/all")
    public ResponseEntity<SuccessResponse> getAllAttribute(){
        List<AttributeEntity> listAttribute = attributeService.findAllAttribute();
        if(listAttribute.size() == 0)
            return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.FOUND.value(), "List Attribute is Empty",null), HttpStatus.FOUND);
        Map<String, Object> data = new HashMap<>();
        data.put("listAttribute", listAttribute);
        return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Query Attribute Successfully", data ), HttpStatus.OK);
    }
    @GetMapping("/attribute/option/all")
    public ResponseEntity<SuccessResponse> getAllAttributeOptions(){
        List<AttributeOptionEntity> listAttribute = attributeService.findAllAttributeOption();
        if(listAttribute.size() == 0)
            return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"List Attribute is Empty",null), HttpStatus.NOT_FOUND);
        Map<String, Object> data = new HashMap<>();
        data.put("listAttribute", listAttribute);
        return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Query Attribute Successfully",data), HttpStatus.OK);
    }
    @PostMapping("/admin/attribute/insert")
    public ResponseEntity<SuccessResponse> insertAttributeFromJson(HttpServletRequest req, @RequestBody List<AttributeRequest> attributeRequests){
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
                SuccessResponse response = new SuccessResponse();
                if (attributeRequests.isEmpty())
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"List Attribute Is Empty",null ), HttpStatus.NOT_FOUND);
                for(AttributeRequest attributeRequest : attributeRequests){
                    AttributeEntity attribute = new AttributeEntity();
                    attribute.setId(attributeRequest.getId());
                    attribute.setName(attributeRequest.getName());
                    attributeService.saveAttribute(attribute);
                }
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Insert Attribute Successfully",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/admin/attribute/option/insert")
    public ResponseEntity<SuccessResponse> insertAttributeOptions(HttpServletRequest req,@RequestBody List<AttributeOptionRequest> attributeOptionRequests){
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
                if(attributeOptionRequests.isEmpty())
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"List Attribute Options is Empty",null ), HttpStatus.NOT_FOUND);
                for(AttributeOptionRequest attributeOptionRequest : attributeOptionRequests){
                    AttributeOptionEntity attributeOption = new AttributeOptionEntity();
                    attributeOption.setId(attributeOptionRequest.getId());
                    attributeOption.setValue(attributeOptionRequest.getValue());
                    attributeOption.setCompareValue(0);
                    AttributeEntity attribute = attributeService.findById(attributeOptionRequest.getIdType());
                    attributeOption.setIdType(attribute);
                    attributeService.saveAttributeOption(attributeOption);
                }
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Insert Attribute Options Successfully", null ), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/admin/attribute/update/{id}")
    public ResponseEntity<SuccessResponse> UpdateAttribute(HttpServletRequest req,@PathVariable String id, @RequestBody UpdateAttributeRequest attributeRequest){
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
                AttributeEntity attribute = attributeService.findById(id);
                if(attribute == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"Attribute not found",null ), HttpStatus.NOT_FOUND);
                attribute.setName(attributeRequest.getName());
                attributeService.saveAttribute(attribute);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Update Attribute Successfully",null ), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/admin/attribute/option/update/{id}")
    public ResponseEntity<SuccessResponse> UpdateAttributeOptions(HttpServletRequest req,@PathVariable String id, @RequestBody UpdateAttributeOptionRequest attributeRequest){
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
                AttributeOptionEntity attributeOption = attributeService.findByIdAttributeOption(id);
                if(attributeOption == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"Attribute Option not found",null ), HttpStatus.NOT_FOUND);
                attributeOption.setValue(attributeRequest.getValue());
                attributeService.saveAttributeOption(attributeOption);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Update Attribute Option Successfully",null ), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/admin/attribute/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteAttributeById(HttpServletRequest req,@PathVariable String id){
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
                AttributeEntity attribute = attributeService.findById(id);
                SuccessResponse response=new SuccessResponse();
                if(attribute == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Attribute Not found",null), HttpStatus.NOT_FOUND);
                try {
                    attributeService.deleteAttribute(id);
                }
                catch(Exception e){
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_ACCEPTABLE.value(), "Attribute is not deleted", null), HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Attribute is deleted", null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/admin/attribute/option/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteAttributeOptionById(HttpServletRequest req,@PathVariable String id){
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
                AttributeOptionEntity attribute = attributeService.findByIdAttributeOption(id);
                if(attribute == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"Attribute option Not found",null ), HttpStatus.NOT_FOUND);
                try {
                    attributeService.deleteAttribute(id);
                }
                catch(Exception e){
                    return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.NOT_ACCEPTABLE.value(), "Attribute option is not deleted", null), HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Attribute option is deleted",null ), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
