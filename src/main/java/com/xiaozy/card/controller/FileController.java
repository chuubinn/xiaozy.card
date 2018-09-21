package com.xiaozy.card.controller;

/**
 *数据库不能存图片，一般的做法是将图片保存在服务器的文件夹里，然后在数据库里存那个文件夹的地址
 */
import com.xiaozy.card.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/user/*")
public class FileController {


    //跳转到上传的页面
    @GetMapping("/gouploadimg")
    public String goUploadImg(){
        //跳转到 templates 目录下的 uploadimg.html
        return "uploadimg";
    }


    //处理上传文件
    @PostMapping("/testuploadimg")
    public @ResponseBody  String uploadImg(@RequestParam("file") MultipartFile file,
                                         HttpServletRequest request){
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try{
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        }catch(Exception e){
             //TODO：handle exception
        }
        //返回Json
        return "uploadimg success";
    }
}
