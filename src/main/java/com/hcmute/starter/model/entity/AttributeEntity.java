package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@RestResource(exported = false)
@Entity
@Table(name = "\"attribute\"")
public class AttributeEntity {
    @Id
    @Column(name = "\"attribute_id\"")
    private String id;
    @Column(name = "\"name\"")
    private String name;
    @OneToMany(mappedBy = "idType",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AttributeOptionEntity> values;
    public AttributeEntity() {
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

    public List<AttributeOptionEntity> getValues() {
        return values;
    }

    public void setValues(List<AttributeOptionEntity> values) {
        this.values = values;
    }


}
