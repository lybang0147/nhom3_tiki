package com.hcmute.starter.model.entity.userAddress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.starter.model.entity.BrandEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"vn_district\"")
public class DistrictEntity {
    @Id
    @Column(name="\"id\"")
    private String id;
    @Column(name = "\"name\"")
    private String name;
    @Column(name = "\"type\"")
    private String type;
    @Column(name = "\"pro_id\"")
    private String province;
    @OneToMany(mappedBy = "district",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AddressEntity> addressEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "brandDistrict",cascade = CascadeType.ALL)
    private List<BrandEntity> brandEntity;

    public DistrictEntity( String name, String type, String province, List<AddressEntity> addressEntities) {
        this.name = name;
        this.type = type;
        this.province = province;
        this.addressEntities = addressEntities;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public DistrictEntity(String name, String type, String province) {

        this.name = name;
        this.type = type;
        this.province = province;


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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }



    public DistrictEntity() {
    }
}
