package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    int batchInsert(@Param("items") List<OrderItem> items);

    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    OrderItem selectOneByOrderIdAndDishId(@Param("orderId") Long orderId,
                                          @Param("dishId") Long dishId);

    List<Long> listDishIdsBySales(@Param("num") Long num);
}
