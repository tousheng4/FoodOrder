package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.CreateReviewRequest;
import cugb.ai.foodorder.vo.ReviewVO;

public interface ReviewService {

    void createReview(Long userId, CreateReviewRequest req);

    PageResult<ReviewVO> listDishReviews(Long dishId, Integer page, Integer size);

    PageResult<ReviewVO> listMyReviews(Long userId, Integer page, Integer size);
}
