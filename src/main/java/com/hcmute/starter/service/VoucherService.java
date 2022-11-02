package com.hcmute.starter.service;

import com.hcmute.starter.model.entity.UserEntity;
import com.hcmute.starter.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Component
@Service
public interface VoucherService {

    VoucherEntity saveVoucher(VoucherEntity voucher);

    List<VoucherEntity> findAllVoucher();
    List<VoucherEntity> findAllVoucherPublic();

    List<VoucherEntity> foundVoucher(String type);

    VoucherEntity findById(UUID id);

    void deleteVoucher(UUID id);

    VoucherEntity findByIdAndUser(UUID id, UserEntity user);
}
