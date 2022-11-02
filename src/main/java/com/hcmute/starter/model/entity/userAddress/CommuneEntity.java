package com.hcmute.starter.model.entity.userAddress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.BrandEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"vn_commune\"")
public class CommuneEntity {
    @Id
    @Column(name="\"id\"")
    private String id;
    @Column(name = "\"name\"")
    private String name;
    @Column(name = "\"type\"")
    private String type;
    @Column(name="\"dis_id\"")
    private String district;
    @JsonIgnore
    @OneToMany(mappedBy = "commune",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AddressEntity> addressEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "brandCommune",cascade = CascadeType.ALL)
    private List<BrandEntity> brandEntity;

    public CommuneEntity(String name, String type, String district, List<AddressEntity> addressEntities) {
        this.name = name;
        this.type = type;
        this.district = district;
        this.addressEntities = addressEntities;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public CommuneEntity(String name, String type, String district) {
        this.name = name;
        this.type = type;
        this.district = district;

    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





    public CommuneEntity() {
    }
}
