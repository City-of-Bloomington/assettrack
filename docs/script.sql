;;
;;
;; database tables
;;
;; after creating database tables, some of the tables are expected to have
;; data as they are lookup tables, such as locations, department,
;; divisions, categories, recycle_locations, etc. The software provides
;; the inteface for some of these. Other may need to be added directly
;;
   create table users(                                                             id mediumint unsigned not null auto_increment,                                  username     varchar(10) not null,                                              first_name     varchar(70),                                                     last_name     varchar(70) not null,                                             office_phone   varchar(50),                                                     role          enum('end_user','View','Edit','Edit:Admin'),                      primary key(id),                                                                unique(username)                                                                )engine=InnoDB;

	create table employees(                                                          id mediumint unsigned not null auto_increment,                                  external_id  mediumint unsigned,                                                username     varchar(30) not null,                                              first_name     varchar(70),                                                     last_name     varchar(70) not null,                                             office_phone   varchar(50),                                                     role          enum('end_user','admin','helpdesk_admin','helpdesk_teck'),        primary key(id),                                                                unique(username)                                                                )engine=InnoDB;

	  create table locations (                                                     	 id           mediumint unsigned not null auto_increment,                        name         varchar(80),                                                       inactive    char(1),                                                            primary key(id)                                                                )engine=InnoDB;

	 create table recycle_locations (                                              	 id           tinyint unsigned not null auto_increment,                          name         varchar(80),                                                       inactive     char(1),                                                           primary key(id)                                                                 )engine=InnoDB;
					 
	 create table departments (                                                        id           mediumint unsigned not null auto_increment,                        name         varchar(50),                                                       inactive     char(1),                                                           primary key(id)                                                               )engine=InnoDB;					 
					 
	 create table divisions(                                                         id           mediumint unsigned not null auto_increment,                        dept_id      mediumint unsigned,                                                name         varchar(50),                                                       code    varchar(55),                                                            inactive  char(1),                                                              primary   key(id),                                                              foreign key(dept_id) references departments(id)                                 )engine=InnoDB;

		create table categories(                                                        id           mediumint unsigned not null auto_increment,                        name        varchar(80),                                                        inactive    char(1),                                                            primary key(id)                                                                 )engine=InnoDB;

	 insert into categories (id,name) values(1,'Computer'),(2,'Laptop'),(3,'NetworkPrinter'), (4,'UPS'),(5,'LCD 1'),(6,'LCD 2'),(7,'LCD 3'),(8,'LCD 4'),(9,'Scanner'),(10,'MacBook'),(11,'TV'),(12,'Projector'),(13,'External Hard Drive'),(14,'Docking Station'),(15,'Switch'),(16,'Plotter'),(17,'Copier'),(18,'Server'),(19,'Tablet'),(20,'SnmpDevice'),(21,'HttpDevice'),(22,'Unknown'),(23,'Other');


	 create table domains(                                                           id            tinyint unsigned not null auto_increment,                         name         varchar(70),                                                       inactive     char(1),                                                           primary key(id),                                                                index(name)                                                                     )engine=InnoDB;
