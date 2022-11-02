package com.hcmute.starter.model.payload.request.Ship;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddShipRequest {
    @NotBlank(message = "Empty Ship Type")
    private String shipType;

    @NotBlank(message = "Empty Ship Price")
    private double shipPrice;
}
