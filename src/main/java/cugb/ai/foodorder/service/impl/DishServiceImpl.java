package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.PageResult;
import cugb.ai.foodorder.dto.DishQueryRequest;
import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.mapper.DishMapper;
import cugb.ai.foodorder.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;

    public DishServiceImpl(DishMapper dishMapper){
        this.dishMapper=dishMapper;
    }

    @Override
    public PageResult<Dish> pageDishesForUser(DishQueryRequest req) {
        Integer page = (req.getPage() == null || req.getPage() < 1) ? 1 : req.getPage();
        Integer size = (req.getSize() == null || req.getSize() < 1) ? 10 : req.getSize();
        int offset = (page - 1) * size;

        Long total = dishMapper.countPage(
                req.getCategoryId(),
                req.getKeyword(),
                1  // 只查上架的
        );

        List<Dish> list = dishMapper.selectPage(
                req.getCategoryId(),
                req.getKeyword(),
                1,
                offset,
                size
        );

        return PageResult.of(list, total, page, size);
    }

    @Override
    public Dish getDishDetail(Long id) {
        return dishMapper.selectById(id);
    }

    @Override
    public PageResult<Dish> searchDishes(DishSearchRequest req) {
        Integer page = Math.max(req.getPage(), 1);
        Integer size = Math.max(req.getSize(), 10);
        int offset = (page - 1) * size;

        // 用户端默认只查询上架商品
        if (req.getStatus() == null) {
            req.setStatus(1);
        }

        Long total = dishMapper.count(req);
        List<Dish> list = dishMapper.search(req, offset, size);

        return PageResult.of(list, total, page, size);
    }

}
