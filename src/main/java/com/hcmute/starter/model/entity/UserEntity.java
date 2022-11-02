package com.hcmute.starter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.hcmute.starter.model.entity.ProductRating.ProductRatingCommentEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingEntity;
import com.hcmute.starter.model.entity.ProductRating.ProductRatingLikeEntity;
import com.hcmute.starter.model.entity.userAddress.AddressEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestResource(exported = false)
@Entity
@Table(name = "\"users\"")
public class UserEntity {
    @Id
    @Column(name = "user_id")

    private UUID id;
    @Column(name = "\"full_name\"")
    private String fullName;

    @Column(name = "\"email\"")
    private String email;

    @JsonIgnore
    @Column(name = "\"password\"")
    private String password;

    @Column(name = "\"gender\"")
    private String gender;

    @Column(name = "\"nick_name\"")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "\"phone\"")
    private String phone;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AddressEntity> address;

    @Column(name = "\"birth_day\"")
    private LocalDateTime birth_day;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"country_id\"",referencedColumnName = "country_id")
    private CountryEntity country;
    @JsonIgnore
    @OneToMany(mappedBy = "user",targetEntity = ProductRatingEntity.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProductRatingEntity> productRating;
    @JsonIgnore
    @OneToMany(mappedBy = "user",targetEntity = ProductRatingLikeEntity.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProductRatingLikeEntity> productRatingLiked;


    @JsonIgnore
    @OneToMany(mappedBy = "user",targetEntity = UserNotificationEntity.class,cascade = CascadeType.ALL)
    private Collection<UserNotificationEntity> notificationEntities;


    @JsonIgnore
    @OneToMany(mappedBy = "userOrder",targetEntity = OrderEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderEntity> order;


    @Column(name = "\"img\"")
    private String img;
    @JsonIgnore
    @OneToMany(mappedBy = "user",targetEntity = ProductRatingCommentEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductRatingCommentEntity> commentList;

    private boolean status;
    private boolean active;

    @JsonIgnore
    private LocalDateTime createAt;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "\"favorite_list\"",
            joinColumns = @JoinColumn(name = "\"user_id\""),
            inverseJoinColumns = @JoinColumn(name = "\"product_id\""))
    private List<ProductEntity> favoriteProducts;

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "\"user_role\"", joinColumns = @JoinColumn(name = "\"user_id\""), inverseJoinColumns = @JoinColumn(name = "\"role_id\""))
    private Set<RoleEntity> roles;
    @JsonIgnore
    @OneToOne(mappedBy = "user",targetEntity = CartEntity.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CartEntity cart;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "\"user_voucher\"", joinColumns = @JoinColumn(name = "\"user_id\""), inverseJoinColumns = @JoinColumn(name = "\"voucher_id\""))
    private Set<VoucherEntity> voucherEntities;

    private Boolean facebookAuth;
    private Boolean googleAuth;

    public Boolean getFacebookAuth() {
        return facebookAuth;
    }

    public void setFacebookAuth(Boolean facebookAuth) {
        this.facebookAuth = facebookAuth;
    }

    public Boolean getGoogleAuth() {
        return googleAuth;
    }

    public void setGoogleAuth(Boolean googleAuth) {
        this.googleAuth = googleAuth;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public UserEntity() {
    }
    public UserEntity(String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.active = false;
        this.status = true;
        this.createAt=LocalDateTime.now(ZoneId.of("GMT+07:00"));
    }
    public UserEntity(String phone,String password){
        this.phone = phone;
        this.password = password;
        this.active = false;
        this.status = true;
        this.createAt=LocalDateTime.now(ZoneId.of("GMT+07:00"));

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDateTime getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(LocalDateTime birth_day) {
        this.birth_day = birth_day;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<ProductEntity> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<ProductEntity> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }


    public Collection<UserNotificationEntity> getNotificationEntities() {
        return notificationEntities;
    }

    public void setNotificationEntities(Collection<UserNotificationEntity> notificationEntities) {
        this.notificationEntities = notificationEntities;
    }

    public List<OrderEntity> getOrder() {
        return order;
    }

    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }

    public List<ProductRatingEntity> getProductRating() {
        return productRating;
    }

    public void setProductRating(List<ProductRatingEntity> productRating) {
        this.productRating = productRating;
    }

    public List<ProductRatingLikeEntity> getProductRatingLiked() {
        return productRatingLiked;
    }

    public void setProductRatingLiked(List<ProductRatingLikeEntity> productRatingLiked) {
        this.productRatingLiked = productRatingLiked;
    }

    public List<ProductRatingCommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ProductRatingCommentEntity> commentList) {
        this.commentList = commentList;
    }

    public Set<VoucherEntity> getVoucherEntities() {
        return voucherEntities;
    }

    public void setVoucherEntities(Set<VoucherEntity> voucherEntities) {
        this.voucherEntities = voucherEntities;
    }
}
