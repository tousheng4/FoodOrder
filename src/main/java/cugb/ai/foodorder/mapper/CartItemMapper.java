package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.CartItem;
import cugb.ai.foodorder.vo.CartItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartItemMapper {

    CartItem selectByUserIdAndDishId(@Param("userId") Long userId,
                                     @Param("dishId") Long dishId);

    List<CartItem> selectByUserId(@Param("userId") Long userId);

    int insert(CartItem item);

    int update(CartItem item);

    int deleteByUserIdAndDishId(@Param("userId") Long userId,
                                @Param("dishId") Long dishId);

    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 查询当前用户购物车（带菜品信息）
     */
    List<CartItemVO> selectDetailByUserId(@Param("userId") Long userId);
}
