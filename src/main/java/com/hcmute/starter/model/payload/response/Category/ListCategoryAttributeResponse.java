package com.hcmute.starter.model.payload.response.Category;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Setter
@Getter
public class ListCategoryAttributeResponse {
    private int id;
    private String name;
    private List<CategoryAttributeResponse> categoryAttributeResponseList;

    public ListCategoryAttributeResponse(int id, String name, List<CategoryAttributeResponse> categoryAttributeResponseList) {
        this.id = id;
        this.name = name;
        this.categoryAttributeResponseList = categoryAttributeResponseList;
    }
}
