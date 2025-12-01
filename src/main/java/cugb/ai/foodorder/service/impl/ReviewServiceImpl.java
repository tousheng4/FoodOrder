package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.CreateReviewRequest;
import cugb.ai.foodorder.entity.OrderInfo;
import cugb.ai.foodorder.entity.OrderItem;
import cugb.ai.foodorder.entity.Review;
import cugb.ai.foodorder.mapper.OrderInfoMapper;
import cugb.ai.foodorder.mapper.OrderItemMapper;
import cugb.ai.foodorder.mapper.ReviewMapper;
import cugb.ai.foodorder.service.ReviewService;
import cugb.ai.foodorder.vo.ReviewVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderItemMapper orderItemMapper;

    public ReviewServiceImpl(ReviewMapper reviewMapper,
                             OrderInfoMapper orderInfoMapper,
                             OrderItemMapper orderItemMapper) {
        this.reviewMapper = reviewMapper;
        this.orderInfoMapper = orderInfoMapper;
        this.orderItemMapper = orderItemMapper;
    }

    /**
     * 创建评价：必须是自己的订单、订单中有该菜品、且订单已支付/已完成、且没评过
     */
    @Transactional
    @Override
    public void createReview(Long userId, CreateReviewRequest req) {
        // 1. 校验订单属于当前用户
        OrderInfo order = orderInfoMapper.selectByIdAndUserId(req.getOrderId(), userId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }

        // 2. 订单状态检查：这里只允许已支付(1)或已完成(3)的订单评价
        Integer status = order.getStatus();
        if (status == null || (status != 1 && status != 3)) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有已支付或已完成的订单可以评价");
        }

        // 3. 确认订单里确实包含这个菜
        OrderItem orderItem = orderItemMapper.selectOneByOrderIdAndDishId(req.getOrderId(), req.getDishId());
        if (orderItem == null) {
            throw new BusinessException(ErrorCode.ORDER_ITEM_NOT_FOUND, "该订单中没有这个菜品");
        }

        // 4. 检查是否已经评价过
        Review exist = reviewMapper.selectByUserAndOrderAndDish(userId, req.getOrderId(), req.getDishId());
        if (exist != null) {
            throw new BusinessException(ErrorCode.REVIEW_ALREADY_EXISTS, "你已经评价过这个菜品了");
        }

        // 5. 写入评价
        Review review = new Review();
        review.setUserId(userId);
        review.setOrderId(req.getOrderId());
        review.setOrderItemId(orderItem.getId());
        review.setDishId(req.getDishId());
        review.setRating(req.getRating());
        review.setContent(req.getContent());
        review.setStatus(1); // 默认展示

        reviewMapper.insert(review);
    }

    @Override
    public PageResult<ReviewVO> listDishReviews(Long dishId, Integer page, Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        int offset = (page - 1) * size;

        Integer status = 1; // 只展示正常评价
        Long total = reviewMapper.countByDish(dishId, status);
        List<ReviewVO> list = reviewMapper.listByDish(dishId, status, offset, size);

        return PageResult.of(list, total, page, size);
    }

    @Override
    public PageResult<ReviewVO> listMyReviews(Long userId, Integer page, Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        int offset = (page - 1) * size;

        Long total = reviewMapper.countByUser(userId);
        List<ReviewVO> list = reviewMapper.listByUser(userId, offset, size);

        return PageResult.of(list, total, page, size);
    }
}
