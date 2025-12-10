package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.service.RecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("/rating")
    public Result<List<Dish>> recommendByRating(Long num) {
        return Result.success(recommendService.recommendDishesByRating(num));
    }
    
    @GetMapping("/sales")
    public Result<List<Dish>> recommendBySales(Long num) {
        return Result.success(recommendService.recommendDishesBySales(num));
    }
}
