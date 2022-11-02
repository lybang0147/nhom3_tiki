package com.hcmute.starter.controller;

import com.hcmute.starter.handler.HttpMessageNotReadableException;
import com.hcmute.starter.handler.MethodArgumentNotValidException;
import com.hcmute.starter.mapping.AddressMapping;
import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.userAddress.*;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Address.AddNewAddressRequest;
import com.hcmute.starter.model.payload.request.Address.InfoAddressRequest;
import com.hcmute.starter.model.payload.response.ErrorResponseMap;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.AddressService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    private static final Logger LOGGER = LogManager.getLogger(AddressController.class);
    @PutMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> saveAddress(@RequestBody @Valid AddNewAddressRequest request, BindingResult errors, HttpServletRequest httpServletRequest) throws Exception
    {
        if (errors.hasErrors())
        {
            throw new MethodArgumentNotValidException(errors);
        }
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            if (request == null) {
                LOGGER.info("Inside addIssuer, adding: " + request.toString());
                throw new HttpMessageNotReadableException("Missing field");
            }
            try {
                SuccessResponse response = new SuccessResponse();
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));
                ProvinceEntity provinceEntity = addressService.findByPid(request.getProvince());
                DistrictEntity districtEntity = addressService.findByDId(request.getDistrict());
                CommuneEntity communeEntity = addressService.findByCid(request.getCommune());
                AddressTypeEntity addressType = addressService.findByTid(request.getAddressType());
                AddressEntity address = AddressMapping.ModelToEntity(request,provinceEntity,districtEntity,communeEntity,addressType);
                if (address==null)
                {
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    response.setSuccess(true);
                    response.setMessage("Add address failure");
                    return new ResponseEntity<SuccessResponse>(response, HttpStatus.NOT_FOUND);
                }
                address.setUser(user);
                try {
                    addressService.saveAddress(address);
                }
                catch (Exception e)
                {
                    response.setStatus(HttpStatus.CONFLICT.value());
                    response.setSuccess(false);
                    response.setMessage("Address limit reach");
                    response.getData().put("Message",e.getMessage());
                    return new ResponseEntity<>(response,HttpStatus.CONFLICT);
                }
                response.setStatus(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("Add address success");
                response.getData().put("name", address.getFullName());
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception(e.getMessage() + "\nAdd address fail");
            }
        }
        else throw new BadCredentialsException("access token is missing");

    }
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getUserAddress(HttpServletRequest httpServletRequest) throws Exception{
        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());
            if (jwtUtils.validateExpiredToken(accessToken) == true) {
                throw new BadCredentialsException("access token is  expired");
            }
            try {
                UserEntity user = userService.findById(UUID.fromString(jwtUtils.getUserNameFromJwtToken(accessToken)));

                if (user == null) {
                    return SendErrorValid("User", "User not valid", "User not found");
                }
                List<AddressEntity> list = user.getAddress();
                if (list.isEmpty()) {
                    return SendErrorValid("Address", "User not have address", "List Empty");
                }
                SuccessResponse response = new SuccessResponse();
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Address Info:");
                response.setSuccess(true);
                response.getData().put("addressList", list);
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
            }
            catch (Exception e)
            {
                throw new Exception(e.getMessage() + "\n Get Address Fail");
            }
        }

        else
        {
            throw new BadCredentialsException("access token is missing!!!");
        }

    }
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> updateUserAddress(@RequestBody @Valid InfoAddressRequest request,@PathVariable("id") String id,BindingResult errors) throws Exception
    {

        if (request == null) {
            LOGGER.info("Inside addIssuer, adding: " + request.toString());
            throw new HttpMessageNotReadableException("Missing field");
        }
        try {
            SuccessResponse response = new SuccessResponse();
            AddressEntity address = addressService.findById(id);
            ProvinceEntity provinceEntity = addressService.findByPid(request.getProvince());
            DistrictEntity districtEntity = addressService.findByDId(request.getDistrict());
            CommuneEntity communeEntity = addressService.findByCid(request.getCommune());
            AddressTypeEntity addressType = addressService.findByTid(request.getAddressType());
            address = AddressMapping.UpdateAddressEntity(address,request,provinceEntity,districtEntity,communeEntity,addressType);
            if (address==null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(true);
                response.setMessage("Add address failure");
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.NOT_FOUND);
            }
            addressService.saveAddress(address);
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Update address success");
            response.getData().put("name", address.getFullName());
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nAdd address fail");
        }
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> deleteAddress(@PathVariable("id") String id) throws Exception
    {
        try {
            addressService.deleteAddress(id);
            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Delete address success");
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() + "\nDelete address fail");
        }
    }
    @GetMapping("/province")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getAllProvince() throws Exception {

        try
        {
            List<ProvinceEntity> list = addressService.getAllProvince();
            SuccessResponse response = new SuccessResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("list",list);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("/district/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getDistrictInProvince(@PathVariable("id") String id) throws Exception
    {
        try
        {
            List<DistrictEntity> list = addressService.getAllDistrictInProvince(id);
            SuccessResponse response = new SuccessResponse();
            if (list==null)
            {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found District with id=" + id);
                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
            }

            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("provinceId",id);
            response.getData().put("list",list);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("/commune/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getCommuneInDistrict(@PathVariable("id") String id) throws Exception
    {
        try
        {
            List<CommuneEntity> list = addressService.getAllCommuneInDistrict(id);
            SuccessResponse response = new SuccessResponse();
            if (list==null)
            {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found Commune with id=" + id);
                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("communeId",id);
            response.getData().put("list",list);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("/commune/get/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getCommuneById(@PathVariable("id") String id) throws Exception
    {
        try
        {
            CommuneEntity communeEntity = addressService.getCommuneById(id);
            SuccessResponse response = new SuccessResponse();
            if (communeEntity==null)
            {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found Commune with id=" + id);
                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("communeId",id);
            response.getData().put("Commune",communeEntity);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("district/get/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getDistrictById(@PathVariable("id") String id) throws Exception
    {
        DistrictEntity districtEntity = addressService.getDistrictById(id);
        SuccessResponse response = new SuccessResponse();
        try
        {
            if (districtEntity==null)
            {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found District with id=" + id);
                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("DistrictId",id);
            response.getData().put("District",districtEntity);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("province/get/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getProvinceById(@PathVariable("id") String id) throws Exception
    {
        ProvinceEntity provinceEntity = addressService.getProvinceById(id);
        SuccessResponse response = new SuccessResponse();
        try
        {
            if (provinceEntity==null)
            {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found province with id=" + id);
                return new ResponseEntity<SuccessResponse>(response,HttpStatus.NOT_FOUND);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("ProvinceId",id);
            response.getData().put("Province",provinceEntity);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
    }
    @GetMapping("/country/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse> getCountryById(@PathVariable("id") String id) throws Exception
    {
        try {
            CountryEntity country = addressService.findByCountryId(id);
            SuccessResponse response = new SuccessResponse();
            if (country == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setSuccess(false);
                response.setMessage("Can't found country with id=" + id);
                return new ResponseEntity<SuccessResponse>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Get success");
            response.getData().put("CountryId", id);
            response.getData().put("Country", country);
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage() +"\n get failed");
        }
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
}
