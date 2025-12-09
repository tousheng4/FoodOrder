package cugb.ai.foodorder.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserInfoRequest {

    private String nickname;

    private MultipartFile avatarFile;
}
