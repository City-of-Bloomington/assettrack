;;
;; tables in this database
;; if you are going to use quartz to import data
;; copy their latest from quartz.org
;;
;;+--------------------------+
;;| Tables_in_assettrack     |
;;+--------------------------+
;;| QRTZ_BLOB_TRIGGERS       |
;;| QRTZ_CALENDARS           |
;;| QRTZ_CRON_TRIGGERS       |
;;| QRTZ_FIRED_TRIGGERS      |
;;| QRTZ_JOB_DETAILS         |
;;| QRTZ_LOCKS               |
;;| QRTZ_PAUSED_TRIGGER_GRPS |
;;| QRTZ_SCHEDULER_STATE     |
;;| QRTZ_SIMPLE_TRIGGERS     |
;;| QRTZ_SIMPROP_TRIGGERS    |
;;| QRTZ_TRIGGERS            |
;;
;; quartz tables end here
;;
;;| attachment_seq           |
;;| attachments              |
;;| auction_items            |
;;| auctions                 |
;;| categories               |
;;| data_imports             |
;;| departments              |
;;| depts                    |
;;| device_history           |
;;| devices                  |
;;| discarded_items          |
;;| divisions                |
;;| domains                  |
;;| donations                |
;;| employees                |
;;| import_details           |
;;| locations                |
;;| lot_options              |
;;| lots                     |
;;| monitors                 |
;;| organizations            |
;;| people                   |
;;| printers                 |
;;| recycle_locations        |
;;| recycled_items           |
;;| software_installations   |
;;| software_licenses        |
;;| softwares                |
;;| tickets                  |
;;| users                    |
;;+--------------------------+
;;
CREATE TABLE `attachment_seq` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `attachments` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(30) DEFAULT NULL,
  `old_file_name` varchar(258) DEFAULT NULL,
  `obj_id` mediumint unsigned DEFAULT NULL,
  `obj_type` enum('Device','Printer','Monitor','Organization') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `notes` varchar(516) DEFAULT NULL,
  `user_id` mediumint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `attachments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `auction_items` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `auction_id` mediumint unsigned NOT NULL,
  `asset_id` mediumint unsigned NOT NULL,
  `asset_num` varchar(15) DEFAULT NULL,
  `type` enum('Device','Monitor','Printer','Other') DEFAULT NULL,
  `value` decimal(7,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lot_id` mediumint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `auction_id` (`auction_id`),
  KEY `lot_id` (`lot_id`),
  CONSTRAINT `auction_items_ibfk_1` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`),
  CONSTRAINT `auction_items_ibfk_2` FOREIGN KEY (`lot_id`) REFERENCES `lots` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `auctions` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(70) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `categories` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `data_imports` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3351 DEFAULT CHARSET=utf8mb3;
;;
 CREATE TABLE `departments` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `depts` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `device_history` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `device_id` mediumint unsigned NOT NULL,
  `status` enum('Active','Auctioned','Donated','Disposed','Recycled') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `updater_id` mediumint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`),
  KEY `updater_id` (`updater_id`),
  CONSTRAINT `device_history_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`),
  CONSTRAINT `device_history_ibfk_2` FOREIGN KEY (`updater_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10204 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `devices` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `name` varchar(70) DEFAULT NULL,
  `asset_num` varchar(40) DEFAULT NULL,
  `serial_num` varchar(90) DEFAULT NULL,
  `model` varchar(70) DEFAULT NULL,
  `employee_id` mediumint unsigned DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `category_id` mediumint unsigned DEFAULT NULL,
  `installed` date DEFAULT NULL,
  `age_length` tinyint DEFAULT NULL,
  `location_id` mediumint unsigned DEFAULT NULL,
  `division_id` mediumint unsigned DEFAULT NULL,
  `domain_id` tinyint unsigned DEFAULT NULL,
  `status` enum('Active','Donated','Auctioned','Recycled','Disposed','Replaced','Duplicate') DEFAULT NULL,
  `processor` varchar(50) DEFAULT NULL,
  `ram` varchar(30) DEFAULT NULL,
  `hd_size` varchar(30) DEFAULT NULL,
  `notes` text,
  `mac_address` varchar(30) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  `related_id` mediumint unsigned DEFAULT NULL,
  `cost` double(10,2) DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  `replace_asset_num` varchar(20) DEFAULT NULL,
  `voided` char(1) DEFAULT NULL,
  `replaced_id` int DEFAULT NULL,
  `tag_code` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `location_id` (`location_id`),
  KEY `name` (`name`,`serial_num`),
  KEY `employee_id` (`employee_id`),
  KEY `division_id` (`division_id`),
  KEY `related_id` (`related_id`),
  CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `devices_ibfk_3` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `devices_ibfk_4` FOREIGN KEY (`division_id`) REFERENCES `divisions` (`id`),
  CONSTRAINT `devices_ibfk_5` FOREIGN KEY (`related_id`) REFERENCES `devices` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7201 DEFAULT CHARSET=utf8mb3;
;;
;;
 CREATE TABLE `discarded_items` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `item_id` mediumint unsigned NOT NULL,
  `asset_num` varchar(15) DEFAULT NULL,
  `type` enum('Device','Monitor','Printer','Other') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `method` enum('Consumed','Discard','Lost') DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `divisions` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `dept_id` mediumint unsigned DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(55) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `divisions_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3;
;;
;;
 CREATE TABLE `domains` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(70) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `donations` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `organization_id` mediumint unsigned DEFAULT NULL,
  `asset_id` mediumint unsigned NOT NULL,
  `asset_num` varchar(15) DEFAULT NULL,
  `type` enum('Device','Monitor','Printer','Other') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `value` decimal(7,2) DEFAULT NULL,
  `lot_id` mediumint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_id` (`asset_id`),
  KEY `organization_id` (`organization_id`),
  KEY `lot_id` (`lot_id`),
  CONSTRAINT `donations_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `donations_ibfk_2` FOREIGN KEY (`lot_id`) REFERENCES `lots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=884 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `employees` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `first_name` varchar(70) DEFAULT NULL,
  `last_name` varchar(70) NOT NULL,
  `office_phone` varchar(50) DEFAULT NULL,
  `role` enum('end_user','admin','helpdesk_admin','helpdesk_teck') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `external_id` (`external_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1427 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `import_details` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `import_id` mediumint unsigned DEFAULT NULL,
  `type` enum('Location','Division','Employee','Device','Monitor','Printer','Software','Ticket') DEFAULT NULL,
  `status` enum('Success','Failure') DEFAULT NULL,
  `message` text,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `import_id` (`import_id`),
  CONSTRAINT `import_details_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `data_imports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23494 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `locations` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb3 ;
;;
 CREATE TABLE `lot_options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `asset_id` char(1) DEFAULT NULL,
  `asset_num` char(1) DEFAULT NULL,
  `serial_num` char(1) DEFAULT NULL,
  `name` char(1) DEFAULT NULL,
  `category` char(1) DEFAULT NULL,
  `division` char(1) DEFAULT NULL,
  `installed` char(1) DEFAULT NULL,
  `date` char(1) DEFAULT NULL,
  `description` char(1) DEFAULT NULL,
  `value` char(1) DEFAULT NULL,
  `weight` char(1) DEFAULT NULL,
  `organization` char(1) DEFAULT NULL,
  `recycle_location` char(1) DEFAULT NULL,
  `auction_name` char(1) DEFAULT NULL,
  `type` enum('Donation','Recycle','Auction') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `lots` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` enum('Donation','Recycle','Auction') DEFAULT NULL,
  `status` enum('Active','Approval','Complete') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `monitors` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `device_id` mediumint unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `asset_num` varchar(40) DEFAULT NULL,
  `serial_num` varchar(90) DEFAULT NULL,
  `screen_size` tinyint DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `vertical_resolution` mediumint unsigned DEFAULT NULL,
  `horizontal_resolution` mediumint unsigned DEFAULT NULL,
  `manufacturer` varchar(100) DEFAULT NULL,
  `received` date DEFAULT NULL,
  `expected_age` tinyint DEFAULT NULL,
  `status` enum('Active','Auctioned','Donated','Recycled','Disposed') DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`),
  KEY `name` (`name`,`serial_num`,`asset_num`),
  CONSTRAINT `monitors_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2355 DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `organizations` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(70) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `city` varchar(80) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `zip` varchar(15) DEFAULT NULL,
  `contact` varchar(80) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `people` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `fullname` varchar(70) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `printers` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `asset_num` varchar(40) DEFAULT NULL,
  `name` varchar(70) DEFAULT NULL,
  `device_id` mediumint DEFAULT NULL,
  `print_processor` varchar(150) DEFAULT NULL,
  `description` varchar(70) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` enum('Active','Donated','Auctioned','Recycled','Disposed') DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  `serial_num` varchar(90) DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`print_processor`,`description`)
) ENGINE=InnoDB AUTO_INCREMENT=7207 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `recycle_locations` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  `inactive` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `recycled_items` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `location_id` tinyint unsigned DEFAULT NULL,
  `asset_id` mediumint unsigned DEFAULT NULL,
  `asset_num` varchar(15) DEFAULT NULL,
  `type` enum('Device','Monitor','Printer','Other') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `weight` decimal(8,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lot_id` mediumint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `lot_id` (`lot_id`),
  CONSTRAINT `recycled_items_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `recycle_locations` (`id`),
  CONSTRAINT `recycled_items_ibfk_2` FOREIGN KEY (`lot_id`) REFERENCES `lots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1110 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `software_installations` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `software_id` mediumint unsigned DEFAULT NULL,
  `device_id` mediumint unsigned DEFAULT NULL,
  `license_id` mediumint unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `external_id` (`external_id`),
  KEY `device_id` (`device_id`),
  KEY `software_id` (`software_id`),
  KEY `license_id` (`license_id`),
  CONSTRAINT `software_installations_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`),
  CONSTRAINT `software_installations_ibfk_2` FOREIGN KEY (`software_id`) REFERENCES `softwares` (`id`),
  CONSTRAINT `software_installations_ibfk_3` FOREIGN KEY (`license_id`) REFERENCES `software_licenses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3065 DEFAULT CHARSET=utf8mb3;
;;
;;
 CREATE TABLE `software_licenses` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `key_value` varchar(40) DEFAULT NULL,
  `type` enum('IndividualLicense','VolumeLicense') DEFAULT NULL,
  `created` date DEFAULT NULL,
  `seat_limit` mediumint DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `external_id` (`external_id`)
) ENGINE=InnoDB AUTO_INCREMENT=862 DEFAULT CHARSET=utf8mb3;
;;
;;
 CREATE TABLE `softwares` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `external_id` mediumint unsigned DEFAULT NULL,
  `display_name` varchar(70) DEFAULT NULL,
  `vendor` varchar(70) DEFAULT NULL,
  `installed` date DEFAULT NULL,
  `install_count` int DEFAULT NULL,
  `editable` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `external_id` (`external_id`),
  KEY `display_name` (`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4025 DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `tickets` (
  `id` int NOT NULL,
  `status` enum('Open','Closed') DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `closed_at` datetime DEFAULT NULL,
  `first_response_sec` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `users` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `first_name` varchar(70) DEFAULT NULL,
  `last_name` varchar(70) NOT NULL,
  `office_phone` varchar(50) DEFAULT NULL,
  `role` enum('end_user','View','Edit','Edit:Admin') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 ;
;;
;;





