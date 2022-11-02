package com.hcmute.starter.model.entity.userAddress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.BrandEntity;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"vn_province\"")
public class ProvinceEntity {
    @Id
    @Column(name = "\"id\"")
    private String id;
    @Column(name = "\"name\"")
    private String name;
    @Column(name="\"type\"")
    private String type;
    @Column(name="\"slug\"")
    private String slug;
    @OneToMany(mappedBy = "province",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AddressEntity> addressEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "brandProvince",cascade = CascadeType.ALL)
    private List<BrandEntity> brandEntity;

    public ProvinceEntity(String name, String type, String slug, List<AddressEntity> addressEntities) {
        this.name = name;
        this.type = type;
        this.slug = slug;
        this.addressEntities = addressEntities;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public ProvinceEntity(String name, String type, String slug) {
        this.name = name;
        this.type = type;
        this.slug = slug;


    }

    public String getId() {
        return id;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ProvinceEntity() {
    }

}
