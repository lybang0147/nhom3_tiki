package com.hcmute.starter.model.payload.request.Notification;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddNotificationRequest {
    @NotBlank(message = "Empty User")
    private UUID idUser;
    @NotBlank(message = "Empty Message")
    private String message;
    @NotBlank(message = "Empty Type")
    private String type;
}
