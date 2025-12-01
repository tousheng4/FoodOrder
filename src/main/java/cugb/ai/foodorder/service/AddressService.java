package cugb.ai.foodorder.service;

import cugb.ai.foodorder.dto.AddressCreateRequest;
import cugb.ai.foodorder.dto.AddressUpdateRequest;
import cugb.ai.foodorder.vo.AddressVO;

import java.util.List;

public interface AddressService {

    List<AddressVO> listUserAddresses(Long userId);

    AddressVO getAddress(Long userId, Long id);

    Long addAddress(Long userId, AddressCreateRequest req);

    void updateAddress(Long userId, Long id, AddressUpdateRequest req);

    void deleteAddress(Long userId, Long id);

    void setDefault(Long userId, Long id);

    AddressVO getDefault(Long userId);
}
