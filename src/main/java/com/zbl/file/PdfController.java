package com.zbl.file;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: zbl
 * @Date: Created in 14:11 2020/8/28
 * @Description:
 * @Version: $
 */
@Controller
@RequestMapping("/pdf")
public class PdfController {


    @GetMapping
    public String toPage() {
        return "pdf";
    }



    @GetMapping("/1")
    public String toPage1() {
        return "pdf1";
    }

    @GetMapping("/2")
    public String toPage2() {
        return "pdf2";
    }

    @RequestMapping("test")
    public void fetchFile(HttpServletResponse httpServletResponse) throws IOException {
        File pdf = ResourceUtils.getFile("classpath:file/java.pdf");
        FileInputStream fis = new FileInputStream(pdf);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buffer = new byte[bis.available()];
        bis.read(buffer);

        String fileName = pdf.getName();
        String fileType = "pdf";//fileName.substring(fileName.indexOf(".") + 1);

        httpServletResponse.reset();
        // 设置response的Header
        httpServletResponse.addHeader("Content-Disposition",
                "inline;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1"));
        httpServletResponse.addHeader("Content-Length", "" + buffer.length);

        OutputStream toClient = new BufferedOutputStream(httpServletResponse.getOutputStream());
        httpServletResponse.setContentType("application/" + fileType);
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

}
