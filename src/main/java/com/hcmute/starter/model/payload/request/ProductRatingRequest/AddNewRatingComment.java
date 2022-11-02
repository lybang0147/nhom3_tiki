package com.hcmute.starter.model.payload.request.ProductRatingRequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AddNewRatingComment {
    @NotNull(message = "id Rating can't empty")
    private int ratingId;
    @NotEmpty(message = "Comment can't empty")
    private String comment;
}
