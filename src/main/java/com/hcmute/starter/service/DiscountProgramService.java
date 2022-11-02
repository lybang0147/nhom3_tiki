package com.hcmute.starter.service;
import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.CartItemEntity;
import com.hcmute.starter.model.entity.DiscountProgramEntity;
import com.hcmute.starter.model.entity.ProductEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface DiscountProgramService {
    DiscountProgramEntity findByName(String name);

    List<DiscountProgramEntity> getAllDiscountProgram();

    DiscountProgramEntity saveDiscountProgram(DiscountProgramEntity discountProgram);

    Boolean existsByName(String name);

    void delete(Long id);

    DiscountProgramEntity findByDiscountId(Long id);

    DiscountProgramEntity findByIdAndProductBrand(Long id,BrandEntity brand);


}