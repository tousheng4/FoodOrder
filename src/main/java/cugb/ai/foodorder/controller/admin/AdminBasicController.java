package cugb.ai.foodorder.controller.admin;

import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.security.AdminAuth;
import cugb.ai.foodorder.service.AdminBasicService;
import cugb.ai.foodorder.vo.ConsoleDetailVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/basic")
public class AdminBasicController {
    private final AdminBasicService adminBasicService;

    public AdminBasicController(AdminBasicService adminBasicService) {
        this.adminBasicService = adminBasicService;
    }

    @GetMapping("/console-detail")
    public Result<ConsoleDetailVO> getConsoleDetail() {
        AdminAuth.requireAdmin();
        return Result.success(adminBasicService.getConsoleDetail());
    }
}
