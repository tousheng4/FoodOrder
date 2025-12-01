package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AddressCreateRequest;
import cugb.ai.foodorder.dto.AddressUpdateRequest;
import cugb.ai.foodorder.security.UserContext;
import cugb.ai.foodorder.service.AddressService;
import cugb.ai.foodorder.vo.AddressVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    private Long currentUserId() {
        UserContext.LoginUser user = UserContext.get();
        return user.getUserId();
    }

    /**
     * 地址列表
     */
    @GetMapping
    public Result<List<AddressVO>> list() {
        Long userId = currentUserId();
        List<AddressVO> list = addressService.listUserAddresses(userId);
        return Result.success(list);
    }

    /**
     * 获取单个地址详情
     */
    @GetMapping("/{id}")
    public Result<AddressVO> detail(@PathVariable Long id) {
        Long userId = currentUserId();
        AddressVO vo = addressService.getAddress(userId, id);
        return Result.success(vo);
    }

    /**
     * 新增地址
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody AddressCreateRequest req) {
        Long userId = currentUserId();
        Long id = addressService.addAddress(userId, req);
        return Result.success(id);
    }

    /**
     * 修改地址
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id,
                               @Valid @RequestBody AddressUpdateRequest req) {
        Long userId = currentUserId();
        addressService.updateAddress(userId, id, req);
        return Result.success();
    }

    /**
     * 删除地址（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Long userId = currentUserId();
        addressService.deleteAddress(userId, id);
        return Result.success();
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/{id}/default")
    public Result<Void> setDefault(@PathVariable Long id) {
        Long userId = currentUserId();
        addressService.setDefault(userId, id);
        return Result.success();
    }

    /**
     * 获取默认地址
     */
    @GetMapping("/default")
    public Result<AddressVO> getDefault() {
        Long userId = currentUserId();
        AddressVO vo = addressService.getDefault(userId);
        return Result.success(vo);
    }
}
