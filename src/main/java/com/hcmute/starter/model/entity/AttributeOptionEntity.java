package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@RestResource(exported = false)
@Entity
@Table(name = "\"attribute_options\"")
public class AttributeOptionEntity {
    @Id
    @Column(name = "\"id\"")
    private String id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "\"attribute_id\"", referencedColumnName = "\"attribute_id\"")
    private AttributeEntity idType;
    @Column(name = "\"value\"")
    private String value;
    @Column(name = "\"compare_value\"")
    private long compareValue;

    public AttributeOptionEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AttributeEntity getIdType() {
        return idType;
    }

    public void setIdType(AttributeEntity idType) {
        this.idType = idType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(long compareValue) {
        this.compareValue = compareValue;
    }
}
