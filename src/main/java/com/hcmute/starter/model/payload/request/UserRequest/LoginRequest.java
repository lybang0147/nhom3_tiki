package com.hcmute.starter.model.payload.request.UserRequest;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
}
