drop database ERPdb
create database ERPdb

use ERPdb

create table EmployeeTB(
Emp_Id varchar(4) primary key  not null check(Emp_Id like 'E___'),
Name_Cn varchar(30) not null,
Name_En varchar(30) null,
Gender varchar(4)  constraint CK_Gender check(Gender in ('man','woman')) not null,
Dob Date not null,
Mobile char(11) unique,
Email varchar(30) constraint CK_Email check(Email like '%@%'),
Address varchar(50) not null,
Join_Date Date
)
------------------------------------------------------------
create table ModelTB(
Model_ID int primary key  not null,
Model_Name  Varchar(40) unique not null, --���ú�ѡ��
Model_Group varchar(40) not null,
Model_Remarks varchar(500) null
)
create table PermissionTB (
Emp_Id varchar(4) foreign  key references EmployeeTB(Emp_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80)  null
)
 create table usersTB(
 userid varchar(20) not null unique,
 password varchar(50) not null,
 Emp_Id varchar(4) foreign key references EmployeeTB not null
 )

 insert into EmployeeTB values ('E001','Dodd','Dodd','man','1995-09-25','15066817901','291106637@qq.com','����԰����',getdate())
 insert into usersTB values('Dodd','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','E001')
 insert into ModelTB values (1002,'EMP Management','100EMP Management','��ɾ�Ĳ�')
 insert into PermissionTB values ('E001',1002,'---')


 -------------------------------------------------------------------------------
 create table 