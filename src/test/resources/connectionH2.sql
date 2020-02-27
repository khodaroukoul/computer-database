drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;
  
  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);

insert into company (id,name) values (  1,'Apple Inc.');
insert into company (id,name) values (  2,'Thinking Machines');
insert into company (id,name) values (  3,'RCA');
insert into company (id,name) values (  4,'Netronics');
insert into company (id,name) values (  5,'Tandy Corporation');
insert into company (id,name) values (  6,'Commodore International');
insert into company (id,name) values (  7,'MOS Technology');
insert into company (id,name) values (  8,'Micro Instrumentation and Telemetry Systems');
insert into company (id,name) values (  9,'IMS Associates, Inc.');
insert into company (id,name) values ( 10,'Digital Equipment Corporation');
insert into company (id,name) values ( 11,'Lincoln Laboratory');
insert into company (id,name) values ( 12,'Moore School of Electrical Engineering');
insert into company (id,name) values ( 13,'IBM');
insert into company (id,name) values ( 14,'Amiga Corporation');
insert into company (id,name) values ( 15,'Canon');
insert into company (id,name) values ( 16,'Nokia');
insert into company (id,name) values ( 17,'Sony');
insert into company (id,name) values ( 18,'OQO');
insert into company (id,name) values ( 19,'NeXT');    
insert into company (id,name) values ( 20,'Atari');
insert into company (id,name) values ( 22,'Acorn computer');
insert into company (id,name) values ( 23,'Timex Sinclair');
insert into company (id,name) values ( 24,'Nintendo');
insert into company (id,name) values ( 25,'Sinclair Research Ltd');
insert into company (id,name) values ( 26,'Xerox');
insert into company (id,name) values ( 27,'Hewlett-Packard');
insert into company (id,name) values ( 28,'Zemmix');
insert into company (id,name) values ( 29,'ACVS');
insert into company (id,name) values ( 30,'Sanyo');
insert into company (id,name) values ( 31,'Cray');
insert into company (id,name) values ( 32,'Evans & Sutherland');    
insert into company (id,name) values ( 33,'E.S.R. Inc.');
insert into company (id,name) values ( 34,'OMRON');
insert into company (id,name) values ( 35,'BBN Technologies');
insert into company (id,name) values ( 36,'Lenovo Group');
insert into company (id,name) values ( 37,'ASUS');
insert into company (id,name) values ( 38,'Amstrad');
insert into company (id,name) values ( 39,'Sun Microsystems');
insert into company (id,name) values ( 40,'Texas Instruments');
insert into company (id,name) values ( 41,'HTC Corporation');
insert into company (id,name) values ( 42,'Research In Motion');
insert into company (id,name) values ( 43,'Samsung Electronics');

insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  2,'CM-2a',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  3,'CM-200',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  4,'CM-5e',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  5,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  6,'MacBook Pro','2006-01-10',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  7,'Apple IIe',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values (  8,'Apple IIc',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values (  9,'Apple IIGS',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 10,'Apple IIc Plus',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 11,'Apple II Plus',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 12,'Apple III','1980-05-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 13,'Apple Lisa',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 14,'CM-2',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 15,'Connection Machine','1987-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 16,'Apple II','1977-04-01','1993-10-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 17,'Apple III Plus','1983-12-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 18,'COSMAC ELF',null,null,3);
insert into computer (id,name,introduced,discontinued,company_id) values ( 19,'COSMAC VIP','1977-01-01',null,3);
insert into computer (id,name,introduced,discontinued,company_id) values ( 20,'ELF II','1977-01-01',null,4);
insert into computer (id,name,introduced,discontinued,company_id) values ( 21,'Macintosh','1984-01-24',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 22,'Macintosh II',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 23,'Macintosh Plus','1986-01-16','1990-10-15',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 24,'Macintosh IIfx',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 25,'iMac','1998-01-01',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 26,'Mac Mini','2005-01-22',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 27,'Mac Pro','2006-08-07',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 28,'Power Macintosh','1994-03-01','2006-08-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 29,'PowerBook','1991-01-01','2006-01-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 30,'Xserve',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 31,'Powerbook 100',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 32,'Powerbook 140',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 33,'Powerbook 170',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 34,'PowerBook Duo',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 35,'PowerBook 190',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 36,'Macintosh Quadra','1991-01-01',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 37,'Macintosh Quadra 900',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 38,'Macintosh Quadra 700',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 39,'Macintosh LC','1990-01-01',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 40,'Macintosh LC II','1990-01-01',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 41,'Macintosh LC III','1993-01-01',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 42,'Macintosh LC III+',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 43,'Macintosh Quadra 605','1993-10-21',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 44,'Macintosh LC 500 series',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 45,'TRS-80 Color Computer','1980-01-01',null,5);
insert into computer (id,name,introduced,discontinued,company_id) values ( 46,'Acorn System 2',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 47,'Dragon 32/64',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 48,'MEK6800D2',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 49,'Newbear 77/68',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 50,'Commodore PET',null,null,6);