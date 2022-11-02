package com.hcmute.starter.model.entity.userAddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"address_type\"")

public class AddressTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="\"address_type_name\"")
    private String addressTypeName;
    @JsonIgnore
    @OneToMany(mappedBy = "addressType",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AddressEntity> addressList;

    public AddressTypeEntity(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public AddressTypeEntity() {
    }
}
