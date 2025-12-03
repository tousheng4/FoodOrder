package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectAllEnabled();

    Category selectById(@Param("id") Long id);
}