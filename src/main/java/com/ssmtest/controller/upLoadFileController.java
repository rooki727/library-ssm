package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@CrossOrigin
@Controller
@RequestMapping("/file")
public class upLoadFileController {
    private static final String UPLOAD_DIR = "E:\\library-ssm\\bookPicture";
    @PostMapping("/uploadBookMainPicture")
    @ResponseBody
    public ApiResponse<String> uploadAvatar(@RequestParam("image") MultipartFile file) {
        ApiResponse<String> response = new ApiResponse<>();
        if (file.isEmpty()) {
            response.setResult("uploadFile null");
            return response;
        }
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 确定存储路径
            String filePath = UPLOAD_DIR + File.separator + originalFilename;
            File dest = new File(filePath);

            // 检查目标文件夹是否存在，不存在则创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 存储文件
            file.transferTo(dest);

            // 构造完整的URL路径
            String avatarUrl = "http://localhost:8080/bookPicture/" + originalFilename;

            // 返回成功信息和完整的URL路径
            response.setCode("1");
            response.setMsg("uploadFile Success");
            response.setResult(avatarUrl);
            return response;
        } catch (Exception e) {
            response.setResult("uploadFile error");
            return response;
        }
    }
}
