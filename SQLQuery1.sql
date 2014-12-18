create database ERPdb
use ERPdb
create table users(
userid varchar(20) unique not null,
userpassword varchar(20) not null
)
insert into users values('1','1')