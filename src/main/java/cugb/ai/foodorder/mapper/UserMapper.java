package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);

    User selectById(@Param("id") Long id);

    int insert(User user);

    int updateLastLoginAt(@Param("id") Long id);

    List<User> selectPage(@Param("username") String username,
                          @Param("role") Integer role,
                          @Param("status") Integer status,
                          @Param("offset") Integer offset,
                          @Param("size") Integer size);

    Long count(@Param("username") String username,
               @Param("role") Integer role,
               @Param("status") Integer status);

    int updateStatus(@Param("id") Long id,
                     @Param("status") Integer status);

    int updateRole(@Param("id") Long id,
                   @Param("role") Integer role);
}
