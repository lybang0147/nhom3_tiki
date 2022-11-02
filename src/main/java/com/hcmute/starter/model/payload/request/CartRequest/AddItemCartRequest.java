package com.hcmute.starter.model.payload.request.CartRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Data
public class AddItemCartRequest {
    @NotEmpty(message = "Id can't empty")
    String productId;
    @NotNull(message = "Quantity can't empty")
    @Min(value = 1, message = "Quantity must bigger than 1")
    int quantity;

}
