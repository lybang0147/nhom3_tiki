package com.hcmute.starter.model.payload.request.Brand;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class AddNewBrandRequest {
    @NotEmpty(message = "Tên nhãn hàng không được bỏ trống")
    private String name;
    @NotEmpty(message = "Số điện thoại không được để trống")
    private String phone;
    @NotEmpty(message = "Quốc gia không được để trống")
    private String country;
    @NotEmpty(message = "Tỉnh thành không được để trống")
    private String province;
    @NotEmpty(message = "Quận huyện không được để trống")
    private String district;
    @NotEmpty(message = "Phường xã không được để trống")
    private String commune;
    private String addressDetails;
    @NotEmpty(message = "Thông tin chi tiết không được để trống")
    private String description;
    private String img;
}
