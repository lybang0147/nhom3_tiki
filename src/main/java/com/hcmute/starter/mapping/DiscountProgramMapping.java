package com.hcmute.starter.mapping;


import com.hcmute.starter.model.entity.BrandEntity;
import com.hcmute.starter.model.entity.DiscountProgramEntity;
import com.hcmute.starter.model.payload.request.DiscountProgram.AddDiscountProgramRequest;
import com.hcmute.starter.service.BrandService;
import com.hcmute.starter.service.DiscountProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DiscountProgramMapping {
    @Autowired
    DiscountProgramService discountProgramService;
    @Autowired
    BrandService brandService;

    public DiscountProgramEntity modelToEntity(AddDiscountProgramRequest request){
        DiscountProgramEntity discountProgram = discountProgramService.findByDiscountId(request.getDiscountId());
        return discountProgram;
    }
}
