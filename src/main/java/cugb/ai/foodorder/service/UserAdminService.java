package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.vo.AdminUserVO;

public interface UserAdminService {

    PageResult<AdminUserVO> pageUsers(String username, Integer role, Integer status,
                                      Integer page, Integer size);

    void updateUserStatus(Long userId, Integer status);

    void updateUserRole(Long userId, Integer role);
}