;;
;; the following are City of Bloomington domains, change according to your
;; organization setup
;; 
	 
	 insert into domains (id,name) values(1,'COB'),(2,'City Hall'),(3,'Utilities'),(4,'Police'),(5,'Fire'),(6,'Blucher'),(7,'Dillman'),(8,'Monroe'),(9,'Transit')	 

	 create table devices(                                                            id              mediumint unsigned not null auto_increment,                     external_id     mediumint unsigned,                                             name            varchar(70),                                                    asset_num       varchar(20),                                                    serial_num      varchar(30),                                                    model      varchar(30),                                                         employee_id     mediumint unsigned,                                             description     varchar(30),                                                    category_id     mediumint unsigned,                                             received        date,                                                           age_length      tinyint,                                                        location_id     mediumint unsigned,                                             dept_id         mediumint unsigned,                                             domain_id       tinyint unsigned,                                               status          enum('Active','Donated','Auctioned','Recycled','Disposed'),                                                                                     processor       varchar(50),                                                    ram             varchar(30),                                                    hd_size         varchar(30),                                                    notes           text,                                                           mac_address     varchar(20),                                                    ip_address      varchar(20),                                                    editable        char(1),                                                        related_id      mediumint unsigned,                                             cost double(10,2),                                                              inventory_date  date,                                                           replace_asset_num varchar(20),                                                  voided          char(1),                                                        primary key(id),                                                                foreign key(category_id) references categories(id),                             foreign key(dept_id) references departments(id),                                foreign key(location_id) references locations(id),                              foreign key(employee_id) references employees(id),                              foreign key(related_id) references devices(id),                                 index(name, serial_num)                                                         )engine=InnoDB;	 
	
	 create table softwares(                                                          id            mediumint unsigned not null auto_increment,                       external_id   mediumint unsigned,                                               display_name varchar(70),                                                       vendor       varchar(70),                                                       installed         date,                                                         install_count int,                                                              editable      char(1),                                                          primary key(id),                                                                index(display_name)                                                             )engine=InnoDB;

					
 	 create table software_licenses(                                                 id            mediumint unsigned not null auto_increment,                       external_id   mediumint unsigned,                                               key_value     varchar(40),                                                      type          enum('IndividualLicense','VolumeLicense'),                        created       date,                                                             seat_limit    mediumint,                                                        editable      char(1),                                                          primary key(id)                                                                 )engine=InnoDB;


	 create table software_installations(                                            id          mediumint unsigned not null auto_increment,                         external_id mediumint unsigned,                                                 software_id mediumint unsigned,                                                 device_id   mediumint unsigned,                                                 license_id  mediumint unsigned,                                                 installed        date,                                                          editable    char(1),                                                            primary key(id),                                                                foreign key (device_id) references devices(id),                                 foreign key (software_id) references softwares(id),                             foreign key (license_id) references software_licenses(id)                       )engine=InnoDB;

	  create table discarded_items(                                                    id mediumint unsigned not null auto_increment,                                  item_id      mediumint unsigned not null,                                       type         enum('Device','Monitor','Printer','Other'),                        date         date,                                                              method       enum('Consumed','Discard','Lost'),                                 description  varchar(255),                                                      primary key(id)					                                                       )Engine=InnoDB;
					 

		create table auctions(                                                          id          mediumint unsigned not null auto_increment,                         name        varchar(70),                                                        date        date,                                                               primary key(id)                                                                 )engine=InnoDB;



    create table organizations(                                                     id          mediumint unsigned not null auto_increment,                         name        varchar(70),                                                        inactive    char(1),                                                             address     varchar(80),                                                        city        varchar(80),                                                        state       char(2),                                                            zip         varchar(15),                                                        contact     varchar(80),                                                        phone       varchar(50),                                                        primary key(id)                                                                )engine=InnoDB;		 

		 create table auction_items(                                                     id       mediumint unsigned not null auto_increment,                            auction_id mediumint unsigned not null,                                         asset_id    mediumint unsigned not null,                                        type       enum('Device','Monitor','Printer','Other'),                          value       decimal(7,2),                                                       description varchar(255),                                                       primary key(id),                                                                foreign    key(auction_id) references auctions(id)                             )engine=InnoDB;

  	 create table donations(                                                         id    mediumint unsigned not null auto_increment,                               organization_id       mediumint unsigned not null,                              asset_id         mediumint unsigned not null,                                    type enum('Device','Monitor','Printer','Other'),                                date            date,                                                           value           decimal(7,2),                                                   primary key(id),                                                                foreign key(organization_id) references organizations(id)                       )engine=InnoDB;

		 create table device_history(                                                      id          mediumint unsigned not null auto_increment,                         device_id mediumint unsigned not null,                                          status  enum('Active','Auctioned','Donated','Disposed','Recycled'),            date  date,                                                                     updater_id mediumint unsigned,                                                    primary key(id),                                                                foreign key(device_id) references devices(id),                                  foreign key(updater_id) references users(id)                                   )engine=InnoDB;
	  
		 create table recycled_items(                                                      id mediumint unsigned not null auto_increment,                                  location_id  tinyint unsigned,                                                  asset_id      mediumint unsigned,                                                type         enum('Device','Monitor','Printer','Other'),                        date         date,                                                              weight      decimal(8,2),                                                       description varchar(255),                                                       primary     key(id),                                                            foreign     key(location_id) references recycle_locations(id)                 )engine=InnoDB;

		 	create table data_imports(                                                       id mediumint unsigned not null auto_increment,                                  date         date,                                                              primary key(id)                                                                )engine=InnoDB;
				 
			 create table monitors(                                                           id              mediumint unsigned not null auto_increment,                     external_id     mediumint unsigned,                                             device_id       mediumint unsigned,                                             name            varchar(100),                                                   asset_num       varchar(20),                                                    serial_num      varchar(100),                                                   screen_size     tinyint,                                                        model           varchar(100),                                                   type           varchar(100),                                                    vertical_resolution tinyint,                                                    horizontal_resolution tinyint,                                                  manufacturer     varchar(100),                                                  received        date,                                                           expected_age    tinyint,                                                        status       enum('Active','Auctioned','Donated','Recycled','Disposed'),                                                                                        notes        varchar(255),                                                      editable     char(1),                                                           primary key(id),                                                                foreign key(device_id) references devices(id),                                  index(name, serial_num,asset_num)                                               )engine=InnoDB;

			 create table printers(                                                           id              mediumint unsigned not null auto_increment,                     external_id     mediumint unsigned,                                             name            varchar(70),                                                    device_id       mediumint,                                                      print_processor varchar(30),                                                    description     varchar(70),                                                    date            date,                                                           status          enum('Active','Donated','Auctioned','Recycled','Disposed'),                                                                                     notes           varchar(255),                                                   editable        char(1),                                                        primary key(id),                                                               index (name, print_processor, description)                                      )engine=InnoDB;

 			 create table attachments (                                                       id mediumint unsigned NOT NULL AUTO_INCREMENT,                                 file_name varchar(30) DEFAULT NULL,                                             old_file_name varchar(258) DEFAULT NULL,                                        obj_id mediumint unsigned DEFAULT NULL,                                         obj_type enum('Device','Printer','Monitor','Organization'),                     date date DEFAULT NULL,                                                         notes varchar(516) DEFAULT NULL,                                                user_id mediumint unsigned,                                                     PRIMARY KEY (id),                                                               foreign key(user_id) references users(id)                                       ) ENGINE=InnoDB;

