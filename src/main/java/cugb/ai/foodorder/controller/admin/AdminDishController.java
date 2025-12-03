package cugb.ai.foodorder.controller.admin;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AdminSaveDishRequest;
import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.security.AdminAuth;
import cugb.ai.foodorder.service.DishAdminService;
import cugb.ai.foodorder.vo.AdminDishVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dishes")
public class AdminDishController {

    private final DishAdminService dishAdminService;

    public AdminDishController(DishAdminService dishAdminService) {
        this.dishAdminService = dishAdminService;
    }

    @GetMapping
    public Result<PageResult<AdminDishVO>> pageDishes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        AdminAuth.requireAdmin();
        PageResult<AdminDishVO> result =
                dishAdminService.pageDishes(name, categoryId, status, page, size);
        return Result.success(result);
    }

    @PostMapping
    public Result<Long> create(@Valid @RequestBody AdminSaveDishRequest req) {
        AdminAuth.requireAdmin();
        Long id = dishAdminService.createDish(req);
        return Result.success(id);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id,
                               @Valid @RequestBody AdminSaveDishRequest req) {
        AdminAuth.requireAdmin();
        dishAdminService.updateDish(id, req);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @RequestParam Integer status) {
        AdminAuth.requireAdmin();
        dishAdminService.updateDishStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        AdminAuth.requireAdmin();
        dishAdminService.deleteDish(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<PageResult<AdminDishVO>> searchDishes(DishSearchRequest req) {
        AdminAuth.requireAdmin();
        return Result.success(dishAdminService.searchDishes(req));
    }
}
