package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.entity.User;
import cugb.ai.foodorder.mapper.UserMapper;
import cugb.ai.foodorder.service.UserAdminService;
import cugb.ai.foodorder.vo.AdminUserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    private final UserMapper userMapper;

    public UserAdminServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PageResult<AdminUserVO> pageUsers(String username, Integer role, Integer status,
                                             Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        int offset = (page - 1) * size;

        Long total = userMapper.count(username, role, status);
        List<User> list = userMapper.selectPage(username, role, status, offset, size);

        List<AdminUserVO> voList = new ArrayList<>();
        for (User u : list) {
            AdminUserVO vo = new AdminUserVO();
            vo.setId(u.getId());
            vo.setUsername(u.getUsername());
            vo.setNickname(u.getNickname());
            vo.setPhone(u.getPhone());
            vo.setAvatar(u.getAvatar());
            vo.setRole(u.getRole());
            vo.setStatus(u.getStatus());
            vo.setLastLoginAt(u.getLastLoginAt());
            vo.setCreatedAt(u.getCreatedAt());
            voList.add(vo);
        }

        return PageResult.of(voList, total, page, size);
    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        int updated = userMapper.updateStatus(userId, status);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
    }

    @Override
    public void updateUserRole(Long userId, Integer role) {
        int updated = userMapper.updateRole(userId, role);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
    }
}
