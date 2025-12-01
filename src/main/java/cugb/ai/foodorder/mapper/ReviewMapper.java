package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.Review;
import cugb.ai.foodorder.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    int insert(Review review);

    Review selectByUserAndOrderAndDish(@Param("userId") Long userId,
                                       @Param("orderId") Long orderId,
                                       @Param("dishId") Long dishId);

    Long countByDish(@Param("dishId") Long dishId,
                     @Param("status") Integer status);

    List<ReviewVO> listByDish(@Param("dishId") Long dishId,
                              @Param("status") Integer status,
                              @Param("offset") Integer offset,
                              @Param("size") Integer size);

    Long countByUser(@Param("userId") Long userId);

    List<ReviewVO> listByUser(@Param("userId") Long userId,
                              @Param("offset") Integer offset,
                              @Param("size") Integer size);

    int updateStatus(@Param("id") Long id,
                     @Param("status") Integer status);

    int deleteById(@Param("id") Long id);

    /**
     * 后台按条件查评价
     */
    Long countAdmin(@Param("dishId") Long dishId,
                    @Param("userId") Long userId);

    List<ReviewVO> listAdmin(@Param("dishId") Long dishId,
                             @Param("userId") Long userId,
                             @Param("offset") Integer offset,
                             @Param("size") Integer size);
}
