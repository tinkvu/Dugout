/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - turf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`turf` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `turf`;

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `turf_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` varchar(40) NOT NULL,
  `timeslot` varchar(50) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `ground_type` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

insert  into `booking`(`book_id`,`turf_id`,`user_id`,`date`,`timeslot`,`status`,`ground_type`) values 
(1,3,2,'27/9/2021','00:00:01','Accepted','5s'),
(2,3,2,'27/9/2021','00:00:01','Accepted','5s'),
(3,3,2,'12/9/2021','13:00-14:00','Accepted','5s'),
(4,3,2,'10/9/2021','16:00-17:00','Accepted','5s'),
(5,3,132,'10/9/2021','16:00-17:00','Accepted','5s'),
(6,3,132,'10/9/2021','17:00-18:00','Accepted','5s'),
(7,3,132,'10/9/2021','17:00-18:00','pending','5s'),
(8,3,132,'10/9/2021','18:00-19:00','pending','5s'),
(9,3,2,'11/9/2021','4:00-5:00','pending','5s'),
(10,3,2,'11/9/2021','1:00-2:00','pending','5s'),
(11,3,132,'11/9/2021','2:00-3:00','pending','5s'),
(12,133,132,'11/9/2021','4:00-5:00','Rejected','5s'),
(13,133,2,'11/9/2021','14:00-15:00','Accepted','5s'),
(14,3,2,'12/9/2021','20:00-21:00','pending','7s'),
(15,3,2,'12/9/2021','16:00-17:00','Rejected','5s'),
(16,133,137,'13/9/2021','13:00-14:00','Accepted','5s'),
(17,3,137,'13/9/2021','16:00-17:00','Rejected','5s'),
(18,3,2,'12/9/2021','16:00-17:00','Accepted','5s'),
(19,3,2,'12/9/2021','16:00-17:00','pending','7s'),
(20,3,2,'14/9/2021','15:00-16:00','pending','5s'),
(21,3,2,'14/9/2021','16:00-17:00','Accepted','7s');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `complaint` varchar(30) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `reply` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`user_id`,`complaint`,`date`,`reply`) values 
(1,2,'1. App crashes','2021-08-25','ggfbdgh'),
(2,3,'2. App crashes','2021-08-17',' ytd juhnj hn'),
(3,2,'kuttaaaan pillaa','2021-09-09','Sorry'),
(4,2,'dfbb','2021-09-09','mmmmm\r\n'),
(5,2,'dh ggsd','2021-09-09','pariganayil undd!'),
(6,2,'you go away stupid idiot','2021-09-10','pending'),
(7,2,'Shibil Mambra','2021-09-13','pending'),
(8,2,'raeaf icjcgu','2021-09-13','asddffgfg'),
(9,2,'raeaf icjcgu','2021-09-13','pending'),
(10,2,'ffhcfv','2021-09-13','pending');

/*Table structure for table `facility` */

DROP TABLE IF EXISTS `facility`;

