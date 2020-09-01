package com.zbl.file;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
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

    @RequestMapping("fetchFile")
    public void fetchFile(HttpServletResponse httpServletResponse) throws IOException {
        File pdf = ResourceUtils.getFile("classpath:file/java.pdf");
        FileInputStream fis = new FileInputStream(pdf);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buffer = new byte[bis.available()];
        bis.read(buffer);

        String fileName = pdf.getName();
        httpServletResponse.reset();
        // 设置response的Header
        httpServletResponse.addHeader("Content-Disposition",
                "inline;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1"));
        httpServletResponse.addHeader("Content-Length", "" + buffer.length);

        OutputStream toClient = new BufferedOutputStream(httpServletResponse.getOutputStream());
        httpServletResponse.setContentType("application/pdf");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

}
