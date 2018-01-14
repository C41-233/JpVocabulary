# JpVocabulary
简单的日语学习网站，包含汉字、单词、常用语句、常用语法的搜集。

## 需要配置的环境
* **Jdk8**  
	需要配置相关环境变量。
* **Mysql**  
	https://dev.mysql.com/downloads/mysql/  
	有关配置可以在project/conf/application.conf中更改，此处以默认配置为例。  
	* 创建数据库jpndb和jpndb_test，后者在debug模式中使用。  
	```SQL
	CREATE DATABASE jpndb;  
	CREATE DATABASE jpndb_test;  
	```
	* 创建用户jpndb，密码为123456。
	```SQL
	CREATE USER 'jpndb'@'localhost' IDENTIFIED BY '123456';
	GRANT ALL PRIVILEGES ON `jpndb\_test`.* TO 'jpndb'@'localhost';
	GRANT ALL PRIVILEGES ON `jpndb`.* TO 'jpndb'@'localhost';
	```
	* 导入初始数据db/data.sql  
	```SQL
	SOURCE D:/JpVocalublary/db/data.sql
	```
* **Ant**  
	http://ant.apache.org/bindownload.cgi  
	需要配置相关环境变量，项目通过Ant构建。

## 运行
执行build.bat，等待构建  
执行start.bat，随后访问 http://127.0.0.1:9000/ 可以看到主界面，第一次因为要编译可能要等一段时间。
  
## 文档
API for c41-base https://c41-233.github.io/JpVocabulary/base/doc/api/index.html
API for simple-xml https://c41-233.github.io/JpVocabulary/simple-xml/doc/api/index.html