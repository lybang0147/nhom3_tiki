package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.model.entity.userAddress.CommuneEntity;
import com.hcmute.starter.model.entity.userAddress.DistrictEntity;
import com.hcmute.starter.model.entity.userAddress.ProvinceEntity;
import com.hcmute.starter.model.payload.request.Brand.AddNewBrandRequest;
import com.hcmute.starter.model.payload.request.Brand.UpdateBrandRequest;

import java.time.LocalDate;
import java.util.UUID;

public class BrandMapping {
    public static BrandEntity addBrandToEntity(AddNewBrandRequest addNewBrand,CountryEntity country,CommuneEntity commune, DistrictEntity district, ProvinceEntity province) {
        BrandEntity brand = new BrandEntity();
        brand.setId(getIdFromURL(addNewBrand.getImg()));
        brand.setBrandCountry(country);
        brand.setBrandProvince(province);
        brand.setBrandDistrict(district);
        brand.setBrandCommune(commune);
        brand.setName(addNewBrand.getName());
        brand.setPhone(addNewBrand.getPhone());
        brand.setDescription(addNewBrand.getDescription());
        brand.setYearCreate(LocalDate.now().getYear());
        brand.setImg(addNewBrand.getImg());
        brand.setAddressDetails(addNewBrand.getAddressDetails());
        return brand;
    }
    public static BrandEntity addBrandToEntity(BrandEntity brand,UpdateBrandRequest updateBrandRequest, CountryEntity country, CommuneEntity commune, DistrictEntity district, ProvinceEntity province) {
        brand.setBrandCountry(country);
        brand.setBrandProvince(province);
        brand.setBrandDistrict(district);
        brand.setBrandCommune(commune);
        brand.setName(updateBrandRequest.getName());
        brand.setPhone(updateBrandRequest.getPhone());
        brand.setDescription(updateBrandRequest.getDescription());
        brand.setYearCreate(LocalDate.now().getYear());
        brand.setAddressDetails(updateBrandRequest.getAddressDetails());
        return brand;
    }

    public static UUID getIdFromURL(String url){
        String[] arrOfStr = url.split("/");
        String[] publicId = arrOfStr[arrOfStr.length-1].split("\\.(?=[^\\.]+$)");
        return UUID.fromString(publicId[0]);
    }

}
