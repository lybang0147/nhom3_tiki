package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.payload.response.ProductResponse;
import com.hcmute.starter.repository.AttributeOptionRepository;
import com.hcmute.starter.repository.ImageProductRepository;
import com.hcmute.starter.repository.ProductRepository;
import com.hcmute.starter.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AttributeOptionRepository attributeOptionRepository;
    private final ImageProductRepository imageProductRepository;
    @Override
    public List<ProductEntity> findAllProduct(){
        List<ProductEntity> productEntityList = productRepository.findAll();
        return productEntityList;
    }
    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity findById(UUID id){
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if(productEntity.isEmpty()){
            return null;
        }
        return productEntity.get();
    }
    @Override
    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }
    @Override
    public void saveListImageProduct(List<String> listUrl, ProductEntity product){
        for (String url : listUrl){
            ImageProductEntity imageProductEntity = new ImageProductEntity();
            imageProductEntity.setUrl(url);
            imageProductEntity.setProduct(product);
            imageProductRepository.save(imageProductEntity);
        }
    }
    @Override
    public void deleteListImgProduct(ProductEntity product){
        List<ImageProductEntity> imageProductEntityList = imageProductRepository.findByProduct(product);
        for (ImageProductEntity imageProductEntity : imageProductEntityList){
            imageProductRepository.delete(imageProductEntity);
        }
    }

    @Override
    public void addAttribute(ProductEntity product, String attributeOptionId){
        Optional<AttributeOptionEntity> attribute = attributeOptionRepository.findById(attributeOptionId);
        product.getAttributeOptionEntities().add(attribute.get());
        productRepository.save(product);
    }
    @Override
    public void deleteAttribute(ProductEntity product, String attributeId){
        Optional<AttributeOptionEntity> attribute = attributeOptionRepository.findById(attributeId);
        product.getAttributeOptionEntities().remove(attribute.get());
        productRepository.save(product);
    }

    @Override
    public ProductResponse productResponse(ProductEntity product){
        List<ImageProductEntity> imageProductEntityList = imageProductRepository.findByProduct(product);
        List<String> list = new ArrayList<>();
        for (ImageProductEntity imageProductEntity : imageProductEntityList){
            list.add(imageProductEntity.getUrl());
        }
        Set<AttributeOptionEntity> attributeEntitySet = product.getAttributeOptionEntities();
        return new ProductResponse(
                product.getId(),
                product.getImageProductEntityList().get(0).getUrl(),
                product.getName(),
                product.getDescription(),
                product.getProductCategory().getName(),
                4,
                product.getPrice(),
                0,
                product.getSellAmount(),
                product.getProductBrand().getName(),
                product.getProductBrand().getBrandCountry().getName(),
                list,
                attributeEntitySet);
    }
    @Override
    public List<ProductEntity> findPaginated(int pageNo, int pageSize, String sort) {
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pagedResult = productRepository.findAllProduct(paging);
        return pagedResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByCategory(CategoryEntity category, int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        List<UUID> listCategory = new ArrayList<>();
        getAllCategory(category,listCategory);
        Page<ProductEntity> pagedResult = productRepository.findByCategory(listCategory,paging);
        return pagedResult.toList();
    }
    public void getAllCategory(CategoryEntity category, List<UUID> categoryEntities){
        if(category.getCategoryEntities() != null){
            categoryEntities.add(category.getId());
            for (CategoryEntity categoryEntity : category.getCategoryEntities())
                getAllCategory(categoryEntity,categoryEntities);
        }else
            categoryEntities.add(category.getId());
    }
    @Override
    public List<ProductEntity> findProductByBrand(BrandEntity brand, int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pagedResult = productRepository.findByBrand(brand.getId(),paging);
        return pagedResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByAttributes(CategoryEntity category, List<String> listAttribute,int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        List<UUID> listCategory = new ArrayList<>();
        getAllCategory(category,listCategory);
        Page<ProductEntity> pageResult = productRepository.findByAttributes(listCategory,listAttribute,paging);
        return pageResult.toList();
    }
    @Override
    public List<ProductEntity> findProductByKeyword(String keyword,int pageNo, int pageSize, String sort){
        Pageable paging = null;
        switch (sort){
            case "product_price_up" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").descending());break;
            case "product_price_down" : paging = PageRequest.of(pageNo, pageSize, Sort.by("product_price").ascending());break;
            default : paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        }
        Page<ProductEntity> pageResult = productRepository.findByKeyword(keyword.toLowerCase(),paging);
        return pageResult.toList();
    }
    @Override
    public List<ProductEntity> searchByBrand(BrandEntity brand){
        return brand.getListProduct();
    }
    @Override
    public long countProduct() {
        return productRepository.count();
    }

}
