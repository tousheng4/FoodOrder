package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    int insert(OrderInfo orderInfo);

    OrderInfo selectById(@Param("id") Long id);

    OrderInfo selectByIdAndUserId(@Param("id") Long id,
                                  @Param("userId") Long userId);

    List<OrderInfo> selectPageByUserId(@Param("userId") Long userId,
                                       @Param("status") Integer status,
                                       @Param("offset") Integer offset,
                                       @Param("size") Integer size);

    Long countByUserId(@Param("userId") Long userId,
                       @Param("status") Integer status);

    int updateStatusToPaid(@Param("id") Long id);

    int updateStatusToCancelled(@Param("id") Long id,
                                @Param("reason") String reason);

    List<OrderInfo> selectPageAdmin(@Param("orderNo") String orderNo,
                                    @Param("userId") Long userId,
                                    @Param("status") Integer status,
                                    @Param("from") LocalDateTime from,
                                    @Param("to") LocalDateTime to,
                                    @Param("offset") Integer offset,
                                    @Param("size") Integer size);

    Long countAdmin(@Param("orderNo") String orderNo,
                    @Param("userId") Long userId,
                    @Param("status") Integer status,
                    @Param("from") LocalDateTime from,
                    @Param("to") LocalDateTime to);

    Double countTotalSales();

    int updateStatusToCompleted(@Param("id") Long id);

    /**
     * 管理员取消订单（不按 userId 限制）
     */
    int adminCancelOrder(@Param("id") Long id,
                         @Param("reason") String reason);
}
