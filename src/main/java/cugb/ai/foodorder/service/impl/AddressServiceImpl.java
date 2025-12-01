package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.dto.AddressCreateRequest;
import cugb.ai.foodorder.dto.AddressUpdateRequest;
import cugb.ai.foodorder.entity.Address;
import cugb.ai.foodorder.mapper.AddressMapper;
import cugb.ai.foodorder.service.AddressService;
import cugb.ai.foodorder.vo.AddressVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public List<AddressVO> listUserAddresses(Long userId) {
        List<Address> list = addressMapper.selectByUserId(userId);
        List<AddressVO> voList = new ArrayList<>();
        for (Address a : list) {
            voList.add(toVO(a));
        }
        return voList;
    }

    @Override
    public AddressVO getAddress(Long userId, Long id) {
        Address address = addressMapper.selectByIdAndUserId(id, userId);
        if (address == null || address.getDeleted() != null && address.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND, "地址不存在");
        }
        return toVO(address);
    }

    @Transactional
    @Override
    public Long addAddress(Long userId, AddressCreateRequest req) {
        Address address = new Address();
        address.setUserId(userId);
        address.setReceiverName(req.getReceiverName());
        address.setReceiverPhone(req.getReceiverPhone());
        address.setDetailAddress(req.getDetailAddress());
        address.setDeleted(0);

        // 如果是用户第一个地址，自动设为默认
        List<Address> existing = addressMapper.selectByUserId(userId);
        boolean first = (existing == null || existing.isEmpty());

        boolean wantDefault = Boolean.TRUE.equals(req.getIsDefault()) || first;
        if (wantDefault) {
            addressMapper.clearDefaultByUserId(userId);
            address.setIsDefault(1);
        } else {
            address.setIsDefault(0);
        }

        addressMapper.insert(address);
        return address.getId();
    }

    @Transactional
    @Override
    public void updateAddress(Long userId, Long id, AddressUpdateRequest req) {
        Address address = addressMapper.selectByIdAndUserId(id, userId);
        if (address == null || address.getDeleted() != null && address.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND, "地址不存在");
        }

        address.setReceiverName(req.getReceiverName());
        address.setReceiverPhone(req.getReceiverPhone());
        address.setDetailAddress(req.getDetailAddress());

        boolean wantDefault = Boolean.TRUE.equals(req.getIsDefault());
        if (wantDefault) {
            addressMapper.clearDefaultByUserId(userId);
            address.setIsDefault(1);
        } else {
            // 可以选择不取消默认，这里尊重传参：
            // 如果 isDefault=false，则取消这个地址的默认
            address.setIsDefault(0);
        }

        addressMapper.update(address);
    }

    @Transactional
    @Override
    public void deleteAddress(Long userId, Long id) {
        Address address = addressMapper.selectByIdAndUserId(id, userId);
        if (address == null || address.getDeleted() != null && address.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND, "地址不存在");
        }

        addressMapper.softDeleteByIdAndUserId(id, userId);

        // 如果删的是默认地址，这里可以选择：不做额外处理 或 自动把最新一个设为默认。
        // 为简单起见，先不自动重置默认地址。
    }

    @Transactional
    @Override
    public void setDefault(Long userId, Long id) {
        Address address = addressMapper.selectByIdAndUserId(id, userId);
        if (address == null || address.getDeleted() != null && address.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND, "地址不存在");
        }

        addressMapper.clearDefaultByUserId(userId);
        address.setIsDefault(1);
        addressMapper.update(address);
    }

    @Override
    public AddressVO getDefault(Long userId) {
        Address address = addressMapper.selectDefaultByUserId(userId);
        if (address == null) {
            return null;
        }
        return toVO(address);
    }

    private AddressVO toVO(Address a) {
        AddressVO vo = new AddressVO();
        vo.setId(a.getId());
        vo.setReceiverName(a.getReceiverName());
        vo.setReceiverPhone(a.getReceiverPhone());
        vo.setDetailAddress(a.getDetailAddress());
        vo.setIsDefault(a.getIsDefault());
        return vo;
    }
}