CREATE TABLE `facility` (
  `fac_id` int(11) NOT NULL AUTO_INCREMENT,
  `turf_id` int(11) NOT NULL,
  `facility` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`fac_id`,`turf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Data for the table `facility` */

insert  into `facility`(`fac_id`,`turf_id`,`facility`) values 
(17,3,'locker,washroom,water,parking,prayer_room,gallery,cafeteria,dressing_room,security_camera,first_aid,shower,gym'),
(18,133,'locker,water,parking,gallery,cafeteria,security_camera,first_aid,gym');

/*Table structure for table `features` */

DROP TABLE IF EXISTS `features`;

CREATE TABLE `features` (
  `feature_id` int(11) NOT NULL AUTO_INCREMENT,
  `feature` varchar(30) DEFAULT NULL,
  `photo` varchar(30) DEFAULT NULL,
  `turf_id` int(11) NOT NULL,
  PRIMARY KEY (`feature_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `features` */

insert  into `features`(`feature_id`,`feature`,`photo`,`turf_id`) values 
(1,'','103.png',1),
(2,'','102.png',2);

/*Table structure for table `gallery` */

DROP TABLE IF EXISTS `gallery`;

CREATE TABLE `gallery` (
  `gallery_id` int(11) NOT NULL AUTO_INCREMENT,
  `turf_id` int(11) DEFAULT NULL,
  `gallery` text,
  PRIMARY KEY (`gallery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `gallery` */

insert  into `gallery`(`gallery_id`,`turf_id`,`gallery`) values 
(14,3,'mathias-herheim-mzwMBFUOECQ-unsplash.jpg'),
(15,3,'abigail-keenan-8-s5QuUBtyM-unsplash.jpg'),
(16,133,'1.png');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `utype` varchar(20) NOT NULL,
  PRIMARY KEY (`login_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`utype`) values 
(1,'admin','123456','admin'),
(2,'user','user','user'),
(3,'turf','1234','turf'),
(132,'8156936165','8156936165','user'),
(133,'aslah@gmail.com','123','turf'),
(134,'9747541376','123','user'),
(135,'9638527410','1234567890','user'),
(137,'9747958189','9747958189','user');

/*Table structure for table `rateinfo` */

DROP TABLE IF EXISTS `rateinfo`;

CREATE TABLE `rateinfo` (
  `rate_id` int(30) NOT NULL AUTO_INCREMENT,
  `turf_id` int(30) DEFAULT NULL,
  `d_rate` varchar(50) NOT NULL,
  `n_rate` varchar(50) NOT NULL,
  PRIMARY KEY (`rate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `rateinfo` */

insert  into `rateinfo`(`rate_id`,`turf_id`,`d_rate`,`n_rate`) values 
(9,3,'500,800,1000','1000,1500,2000'),
(10,133,'300,500','500,800');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rate_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `turf_id` int(11) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  PRIMARY KEY (`rate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rate_id`,`user_id`,`turf_id`,`rating`) values 
(29,132,3,5),
(30,2,3,3),
(31,2,3,1),
(32,2,3,1.5);

/*Table structure for table `sendreply` */

DROP TABLE IF EXISTS `sendreply`;

CREATE TABLE `sendreply` (
  `Reply` varchar(999) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sendreply` */

/*Table structure for table `turf` */

DROP TABLE IF EXISTS `turf`;

CREATE TABLE `turf` (
  `turf_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `turf_name` varchar(30) NOT NULL,
  `place` varchar(30) NOT NULL,
  `post` varchar(30) NOT NULL,
  `pin` int(10) NOT NULL,
  `mob` bigint(10) NOT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  `login_id` int(11) DEFAULT NULL,
  `turf_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`turf_id`),
  UNIQUE KEY `turf_id` (`turf_id`,`mob`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `turf` */

insert  into `turf`(`turf_id`,`turf_name`,`place`,`post`,`pin`,`mob`,`latitude`,`longitude`,`login_id`,`turf_type`) values 
(1,'abcd','calicut','ddd',0,98765432,'11.6554','75.76546',3,'5s,7s,11s'),
(2,'aslah ','puzhakattiri','puzhakattiri',679325,9775454754,'11.11675','11.1123',133,'5 s,7 s');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(30) NOT NULL,
  `l_name` varchar(20) NOT NULL,
  `place` varchar(30) NOT NULL,
  `post` varchar(30) NOT NULL,
  `pin` int(11) NOT NULL,
  `email` varchar(25) NOT NULL,
  `pnum` bigint(11) NOT NULL,
  `login_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`f_name`,`l_name`,`place`,`post`,`pin`,`email`,`pnum`,`login_id`) values 
(1,'abc','de','calicut','cal',673001,'abc@gmail.com',1234567890,2),
(11,'Fahad','Shaneen','Patterkadavu','Patterkadavu',676519,'fsm6165@gmail.com',8156936165,132),
(12,'Shibil','Mambra','shibil.mambra57@gmail.com','shibil.mambra57@gmail.com',679325,'shibil.mambra57@gmail.com',9747541376,134),
(13,'Rinshad','c','Vazhakkad','vazhakkad',676518,'tinkvu123@gmail.com',9638527410,135),
(14,'mohammed','niyas','Patterkadavu','Patterkadavu',679519,'niyas@gmail.com',9747958189,137);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
