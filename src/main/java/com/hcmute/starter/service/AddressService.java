package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.CountryEntity;
import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.userAddress.*;
import com.hcmute.starter.model.payload.request.Address.InfoAddressRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
@Service
public interface AddressService {
    AddressEntity findById(String id);
    List<AddressEntity> getAll();
    AddressEntity saveAddress(AddressEntity address);
    ProvinceEntity findByPid(String id);
    DistrictEntity findByDId(String id);
    CommuneEntity findByCid(String id);
    AddressTypeEntity findByTid(int id);

    CountryEntity findByCountryId(String id);

    void deleteAddress(String id);


    List<CommuneEntity> getAllCommuneInDistrict(String id);
    List<DistrictEntity> getAllDistrictInProvince(String id);
    List<ProvinceEntity> getAllProvince();

    CommuneEntity getCommuneById(String id);
    DistrictEntity getDistrictById(String id);
    ProvinceEntity getProvinceById(String id);

}
