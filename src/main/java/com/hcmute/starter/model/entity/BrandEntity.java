package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.userAddress.CommuneEntity;
import com.hcmute.starter.model.entity.userAddress.DistrictEntity;
import com.hcmute.starter.model.entity.userAddress.ProvinceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestResource(exported = false)
@Entity
@Table(name = "\"brands\"")
public class BrandEntity {
    @Id
    @Column(name = "\"brand_id\"")
    private UUID id;
    @Column(name = "\"brand_name\"")
    private String name;
    @Column(name = "\"brand_description\"")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="\"country_id\"")
    private CountryEntity brandCountry;
    @Column(name = "\"brand_phone\"")
    private String phone;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="\"commune_id\"")
    private CommuneEntity brandCommune;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="\"district_id\"")
    private DistrictEntity brandDistrict;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="\"province_id\"")
    private ProvinceEntity brandProvince;

    @JsonIgnore
    @OneToMany(mappedBy = "productBrand",cascade = CascadeType.ALL)
    private List<ProductEntity> listProduct;
    @Column(name = "\"address_details\"")
    private String addressDetails;
    @Column(name = "\"logo\"")
    private String img;
    @Column(name = "year_create")
    private Integer yearCreate;

    public BrandEntity() {
    }

    public BrandEntity(String name, String description, CountryEntity brandCountry, String phone, CommuneEntity brandCommune, DistrictEntity brandDistrict, ProvinceEntity brandProvince, String addressDetails, String url) {
        this.name = name;
        this.description = description;
        this.brandCountry = brandCountry;
        this.phone = phone;
        this.brandCommune = brandCommune;
        this.brandDistrict = brandDistrict;
        this.brandProvince = brandProvince;
        this.addressDetails = addressDetails;
        LocalDate current_date = LocalDate.now();
        this.yearCreate = current_date.getYear();
        this.img = url;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CountryEntity getBrandCountry() {
        return brandCountry;
    }

    public void setBrandCountry(CountryEntity brandCountry) {
        this.brandCountry = brandCountry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CommuneEntity getBrandCommune() {
        return brandCommune;
    }

    public void setBrandCommune(CommuneEntity brandCommune) {
        this.brandCommune = brandCommune;
    }

    public DistrictEntity getBrandDistrict() {
        return brandDistrict;
    }

    public void setBrandDistrict(DistrictEntity brandDistrict) {
        this.brandDistrict = brandDistrict;
    }

    public ProvinceEntity getBrandProvince() {
        return brandProvince;
    }

    public void setBrandProvince(ProvinceEntity brandProvince) {
        this.brandProvince = brandProvince;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getYearCreate() {
        return yearCreate;
    }

    public void setYearCreate(Integer yearCreate) {
        this.yearCreate = yearCreate;
    }

    public List<ProductEntity> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<ProductEntity> listProduct) {
        this.listProduct = listProduct;
    }
}
