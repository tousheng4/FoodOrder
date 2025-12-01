package cugb.ai.foodorder.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewVO {

    private Long id;

    private Long userId;

    private String username;

    private String nickname;

    private Long dishId;

    private String dishName;

    private Integer rating;

    private String content;

    private LocalDateTime createdAt;
}
