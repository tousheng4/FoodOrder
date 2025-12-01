package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.DishQueryRequest;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public Result<PageResult<Dish>> page(DishQueryRequest req){
        PageResult<Dish> pageResult=dishService.pageDishesForUser(req);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Dish> detail(@PathVariable Long id){
        Dish dish=dishService.getDishDetail(id);
        return Result.success(dish);
    }
}
