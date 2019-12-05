# jfoenix-javafx-mysql-schoollibrary
软件工程课程设计-Gdufe图书管理系统

## 项目说明
本项目采用的是maven项目结构；

编辑器：IDEA 2019.2.4；

数据库和版本号：MySQL5.7；

java环境：JDK1.8；

使用JDBC数据库连接池操作数据库

javafx 图形化工具：JavaFX Scene Builder 8.5.0


## 可选配置
1、在自己的服务器（例如我的是centos7.x）搭建好Docker环境，先拉取一个MySQL5.7的镜像：`docker pull mysql:5.7`；

2、运行一个MySQL容器实例：`docker run -p 3306（服务器上的某个端口）:3306 --name mysql57 -e MYSQL_ROOT_PASSWORD=登录密码（自己设置） -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci`；

3、开启服务器防火墙：`systemctl start firewalld`，查看防火墙状态：`systemctl status firewalld`，然后永久开启一下前面在服务器上设置的某个端口：`firewall-cmd --zone=public --add-port=要开放的某个端口（例如3306）/tcp --permanent`，重新载入配置文件： `firewall-cmd --reload`，接下来使用navicat软件连接即可。

4、注意：配置文件设置url=jdbc:mysql://服务器ip:某个端口/数据库名?useSSL=false&characterEncoding=utf-8&autoReconnect=true


## 快速上手
1、在本地或者服务器上搭建好MySQL环境

2、使用navicat软件（或其他SQL图形化工具）连接MySQL；

3、在MySQL连接中创建一个新的数据库；

4、选择创建的数据库并运行项目中的library.sql文件，刷新后即可创建成功；

5、将schoollibrary项目整个文件import到IDEA中，更改一下schoolLibrary\src\main\resources\config\druid.properties文件中的参数，点击运行即可。

## 项目打包教程
如何简单地使用idea将javafx项目打包成jar文件，以及使用exe4j软件将jar打包成exe程序？
[av76761111](https://www.bilibili.com/video/av76761111/)
