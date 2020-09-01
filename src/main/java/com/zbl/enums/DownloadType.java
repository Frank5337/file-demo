package com.zbl.enums;

/**
 * @Author: zbl
 * @Date: Created in 10:36 2020/8/26
 * @Description:
 * @Version: $
 */
public enum DownloadType {
//    1、文件名在response头中，传输类型为octect-stream
//    2、文件名在response头中，传输类型为MIME
//    3、文件名在uri末尾，传输类型为octect-stream
    RsOs(0, "文件名在response头中，传输类型为octect-stream"),
    RsMIME(1, "文件名在response头中，传输类型为MIME"),
    UriOs(2, "文件名在uri末尾，传输类型为octect-stream"),
    GZip(3, "Accept-Encoding:gzip HTTP压缩传输");

    private final Integer code;

    private final String msg;

    DownloadType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static DownloadType getEnum(Integer code) {
        return DownloadType.values()[code];
    }
}
