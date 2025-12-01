package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.entity.Category;
import cugb.ai.foodorder.mapper.CategoryMapper;
import cugb.ai.foodorder.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    // 推荐构造器注入
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> listEnabledCategories() {
        return categoryMapper.selectAllEnabled();
    }
}
