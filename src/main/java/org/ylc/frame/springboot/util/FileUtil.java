package org.ylc.frame.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ylc.frame.springboot.setting.exception.OperationException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 文件工具类
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/4
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final String[] validFileExtension = {".jpg", ".png"};

    /**
     * 上传文件
     *
     * @param file     文件
     * @param filePath 本地磁盘路径
     * @param fileName 文件名称
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) {

        isValidFile(fileName);

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            if (!targetFile.mkdirs()) {
                throw new OperationException("创建文件失败");
            }
        }
        try (FileOutputStream out = new FileOutputStream(filePath + fileName)) {
            out.write(file);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new OperationException("上传失败");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param filePath 本次磁盘路径
     * @param response response
     */
    public static void downLoadFile(String fileName, String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        if (file.exists()) {
            try (OutputStream outputStream = response.getOutputStream(); FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
                response.setCharacterEncoding("UTF-8");
                // 设置强制下载不打开            
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
                response.addHeader("Content-Transfer-Encoding", "binary");
                byte[] buffer = new byte[1024];
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                logger.error("下载文件失败", e);
            }
        } else {
            logger.error("文件[{}]不存在", filePath);
        }
    }

    /**
     * 文件类型校验
     *
     * @param fileName 文件名
     */
    private static void isValidFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        if (!Arrays.asList(validFileExtension).contains(extension.toLowerCase())) {
            throw new OperationException("【" + extension + "】该文件类型不允许上传。");
        }
    }
}
