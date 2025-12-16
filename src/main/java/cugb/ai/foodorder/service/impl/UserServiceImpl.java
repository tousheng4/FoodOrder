package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.dto.LoginRequest;
import cugb.ai.foodorder.dto.RegisterRequest;
import cugb.ai.foodorder.dto.UpdateUserInfoRequest;
import cugb.ai.foodorder.entity.User;
import cugb.ai.foodorder.mapper.UserMapper;
import cugb.ai.foodorder.security.JwtUtil;
import cugb.ai.foodorder.service.UserService;
import cugb.ai.foodorder.storage.OssService;
import cugb.ai.foodorder.vo.LoginResponse;
import cugb.ai.foodorder.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private OssService ossService;

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

    @Override
    @Transactional
    public void updateUserInfo(Long userId, UpdateUserInfoRequest req) {
        // 查询当前用户信息
        User currentUser = userMapper.selectById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 记录旧头像URL，用于删除
        String oldAvatarUrl = currentUser.getAvatar();

        // 创建更新对象，只设置要更新的字段
        User updateUser = new User();
        updateUser.setId(userId); // 必须设置ID作为WHERE条件

        boolean hasUpdate = false;

        // 处理头像上传 - 在service层判断，避免OSS过早删除
        if (req.getAvatarFile() != null && !req.getAvatarFile().isEmpty()) {
            try {
                // 上传新头像到OSS
                String avatarUrl = ossService.uploadFile(req.getAvatarFile(), "avatar/");
                updateUser.setAvatar(avatarUrl);
                hasUpdate = true;

                // 只有在头像上传成功后，才删除旧的头像文件
                if (StringUtils.hasText(oldAvatarUrl)) {
                    ossService.deleteFile(oldAvatarUrl);
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, "头像上传失败：" + e.getMessage());
            }
        }

        // 处理昵称更新 - 在service层判断，避免无效更新
        if (StringUtils.hasText(req.getNickname())) {
            updateUser.setNickname(req.getNickname().trim());
            hasUpdate = true;
        }

        // 只有在确实有字段需要更新时，才调用数据库
        if (hasUpdate) {
            userMapper.updateUserInfo(updateUser);
        }
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setPhone(user.getPhone());
        userVO.setAvatar(user.getAvatar());
        userVO.setRole(user.getRole());
        userVO.setStatus(user.getStatus());
        return userVO;
    }
}
