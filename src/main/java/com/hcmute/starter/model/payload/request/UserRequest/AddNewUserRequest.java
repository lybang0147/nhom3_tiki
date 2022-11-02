package com.hcmute.starter.model.payload.request.UserRequest;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Setter
@Getter
public class AddNewUserRequest {
    @NotEmpty(message = "Thiếu số điện thoại")
    @Size(min = 9, message = "sdt tối thiểu 9 kí tự")
    private String phone;

    @NotEmpty(message = "Thiếu password")
    @Size(min = 8, message = "Password phải từ 8 kí tự trở lên")
    private String password;


}
