package com.hcmute.starter.model.entity.userAddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.starter.model.entity.OrderEntity;
import com.hcmute.starter.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@RestResource(exported = false)
@Entity
@Table(name = "\"addresses\"")
public class AddressEntity {
    @Id
    @GeneratedValue(
            generator = "UUID"
    )
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "\"id\"")
    private String id;
    @Column(name = "\"full_name\"")
    private String fullName;
    @Basic
    @Column(name="\"company_name\"")
    private String companyName;
    @Column(name = "\"phone_number\"")
    private String phoneNumber;
    @ManyToOne
    @Nullable
    @JoinColumn(name="\"province\"")
    private ProvinceEntity province;
    @ManyToOne
    @Nullable
    @JoinColumn(name="\"district\"")
    private DistrictEntity district;

    @ManyToOne
    @Nullable
    @JoinColumn(name="\"commune\"")
    private CommuneEntity commune;
    @Column(name="\"address_detail\"")
    private String addressDetail;
    @ManyToOne
    @JoinColumn(name="\"address_type\"",columnDefinition = "integer")
    private AddressTypeEntity addressType;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "\"user\"")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "addressOrder",targetEntity = OrderEntity.class)
    private List<OrderEntity> order;

    public List<OrderEntity> getOrder() {
        return order;
    }

    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ProvinceEntity getProvince() {
        return province;
    }

    public void setProvince(ProvinceEntity province) {
        this.province = province;
    }

    public DistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }

    public CommuneEntity getCommune() {
        return commune;
    }

    public void setCommune(CommuneEntity commune) {
        this.commune = commune;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public AddressTypeEntity getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeEntity addressType) {
        this.addressType = addressType;
    }

    public AddressEntity(String fullName, String companyName, String phoneNumber, ProvinceEntity province, DistrictEntity district, CommuneEntity commune, String addressDetail, AddressTypeEntity addressType,UserEntity user) {
        this.fullName = fullName;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.addressDetail = addressDetail;
        this.addressType = addressType;
        this.user=user;
    }

    public AddressEntity() {
    }
}