;;
;; the following device id are dups
;; 1095, 2366,
;; delete from device_history where device_id in (1095, 2366)
;; delete from monitors where device_id in (1095,2366);
;; delete from software_installations where device_id in(1095,2366);
;; delete from softwares where external_id in (1095,2366);
;; alter table devices add voided char(1);
;;
;; we need to drop external_id unique constraint from the tables

drop index external_id on devices;
drop index external_id on monitors;
drop index external_id on printers;

;; finding duplicate script
;;
 select count(*) cnt, name from devices where name is not null and not (name like '10.%' or name like 'dhcp-%') group by  name having cnt > 1;
 
select count(*) cnt, external_id from devices where external_id is not null group by  external_id having cnt > 1;

select count(*) cnt, asset_num from devices where asset_num is not null and not asset_num like 'N/A' group by  asset_num having cnt > 1;



|count| asset_num  |
+-----+------------+
|   2 | 040003     |
|   2 | 070001     |
|   2 | 070109     |
|   2 | 070316     |
|   2 | 080182     |
|   2 | 090174     |
|   2 | 090323     |
|   2 | 090338     |
|   2 | 100001     |
|   2 | 100004     |
|   3 | 100359     |
|   2 | 100406     |
|   2 | 110011     |
|   2 | 110116     |
|   2 | 110146     |
|   2 | 110181     |
|   2 | 110183     |
|   2 | 110194     |
|   2 | 110239-pc  |
|   2 | 120009     |
|   2 | 120013     |
|   2 | 120025     |
|   3 | 120027     |
|   2 | 120044     |
|   2 | 120045     |
|   3 | 120056     |
|   3 | 120061     |
|   2 | 120077     |
|   2 | 120078     |
|   2 | 120087     |
|   2 | 120111     |
|   2 | 120113     |
|   3 | 120128     |
|   3 | 120159     |
|   3 | 130011     |
|   3 | 130045     |
|   2 | 130065     |
|   2 | 130067     |
|   2 | 130071     |
|   2 | 130079     |
|   2 | 130159     |
|   3 | 130172     |
|   4 | 130429     |
|   3 | 130457     |
|   2 | 130461     |
|   2 | 130463     |
|   2 | 130466     |
|   4 | 130551     |
|   3 | 130559     |
|   2 | 140001     |
|   2 | 140009T    |
|   2 | 140033     |
|   2 | 140100     |
|   5 | 140113     |
|   2 | 140121     |
|   6 | 140123     |
|   3 | 140148     |
|   2 | 140159     |
|   2 | 140189     |
|   2 | 140205     |
|   3 | 140238     |
|   2 | 140274     |
|   3 | 140281     |
|   2 | 140290     |
|   2 | 140314     |
|   2 | 140346     |
|   2 | 140374     |
|   3 | 140395     |
|   2 | 140423     |
|   2 | 14999E     |
|   2 | 14999F     |
|   4 | 1500937A   |
|   2 | 15010014   |
|   2 | 1504092B   |
|   2 | 1524576m   |
|   2 | 1524579a   |
|   2 | 1524579b   |
|   4 | 1525008b   |
|   2 | 1525010c   |
|   2 | 1525010D   |
|   2 | 1525010j   |
|   4 | 1525010s   |
|   4 | 1525010t   |
|   2 | 1525013n   |
|   2 | 1525103    |
|   2 | 1525341    |
|   2 | 1525575D   |
|   2 | 1525665a   |
|   2 | 1530091b   |
|   2 | 1550117b   |
|   3 | 1600427a   |
|   2 | 1600481e   |
|   2 | 1600582c   |
|   2 | 1600650-pc |
|   2 | 1600695d   |
|   2 | 1600967B   |
|   2 | 1601017a   |
|   4 | 1601017C   |
|   4 | 1601017f   |
|   2 | 1601203C   |
|   2 | 1601224d   |
|   2 | 1601260    |
|   2 | 1601393H   |
|   2 | 1601626A   |
|   2 | 1601956    |
|   2 | 1601992    |
|   2 | 1602063A   |
|   2 | 1602063B   |
|   2 | 1602063E   |
|   2 | 1625709J   |
|   2 | 16cc19     |
|   2 | 16CC20     |
|   2 | 1702307a   |
|   2 | 1702332F   |
|   2 | 1702377A   |
|   2 | 1702380A   |
|   2 | 1702380H   |
|   2 | 1702449    |
|   2 | 1702755A   |
|   2 | 1702755N   |
|   2 | 1703050    |
|   2 | 1703069B   |
|   2 | 1703069d   |
|   2 | 1703069e   |
|   2 | 1703069H   |
|   2 | 1703080A   |
|   2 | 1703456b   |
|   2 | 1703466c   |
|   2 | 1703466N   |
|   2 | 1703563b   |
|   2 | 1703840A   |
|   2 | 1703867    |
|   2 | 1703869D   |
|   2 | 1703869e   |
|   2 | 1703923    |
|   2 | 1703925d   |
|   2 | 1703933a   |
|   2 | 1703933B   |
|   2 | 1703933c   |
|   2 | 1703933d   |
|   2 | 1703933e   |
|   2 | 1703933f   |
|   2 | 1703933i   |
|   2 | 1703994    |
|   2 | 1704010b   |


			 
			 
