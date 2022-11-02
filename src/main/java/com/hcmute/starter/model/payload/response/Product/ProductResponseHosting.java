package com.hcmute.starter.model.payload.response.Product;

import com.hcmute.starter.model.entity.ImageProductEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
@Getter
@Setter
public class ProductResponseHosting {
    private UUID id;
    private String image;
    private String name;
    private int rate;
    private long price;
    private int discount;
    private String slug;
    private int sold;
    private Details details;
    @Data
    @Getter
    @Setter
    public class Details{
        Map<String, Object> category;
        List<String> images;
        List<Map<String,Object>> options;
        List<Map<String,Object>> specifications;
        String description;

        public Details(ProductEntity product) {
            Map<String, Object> category = new HashMap<>();
            category.put("id",product.getProductCategory().getId());
            category.put("name",product.getProductCategory().getName());
            this.category = category;
            List<String> list = new ArrayList<>();
            for(ImageProductEntity imageProductEntity : product.getImageProductEntityList())
                list.add(imageProductEntity.getUrl());
            this.images = list;
            List<Map<String,Object>> options = new ArrayList<>();
            List<Map<String,Object>> specifications = new ArrayList<>();
            Map<String,Object> specification = new HashMap<>();
            specification.put("id","93643b2a7f904b138fb037e384dd3e48");
            specification.put("name","Giao hàng");
            specification.put("value","Toàn quốc");
            specifications.add(specification);
            String description = product.getDescription();
            this.options = options;
            this.specifications = specifications;
            this.description = description;
        }
    }

    public ProductResponseHosting(ProductEntity product,UUID id, String image, String name, int rate, long price, int discount, String slug, int sold) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.rate = rate;
        this.price = price;
        this.discount = discount;
        this.slug = slug;
        this.sold = sold;
        Details details = new Details(product);
        this.details = details;
    }
}
