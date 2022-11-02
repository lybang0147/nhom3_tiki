package com.hcmute.starter.model.payload.request.CartRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SubtractItemRequest {
    @NotNull(message = "Id can't empty")
    private int id;
    @NotNull(message = "Quantity can't empty")
    @Min(value = 1)
    private int quantity;

}
