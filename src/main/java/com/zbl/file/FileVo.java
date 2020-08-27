package com.zbl.file;

/**
 * @Author: zbl
 * @Date: Created in 9:26 2020/8/26
 * @Description:
 * @Version: $
 */
public class FileVo {

    String fileName;

    String fileSize;

    public FileVo(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;

    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
