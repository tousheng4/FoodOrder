package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.AdminSaveDishRequest;
import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.entity.Category;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.mapper.CategoryMapper;
import cugb.ai.foodorder.mapper.DishMapper;
import cugb.ai.foodorder.service.DishAdminService;
import cugb.ai.foodorder.vo.AdminDishVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishAdminServiceImpl implements DishAdminService {

    private final DishMapper dishMapper;
    private final CategoryMapper categoryMapper;

    public DishAdminServiceImpl(DishMapper dishMapper, CategoryMapper categoryMapper) {
        this.dishMapper = dishMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public PageResult<AdminDishVO> pageDishes(String name, Long categoryId, Integer status,
                                              Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        int offset = (page - 1) * size;

        Long total = dishMapper.countAdmin(name, categoryId, status);
        List<Dish> list = dishMapper.selectPageAdmin(name, categoryId, status, offset, size);

        List<AdminDishVO> voList = new ArrayList<>();
        for (Dish d : list) {
            AdminDishVO vo = new AdminDishVO();
            vo.setId(d.getId());
            vo.setName(d.getName());
            vo.setDescription(d.getDescription());
            vo.setImage(d.getImage());
            vo.setPrice(d.getPrice());
            vo.setCategoryId(d.getCategoryId());
            vo.setStatus(d.getStatus());
            vo.setCreatedAt(d.getCreatedAt());
            voList.add(vo);
        }
        return PageResult.of(voList, total, page, size);
    }

    @Override
    public Long createDish(AdminSaveDishRequest req) {
        Dish dish = new Dish();
        dish.setName(req.getName());
        dish.setDescription(req.getDescription());
        dish.setImage(req.getImage());
        dish.setPrice(req.getPrice());
        dish.setCategoryId(req.getCategoryId());
        dish.setStatus(req.getStatus());
        dish.setDeleted(0);
        dishMapper.insert(dish);
        return dish.getId();
    }

    @Override
    public void updateDish(Long id, AdminSaveDishRequest req) {
        Dish exist = dishMapper.selectById(id);
        if (exist == null || (exist.getDeleted() != null && exist.getDeleted() == 1)) {
            throw new BusinessException(ErrorCode.DISH_NOT_FOUND, "菜品不存在");
        }
        exist.setName(req.getName());
        exist.setDescription(req.getDescription());
        exist.setImage(req.getImage());
        exist.setPrice(req.getPrice());
        exist.setCategoryId(req.getCategoryId());
        exist.setStatus(req.getStatus());
        dishMapper.update(exist);
    }

    @Override
    public void updateDishStatus(Long id, Integer status) {
        Dish exist = dishMapper.selectById(id);
        if (exist == null || (exist.getDeleted() != null && exist.getDeleted() == 1)) {
            throw new BusinessException(ErrorCode.DISH_NOT_FOUND, "菜品不存在");
        }
        exist.setStatus(status);
        dishMapper.update(exist);
    }

    @Override
    public void deleteDish(Long id) {
        dishMapper.logicalDelete(id);
    }

    public PageResult<AdminDishVO> searchDishes(DishSearchRequest req) {
        Integer page = Math.max(req.getPage(), 1);
        Integer size = Math.max(req.getSize(), 10);
        int offset = (page - 1) * size;

        // 调用Mapper查询
        Long total = dishMapper.count(req);
        List<Dish> dishList = dishMapper.search(req, offset, size);

        // 转换为VO并补充分类名称
        List<AdminDishVO> voList = dishList.stream().map(dish -> {
            AdminDishVO vo = new AdminDishVO();
            vo.setId(dish.getId());
            vo.setName(dish.getName());
            vo.setDescription(dish.getDescription());
            vo.setImage(dish.getImage());
            vo.setPrice(dish.getPrice());
            vo.setCategoryId(dish.getCategoryId());
            vo.setStatus(dish.getStatus());
            vo.setCreatedAt(dish.getCreatedAt());

            // 查询分类名称（通过CategoryMapper）
            if (dish.getCategoryId() != null) {
                Category category = categoryMapper.selectById(dish.getCategoryId()); // 需要在CategoryMapper中新增selectById方法
                if (category != null) {
                    vo.setCategoryName(category.getName());
                }
            }
            return vo;
        }).collect(Collectors.toList());

        return PageResult.of(voList, total, page, size);
    }

}
