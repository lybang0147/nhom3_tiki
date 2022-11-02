package com.hcmute.starter.model.payload.request.Order;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddOrderRequest {
    @NotBlank(message = "Nhập id địa chỉ")
    private String idAddress;
    @NotBlank(message = "Nhập id phương thức thanh toán")
    private int idPayment;
    @NotBlank(message = "Nhập id loại vận chuyển")
    private int idShip;
    private int[] cartItem;
    private UUID voucher;
    private Long DiscountId;
}
