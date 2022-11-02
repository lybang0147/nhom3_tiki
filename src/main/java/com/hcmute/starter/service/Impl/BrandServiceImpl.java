package com.hcmute.starter.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.model.payload.request.Brand.AddNewBrandRequest;
import com.hcmute.starter.model.payload.response.Brand.BrandResponse;
import com.hcmute.starter.repository.BrandRepository;
import com.hcmute.starter.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Override
    public BrandEntity findById(UUID id) {
        Optional<BrandEntity> brand = brandRepository.findById(id);
        if(brand.isEmpty())
            return null;
        return brand.get();
    }
    @Override
    public BrandEntity saveBrand(BrandEntity brand) {
        return brandRepository.save(brand);
    }
    @Override
    public List<BrandEntity> findAll(int page, int size){
        Pageable paging = PageRequest.of(page, size);
        Page<BrandEntity> pagedResult = brandRepository.findAll(paging);
        return pagedResult.toList();
    }
    @Override
    public void deleteBrand(UUID id){
        brandRepository.deleteById(id);
    }
    @Override
    public BrandResponse brandResponse(BrandEntity brand){
        return new BrandResponse(brand.getId(),
                brand.getName(),
                brand.getDescription(),
                brand.getBrandCountry().getId(),
                brand.getPhone(),
                brand.getBrandCommune().getId(),
                brand.getBrandDistrict().getId(),
                brand.getBrandProvince().getId(),
                brand.getAddressDetails(),
                brand.getImg(),
                brand.getYearCreate());
    }
    @Override
    public AddNewBrandRequest getJson(String brand) throws JsonProcessingException {
        AddNewBrandRequest brandEntity = new AddNewBrandRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        brandEntity = objectMapper.readValue(brand, AddNewBrandRequest.class);
        return  brandEntity;
    }
}
