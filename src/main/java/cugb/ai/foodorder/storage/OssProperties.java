package cugb.ai.foodorder.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云 OSS 配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    /**
     * OSS 访问密钥 ID
     */
    private String accessKeyId;

    /**
     * OSS 访问密钥 Secret
     */
    private String accessKeySecret;

    /**
     * OSS 端点（地域节点）
     */
    private String endpoint;

    /**
     * OSS 存储桶名称
     */
    private String bucketName;

    /**
     * 文件访问地址前缀
     */
    private String urlPrefix;
}
