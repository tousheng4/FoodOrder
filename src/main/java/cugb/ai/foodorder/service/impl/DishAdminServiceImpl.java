package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.AdminSaveDishRequest;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.mapper.DishMapper;
import cugb.ai.foodorder.service.DishAdminService;
import cugb.ai.foodorder.vo.AdminDishVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishAdminServiceImpl implements DishAdminService {

    private final DishMapper dishMapper;

    public DishAdminServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
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
}
