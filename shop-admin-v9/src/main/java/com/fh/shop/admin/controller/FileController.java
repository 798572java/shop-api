package com.fh.shop.admin.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.OssUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadImage(@RequestParam MultipartFile image, HttpServletRequest request){
        Map map=new HashMap();

        try {
            //获取io流
            InputStream inputStream = image.getInputStream();
            //获取源文件名称
            String originalFilename = image.getOriginalFilename();
            //获取文件路径
            String realPath = request.getServletContext().getRealPath(SystemConstant.UPLOAD);
            //工具类 进行重命名并进行复制
            String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, realPath);
            //将文件路径和新的文件名称进行拼接返回到前台
            map.put("data", SystemConstant.UPLOAD+uploadedFileName);


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return  map;
    }


    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam MultipartFile file, HttpServletRequest request){
        try {
            //获取io流
            InputStream inputStream = file.getInputStream();
            //获取源文件名称
            String originalFilename = file.getOriginalFilename();
            //工具类 进行重命名并进行复制
            String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, SystemConstant.UPLOAD_EXCEL);
            //将文件路径和新的文件名称进行拼接返回到前台
            return  ServerResponse.success(SystemConstant.UPLOAD_EXCEL+uploadedFileName);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/uploadBatchImage",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadBatchImage(@RequestParam MultipartFile [] image, HttpServletRequest request){
        try {
            StringBuilder stringBuilder=new StringBuilder();
            for (MultipartFile o : image) {
                //获取io流
                InputStream inputStream = o.getInputStream();
                //获取源文件名称
                String originalFilename = o.getOriginalFilename();
//                //获取文件路径
//                String realPath = request.getServletContext().getRealPath(SystemConstant.UPLOAD);
//                //工具类 进行重命名并进行复制
//                String uploadedFileName = FileUtil.copyFile(inputStream, originalFilename, realPath);
//                //将文件路径和新的文件名称进行拼接返回到前台
                String uploadedFileName = OssUtil.fileOss(originalFilename, inputStream);

                stringBuilder.append(","+uploadedFileName);
            }
            return ServerResponse.success(stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }






}
