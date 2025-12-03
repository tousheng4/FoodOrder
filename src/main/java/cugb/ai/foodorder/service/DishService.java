package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.DishQueryRequest;
import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.entity.Dish;

public interface DishService {

    PageResult<Dish> pageDishesForUser(DishQueryRequest req);

    Dish getDishDetail(Long id);

    PageResult<Dish> searchDishes(DishSearchRequest req);


}
