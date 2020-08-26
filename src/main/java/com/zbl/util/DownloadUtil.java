package com.zbl.util;

import com.zbl.enums.DownloadType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author: zbl
 * @Date: Created in 10:48 2020/8/26
 * @Description:
 * @Version: $
 */
public class DownloadUtil {

    /**
     * 把文件写到response输出流
     *
     * @param request
     * @param response
     * @param fileName
     * @param file
     * @throws IOException
     */
    public static void writeFileToResponse(HttpServletRequest request, HttpServletResponse response, String
            fileName, File file, DownloadType downloadType) throws IOException {
        if (file == null || !file.exists()) {
            return;
        }
        dressResponseHeader(request, response, fileName, downloadType);
        response.addHeader("Content-Length", String.valueOf(file.length()));
        byte[] buffer = new byte[8192];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            OutputStream outputStream = response.getOutputStream();
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            if (bis != null) {
                bis.close();
            }
        }

    }

    public static void dressResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName, DownloadType downloadType)
            throws UnsupportedEncodingException {
        if (HttpUtils.isMSBrowser(request)) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        fileName = fileName.replaceAll("\\+", "%20");    //处理空格转为加号的问题
        response.addHeader("Cache-Control", "private");
        switch (downloadType) {
            case RsOs:
                response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.addHeader("Content-Type", "application/octet-stream;charset=UTF-8");
                break;
            case RsMIME:
                response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.addHeader("Content-Type", MimeTypeUtils.getType(fileName) + ";charset=UTF-8");
                break;
            case UriOs:
                response.addHeader("Content-Disposition", "attachment;");
                response.addHeader("Content-Type", "application/octet-stream;charset=UTF-8");
            default:
                break;
        }

    }

    /**
     * 根据request判断是否是ie/edge浏览器的工具类
     */
    static class HttpUtils {
        private static final String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

        public static boolean isMSBrowser(HttpServletRequest request) {
            if (request == null) {
                return false;
            }
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null) {
                return false;
            }
            for (String signal : IEBrowserSignals) {
                if (userAgent.contains(signal))
                    return true;
            }
            return false;
        }
    }

}
