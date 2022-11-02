package com.hcmute.starter.model.payload.response.CartItem;

import com.hcmute.starter.model.payload.response.ProductResponse;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CartItemResponse{
    private int id;
    private ProductResponse productResponse;
    private int cartId;
    private int quantity;


    public CartItemResponse(int id, ProductResponse productResponse, int cartId, int quantity) {
        this.id = id;
        this.productResponse = productResponse;
        this.cartId = cartId;
        this.quantity = quantity;

    }


}
