USE [master]
GO
/****** Object:  Database [ERPdb]    Script Date: 2014/12/28 16:04:28 ******/
CREATE DATABASE [ERPdb]
GO
ALTER DATABASE [ERPdb] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ERPdb] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ERPdb] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ERPdb] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ERPdb] SET ARITHABORT OFF 
GO
ALTER DATABASE [ERPdb] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ERPdb] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [ERPdb] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ERPdb] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ERPdb] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ERPdb] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ERPdb] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ERPdb] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ERPdb] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ERPdb] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ERPdb] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ERPdb] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ERPdb] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ERPdb] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ERPdb] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ERPdb] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ERPdb] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ERPdb] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ERPdb] SET RECOVERY FULL 
GO
ALTER DATABASE [ERPdb] SET  MULTI_USER 
GO
ALTER DATABASE [ERPdb] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ERPdb] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ERPdb] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ERPdb] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
EXEC sys.sp_db_vardecimal_storage_format N'ERPdb', N'ON'
GO
USE [ERPdb]
GO
/****** Object:  Table [dbo].[EmployeeTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EmployeeTB](
	[Emp_Id] [varchar](4) NOT NULL,
	[Name_Cn] [varchar](30) NOT NULL,
	[Name_En] [varchar](30) NULL,
	[Gender] [varchar](6) NOT NULL,
	[Dob] [date] NOT NULL,
	[Mobile] [char](11) NULL,
	[Email] [varchar](30) NULL,
	[Address] [varchar](50) NOT NULL,
	[Join_Date] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[Emp_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Mobile] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[inventoryTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[inventoryTB](
	[Inventory_Id] [int] IDENTITY(1,1) NOT NULL,
	[Item_Id] [int] NOT NULL,
	[Address] [varchar](50) NOT NULL,
	[Item_Num] [int] NOT NULL,
	[Sell_Status] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Inventory_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ItemDetailTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ItemDetailTB](
	[Item_Id] [int] IDENTITY(1,1) NOT NULL,
	[Item_NameCN] [varchar](50) NOT NULL,
	[Item_NameEN] [varchar](50) NOT NULL,
	[supplier] [varchar](50) NULL,
	[descriptions] [varchar](300) NULL,
	[price] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Item_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ModelTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ModelTB](
	[Model_ID] [int] NOT NULL,
	[Model_Name] [varchar](40) NOT NULL,
	[Model_Group] [varchar](40) NOT NULL,
	[Model_Remarks] [varchar](500) NULL,
PRIMARY KEY CLUSTERED 
(
	[Model_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Model_Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PermissionTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PermissionTB](
	[Emp_Id] [varchar](4) NOT NULL,
	[Per_Id] [int] NOT NULL,
	[Per_Remarks] [varchar](80) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sellDetailTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sellDetailTB](
	[sell_Id] [int] NOT NULL,
	[Item_Id] [int] NOT NULL,
	[item_Num] [int] NOT NULL,
	[money_Sum] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[sellTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sellTB](
	[sell_Id] [int] IDENTITY(1,1) NOT NULL,
	[customer_Name] [varchar](50) NOT NULL,
	[sell_Date] [date] NOT NULL,
	[money_Sum] [int] NOT NULL,
	[Emp_Id] [varchar](4) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[sell_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[usersTB]    Script Date: 2014/12/28 16:04:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[usersTB](
	[userid] [varchar](20) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[Emp_Id] [varchar](4) NOT NULL,
UNIQUE NONCLUSTERED 
(
	[userid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[inventoryTB]  WITH CHECK ADD FOREIGN KEY([Item_Id])
REFERENCES [dbo].[ItemDetailTB] ([Item_Id])
GO
ALTER TABLE [dbo].[PermissionTB]  WITH CHECK ADD FOREIGN KEY([Emp_Id])
REFERENCES [dbo].[EmployeeTB] ([Emp_Id])
GO
ALTER TABLE [dbo].[PermissionTB]  WITH CHECK ADD FOREIGN KEY([Per_Id])
REFERENCES [dbo].[ModelTB] ([Model_ID])
GO
ALTER TABLE [dbo].[sellDetailTB]  WITH CHECK ADD FOREIGN KEY([Item_Id])
REFERENCES [dbo].[ItemDetailTB] ([Item_Id])
GO
ALTER TABLE [dbo].[sellDetailTB]  WITH CHECK ADD FOREIGN KEY([sell_Id])
REFERENCES [dbo].[sellTB] ([sell_Id])
GO
ALTER TABLE [dbo].[sellTB]  WITH CHECK ADD FOREIGN KEY([Emp_Id])
REFERENCES [dbo].[EmployeeTB] ([Emp_Id])
GO
ALTER TABLE [dbo].[usersTB]  WITH CHECK ADD FOREIGN KEY([Emp_Id])
REFERENCES [dbo].[EmployeeTB] ([Emp_Id])
GO
ALTER TABLE [dbo].[EmployeeTB]  WITH CHECK ADD CHECK  (([Emp_Id] like 'E___'))
GO
ALTER TABLE [dbo].[EmployeeTB]  WITH CHECK ADD  CONSTRAINT [CK_Email] CHECK  (([Email] like '%@%'))
GO
ALTER TABLE [dbo].[EmployeeTB] CHECK CONSTRAINT [CK_Email]
GO
ALTER TABLE [dbo].[EmployeeTB]  WITH CHECK ADD  CONSTRAINT [CK_Gender] CHECK  (([Gender]='woman' OR [Gender]='man'))
GO
ALTER TABLE [dbo].[EmployeeTB] CHECK CONSTRAINT [CK_Gender]
GO
ALTER TABLE [dbo].[inventoryTB]  WITH CHECK ADD CHECK  (([Sell_Status]='putaway' OR [Sell_Status]='sold out'))
GO
ALTER TABLE [dbo].[sellDetailTB]  WITH CHECK ADD CHECK  (([item_Num]>(0)))
GO
USE [master]
GO
ALTER DATABASE [ERPdb] SET  READ_WRITE 
GO
USE [ERPdb]
 insert into EmployeeTB values ('E001','Dodd','Dodd','man','1995-09-25','15066817901','291106637@qq.com','青大汇园二号',getdate())
 insert into usersTB values('Dodd','3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','E001')
 insert into ModelTB values (1001,'EMP Management','100EMP','增删改查')
 insert into ModelTB values (1002,'User Management','100EMP','增删改查')
 insert into ModelTB values (2001,'ItemDetail Management','200Inventory','增删改查')
 insert into ModelTB values (2002,'Inventory Management','200Inventory','增删改查')
 insert into ModelTB values (3001,'Sell','300Sell','增删改查')
 insert into ModelTB values (3002,'SalesPerformance','300Sell','fdsa')


 insert into PermissionTB values ('E001',1001,'---')
 insert into PermissionTB values ('E001',1002,'---')
 insert into PermissionTB values ('E001',2001,'---')
 insert into PermissionTB values ('E001',2002,'---')
 insert into PermissionTB values ('E001',3001,'---')
 insert into PermissionTB values ('E001',3002,'---')
 -------------------------------------------------------------------------------
 insert into EmployeeTB values ('E002','Allen','Allen','man','1995-09-25','15000000000','291106637@qq.com','青大汇园二号',getdate())
 insert into usersTB values('Allen','273a0c7bd3c679ba9a6f5d99078e36e85d02b952','E002')
 insert into ModelTB values (1003,'EmpSearch','100EMP','增删改查')
 insert into ModelTB values (2004,'ItemDetailSearch','200Inventory','增删改查')
 insert into ModelTB values (2003,'InventorySearch','200Inventory','增删改查')
 insert into PermissionTB values ('E002',3001,'---')
 insert into PermissionTB values ('E002',3002,'---')
 insert into PermissionTB values ('E002',2003,'---')
 insert into PermissionTB values ('E002',2004,'---')
 insert into PermissionTB values ('E002',1003,'---')


 ------------------------------------------------------
 insert into ItemDetailTB values('笔','pen','晨光','晨光牌黑笔',2)
 insert into inventoryTB values( 1,'货架一',2,'putaway')
 insert into sellTB values('Dodd','2014-05-06',50,'E001')