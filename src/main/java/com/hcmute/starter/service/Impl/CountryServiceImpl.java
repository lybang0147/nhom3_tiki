package com.hcmute.starter.service.Impl;

import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.repository.CountryRepository;
import com.hcmute.starter.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    final CountryRepository countryRepository;
    @Override
    public CountryEntity findByName(String name) {
        Optional<CountryEntity> country = countryRepository.findByName(name);
        if (country.isEmpty())
            return null;
        return country.get();
    }

    @Override
    public CountryEntity findById(String id) {
        Optional<CountryEntity> country = countryRepository.findById(id);
        if (country.isEmpty())
            return null;
        return country.get();
    }
}
