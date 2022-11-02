package com.hcmute.starter.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
@RestResource(exported = false)
@Entity
@Table(name = "\"countries\"")
@NoArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "country_id")
    private String id;

    @Column(name = "country_name")
    private String name;


    public CountryEntity(String name) {
        this.name = name;
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
    @OneToMany(mappedBy = "country",cascade = CascadeType.ALL)
    private Collection<UserEntity> users;
    @JsonIgnore
    @OneToMany(mappedBy = "brandCountry",cascade = CascadeType.ALL)
    private List<BrandEntity> brandEntity;
}
