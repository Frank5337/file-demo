package com.zbl.util;

import com.zbl.enums.DownloadType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.zip.GZIPOutputStream;

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

        if (downloadType == DownloadType.GZip) {
            System.out.println("压缩前的数据大小：" + file.length());
            ByteArrayOutputStream bout = new ByteArrayOutputStream();  //缓冲流
            GZIPOutputStream gout = new GZIPOutputStream(bout);   //压缩流
            try {
                gout.write(fileToByte(file));      //获取到数据自动压缩，压缩到缓冲流中
                gout.close();                //压缩流一关就会进入到缓冲流中
                byte gzip[] = bout.toByteArray(); //得到压缩后的数据
                System.out.println("压缩后的数据大小：" + gzip.length);

                //通知浏览器数据采用的压缩格式（设置Http响应中的消息头）
                response.setHeader("Content-Encoding", "gzip");

                //通知浏览器回送压缩后数据的长度（设置Http响应中的消息头）
                response.setHeader("Content-Length", gzip.length + "");

                response.getOutputStream().write(gzip);

            } catch (Exception e) {
                // do nothing
            } finally {
                gout.close();
            }

        } else {
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
                break;
            case GZip:
                response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.addHeader("Content-Type", "application/octet-stream;charset=UTF-8");
                //response.addHeader("Content-Encoding", "gzip");
                break;
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

    public static String fileSizeByteToM(long fileLength) {
        long size = 0;
        size = fileLength;

        BigDecimal fileSize = new BigDecimal(size);
        BigDecimal param = new BigDecimal(1024);
        int count = 0;
        while (fileSize.compareTo(param) > 0 && count < 5) {
            fileSize = fileSize.divide(param);
            count++;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(fileSize) + "";
        switch (count) {
            case 0:
                result += "B";
                break;
            case 1:
                result += "KB";
                break;
            case 2:
                result += "MB";
                break;
            case 3:
                result += "GB";
                break;
            case 4:
                result += "TB";
                break;
            case 5:
                result += "PB";
                break;
        }
        return result;
    }

    public static byte[] fileToByte(File file) {
        byte[] buffer = null;
        try {
            if (!file.exists()) {
                return null;
            }

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
