# 根据multipart/from-data & octect-stream 多种方式上传下载文件

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
4、文件名在response头中，传输类型为octect-stream, http压缩下载
```

部署:
```
修改application.yml 的 fileDirectory 参数 为 linux中文件目录
先从docker.hub 拉取tomcat 1.9的docker镜像
docker pull tomcat (默认最新版本)
-v 挂载
  --创建目录(自定义即可, 挂载tomcat war包) /home/zbl/fileDemo/webapps
-p 端口映射
-d 后台运行, 守护式进程
--name docker实例名称 
docker run -p 5337:8080 -d --name fileDemo -v /home/zbl/fileDemo/webapps:/usr/local/tomcat/webappps tomcat:1.9
```
