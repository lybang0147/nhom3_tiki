package com.hcmute.starter.model.payload.request.ProductRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequest {
    @NotNull(message = "Category không được để trống")
    int categoryId;
    String productName;
    @Min(value=0,message = "Giá sản phẩm phải lớn hơn 0")
    int price;
    String description;
    @Min(value = 0,message = "Số lượng tồn kho phải lớn hơn 0")
    int inventory;
    int status;
}