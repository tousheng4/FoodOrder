package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.entity.Category;
import cugb.ai.foodorder.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET /api/categories
     * 返回所有启用的分类
     */
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> list = categoryService.listEnabledCategories();
        return Result.success(list);
    }
}