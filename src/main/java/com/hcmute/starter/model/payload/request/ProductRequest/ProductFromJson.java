package com.hcmute.starter.model.payload.request.ProductRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductFromJson {
    private String name;
    private UUID brand;
    private UUID category;
    private String description;
    private Double price;
    private Integer inventory;
    List<String> attribute;
    List<String> imgUrl;
}
