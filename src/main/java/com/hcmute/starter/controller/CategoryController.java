package com.hcmute.starter.controller;

import com.hcmute.starter.model.entity.AttributeEntity;
import com.hcmute.starter.model.entity.CategoryEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.SuccessResponse;
import com.hcmute.starter.model.payload.response.Category.CategoryResponse;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final AttributeService attributeService;
    private final UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @GetMapping("/category/root")
    public List<Map<String,Object>> getCategoryRoot(){
        List<CategoryEntity> list = categoryService.findCategoryRoot();
        List<Map<String,Object>> listCategory = new ArrayList<>();
        for (CategoryEntity categoryEntity : list){
            Map<String,Object> response = new HashMap<>();
            response.put("id",categoryEntity.getId());
            response.put("slug","bach-hoa-online");
            response.put("name",categoryEntity.getName());
            Map<String, Object> rangePrice = new HashMap<>();
            rangePrice.put("min",0);
            rangePrice.put("max",1000000000);
            rangePrice.put("slug","price");
            List<Map<String, Object>> values = new ArrayList<>();
            Map<String, Object> value = new HashMap<>();
            value.put("display_value","Dưới 40.000");
            value.put("value","0,40000");
            values.add(value);
            response.put("rangePrice",rangePrice);
            response.put("values",values);
            response.put("properties", new ArrayList<>());
            listCategory.add(response);
        }
        return listCategory;
    }
    @GetMapping("/category/{id}")
    public List<Map<String,Object>> getCategoryById(@PathVariable UUID id){
        CategoryEntity category = categoryService.findById(id);
        List<Map<String,Object>> listCategory = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        response.put("id",category.getId());
        response.put("slug","bach-hoa-online");
        response.put("name",category.getName());
        Map<String, Object> rangePrice = new HashMap<>();
        rangePrice.put("min",0);
        rangePrice.put("max",1000000000);
        rangePrice.put("slug","price");
        List<Map<String, Object>> values = new ArrayList<>();
        Map<String, Object> value = new HashMap<>();
        value.put("display_value","Dưới 40.000");
        value.put("value","0,40000");
        values.add(value);
        response.put("rangePrice",rangePrice);
        response.put("values",values);
        response.put("properties", new ArrayList<>());
        listCategory.add(response);
        return listCategory;
    }
    @GetMapping("/category/child")
    public List<Map<String,Object>> getCategoryChild(@RequestParam UUID parentId){
        CategoryEntity category = categoryService.findById(parentId);
        List<CategoryEntity> list = categoryService.findCategoryChild(category);
        List<Map<String,Object>> listCategory = new ArrayList<>();
        for (CategoryEntity categoryEntity : list){
            Map<String,Object> response = new HashMap<>();
            response.put("id",categoryEntity.getId());
            response.put("slug","bach-hoa-online");
            response.put("name",categoryEntity.getName());
            Map<String, Object> rangePrice = new HashMap<>();
            rangePrice.put("min",0);
            rangePrice.put("max",1000000000);
            rangePrice.put("slug","price");
            List<Map<String, Object>> values = new ArrayList<>();
            Map<String, Object> value = new HashMap<>();
            value.put("display_value","Dưới 40.000");
            value.put("value","0,40000");
            values.add(value);
            response.put("rangePrice",rangePrice);
            response.put("values",values);
            response.put("properties", new ArrayList<>());
            listCategory.add(response);
        }
        return listCategory;
    }
    @GetMapping("admin/category/all")
    private ResponseEntity<SuccessResponse> showAllCategory(HttpServletRequest req){
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
                List<CategoryEntity> listCategory = categoryService.findAllCategory();
                if(listCategory.size() == 0)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"List Category is Empty",null ), HttpStatus.FOUND);
                List<CategoryResponse> categoryResponseList = new ArrayList<>();
                for(CategoryEntity category : listCategory)
                    if (category.getParent() == null)
                        categoryResponseList.add(new CategoryResponse(category.getId(), category.getName(), null));
                    else
                        categoryResponseList.add(new CategoryResponse(category.getId(), category.getName(), category.getParent().getId()));
                Map<String, Object> data = new HashMap<>();
                data.put("listCategory",categoryResponseList);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(),"Query Successfully",data), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("admin/category/insert")
    private ResponseEntity<SuccessResponse> insertCategory(HttpServletRequest req,@RequestBody CategoryEntity newCategory,@RequestParam(defaultValue = "00000000-0000-0000-0000-000000000000")UUID parentId){
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
                List<CategoryEntity> foundCategory = categoryService.foundCategory(newCategory);
                if(foundCategory.size() > 0)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"Category found",null), HttpStatus.FOUND);
                if(parentId.compareTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))==0){
                    categoryService.saveCategory(newCategory);
                    return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Add Category Successfully",null), HttpStatus.OK);
                }
                if(categoryService.findById(parentId) == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"Category parent not found",null), HttpStatus.NOT_FOUND);
                newCategory.setParent(categoryService.findById(parentId));
                categoryService.saveCategory(newCategory);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Add Category Successfully",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/admin/category/update/{id}")
    private ResponseEntity<SuccessResponse> updateCategory(HttpServletRequest req,@PathVariable UUID id, @RequestBody CategoryEntity newCategory, @RequestParam(defaultValue = "00000000-0000-0000-0000-000000000000")UUID parentId) {
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
                CategoryEntity category = categoryService.findById(id);
                SuccessResponse response=new SuccessResponse();
                if(category == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(), "Category not found",null), HttpStatus.NOT_FOUND);
                if(parentId.compareTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))==0){
                    category.setName(newCategory.getName());
                    category.setParent(null);
                    categoryService.saveCategory(category);
                    return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Update Category Successfully", null), HttpStatus.OK);
                }
                if(categoryService.findById(parentId) == null)
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_FOUND.value(),"Category parent not found",null), HttpStatus.FOUND);
                if(!checkCategoryList(category.getCategoryEntities(),parentId))
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"The new category is a child of the old category",null), HttpStatus.FOUND);
                category.setName(newCategory.getName());
                category.setParent(categoryService.findById(parentId));
                categoryService.saveCategory(category);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Update Category Successfully", null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("admin/category/{id}")
    public ResponseEntity<SuccessResponse> deleteCategory(HttpServletRequest req,@PathVariable UUID id){
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
                CategoryEntity category = categoryService.findById(id);
                if (category == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Category not found", null), HttpStatus.NOT_FOUND);
                try {
                    categoryService.deleteCategory(id);
                } catch (Exception e) {
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.NOT_ACCEPTABLE.value(),"List brand or product in Category not Empty",null ), HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Delete category successfully",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    public static boolean checkCategoryEntity(CategoryEntity category, UUID id){
        if(category.getId().compareTo(id)==0)
            return false;
        else if(category.getCategoryEntities()!=null)
            return checkCategoryList(category.getCategoryEntities(),id);
        return true;
    }
    public static boolean checkCategoryList(Collection<CategoryEntity> categoryEntities, UUID id){
        for(CategoryEntity category : categoryEntities)
            if(!checkCategoryEntity(category,id))
                return false;
        return true;
    }
    @PostMapping("admin/category/insert/attribute/{id}")
    public ResponseEntity<SuccessResponse> insertAttribute(HttpServletRequest req,@PathVariable UUID id, @RequestBody List<String> listAttributeId){
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
                CategoryEntity category = categoryService.findById(id);
                if(category == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Category not found", null), HttpStatus.NOT_FOUND);
                Set<AttributeEntity> attributeEntityList = new HashSet<>();
                for(String attributeId : listAttributeId){
                    AttributeEntity attribute = attributeService.findById(attributeId);
                    if(attribute == null)
                        return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Attribute "+ attributeId +" not found", null), HttpStatus.NOT_FOUND);
                    attributeEntityList.add(attribute);
                }
                category.setAttributeEntities(attributeEntityList);
                categoryService.saveCategory(category);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(), "Add List Attribute Successfully",null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("admin/category/attribute/all/{id}")
    public ResponseEntity<SuccessResponse> getAllAttribute(HttpServletRequest req,@PathVariable UUID id){
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
                CategoryEntity category = categoryService.findById(id);
                if(category == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Category not found", null), HttpStatus.NOT_FOUND);
                List<AttributeEntity> attributeEntityList = new ArrayList<>();
                for (AttributeEntity attributeEntity : category.getAttributeEntities())
                    attributeEntityList.add(attributeEntity);
                if (attributeEntityList.isEmpty())
                    return new ResponseEntity<>(new SuccessResponse(false,HttpStatus.FOUND.value(),"List Attribute is Empty",null ), HttpStatus.FOUND);
                Map<String, Object> data = new HashMap<>();
                data.put("listAttribute",attributeEntityList);
                return new ResponseEntity<>(new SuccessResponse(true,HttpStatus.OK.value(), "Query Successfully",data), HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping("admin/category/delete/attribute/{id}")
    public ResponseEntity<SuccessResponse> deleteAttribute(HttpServletRequest req,@PathVariable UUID id, @RequestBody List<String> listAttributeId){
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
                CategoryEntity category = categoryService.findById(id);
                if(category == null)
                    return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Category not found", null), HttpStatus.NOT_FOUND);
                List<AttributeEntity> attributeEntityList = new ArrayList<>();
                for(String attributeId : listAttributeId){
                    AttributeEntity attribute = attributeService.findById(attributeId);
                    if(attribute == null)
                        return new ResponseEntity<>(new SuccessResponse(false, HttpStatus.NOT_FOUND.value(), "Attribute "+ attributeId +" not found", null), HttpStatus.NOT_FOUND);
                    attributeEntityList.add(attribute);
                }
                for (AttributeEntity attribute : attributeEntityList)
                    if(category.getAttributeEntities().contains(attribute))
                        category.getAttributeEntities().remove(attribute);
                return new ResponseEntity<>(new SuccessResponse(true, HttpStatus.OK.value(),"Delete List Attribute Successfully",null), HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
