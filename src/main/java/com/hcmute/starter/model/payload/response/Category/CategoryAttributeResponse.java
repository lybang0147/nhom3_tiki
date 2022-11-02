package com.hcmute.starter.model.payload.response.Category;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CategoryAttributeResponse {
    private int id;
    private int value;
    private String suffix;

    public CategoryAttributeResponse(int id, int value, String suffix) {
        this.id = id;
        this.value = value;
        this.suffix = suffix;
    }
}
