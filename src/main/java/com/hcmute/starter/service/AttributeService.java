package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.AttributeEntity;
import com.hcmute.starter.model.entity.AttributeOptionEntity;
import com.hcmute.starter.model.entity.CategoryEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface AttributeService {
    AttributeOptionEntity findByIdAttributeOption(String id);

    List<AttributeEntity> findAllAttribute();

    List<AttributeOptionEntity> findAllAttributeOption();

    AttributeEntity findById(String id);

    AttributeEntity saveAttribute(AttributeEntity attributeEntity);

    void deleteAttribute(String id);

    List<AttributeEntity> findByCategory(CategoryEntity category);

    AttributeOptionEntity saveAttributeOption(AttributeOptionEntity attributeOption);
}
