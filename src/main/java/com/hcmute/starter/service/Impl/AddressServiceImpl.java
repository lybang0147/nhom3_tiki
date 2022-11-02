package com.hcmute.starter.service.Impl;

import com.hcmute.starter.mapping.AddressMapping;
import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.model.entity.userAddress.*;
import com.hcmute.starter.model.payload.request.Address.InfoAddressRequest;
import com.hcmute.starter.repository.*;
import com.hcmute.starter.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    final AddressRepository addressRepository;
    final ProvinceRepository provinceRepository;
    final DistrictRepository districtRepository;
    final CommuneRepository communeRepository;
    final AddressTypeRepository addressTypeRepository;
    final CountryRepository countryRepository;

    @Override
    public AddressEntity findById(String id) {
        Optional<AddressEntity> addressEntity= addressRepository.findById(id);
        if (addressEntity==null)
        {
            return null;
        }
        return addressEntity.get();
    }

    @Override
    public List<AddressEntity> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public AddressEntity saveAddress(AddressEntity address) {
        return addressRepository.save(address);


    }

    @Override
    public ProvinceEntity findByPid(String id) {
        Optional<ProvinceEntity> entity = provinceRepository.findById(id);
        if (entity==null)
            return null;
        return entity.get();
    }

    @Override
    public DistrictEntity findByDId(String id) {
        Optional<DistrictEntity> entity = districtRepository.findById(id);
        if (entity==null)
            return null;
        return entity.get();
    }

    @Override
    public CommuneEntity findByCid(String id) {
        Optional<CommuneEntity> entity = communeRepository.findById(id);
        if (entity==null)
            return null;
        return entity.get();
    }

    @Override
    public AddressTypeEntity findByTid(int id) {
        Optional<AddressTypeEntity> addressType = addressTypeRepository.findById(id);
        if (addressType == null)
            return null;
        return addressType.get();
    }

    @Override
    public CountryEntity findByCountryId(String id){
        Optional<CountryEntity> entity = countryRepository.findById(id);
        if(entity == null)
            return null;
        return entity.get();
    }

    @Override
    public void deleteAddress(String id) {
        addressRepository.deleteById(id);
    }



    @Override
    public List<CommuneEntity> getAllCommuneInDistrict(String id) {
        List<CommuneEntity> list = communeRepository.findAllByDistrict(id);
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public List<DistrictEntity> getAllDistrictInProvince(String id) {
        List<DistrictEntity> list = districtRepository.findAllByProvince(id);
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public List<ProvinceEntity> getAllProvince() {
        return provinceRepository.findAll();
    }

    @Override
    public CommuneEntity getCommuneById(String id) {
        Optional<CommuneEntity> communeEntity = communeRepository.findById(id);
        if(communeEntity.isEmpty())
            return null;
        return communeEntity.get();
    }

    @Override
    public DistrictEntity getDistrictById(String id) {
        Optional<DistrictEntity> districtEntity = districtRepository.findById(id);
        if(districtEntity.isEmpty())
            return null;
        return districtEntity.get();
    }

    @Override
    public ProvinceEntity getProvinceById(String id) {
        Optional<ProvinceEntity> provinceEntity = provinceRepository.findById(id);
        if(provinceEntity.isEmpty())
            return null;
        return provinceEntity.get();
    }


}
