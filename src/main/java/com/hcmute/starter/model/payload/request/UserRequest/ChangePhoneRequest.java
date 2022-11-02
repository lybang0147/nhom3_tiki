package com.hcmute.starter.model.payload.request.UserRequest;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePhoneRequest {
    @NotEmpty(message = "Thiếu số điện thoại")
    @Size(min = 9, message = "sdt tối thiểu 9 kí tự")
    private String phone;
}
