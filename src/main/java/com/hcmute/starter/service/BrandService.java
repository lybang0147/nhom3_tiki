package com.hcmute.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.payload.request.Brand.AddNewBrandRequest;
import com.hcmute.starter.model.payload.response.Brand.BrandResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Component
@Service
public interface BrandService {
    BrandEntity findById(UUID id);

    BrandEntity saveBrand(BrandEntity brand);

    List<BrandEntity> findAll(int page, int size);

    void deleteBrand(UUID id);

    BrandResponse brandResponse(BrandEntity brand);

    AddNewBrandRequest getJson(String brand) throws JsonProcessingException;
}
