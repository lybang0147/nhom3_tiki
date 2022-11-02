package com.hcmute.starter.model.payload.request.Authentication;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Setter
@Getter
public class VerifyPhoneRequest {
    @NotEmpty(message = "Thiếu số điện thoại")
    @Size(min = 9, message = "sdt tối thiểu 9 kí tự")
    private String phone;
}
