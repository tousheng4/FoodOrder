package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.mapper.DishMapper;
import cugb.ai.foodorder.mapper.OrderItemMapper;
import cugb.ai.foodorder.mapper.ReviewMapper;
import cugb.ai.foodorder.service.RecommendService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {

    private final DishMapper dishMapper;
    private final OrderItemMapper orderItemMapper;
    private final ReviewMapper reviewMapper;

    public RecommendServiceImpl(DishMapper dishMapper, OrderItemMapper orderItemMapper, ReviewMapper reviewMapper) {
        this.dishMapper = dishMapper;
        this.orderItemMapper = orderItemMapper;
        this.reviewMapper = reviewMapper;
    }

    public List<Dish> recommendDishesBySales(Long num) {
        List<Long> dishIdsList = orderItemMapper.listDishIdsBySales(num);
        return dishIdsList.stream()
                .map(dishMapper::selectById)
                .collect(Collectors.toList());
    }

    public List<Dish> recommendDishesByRating(Long num) {
        List<Long> dishIdsList = reviewMapper.listDishIdsByRating(num);
        return dishIdsList.stream()
                .map(dishMapper::selectById)
                .collect(Collectors.toList());
    }

}
