# file-demo file upload &amp; download

### 上传：
```
1、通过multipart/from-data的传输类型上传一个文件。
2、通过multipart/from-data的传输类型上传多个文件。
3、通过octect-stream的传输类型上传文件，文件名在url参数中
4、通过octect-stream的传输类型上传文件，文件名在request头中
5、通过octect-stream的传输类型上传文件，文件名在uri中
```

### 上传后文件显示在文件列表中。提供以下下载方式
```
1、文件名在response头中，传输类型为octect-stream
2、文件名在response头中，传输类型为MIME
3、文件名在uri末尾，传输类型为octect-stream
```
