package com.zbl.file;

/**
 * @Author: zbl
 * @Date: Created in 9:26 2020/8/26
 * @Description:
 * @Version: $
 */
public class FileVo {
    public FileVo(String fileName) {
        this.fileName = fileName;
    }

    String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
