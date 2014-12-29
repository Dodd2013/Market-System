drop database ERPdb
create database ERPdb

use ERPdb

create table EmployeeTB(
Emp_Id varchar(4) primary key  not null check(Emp_Id like 'E___'),
Name_Cn varchar(30) not null,
Name_En varchar(30) null,
Gender varchar(6)  constraint CK_Gender check(Gender in ('man','woman')) not null,
Dob Date not null,  --����
Mobile char(11) unique, --�ֻ���
Email varchar(30) constraint CK_Email check(Email like '%@%'),
Address varchar(50) not null,
Join_Date Date   --��������
)
------------------------------------------------------------
create table ModelTB(    --ģ���
Model_ID int primary key  not null,
Model_Name  Varchar(40) unique not null, --���ú�ѡ��
Model_Group varchar(40) not null,
Model_Remarks varchar(500) null
)
create table PermissionTB (   --Ȩ�ޱ�
Emp_Id varchar(4) foreign  key references EmployeeTB(Emp_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80)  null
)
 create table usersTB(   --�˻���
 userid varchar(20) not null unique,
 password varchar(50) not null,
 Emp_Id varchar(4) foreign key references EmployeeTB not null
 )
 select * from usersTB
 insert into EmployeeTB values ('E001','Dodd','Dodd','man','1995-09-25','15066817901','291106637@qq.com','����԰����',getdate())
 insert into usersTB values('Dodd','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','E001')
 insert into ModelTB values (1001,'EMP Management','100EMP','��ɾ�Ĳ�')
 insert into ModelTB values (1002,'User Management','100EMP','��ɾ�Ĳ�')
 insert into ModelTB values (2001,'ItemDetail Management','200Inventory','��ɾ�Ĳ�')
 insert into ModelTB values (2002,'Inventory Management','200Inventory','��ɾ�Ĳ�')
 insert into ModelTB values (3001,'Sell','300Sell','��ɾ�Ĳ�')
 insert into ModelTB values (3002,'SalesPerformance','300Sell','fdsa')


 insert into PermissionTB values ('E001',1001,'---')
 insert into PermissionTB values ('E001',1002,'---')
 insert into PermissionTB values ('E001',2001,'---')
 insert into PermissionTB values ('E001',2002,'---')
 insert into PermissionTB values ('E001',3001,'---')
 insert into PermissionTB values ('E001',3002,'---')
 -------------------------------------------------------------------------------
 insert into EmployeeTB values ('E002','Allen','Allen','man','1995-09-25','15000000000','291106637@qq.com','����԰����',getdate())
 insert into usersTB values('Allen','273a0c7bd3c679ba9a6f5d99078e36e85d02b952','E002')
 insert into ModelTB values (1003,'EmpSearch','100EMP','��ɾ�Ĳ�')
 insert into ModelTB values (2004,'ItemDetailSearch','200Inventory','��ɾ�Ĳ�')
 insert into ModelTB values (2003,'InventorySearch','200Inventory','��ɾ�Ĳ�')
 insert into PermissionTB values ('E002',3001,'---')
 insert into PermissionTB values ('E002',3002,'---')
 insert into PermissionTB values ('E002',2003,'---')
 insert into PermissionTB values ('E002',2004,'---')
 insert into PermissionTB values ('E002',1003,'---')


 ------------------------------------------------------
 insert into ItemDetailTB values('��','pen','����','�����ƺڱ�',2)
  insert into inventoryTB values( 1,'����һ',2,'putaway')
   insert into sellTB values('Dodd','2014-05-06',50,'E001')
 -------------------------------------------------------------------------------
 create table ItemDetailTB(  --������Ʒ��
 Item_Id int primary key identity(1,1) not null,
 Item_NameCN varchar(50) not null,
 Item_NameEN varchar(50) not null,
 supplier varchar(50) null,
 descriptions varchar(300) null,
 price int not null
 )
 
 create table inventoryTB(  --���� 
	Inventory_Id int primary key identity(1,1) not null,
	Item_Id int references ItemDetailTB not null,
	Address varchar(50) not null,
	Item_Num int not null ,
	Sell_Status varchar(10) not null check(Sell_Status ='putaway' or Sell_Status ='sold out')
 )

 create table sellTB(  --���۱�
 sell_Id int primary key identity(1,1) not null,
 customer_Name varchar(50) not null,
 sell_Date date not null,
 money_Sum int not null,
 Emp_Id varchar(4) foreign  key references EmployeeTB(Emp_Id) not null
 )
 create table sellDetailTB(  --������ϸ��
	sell_Id int references sellTB not null,
	Item_Id int references ItemDetailTB not null,
	item_Num int not null check(item_Num>0),
	money_Sum int not null
 )