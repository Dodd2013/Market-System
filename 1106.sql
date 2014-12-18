create database ERPdb
use ERPdb
drop table jurisdictionTB
drop table usersTB
create table usersTB(   --账户表
id int primary key identity(1,1) not null,   --id
userid varchar(20) unique not null,     --用户名
userpassword varchar(50) not null,   --密码
empid int foreign key references employeeTB
)
create table organizationTB(
id varchar(20) primary key  not null,  --id
name varchar(30)

)
create table employeeTB(   --员工信息表
id int primary key  not null,  --id
employeeName varchar(50) not null,   --员工姓名
phoneNumber varchar(20) not null ,
--手机号
dateOfEntry date not null,
--入职日期
positionid int  null foreign key references positionTB(id),  
--职位
organizationid varchar(20) foreign key references organizationTB
)
drop table employeeTB
create table positionTB(   --职位表
id int primary key identity(1,1) not null,   --id
positionName varchar(50) unique not null,    --职位名称
)
drop table jurisdictionTB
create table jurisdictionTB(  --权限表
id int primary key identity(1,1) not null,  --id
userid varchar(20) foreign key references usersTB(userid), --对应账户名
limitsName varchar(50) not null,    --权限名称
moduleid int not null foreign key references moduleTB(moduleid),
isposition bit default 'false' not null,    --是否为系统职位
UNIQUE( moduleid,userid)
)
create table moduleTB(   --模块表
id int identity(1,1) not null,  --id
moduleName varchar(100), --模块名
descb varchar(500) not null,     --模块说明
moduleid int primary key not null,   --导航数
moduleGoup varchar(20) not null     --模块组  100人力，200，300，400，500，600，700，800，900

)
select * from moduleTB
delete  moduleTB
delete jurisdictionTB
select * from usersTB a join employeeTB b on a.empid=b.id  where userid=1
select a.moduleid,b.moduleName,b.moduleGoup from jurisdictionTB a join moduleTB b on a.moduleid=b.moduleid where userid= '1' order by moduleGoup
insert into moduleTB values('添加员工','添加员工模块',2002,'200其他')
insert into moduleTB values('添加员工','添加员工模块',1001,'100人力')
insert into moduleTB values('添加员工1','添加员工模块',3002,'300销售')
insert into jurisdictionTB values('1','超级管理',1001,'false')
insert into jurisdictionTB values('1','ewr',3002,'false')
drop table moduleTB
drop table usersTB
drop database ERPdb
insert into organizationTB values('00000','系统技术部门')
insert into employeeTB values(1,'Dodd','15066817901',GETDATE(),null,'00000')
insert into usersTB values('2','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',1)
delete usersTB
select * from usersTB
sp_help
select * from sysobjects where xtype='u'--查看当前数据库所有表
 select getdate()
 SELECT * FROM [sys].[fn_dblog](NULL,NULL)
