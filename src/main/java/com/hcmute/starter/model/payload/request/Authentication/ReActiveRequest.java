package com.hcmute.starter.model.payload.request.Authentication;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReActiveRequest {
    @NotEmpty
    String email;
}
