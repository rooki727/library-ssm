package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping("/file")
public class UploadFileController {

//    @Value("${file.upload-dir}")
//    private String uploadDir;

    private final ServletContext servletContext;

    public UploadFileController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostMapping("/uploadPicture")
    @ResponseBody
    public ApiResponse<String> uploadAvatar(@RequestParam("image") MultipartFile file, HttpServletRequest request) {
        ApiResponse<String> response = new ApiResponse<>();
        if (file.isEmpty()) {
            response.setCode("-1");
            response.setMsg("File is empty");
            return response;
        }

        try {
            // Generate unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
             //设置图片上传路径
            // 获取名为static的文件夹的路径
            String Local_url = request.getSession().getServletContext().getRealPath("/static");
 
            // Resolve the full file path
            String filePath = Local_url + File.separator + fileName;
            Path destPath = Paths.get(filePath);
            // Create directories if they don't exist
            Files.createDirectories(destPath.getParent());

            // Save file
            file.transferTo(destPath);

            // Construct full URL path
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/static/")
                    .path(fileName)
                    .toUriString();

            // Return success response with file URL
            response.setCode("1");
            response.setMsg("File uploaded successfully");
            response.setResult(fileUrl);
        } catch (IOException e) {
            // Handle file upload error
            response.setCode("-1");
            response.setMsg("Failed to upload file: " + e.getMessage());
            response.setResult(null);
        }

        return response;
    }
    // 文件存储目录（建议通过@Value注入）
    private static final String ZIP_UPLOAD_DIR = "/static/zips/";

    @RequestMapping(value = "/uploadZip")
    @ResponseBody
    public ApiResponse<String> uploadZipFile(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        ApiResponse<String> response = new ApiResponse<>();

        try {
            // 1. 空文件校验
            if (file.isEmpty()) {
                response.setCode("-1");
                response.setMsg("文件不能为空");
                return response;
            }

            // 2. 文件类型校验
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null ||
                    (!originalFilename.toLowerCase().endsWith(".zip") &&
                            !originalFilename.toLowerCase().endsWith(".rar"))) {
                response.setCode("-1");
                response.setMsg("仅支持ZIP/RAR格式");
                return response;
            }

            // 3. 创建存储目录
            String realPath = request.getSession().getServletContext().getRealPath(ZIP_UPLOAD_DIR);
            File destDir = new File(realPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // 4. 生成唯一文件名
            String fileName = UUID.randomUUID() + "_" + originalFilename;
            File destFile = new File(destDir, fileName);

            // 5. 保存文件
            file.transferTo(destFile);

            // 6. 构造访问URL
            String baseUrl = request.getScheme() + "://" +
                    request.getServerName() + ":" +
                    request.getServerPort() +
                    request.getContextPath();

            String fileUrl = baseUrl + ZIP_UPLOAD_DIR + fileName;

            response.setCode("1");
            response.setMsg("上传成功");
            response.setResult(fileUrl);
        } catch (IOException e) {
            response.setCode("-1");
            response.setMsg("上传失败: " + e.getMessage());
        }

        return response;
    }
}
