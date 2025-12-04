package cugb.ai.foodorder.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * OSS 文件存储服务接口
 */
public interface OssService {

    /**
     * 上传文件
     *
     * @param file     文件
     * @param folder   文件夹路径（可选，如 "dish/", "avatar/" 等）
     * @return 文件访问 URL
     */
    String uploadFile(MultipartFile file, String folder);

    /**
     * 上传文件流
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @param folder      文件夹路径（可选）
     * @return 文件访问 URL
     */
    String uploadFile(InputStream inputStream, String fileName, String folder);

    /**
     * 删除文件
     *
     * @param fileUrl 文件 URL
     * @return 是否删除成功
     */
    boolean deleteFile(String fileUrl);

    /**
     * 根据文件路径删除文件
     *
     * @param objectName 文件在 OSS 中的完整路径（对象名）
     * @return 是否删除成功
     */
    boolean deleteFileByPath(String objectName);
}
