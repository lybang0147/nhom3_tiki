package com.hcmute.starter.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeInfoRequest {
    private String fullName;
    private String nickName;
    private String birthDay;
    private String gender;
    private String country;
}
