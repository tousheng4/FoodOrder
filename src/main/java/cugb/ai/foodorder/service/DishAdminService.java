package cugb.ai.foodorder.service;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.AdminSaveDishRequest;
import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.vo.AdminDishVO;

public interface DishAdminService {

    PageResult<AdminDishVO> pageDishes(String name, Long categoryId, Integer status,
                                       Integer page, Integer size);

    Long createDish(AdminSaveDishRequest req);

    void updateDish(Long id, AdminSaveDishRequest req);

    void updateDishStatus(Long id, Integer status);

    void deleteDish(Long id);

    PageResult<AdminDishVO> searchDishes(DishSearchRequest req);
}
