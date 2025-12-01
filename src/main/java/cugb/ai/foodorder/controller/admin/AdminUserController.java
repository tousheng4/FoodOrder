package cugb.ai.foodorder.controller.admin;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AdminUpdateUserRoleRequest;
import cugb.ai.foodorder.dto.AdminUpdateUserStatusRequest;
import cugb.ai.foodorder.security.AdminAuth;
import cugb.ai.foodorder.service.UserAdminService;
import cugb.ai.foodorder.vo.AdminUserVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserAdminService userAdminService;

    public AdminUserController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    /**
     * 用户列表（支持按用户名/角色/状态过滤）
     */
    @GetMapping
    public Result<PageResult<AdminUserVO>> pageUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        AdminAuth.requireAdmin();

        PageResult<AdminUserVO> result =
                userAdminService.pageUsers(username, role, status, page, size);
        return Result.success(result);
    }

    /**
     * 修改用户状态（禁用/启用）
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @Valid @RequestBody AdminUpdateUserStatusRequest req) {
        AdminAuth.requireAdmin();
        userAdminService.updateUserStatus(id, req.getStatus());
        return Result.success();
    }

    /**
     * 修改用户角色（普通用户/管理员）
     */
    @PutMapping("/{id}/role")
    public Result<Void> updateRole(@PathVariable Long id,
                                   @Valid @RequestBody AdminUpdateUserRoleRequest req) {
        AdminAuth.requireAdmin();
        userAdminService.updateUserRole(id, req.getRole());
        return Result.success();
    }
}
