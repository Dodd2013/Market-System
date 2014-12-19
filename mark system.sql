

--创建ERP数据库

create database ERPdb

use ERPdb

--创建公司表

create table CompanyTB(
Com_id int identity(101,1)  primary key not null,
Name_Cn varchar(40) not null,
Name_En varchar(40) null,
City varchar(10) not null,
Address_Cn varchar(50) not null,
Address_En varchar(50) null,
Front_Tel char(11) not null,
Remarks nvarchar(50) null
)


--插入数据
insert into CompanyTB values('联想','lenove','北京','北京中关村',null,'0532-125451','收购IBM PC部')

--查询
select * from CompanyTB where Com_id like '%' and Name_Cn like '%' and Name_En like '%' and city like '%' and Address_Cn like '%' and Front_Tel like '%'
update CompanyTB set Name_Cn='dfs',Name_En='gfds',city='gfds',Address_Cn='gfds',Front_Tel='gfdsgf',Address_En='gret',Remarks='gfgwe' where Com_id=125

---创建部门表


create table DepartmentTB(
Dept int identity(11,1) primary key  not null,
Dept_Name varchar(30),
Com_Id int foreign key references CompanyTB(Com_Id),
)

--创建了复合主键,但是好像问题不能解决

create table DepartmentTB(
Dept int identity(11,1)  not null,
Dept_Name varchar(30),
Com_Id int foreign key references CompanyTB(Com_Id),
Dept_Id int primary key(Com_Id,Dept) 
)

--插入数据

insert into DepartmentTB values ('人力资源部',101)


--查询数据

select * from DepartmentTB

---创建职位表


create table DesignationTB(
Des_Id int  primary key identity(1,1) not null,
Des_Name varchar(40) not null,
Des_Year int not null
)
drop table DesignationTB
drop table EmployeeTB
drop table Employee_HisTB
drop table userTB
drop table PermissionTB
drop table Per_DesTB
--插入数据

insert into DesignationTB values('人事主管',5)

--查询数据

select * from DesignationTB


--创建员工信息表

create table EmployeeTB(
Emp_Id int primary key identity(100000,1) not null,
Name_Cn varchar(30) not null,
Name_En varchar(30) null,
Emp_Dept int foreign key references DepartmentTB(Dept),
Emp_Des int foreign key references DesignationTB(Des_Id),
Gender varchar(4)  constraint CK_Gender check(Gender in ('man','woman')) not null,
Dob Date not null,
Mobile char(11) unique,
Email varchar(30) constraint CK_Email check(Email like '%@%'),
Address varchar(50) not null,
Join_Date Date
)

---插入数据测试

insert into EmployeeTB values ('唐宁','Bean',11,1,'男','1995-09-25','18363995829','1356563293@qq.com','青大汇园二号',getdate())

insert into EmployeeTB values ('张三','Bean',11,1,'男','1995-09-25','18363995429','1356563293@qq.com','青大汇园二号',getdate())

insert into EmployeeTB values ('李四','Bean',11,1,'男','1995-09-25','18363895827','1356563293@qq.com','青大汇园二号',getdate())


---查询数据

select * from EmployeeTB

select a.Emp_Id,a.Name_Cn,a.Name_En,b.Dept_Name,c.Des_Name,a.Gender,a.Dob,a.Mobile,a.Email,a.Address,a.Join_Date from EmployeeTB a 
 join DepartmentTB b on a.Emp_Dept=b.Dept join DesignationTB c on a.Emp_Des = c.Des_Id 

 --创建员工历史表

 create table Employee_HisTB(
Emp_Id int primary key not null,
Name_Cn varchar(20) not null,
Name_En varchar(20) null,
Emp_Dept int foreign key references DepartmentTB(Dept),
Emp_Des int foreign key references DesignationTB(Des_Id),
Gender varchar(4)  not null,
Dob Date not null,
Mobile char(11) unique,
Email varchar(30) ,
Address varchar(50) not null,
Join_Date Date,
Leave_Date Date
)

