
--------------------Query 1: run this to delete the exist database--------------------------------

IF EXISTS(select * from sys.databases where name='MessageDB')
DROP DATABASE MessageDB
-------------------------------End query 1--------------------------------------------------------


--------------------Query 2: run this to create the database---------------------------------
CREATE DATABASE MessageDB
-------------------------------End query 2--------------------------------------------------------

--------------------------------------------------------------------------------------------------
-- before run all script below to create tables, remember to choose database has just been created

CREATE TABLE [dbo].[Users](
	[UserName] [nvarchar](255) NOT NULL PRIMARY KEY,
	[DisplayName] [nvarchar](max) NOT NULL,
);

CREATE TABLE [dbo].[MessageDetail](
	[MessageID] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[FromUser] [nvarchar](255) NULL,
	[ToUser] [nvarchar](255) NULL,
	[DateCreated] [smalldatetime] NOT NULL,
	[Content] [nvarchar](max) NOT NULL,
	[MessageType] [nvarchar](255) NULL,
	);
