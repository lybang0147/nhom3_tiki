package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestResource(exported = false)
@Entity
@Table(name = "\"products\"")
public class ProductEntity {
    @Id
    @Column(name = "\"product_id\"")
    @GeneratedValue(
            generator = "UUID"
    )
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"brand_id\"")
    private BrandEntity productBrand;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"category_id\"")
    private CategoryEntity productCategory;
    @Column(name = "\"product_name\"")
    private String name;
    @Column(name = "\"product_price\"")
    private Double price;
    @Column(name = "\"product_description\"")
    private String description;
    @Column(name = "\"product_status\"")
    private int status;
    @Column(name = "\"product_inventory\"")
    private Integer inventory;
    @Column(name = "\"product_sell_amount\"")
    private Integer sellAmount;
    @Column(name = "\"create_at\"")
    private LocalDate create;
    @JsonIgnore
    @OneToMany(mappedBy = "cart",targetEntity = CartItemEntity.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartItemEntity> list;
    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ImageProductEntity> imageProductEntityList;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "\"product_attribute_options\"", joinColumns = @JoinColumn(name = "\"product_id\""), inverseJoinColumns = @JoinColumn(name = "\"id\""))
    private Set<AttributeOptionEntity> attributeOptionEntities;

    public ProductEntity(BrandEntity productBrand, CategoryEntity productCategory, String name, Double price, String description, Integer inventory) {
        this.productBrand = productBrand;
        this.productCategory = productCategory;
        this.name = name;
        this.price = price;
        this.description = description;
        this.inventory = inventory;
        this.sellAmount = 0;
        this.status = 1;
        this.create = LocalDate.now();
    }

    public ProductEntity(BrandEntity productBrand, CategoryEntity productCategory, String name, Double price, String description, Integer inventory, Set<AttributeOptionEntity> listAttribute, List<ImageProductEntity> listImgUrl) {
        this.productBrand = productBrand;
        this.productCategory = productCategory;
        this.name = name;
        this.price = price;
        this.description = description;
        this.inventory = inventory;
        this.sellAmount = 0;
        this.status = 1;
        this.attributeOptionEntities = listAttribute;
        this.imageProductEntityList = listImgUrl;
        this.create = LocalDate.now();
    }

    public ProductEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ImageProductEntity> getImageProductEntityList() {
        return imageProductEntityList;
    }

    public void setImageProductEntityList(List<ImageProductEntity> imageProductEntityList) {
        this.imageProductEntityList = imageProductEntityList;
    }

    public BrandEntity getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(BrandEntity productBrand) {
        this.productBrand = productBrand;
    }

    public CategoryEntity getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(CategoryEntity productCategory) {
        this.productCategory = productCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Integer sellAmount) {
        this.sellAmount = sellAmount;
    }

    public LocalDate getCreate() {
        return create;
    }

    public void setCreate(LocalDate create) {
        this.create = create;
    }

    public List<CartItemEntity> getList() {
        return list;
    }

    public void setList(List<CartItemEntity> list) {
        this.list = list;
    }

    public Set<AttributeOptionEntity> getAttributeOptionEntities() {
        return attributeOptionEntities;
    }

    public void setAttributeOptionEntities(Set<AttributeOptionEntity> attributeOptionEntities) {
        this.attributeOptionEntities = attributeOptionEntities;
    }
}


