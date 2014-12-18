此程序用netbeans开发，netbeans可以直接打开项目用eclipse的需要手动操作
文件说明
\
1106.sql 数据库相关表（未完善）  
目前程序中只用到users表，所以只需要创建users表即可，此文件中包含insert一条记录，帐号为1，密码为111111
可以创建完users表后插入该数据，即可用该账户登录

Dodd's ERP\
mysql-connector-java-5.1.9.jar 
     mysql数据库驱动
sqljdbc4.jar  
     sql数据库驱动
config.properties 
	此文件为配置文件，可用记事本打开（注该文件回车为liunx格式，用记事本打开看不见换行，可以用netbeans或者eclipse打开）
     内有各项配置说明，尤其是数据库密码的设置。
dist\
内有生成的jar包，可直接打开程序
src\ 源码目录
   DataBasePkg\
	ConnectedDatabase.java  连接数据库类，生成的对像保存在全局类DataOnly中
   DataReadPkg\
	ConfigRead.java   用来读取配置文件
	DataOnly.java 全局类，储存各个全局变量，有部分变量需要InitAll初始化
   Image\
	顾名思义吧，图片文件夹
   Mainpkg\
	Start.java 程序入口 main函数所在类
	InitAll.java   初始化类
	LoginMain.java   登录类
	MainFrame.java   主程序窗口类
	TabPanel.java   自定义JTabbedPanel类所需类
   Toospkg\
    CheckString.java  检查字符是否合法类，目前只写了检查密码合法
	EncoderHandler.java   密码加密类
	SelectAllWhileGetFocusJTF  当JTextFile获取焦点时设置自动全选类（感觉这个以后用的很多，就独立写出来）
	ToolsClass.java    主工具类，目前写了获取数据库服务器时间函数

	
	
另，程序目前状态是
	sql数据库登录帐号sa密码123456   //可以改，在config.properties 文件中
	当然你要先建一个数据库，名为ERPdb，代码都在1106.sql里了







1108更新说明：
	MainFrame 的构建
	JTabbedPanel的自定义
	增加了获取数据库时间的小工具
	将密码改为SHA1加密
   