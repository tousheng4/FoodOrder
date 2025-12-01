package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.vo.ReviewVO;

public interface ReviewAdminService {

    PageResult<ReviewVO> pageReviews(Long dishId, Long userId,
                                     Integer page, Integer size);

    void updateStatus(Long id, Integer status);

    void deleteReview(Long id);
}
