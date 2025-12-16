package cugb.ai.foodorder.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 文件存储服务实现
 */
@Slf4j
@Service
public class AliyunOssServiceImpl implements OssService {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 创建 OSS 客户端
     */
    private OSS createOssClient() {
        return new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            String fileName = generateUniqueFileName(extension);

            // 如果指定了文件夹，则添加文件夹前缀
            String objectName = StringUtils.hasText(folder)
                    ? folder + (folder.endsWith("/") ? "" : "/") + fileName
                    : fileName;

            return uploadFile(file.getInputStream(), objectName, null);
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String fileName, String folder) {
        OSS ossClient = null;
        try {
            // 如果指定了文件夹，则添加文件夹前缀
            String objectName = StringUtils.hasText(folder)
                    ? folder + (folder.endsWith("/") ? "" : "/") + fileName
                    : fileName;

            ossClient = createOssClient();

            // 创建上传请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossProperties.getBucketName(),
                    objectName,
                    inputStream
            );

            // 上传文件
            ossClient.putObject(putObjectRequest);

            // 返回文件访问 URL
            String fileUrl = ossProperties.getUrlPrefix() + "/" + objectName;
            log.info("文件上传成功: {}", fileUrl);
            return fileUrl;

        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        if (!StringUtils.hasText(fileUrl)) {
            return false;
        }

        try {
            // 从完整 URL 中提取对象名称
            String objectName = extractObjectNameFromUrl(fileUrl);
            return deleteFileByPath(objectName);
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean deleteFileByPath(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            return false;
        }

        OSS ossClient = null;
        try {
            ossClient = createOssClient();
            ossClient.deleteObject(ossProperties.getBucketName(), objectName);
            log.info("文件删除成功: {}", objectName);
            return true;
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 生成唯一文件名
     */
    private String generateUniqueFileName(String extension) {
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (!StringUtils.hasText(filename)) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        return lastDotIndex > 0 ? filename.substring(lastDotIndex) : "";
    }

    /**
     * 从完整 URL 中提取对象名称
     */
    private String extractObjectNameFromUrl(String fileUrl) {
        if (!StringUtils.hasText(fileUrl)) {
            return "";
        }

        String urlPrefix = ossProperties.getUrlPrefix();
        if (fileUrl.startsWith(urlPrefix)) {
            return fileUrl.substring(urlPrefix.length() + 1);
        }

        // 如果不是以配置的前缀开头，尝试从 URL 中提取路径部分
        int lastSlashIndex = fileUrl.lastIndexOf("/");
        return lastSlashIndex > 0 ? fileUrl.substring(lastSlashIndex + 1) : fileUrl;
    }
}