--员工历史表是从员工表通过触发器来实现的，不需要自增长
--员工历史表不能进行更新操作

--先创建触发器，连接员工表和员工历史表

create trigger Emp_Del on Emp


--对员工历史表不能进行更新,插入操作，创建触发器实现

create trigger Emp_His_Modity on Employee_HisTB
for update,insert
as
begin
print N'对员工历史表不能进行更新插入操作'
rollback
end


--员工记录从员工表中删除时，自动添加到员工历史表中
---在触发器中因为有另一个不能对员工历史表的数据进行更新和插入的触发器，所以会在删除数据的
--时候不成功，因此，要先把不能更新的那个触发器禁用，然后等把员工表的信息插入到员工历史表中
--后，再启用！
go
create trigger Emp_delete on EmployeeTB
for delete 
as
begin
 disable trigger Emp_His_Modity on Employee_HisTB
end

begin
insert into Employee_HisTB select *,getdate()from deleted 
end

begin
 enable trigger Emp_His_Modity on Employee_HisTB
end

--删除测试

delete from EmployeeTB where Emp_Dept = 11

--查询测试

select * from Employee_HisTB

--更新测试

update Employee_HisTB 
set Name_Cn = '张三' where Name_Cn = '唐宁'



---创建模块表

create table ModelTB(
Model_ID int primary key  not null,
Model_Name  Varchar(40) unique not null, --设置侯选建
Model_Group varchar(40) not null,
Model_Remarks varchar(500) null
)
drop table ModelTB
--插入数据测试

insert into ModelTB values (1001,'员工管理','100人力','增删改查')
insert into ModelTB values (1002,'职位晋升','100人力','人事调动')

insert into ModelTB values (1003,'职位晋升1','100人力','人事调动') --侯选建 Work
--就算插入不成功，自动增长还是继续，这。。
select * from ModelTB

--创建权限表

create table PermissionTB (
Emp_Id int foreign  key references EmployeeTB(Emp_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80)  null
)
drop table PermissionTB
--插入数据测试

insert into PermissionTB values (100003,1001,'---')

--查询测试

select * from PermissionTB

select c.Name_Cn,b.Model_Name from PermissionTB a join ModelTB b on a.Per_Id=b.Model_ID join EmployeeTB c on a.Emp_Id=c.Emp_Id

---创建职位权限表

create table Per_DesTB(
Des_Id int foreign key references DesignationTB(Des_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80) null
)
drop table Per_DesTB
--插入数据

insert into Per_DesTB values(1,1001,null)
insert into Per_DesTB values(1,1002,null)
insert into Per_DesTB values(1,1004,null)


--查询测试
select * from Per_DesTB 

select b.Des_Name,c.Model_Name from Per_DesTB a join DesignationTB b on a.Des_Id=b.Des_Id join ModelTB c on a.Per_Id=c.Model_Id


--对员工权限插入测试

--这个要一步步来，要涉及到好几个表，先找到员工编号所对应的权限

select Per_id from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100004'  

---
if not Exists(select b.Emp_Id, Per_id ,a.Per_Remarks from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100005')
insert into PermissionTB select b.Emp_Id, Per_id ,a.Per_Remarks from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100005' 

--用户表
 create table usersTB(
 userid varchar(20) not null unique,
 password varchar(50) not null,
 Emp_Id int foreign key references EmployeeTB not null
 )
 drop table userTB

 --初始化ERP系统
 insert into CompanyTB values('联想','lenove','BEIJING','BEIJING',null,'0532-125451','收购IBM PC部')
 insert into DepartmentTB values ('IT department',100)
 insert into DesignationTB values('Admain',5)
 insert into EmployeeTB values ('Dodd','Dodd',12,1,'男','1995-09-25','15066817901','291106637@qq.com','青大汇园二号',getdate())
 insert into usersTB values('Dodd','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',100007)
 insert into ModelTB values (1002,'EMP Management','100EMP Management','增删改查')
 insert into PermissionTB values (100007,1002,'---')