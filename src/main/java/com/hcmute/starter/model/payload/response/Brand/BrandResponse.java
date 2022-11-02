package com.hcmute.starter.model.payload.response.Brand;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class BrandResponse {
    private UUID id;
    private String name;
    private String description;
    private String brandCountry;
    private String phone;
    private String brandCommune;
    private String brandDistrict;
    private String brandProvince;
    private String addressDetails;
    private String img;
    private Integer yearCreate;

    public BrandResponse(UUID id, String name, String description, String brandCountry, String phone, String brandCommune, String brandDistrict, String brandProvince, String addressDetails, String img, Integer yearCreate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brandCountry = brandCountry;
        this.phone = phone;
        this.brandCommune = brandCommune;
        this.brandDistrict = brandDistrict;
        this.brandProvince = brandProvince;
        this.addressDetails = addressDetails;
        this.img = img;
        this.yearCreate = yearCreate;
    }
}
