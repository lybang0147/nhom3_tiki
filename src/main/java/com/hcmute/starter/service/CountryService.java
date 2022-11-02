package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.CountryEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public interface CountryService {
    CountryEntity findByName(String name);
    CountryEntity findById(String id);
}
