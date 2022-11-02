package com.hcmute.starter.model.payload.request.Attribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AttributeOptionRequest {
    private String id;
    private String idType;
    private String value;

}
