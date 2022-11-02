package com.hcmute.starter.model.payload.request.ProductRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AddCategoryRequest {
    @NotEmpty(message = "Tên category không được để trống")
    String name;
    @NotEmpty(message = "Category parent không được để trống")
    String parent;
}
