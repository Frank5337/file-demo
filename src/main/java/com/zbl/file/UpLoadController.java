package com.zbl.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @Author: zbl
 * @Date: Created in 17:01 2020/8/25
 * @Description:
 * @Version: $
 */
@Controller
@RequestMapping("/upload")
public class UpLoadController {

    @Value("${fileDirectory}")
    private String fileDirectory;

    @GetMapping
    public String toPage() {
        return "upload";
    }

    /**
     * 通过multipart/from-data的传输类型上传一个文件。
     */
    @PostMapping(value = "/upload1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultInfo<String> upload1(@RequestPart(value = "file", required = false) MultipartFile file) {
        upload(file, file.getOriginalFilename());
        return new ResultInfo<>("0","上传成功");

    }

    /**
     * 通过multipart/from-data的传输类型上传多个文件
     */
    @PostMapping(value = "/upload2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResultInfo<String> upload2(@RequestPart(value = "file", required = false) MultipartFile[] file) {
        for (MultipartFile f : file) {
            upload(f, f.getOriginalFilename());
        }
        return new ResultInfo<>("0","上传成功");

    }

    /**
     * 通过octect-stream的传输类型上传文件，文件名在url参数中
     */
    @PostMapping(value = "/upload3", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResultInfo<String> upload3(HttpServletRequest request, String fileName) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        MultipartFile file = new MockMultipartFile(fileName, inputStream);
        upload(file, fileName);
        return new ResultInfo<>("0","上传成功");

    }

    /**
     * 通过octect-stream的传输类型上传文件，文件名在request头中
     */
    @PostMapping(value = "/upload4", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResultInfo<String> upload4(HttpServletRequest request) throws IOException {
        String fileName = request.getHeader("fileName");
        fileName = URLDecoder.decode(fileName, "UTF-8");
        ServletInputStream inputStream = request.getInputStream();
        MultipartFile file = new MockMultipartFile(fileName, inputStream);
        upload(file, fileName);
        return new ResultInfo<>("0","上传成功");

    }

    /**
     * 通过octect-stream的传输类型上传文件，文件名在uri中
     */
    @PostMapping(value = "/upload5/{fileName}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResultInfo<String> upload5(HttpServletRequest request, @PathVariable("fileName") String fileName) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        MultipartFile file = new MockMultipartFile(fileName, inputStream);
        upload(file, fileName);
        return new ResultInfo<>("0","上传成功");

    }

    public void upload(MultipartFile file, String fileName) {
        File dest = new File(fileDirectory + "/" + fileName);
        //如果文件目录不存在，创建目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            System.out.println("创建目录" + dest);
        }

        try {
            //先删除旧的文件
            dest.delete();
            file.transferTo(dest);
        } catch (IOException e) {
            System.out.println("文件上传失败" + dest);
            e.printStackTrace();
        }
    }

    public static boolean uploadFile(byte[] file, String filePath, String fileName) {
        //默认文件上传成功
        boolean flag = true;
        //new一个文件对象实例
        File targetFile = new File(filePath);
        //如果当前文件目录不存在就自动创建该文件或者目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        try {
            //通过文件流实现文件上传
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
            fileOutputStream.write(file);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在异常");
            flag = false;
        } catch (IOException ioException) {
            System.out.println("javaIO流异常");
            flag = false;
        }
        return flag;
    }
}
