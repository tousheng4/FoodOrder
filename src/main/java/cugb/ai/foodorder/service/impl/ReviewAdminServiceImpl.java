package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.mapper.ReviewMapper;
import cugb.ai.foodorder.service.ReviewAdminService;
import cugb.ai.foodorder.vo.ReviewVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewAdminServiceImpl implements ReviewAdminService {

    private final ReviewMapper reviewMapper;

    public ReviewAdminServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public PageResult<ReviewVO> pageReviews(Long dishId, Long userId,
                                            Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        int offset = (page - 1) * size;

        Long total = reviewMapper.countAdmin(dishId, userId);
        List<ReviewVO> list = reviewMapper.listAdmin(dishId, userId, offset, size);
        return PageResult.of(list, total, page, size);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        reviewMapper.updateStatus(id, status);
    }

    @Override
    public void deleteReview(Long id) {
        reviewMapper.deleteById(id);
    }
}
