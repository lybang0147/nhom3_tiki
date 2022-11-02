package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.*;
import com.hcmute.starter.model.payload.response.CartItem.CartItemResponse;
import com.hcmute.starter.repository.CartItemRepository;
import com.hcmute.starter.repository.CartRepository;
import com.hcmute.starter.service.CartService;
import com.hcmute.starter.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    final CartItemRepository cartItemRepository;
    final CartRepository cartRepository;
    final ProductService productService;
    @Override
    public CartEntity saveCart(CartEntity cart) {
        return cartRepository.save(cart);
    }

    @Override
    public CartEntity getCartByUid(UserEntity user) {
        Optional<CartEntity> cart = cartRepository.findByUserAndStatus(user,true);
        if (cart.isEmpty())
            return null;
        return cart.get();
    }

    @Override
    public List<CartItemEntity> getCartItem(CartEntity cart) {
        return null;
    }

    @Override
    public void deleteCartById(int id) {

    }
    @Override
    public int calCartTotal(CartEntity cart) {
        return 0;
    }

    @Override
    public CartItemEntity saveCartItem(CartItemEntity cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemEntity getCartItemByPidAndCid(ProductEntity id,CartEntity cart) {

        Optional<CartItemEntity> cartItem = cartItemRepository.findByProductAndCart(id,cart);
        if (cartItem.isEmpty())
            return null;
        return cartItem.get();
    }

    @Override
    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItemEntity getCartItemById(int id) {
        Optional<CartItemEntity> cartItem = cartItemRepository.findById(id);
        if (cartItem.isEmpty())
            return null;
        return cartItem.get();
    }

    @Override
    public CartEntity getCartByUid(UserEntity user, Boolean status) {
        Optional<CartEntity> cart = cartRepository.findByUserAndStatus(user,status);
        if (cart.isEmpty())
            return null;
        return cart.get();
    }


    @Override
    public CartItemResponse cartItemResponse(CartItemEntity cartItem){
        return new CartItemResponse(
                cartItem.getId(),
                productService.productResponse(cartItem.getProduct()),
                cartItem.getCart().getId(),
                cartItem.getQuantity());
    }

    @Override
    public CartItemEntity getItemByIdAndCart(int id, CartEntity cart) {
        Optional<CartItemEntity> cartItem = cartItemRepository.findByIdAndCart(id,cart);
        if(cartItem.isEmpty())
            return null;
        return cartItem.get();
    }
}
