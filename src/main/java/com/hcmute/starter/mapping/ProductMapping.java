package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.payload.request.AddNewProductRequest;
import com.hcmute.starter.model.payload.request.ProductRequest.ProductFromJson;
import com.hcmute.starter.model.payload.response.ProductResponse;
import com.hcmute.starter.repository.ImageProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductMapping {
    public static ProductEntity addProductToEntity(AddNewProductRequest addNewProductRequest, CategoryEntity category, BrandEntity brand){
        return new ProductEntity(brand,category,addNewProductRequest.getName(),addNewProductRequest.getPrice(),addNewProductRequest.getDescription(),addNewProductRequest.getInventory());
    }
    public  static ProductEntity addJsonProductToEntity(ProductFromJson productFromJson, CategoryEntity category, BrandEntity brand,Set<AttributeOptionEntity> listAttributeOption){
        ProductEntity product = new ProductEntity();
        product.setName(productFromJson.getName());
        product.setProductBrand(brand);
        product.setProductCategory(category);
        product.setPrice(productFromJson.getPrice());
        product.setDescription(productFromJson.getDescription());
        product.setInventory(productFromJson.getInventory());
        product.setCreate(LocalDate.now());
        product.setSellAmount(0);
        List<ImageProductEntity> listImageProduct = new ArrayList<>();
        for(String url : productFromJson.getImgUrl()){
            ImageProductEntity img = new ImageProductEntity();
            img.setUrl(url);
            listImageProduct.add(img);
            img.setProduct(product);
        }
        product.setImageProductEntityList(listImageProduct);
        product.setAttributeOptionEntities(listAttributeOption);
        return product;
    }
}
