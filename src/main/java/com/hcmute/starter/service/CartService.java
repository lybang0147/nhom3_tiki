package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.CartEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.payload.response.CartItem.CartItemResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface CartService {
    CartEntity saveCart(CartEntity cart);

    CartEntity getCartByUid(UserEntity uid);

    List<CartItemEntity> getCartItem(CartEntity cart);

    void deleteCartById(int id);

    int calCartTotal(CartEntity cart);

    CartItemEntity saveCartItem(CartItemEntity cartItem);

    CartItemEntity getCartItemByPidAndCid(ProductEntity id, CartEntity cart);

    void deleteCartItem(int id);

    CartItemEntity getCartItemById(int id);

    CartEntity getCartByUid(UserEntity uid, Boolean status);


    CartItemResponse cartItemResponse(CartItemEntity cartItem);

    public CartItemEntity getItemByIdAndCart(int id, CartEntity cart);
}