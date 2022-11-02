package com.hcmute.starter.controller;
import com.hcmute.starter.mapping.BrandMapping;
import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.userAddress.CommuneEntity;
import com.hcmute.starter.model.entity.userAddress.DistrictEntity;
import com.hcmute.starter.model.entity.userAddress.ProvinceEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.request.Brand.AddNewBrandRequest;
import com.hcmute.starter.model.payload.request.Brand.UpdateBrandRequest;
import com.hcmute.starter.model.payload.response.Brand.BrandResponse;
import com.hcmute.starter.security.JWT.JwtUtils;
import com.hcmute.starter.service.AddressService;
import com.hcmute.starter.service.BrandService;
import com.hcmute.starter.service.ImageStorageService;
import com.hcmute.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final ImageStorageService imageStorageService;
    private final AddressService addressService;
    @Autowired
    JwtUtils jwtUtils;
    private final UserService userService;
    @GetMapping("brand/{id}")
    public ResponseEntity<SuccessResponse> getBrandByID(@PathVariable UUID id){
        BrandEntity brand = brandService.findById(id);
        if(brand == null)
            return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"Brand Not found", null), HttpStatus.NOT_FOUND);
        Map<String, Object> data = new HashMap<>();
        data.put("Brand", brandService.brandResponse(brand));
        return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Query Brand Successfully",data), HttpStatus.OK);
    }
    //CRUD Brand Here
    @GetMapping("/admin/brand/all")
    public ResponseEntity<SuccessResponse> getAllBrand(HttpServletRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){
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
                List<BrandEntity> brandEntityList = brandService.findAll(page,size);
                if(brandEntityList.size() == 0)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.FOUND.value(),"List Brand is Empty",null), HttpStatus.NOT_FOUND);
                List<BrandResponse> brandResponseList = new ArrayList<>();
                for (BrandEntity brand : brandEntityList)
                    brandResponseList.add(brandService.brandResponse(brand));
                Map<String,Object> data = new HashMap<>();
                data.put("listBrand", brandResponseList);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Query Brand Successfully",data), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/admin/brand/{id}")
    public ResponseEntity<SuccessResponse> getBrandByID(HttpServletRequest request,
                                                        @PathVariable UUID id){
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
                BrandEntity brand = brandService.findById(id);
                if(brand == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"Brand Not found", null), HttpStatus.NOT_FOUND);
                Map<String, Object> data = new HashMap<>();
                data.put("Brand", brandService.brandResponse(brand));
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Query Brand Successfully",data), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/admin/brand/update/{id}")
    public ResponseEntity<SuccessResponse> updateBrand(HttpServletRequest request,
                                                       @PathVariable UUID id,
                                                       @RequestBody UpdateBrandRequest updateBrandRequest){
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
                CountryEntity countryEntity = addressService.findByCountryId(updateBrandRequest.getCountry());
                ProvinceEntity provinceEntity = addressService.findByPid(updateBrandRequest.getProvince());
                DistrictEntity districtEntity = addressService.findByDId(updateBrandRequest.getDistrict());
                CommuneEntity communeEntity = addressService.findByCid(updateBrandRequest.getCommune());
                BrandEntity brand = brandService.findById(id);
                if(brand == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"Brand Not found", null), HttpStatus.NOT_FOUND);
                brand = BrandMapping.addBrandToEntity(brand,updateBrandRequest,countryEntity,communeEntity,districtEntity,provinceEntity);
                brandService.saveBrand(brand);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Add Brand Successfully",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/admin/brand/uploadLogo")
    public ResponseEntity<SuccessResponse> uploadImgLogo(HttpServletRequest request,
                                                         @RequestPart MultipartFile file) throws Exception{
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
                if(!imageStorageService.isImageFile(file))
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"The file is not an image",null), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                UUID uuid = UUID.randomUUID();
                String url = imageStorageService.saveLogo(file, String.valueOf(uuid));
                if(url.equals(""))
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"Upload Logo Failure",null), HttpStatus.NOT_FOUND);
                Map<String, Object> data = new HashMap<>();
                data.put("url",url);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Upload Logo Successfully",data), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping(value = "/admin/brand/insert")
    public ResponseEntity<SuccessResponse> insertBrandNew(HttpServletRequest request,
                                                          @RequestBody AddNewBrandRequest addNewBrandRequest){
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
                CountryEntity countryEntity = addressService.findByCountryId(addNewBrandRequest.getCountry());
                ProvinceEntity provinceEntity = addressService.findByPid(addNewBrandRequest.getProvince());
                DistrictEntity districtEntity = addressService.findByDId(addNewBrandRequest.getDistrict());
                CommuneEntity communeEntity = addressService.findByCid(addNewBrandRequest.getCommune());
                BrandEntity brand = BrandMapping.addBrandToEntity(addNewBrandRequest, countryEntity, communeEntity,districtEntity,provinceEntity);
                brandService.saveBrand(brand);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Insert Brand Successfully",null ),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("/admin/brand/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteBrandById(HttpServletRequest request,
                                                           @PathVariable UUID id){
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
                BrandEntity brand = brandService.findById(id);
                if(brand == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(),"Brand Not found",null ), HttpStatus.NOT_FOUND);
                try {
                    brandService.deleteBrand(id);
                } catch (Exception e) {
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_ACCEPTABLE.value(),"List product in Brand not Empty", null), HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Brand is deleted",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
