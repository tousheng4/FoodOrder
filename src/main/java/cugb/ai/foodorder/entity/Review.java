package cugb.ai.foodorder.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {

    private Long id;

    private Long userId;

    private Long orderId;

    private Long orderItemId;

    private Long dishId;

    /** 评分 1~5 */
    private Integer rating;

    /** 评价内容 */
    private String content;

    /** 预留：图片URL列表（逗号分隔 或 JSON） */
    private String images;

    /** 1=显示，0=隐藏 */
    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
