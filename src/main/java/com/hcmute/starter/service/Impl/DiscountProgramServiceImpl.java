package com.hcmute.starter.service.Impl;


import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.DiscountProgramEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import com.hcmute.starter.repository.DiscountProgramRepository;
import com.hcmute.starter.service.DiscountProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountProgramServiceImpl implements DiscountProgramService {
    final DiscountProgramRepository discountProgramRepository;

    @Override
    public DiscountProgramEntity findByName(String name) {
        Optional<DiscountProgramEntity> discountProgram = discountProgramRepository.findByName(name);
        if(discountProgram.isEmpty())
            return null;
        return discountProgram.get();
    }
    @Override
    public List<DiscountProgramEntity> getAllDiscountProgram() {
        return discountProgramRepository.findAll();
    }

    @Override
    public DiscountProgramEntity saveDiscountProgram (DiscountProgramEntity discountProgram) {
        return discountProgramRepository.save(discountProgram);
    }

    @Override
    public Boolean existsByName(String name) {
        return discountProgramRepository.existsByName(name);
    }

    @Override
    public void delete(Long id) {
        discountProgramRepository.deleteById(id);
    }

    @Override
    public DiscountProgramEntity findByDiscountId(Long id) {
        Optional<DiscountProgramEntity> discountProgram = discountProgramRepository.findById(id);
        if(discountProgram.isEmpty())
            return null;
        return discountProgram.get();
    }

    @Override
    public DiscountProgramEntity findByIdAndProductBrand(Long id,BrandEntity brand) {
        Optional<DiscountProgramEntity> discountProgram = discountProgramRepository.findByIdAndBrandEntities(id,brand);
        if(discountProgram.isEmpty())
            return null;
        return discountProgram.get();
    }


}
