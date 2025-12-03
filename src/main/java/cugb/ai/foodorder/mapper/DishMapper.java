package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.dto.DishSearchRequest;
import cugb.ai.foodorder.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {
    Dish selectById(@Param("id") Long id);

    /**
     * 简单版本的分页查询：按分类 + 关键字 + 上架状态
     */
    List<Dish> selectPage(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("status") Integer status,
            @Param("offset") Integer offset,
            @Param("size") Integer size
    );

    Long countPage(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("status") Integer status
    );

    int insert(Dish dish);

    int update(Dish dish);

    int logicalDelete(@Param("id") Long id);

    /**
     * 后台分页查询菜品
     */
    List<Dish> selectPageAdmin(@Param("name") String name,
                               @Param("categoryId") Long categoryId,
                               @Param("status") Integer status,
                               @Param("offset") Integer offset,
                               @Param("size") Integer size);

    Long countAdmin(@Param("name") String name,
                    @Param("categoryId") Long categoryId,
                    @Param("status") Integer status);

    List<Dish> search(@Param("req") DishSearchRequest req,
                      @Param("offset") Integer offset,
                      @Param("pageSize") Integer pageSize);

    Long count(@Param("req") DishSearchRequest req);
}
