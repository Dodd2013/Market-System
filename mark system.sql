

--����ERP���ݿ�

create database ERPdb

use ERPdb

--������˾��

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


--��������
insert into CompanyTB values('����','lenove','����','�����йش�',null,'0532-125451','�չ�IBM PC��')

--��ѯ
select * from CompanyTB where Com_id like '%' and Name_Cn like '%' and Name_En like '%' and city like '%' and Address_Cn like '%' and Front_Tel like '%'
update CompanyTB set Name_Cn='dfs',Name_En='gfds',city='gfds',Address_Cn='gfds',Front_Tel='gfdsgf',Address_En='gret',Remarks='gfgwe' where Com_id=125

---�������ű�


create table DepartmentTB(
Dept int identity(11,1) primary key  not null,
Dept_Name varchar(30),
Com_Id int foreign key references CompanyTB(Com_Id),
)

--�����˸�������,���Ǻ������ⲻ�ܽ��

create table DepartmentTB(
Dept int identity(11,1)  not null,
Dept_Name varchar(30),
Com_Id int foreign key references CompanyTB(Com_Id),
Dept_Id int primary key(Com_Id,Dept) 
)

--��������

insert into DepartmentTB values ('������Դ��',101)


--��ѯ����

select * from DepartmentTB

---����ְλ��


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
--��������

insert into DesignationTB values('��������',5)

--��ѯ����

select * from DesignationTB


--����Ա����Ϣ��

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

---�������ݲ���

insert into EmployeeTB values ('����','Bean',11,1,'��','1995-09-25','18363995829','1356563293@qq.com','����԰����',getdate())

insert into EmployeeTB values ('����','Bean',11,1,'��','1995-09-25','18363995429','1356563293@qq.com','����԰����',getdate())

insert into EmployeeTB values ('����','Bean',11,1,'��','1995-09-25','18363895827','1356563293@qq.com','����԰����',getdate())


---��ѯ����

select * from EmployeeTB

select a.Emp_Id,a.Name_Cn,a.Name_En,b.Dept_Name,c.Des_Name,a.Gender,a.Dob,a.Mobile,a.Email,a.Address,a.Join_Date from EmployeeTB a 
 join DepartmentTB b on a.Emp_Dept=b.Dept join DesignationTB c on a.Emp_Des = c.Des_Id 

 --����Ա����ʷ��

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

--Ա����ʷ���Ǵ�Ա����ͨ����������ʵ�ֵģ�����Ҫ������
--Ա����ʷ���ܽ��и��²���

--�ȴ���������������Ա�����Ա����ʷ��

create trigger Emp_Del on Emp


--��Ա����ʷ���ܽ��и���,�������������������ʵ��

create trigger Emp_His_Modity on Employee_HisTB
for update,insert
as
begin
print N'��Ա����ʷ���ܽ��и��²������'
rollback
end


--Ա����¼��Ա������ɾ��ʱ���Զ���ӵ�Ա����ʷ����
---�ڴ���������Ϊ����һ�����ܶ�Ա����ʷ������ݽ��и��ºͲ���Ĵ����������Ի���ɾ�����ݵ�
--ʱ�򲻳ɹ�����ˣ�Ҫ�ȰѲ��ܸ��µ��Ǹ����������ã�Ȼ��Ȱ�Ա�������Ϣ���뵽Ա����ʷ����
--�������ã�
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

--ɾ������

delete from EmployeeTB where Emp_Dept = 11

--��ѯ����

select * from Employee_HisTB

--���²���

update Employee_HisTB 
set Name_Cn = '����' where Name_Cn = '����'



---����ģ���

create table ModelTB(
Model_ID int primary key  not null,
Model_Name  Varchar(40) unique not null, --���ú�ѡ��
Model_Group varchar(40) not null,
Model_Remarks varchar(500) null
)
drop table ModelTB
--�������ݲ���

insert into ModelTB values (1001,'Ա������','100����','��ɾ�Ĳ�')
insert into ModelTB values (1002,'ְλ����','100����','���µ���')

insert into ModelTB values (1003,'ְλ����1','100����','���µ���') --��ѡ�� Work
--������벻�ɹ����Զ��������Ǽ������⡣��
select * from ModelTB

--����Ȩ�ޱ�

create table PermissionTB (
Emp_Id int foreign  key references EmployeeTB(Emp_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80)  null
)
drop table PermissionTB
--�������ݲ���

insert into PermissionTB values (100003,1001,'---')

--��ѯ����

select * from PermissionTB

select c.Name_Cn,b.Model_Name from PermissionTB a join ModelTB b on a.Per_Id=b.Model_ID join EmployeeTB c on a.Emp_Id=c.Emp_Id

---����ְλȨ�ޱ�

create table Per_DesTB(
Des_Id int foreign key references DesignationTB(Des_Id) not null,
Per_Id int foreign key references ModelTB(Model_Id) not null,
Per_Remarks varchar(80) null
)
drop table Per_DesTB
--��������

insert into Per_DesTB values(1,1001,null)
insert into Per_DesTB values(1,1002,null)
insert into Per_DesTB values(1,1004,null)


--��ѯ����
select * from Per_DesTB 

select b.Des_Name,c.Model_Name from Per_DesTB a join DesignationTB b on a.Des_Id=b.Des_Id join ModelTB c on a.Per_Id=c.Model_Id


--��Ա��Ȩ�޲������

--���Ҫһ��������Ҫ�漰���ü��������ҵ�Ա���������Ӧ��Ȩ��

select Per_id from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100004'  

---
if not Exists(select b.Emp_Id, Per_id ,a.Per_Remarks from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100005')
insert into PermissionTB select b.Emp_Id, Per_id ,a.Per_Remarks from Per_DesTB a join EmployeeTB  b on a.Des_Id=b.Emp_Des where b.Emp_Id='100005' 

--�û���
 create table usersTB(
 userid varchar(20) not null unique,
 password varchar(50) not null,
 Emp_Id int foreign key references EmployeeTB not null
 )
 drop table userTB

 --��ʼ��ERPϵͳ
 insert into CompanyTB values('����','lenove','BEIJING','BEIJING',null,'0532-125451','�չ�IBM PC��')
 insert into DepartmentTB values ('IT department',100)
 insert into DesignationTB values('Admain',5)
 insert into EmployeeTB values ('Dodd','Dodd',12,1,'��','1995-09-25','15066817901','291106637@qq.com','����԰����',getdate())
 insert into usersTB values('Dodd','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',100007)
 insert into ModelTB values (1002,'EMP Management','100EMP Management','��ɾ�Ĳ�')
 insert into PermissionTB values (100007,1002,'---')