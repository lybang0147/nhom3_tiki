package com.hcmute.starter.model.payload.response.Category;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Setter
@Getter
public class CategoryResponse {
    private UUID id;
    private String name;
    private UUID parentId;

    public CategoryResponse(UUID id, String name, UUID parent) {
        this.id = id;
        this.name = name;
        this.parentId = parent;
    }
}
