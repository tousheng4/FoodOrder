package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.dto.LoginRequest;
import cugb.ai.foodorder.dto.RegisterRequest;
import cugb.ai.foodorder.entity.User;
import cugb.ai.foodorder.mapper.UserMapper;
import cugb.ai.foodorder.security.JwtUtil;
import cugb.ai.foodorder.service.UserService;
import cugb.ai.foodorder.vo.LoginResponse;
import cugb.ai.foodorder.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest req) {
        User exist = userMapper.selectByUsername(req.getUsername());
        if (exist != null) {
            throw new BusinessException(ErrorCode.USER_EXISTS, "用户名已存在");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setNickname(req.getNickname());
        user.setRole(0);   // 普通用户
        user.setStatus(1); // 正常

        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        User user = userMapper.selectByUsername(req.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "账号已被禁用");
        }

        boolean match = passwordEncoder.matches(req.getPassword(), user.getPasswordHash());
        if (!match) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, "密码错误");
        }

        // 更新最后登录时间
        userMapper.updateLastLoginAt(user.getId());

        String token = JwtUtil.generateToken(user.getId(), user.getRole());

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setPhone(user.getPhone());
        userVO.setAvatar(user.getAvatar());
        userVO.setRole(user.getRole());
        userVO.setStatus(user.getStatus());

        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUser(userVO);
        return resp;
    }
}
