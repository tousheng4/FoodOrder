package cugb.ai.foodorder.mapper;

import cugb.ai.foodorder.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    int insert(Address address);

    int update(Address address);

    Address selectByIdAndUserId(@Param("id") Long id,
                                @Param("userId") Long userId);

    List<Address> selectByUserId(@Param("userId") Long userId);

    Address selectDefaultByUserId(@Param("userId") Long userId);

    int clearDefaultByUserId(@Param("userId") Long userId);

    int softDeleteByIdAndUserId(@Param("id") Long id,
                                @Param("userId") Long userId);
}
