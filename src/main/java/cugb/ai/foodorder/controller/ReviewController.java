package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.CreateReviewRequest;
import cugb.ai.foodorder.security.UserContext;
import cugb.ai.foodorder.service.ReviewService;
import cugb.ai.foodorder.vo.ReviewVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private Long currentUserId() {
        UserContext.LoginUser user = UserContext.get();
        return user.getUserId();
    }

    /**
     * 创建评价
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody CreateReviewRequest req) {
        Long userId = currentUserId();
        reviewService.createReview(userId, req);
        return Result.success();
    }

    /**
     * 某个菜品的评价列表（游客也能看）
     */
    @GetMapping("/dish/{dishId}")
    public Result<PageResult<ReviewVO>> listByDish(@PathVariable Long dishId,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        PageResult<ReviewVO> result = reviewService.listDishReviews(dishId, page, size);
        return Result.success(result);
    }

    /**
     * 我的所有评价
     */
    @GetMapping("/my")
    public Result<PageResult<ReviewVO>> listMyReviews(@RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        Long userId = currentUserId();
        PageResult<ReviewVO> result = reviewService.listMyReviews(userId, page, size);
        return Result.success(result);
    }
}
