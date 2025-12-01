package cugb.ai.foodorder.dto;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

    /**
     * 新的数量，如果 <=0，则可以视为删除
     */
    @Min(value = 0, message = "数量不能为负数")
    private Integer quantity;

    /**
     * 是否选中（可选），不传则不改
     * 0=不选中, 1=选中
     */
    private Integer checked;
}
