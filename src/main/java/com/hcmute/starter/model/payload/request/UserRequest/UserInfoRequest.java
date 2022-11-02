package com.hcmute.starter.model.payload.request.UserRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
public class UserInfoRequest {
    @NotEmpty(message = "Tên không được để trống")
    String fullName;
    @NotEmpty(message = "Ngày sinh không được bỏ trống")
    String birthday;
    @NotEmpty(message = "Email không được bỏ trống")
    String email;
    @NotNull(message = "Vui lòng nhập quốc tịch")
    int countryId;
}
