create database ERPdb
use ERPdb
drop table jurisdictionTB
drop table usersTB
create table usersTB(   --�˻���
id int primary key identity(1,1) not null,   --id
userid varchar(20) unique not null,     --�û���
userpassword varchar(50) not null,   --����
empid int foreign key references employeeTB
)
create table organizationTB(
id varchar(20) primary key  not null,  --id
name varchar(30)

)
create table employeeTB(   --Ա����Ϣ��
id int primary key  not null,  --id
employeeName varchar(50) not null,   --Ա������
phoneNumber varchar(20) not null ,
--�ֻ���
dateOfEntry date not null,
--��ְ����
positionid int  null foreign key references positionTB(id),  
--ְλ
organizationid varchar(20) foreign key references organizationTB
)
drop table employeeTB
create table positionTB(   --ְλ��
id int primary key identity(1,1) not null,   --id
positionName varchar(50) unique not null,    --ְλ����
)
drop table jurisdictionTB
create table jurisdictionTB(  --Ȩ�ޱ�
id int primary key identity(1,1) not null,  --id
userid varchar(20) foreign key references usersTB(userid), --��Ӧ�˻���
limitsName varchar(50) not null,    --Ȩ������
moduleid int not null foreign key references moduleTB(moduleid),
isposition bit default 'false' not null,    --�Ƿ�Ϊϵͳְλ
UNIQUE( moduleid,userid)
)
create table moduleTB(   --ģ���
id int identity(1,1) not null,  --id
moduleName varchar(100), --ģ����
descb varchar(500) not null,     --ģ��˵��
moduleid int primary key not null,   --������
moduleGoup varchar(20) not null     --ģ����  100������200��300��400��500��600��700��800��900

)
select * from moduleTB
delete  moduleTB
delete jurisdictionTB
select * from usersTB a join employeeTB b on a.empid=b.id  where userid=1
select a.moduleid,b.moduleName,b.moduleGoup from jurisdictionTB a join moduleTB b on a.moduleid=b.moduleid where userid= '1' order by moduleGoup
insert into moduleTB values('���Ա��','���Ա��ģ��',2002,'200����')
insert into moduleTB values('���Ա��','���Ա��ģ��',1001,'100����')
insert into moduleTB values('���Ա��1','���Ա��ģ��',3002,'300����')
insert into jurisdictionTB values('1','��������',1001,'false')
insert into jurisdictionTB values('1','ewr',3002,'false')
drop table moduleTB
drop table usersTB
drop database ERPdb
insert into organizationTB values('00000','ϵͳ��������')
insert into employeeTB values(1,'Dodd','15066817901',GETDATE(),null,'00000')
insert into usersTB values('2','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d',1)
delete usersTB
select * from usersTB
sp_help
select * from sysobjects where xtype='u'--�鿴��ǰ���ݿ����б�
 select getdate()
 SELECT * FROM [sys].[fn_dblog](NULL,NULL)
