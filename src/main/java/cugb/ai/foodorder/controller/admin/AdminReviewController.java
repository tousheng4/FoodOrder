package cugb.ai.foodorder.controller.admin;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AdminUpdateReviewStatusRequest;
import cugb.ai.foodorder.security.AdminAuth;
import cugb.ai.foodorder.service.ReviewAdminService;
import cugb.ai.foodorder.vo.ReviewVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    private final ReviewAdminService reviewAdminService;

    public AdminReviewController(ReviewAdminService reviewAdminService) {
        this.reviewAdminService = reviewAdminService;
    }

    @GetMapping
    public Result<PageResult<ReviewVO>> pageReviews(
            @RequestParam(required = false) Long dishId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        AdminAuth.requireAdmin();
        PageResult<ReviewVO> result = reviewAdminService.pageReviews(dishId, userId, page, size);
        return Result.success(result);
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @Valid @RequestBody AdminUpdateReviewStatusRequest req) {
        AdminAuth.requireAdmin();
        reviewAdminService.updateStatus(id, req.getStatus());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        AdminAuth.requireAdmin();
        reviewAdminService.deleteReview(id);
        return Result.success();
    }
}
