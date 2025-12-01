package cugb.ai.foodorder.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminSaveDishRequest {

    @NotBlank(message = "菜品名称不能为空")
    private String name;

    private String description;

    private String image;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /** 0=下架，1=上架 */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
