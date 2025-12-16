package cugb.ai.foodorder.service;

import cugb.ai.foodorder.entity.Dish;

import java.util.List;

public interface RecommendService {

    List<Dish> recommendDishesBySales(Long num);

    List<Dish> recommendDishesByRating(Long num);
}
