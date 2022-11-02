package com.hcmute.starter.model.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class AddNewProductRequest {
    @NotEmpty(message = "Name is Not Empty")
    private String name;
    @NotEmpty(message = "Brand is Not Empty")
    private UUID brand;
    @NotEmpty(message = "Category is Not Empty")
    private UUID category;
    private String description;
    @NotEmpty(message = "Price is Not Empty")
    private Double price;
    @NotEmpty(message = "Inventory is Not Empty")
    @Min(0)
    private int inventory;
}
