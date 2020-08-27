package com.zbl.file;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.zbl.enums.DownloadType;
import com.zbl.util.DownloadUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zbl
 * @Date: Created in 17:01 2020/8/25
 * @Description:
 * @Version: $
 */
@Controller
@RequestMapping("/download")
public class DownLoadController {

    @Value("${fileDirectory}")
    private String fileDirectory;

    @GetMapping
    public String toPage() {
        return "download";
    }

    @GetMapping("list")
    @ResponseBody
    public ResultInfo<List<FileVo>> list() {
        File fileList = new File(fileDirectory);
        File[] files = fileList.listFiles();
        List<String> collect = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        List<FileVo> fileVoList = new ArrayList();
        collect.forEach(c -> fileVoList.add(new FileVo(c)));
        return new ResultInfo<>(fileVoList);
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(String fileName) {
        File dest = new File(fileDirectory + "/" + fileName);
        dest.delete();
        return new ResultInfo<>("0", "删除成功");
    }


    /**
     * 下载
     */
    @GetMapping(value = "/downloadFile")
    public void downloadFile(String fileName, Integer code, HttpServletRequest request, HttpServletResponse response) {
        try {
//            String uuid = UUID.randomUUID().toString();
//            String srcPath = System.getProperty("java.io.tmpdir") + "/" + uuid + "_" + fileName;
            File srcFile = new File(fileDirectory + fileName);
            if (!srcFile.exists()) {
                System.out.println("文件下载失败，原因是：文件不存在");
            }
            DownloadUtil.writeFileToResponse(request, response, fileName, srcFile, DownloadType.getEnum(code));
            //srcFile.delete();
        } catch (Exception e) {
            System.out.println(String.format("文件下载失败，原因是：%s", e));
            e.printStackTrace();
        }
    }

    /**
     * 下载
     */
    @GetMapping(value = "/downloadFile/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            File srcFile = new File(fileDirectory+ fileName);
            if (!srcFile.exists()) {
                System.out.println("文件下载失败，原因是：文件不存在");
            }
            DownloadUtil.writeFileToResponse(request, response, fileName, srcFile, DownloadType.UriOs);
            //srcFile.delete();
        } catch (Exception e) {
            System.out.println(String.format("文件下载失败，原因是：%s", e));
            e.printStackTrace();
        }
    }

    /**
     * 下载
     */
    @GetMapping(value = "/downloadFileTest/{fileName}")
    public void downloadTest(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
//            File srcFile = new File(fileDirectory+ fileName);
            File srcFile = new File(fileDirectory+ "run.sh");
            if (!srcFile.exists()) {
                System.out.println("文件下载失败，原因是：文件不存在");
            }
            DownloadUtil.writeFileToResponse(request, response, "run.sh", srcFile, DownloadType.UriOs);
            //srcFile.delete();
        } catch (Exception e) {
            System.out.println(String.format("文件下载失败，原因是：%s", e));
            e.printStackTrace();
        }
    }

    public InputStream getFileInputStream(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream("file/" + fileName);
    }






}
