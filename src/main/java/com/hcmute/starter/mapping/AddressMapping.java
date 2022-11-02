package com.hcmute.starter.mapping;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.userAddress.*;
import com.hcmute.starter.model.payload.request.Address.AddNewAddressRequest;
import com.hcmute.starter.model.payload.request.Address.InfoAddressRequest;
import com.hcmute.starter.service.AddressService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class AddressMapping {

    public static AddressEntity ModelToEntity(AddNewAddressRequest addNewAddressRequest,ProvinceEntity provinceEntity,DistrictEntity districtEntity,CommuneEntity communeEntity,AddressTypeEntity addressType)
    {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setFullName(addNewAddressRequest.getFullName());
        addressEntity.setCompanyName(addNewAddressRequest.getCompanyName());
        addressEntity.setPhoneNumber(addNewAddressRequest.getPhone());
        addressEntity.setAddressDetail(addNewAddressRequest.getAddressDetail());
        if (addressType==null || provinceEntity==null||districtEntity==null||communeEntity==null || !provinceEntity.getId().equals(districtEntity.getProvince()) || !districtEntity.getId().equals(communeEntity.getDistrict()))
        {
            return null;
        }
        addressEntity.setCommune(communeEntity);
        addressEntity.setProvince(provinceEntity);
        addressEntity.setDistrict(districtEntity);
        addressEntity.setAddressType(addressType);
        return addressEntity;
    }
    public static AddressEntity UpdateAddressEntity(AddressEntity address, InfoAddressRequest addressInfo ,ProvinceEntity provinceEntity,DistrictEntity districtEntity,CommuneEntity communeEntity,AddressTypeEntity addressType)
    {
        address.setFullName(addressInfo.getFullName());
        address.setPhoneNumber(addressInfo.getPhone());
        address.setAddressDetail(addressInfo.getAddressDetail());
        address.setCompanyName(addressInfo.getCompanyName());
        if (addressType==null || provinceEntity==null||districtEntity==null||communeEntity==null || !provinceEntity.getId().equals(districtEntity.getProvince()) || !districtEntity.getId().equals(communeEntity.getDistrict()))
        {
            return null;
        }
        address.setCommune(communeEntity);
        address.setProvince(provinceEntity);
        address.setDistrict(districtEntity);
        address.setAddressType(addressType);
        return address;
    }

}
