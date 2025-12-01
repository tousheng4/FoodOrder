package cugb.ai.foodorder.service;

import cugb.ai.foodorder.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有启用的分类
     */
    List<Category> listEnabledCategories();
}
