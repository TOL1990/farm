CREATE DATABASE  IF NOT EXISTS `farmdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `farmdb`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: farmdb
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `building_bonuces`
--

DROP TABLE IF EXISTS `building_bonuces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building_bonuces` (
  `id_bonus` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `time` int(11) DEFAULT NULL COMMENT 'бонус к времени',
  `proceed` int(11) DEFAULT NULL COMMENT 'бонус к урожаю',
  PRIMARY KEY (`id_bonus`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building_bonuces`
--

LOCK TABLES `building_bonuces` WRITE;
/*!40000 ALTER TABLE `building_bonuces` DISABLE KEYS */;
INSERT INTO `building_bonuces` VALUES (1,'Reactor-Time',50,0),(2,'Harvest_proseed',0,20);
/*!40000 ALTER TABLE `building_bonuces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buildings`
--

DROP TABLE IF EXISTS `buildings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buildings` (
  `id_building` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `bonus_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_building`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buildings`
--

LOCK TABLES `buildings` WRITE;
/*!40000 ALTER TABLE `buildings` DISABLE KEYS */;
INSERT INTO `buildings` VALUES (1,'РЕАКТОР',1000,1),(2,'ТЕПЛИЦА',200,2),(3,'ОЗОНАТОР',70,2),(4,'ТЕНТ',40,1);
/*!40000 ALTER TABLE `buildings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cell`
--

DROP TABLE IF EXISTS `cell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cell` (
  `pos_x` int(2) NOT NULL,
  `pos_y` int(2) NOT NULL,
  `field_id` int(11) NOT NULL,
  `cell_type_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT '0' COMMENT 'какой ид растения или строения',
  `planted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `CellTypeToCellFK_idx` (`cell_type_id`),
  KEY `FieldToCellFK_idx` (`field_id`),
  KEY `PlantToCellFK_idx` (`type_id`),
  CONSTRAINT `BldngToCellFK` FOREIGN KEY (`type_id`) REFERENCES `buildings` (`id_building`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `CellTypeToCellFK` FOREIGN KEY (`cell_type_id`) REFERENCES `cell_type` (`id_cell_type`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FieldToCellFK` FOREIGN KEY (`field_id`) REFERENCES `fields` (`id_field`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `PlantToCellFK` FOREIGN KEY (`type_id`) REFERENCES `plants` (`id_plant`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cell`
--

LOCK TABLES `cell` WRITE;
/*!40000 ALTER TABLE `cell` DISABLE KEYS */;
INSERT INTO `cell` VALUES (1,1,22,1,1,'2017-03-17 08:17:44'),(2,1,22,1,1,'2017-03-17 15:43:58'),(3,1,22,1,1,'2017-03-17 08:17:44'),(4,1,22,1,1,'2017-03-17 08:17:44'),(5,1,22,1,1,'2017-03-17 08:17:44'),(6,1,22,1,1,'2017-03-17 08:17:44'),(7,1,22,1,1,'2017-03-17 08:17:44'),(8,1,22,1,1,'2017-03-17 08:17:44'),(1,2,22,1,1,'2017-03-17 08:17:44'),(2,2,22,1,1,'2017-03-17 15:42:03'),(3,2,22,1,1,'2017-03-17 08:17:44'),(4,2,22,1,1,'2017-03-17 08:17:44'),(5,2,22,1,1,'2017-03-17 08:17:44'),(6,2,22,1,1,'2017-03-17 08:17:44'),(7,2,22,1,1,'2017-03-17 08:17:44'),(8,2,22,1,1,'2017-03-17 08:17:44'),(1,3,22,1,1,'2017-03-17 08:17:44'),(2,3,22,1,1,'2017-03-17 08:17:44'),(3,3,22,1,1,'2017-03-17 08:17:45'),(4,3,22,1,1,'2017-03-17 08:17:45'),(5,3,22,1,1,'2017-03-17 08:17:45'),(6,3,22,1,1,'2017-03-17 08:17:45'),(7,3,22,1,1,'2017-03-17 08:17:45'),(8,3,22,1,1,'2017-03-17 08:17:45'),(1,4,22,1,1,'2017-03-17 08:17:45'),(2,4,22,1,1,'2017-03-17 08:17:45'),(3,4,22,1,1,'2017-03-17 08:17:45'),(4,4,22,1,1,'2017-03-17 08:17:45'),(5,4,22,1,1,'2017-03-17 08:17:45'),(6,4,22,1,1,'2017-03-17 08:17:45'),(7,4,22,1,1,'2017-03-17 08:17:45'),(8,4,22,1,1,'2017-03-17 08:17:45'),(1,5,22,1,1,'2017-03-17 08:17:45'),(2,5,22,1,1,'2017-03-17 08:17:46'),(3,5,22,1,1,'2017-03-17 08:17:46'),(4,5,22,1,1,'2017-03-17 08:17:46'),(5,5,22,1,1,'2017-03-17 08:17:46'),(6,5,22,1,1,'2017-03-17 08:17:46'),(7,5,22,1,1,'2017-03-17 08:17:46'),(8,5,22,1,1,'2017-03-17 08:17:46'),(1,6,22,1,1,'2017-03-17 08:17:46'),(2,6,22,1,1,'2017-03-17 08:17:46'),(3,6,22,1,1,'2017-03-17 08:17:47'),(4,6,22,1,1,'2017-03-17 08:17:47'),(5,6,22,1,1,'2017-03-17 08:17:47'),(6,6,22,1,1,'2017-03-17 08:17:47'),(7,6,22,1,1,'2017-03-17 08:17:47'),(8,6,22,1,1,'2017-03-17 08:17:47'),(1,7,22,1,1,'2017-03-17 08:17:47'),(2,7,22,1,1,'2017-03-17 08:17:47'),(3,7,22,1,1,'2017-03-17 08:17:47'),(4,7,22,1,1,'2017-03-17 08:17:48'),(5,7,22,1,1,'2017-03-17 08:17:48'),(6,7,22,1,1,'2017-03-17 08:17:48'),(7,7,22,1,1,'2017-03-17 08:17:48'),(8,7,22,1,1,'2017-03-17 08:17:48'),(1,8,22,1,1,'2017-03-17 08:17:48'),(2,8,22,1,1,'2017-03-17 08:17:48'),(3,8,22,1,1,'2017-03-17 08:17:48'),(4,8,22,1,1,'2017-03-17 08:17:49'),(5,8,22,1,1,'2017-03-17 08:17:49'),(6,8,22,1,1,'2017-03-17 08:17:49'),(7,8,22,1,1,'2017-03-17 08:17:49'),(8,8,22,1,1,'2017-03-17 08:17:49');
/*!40000 ALTER TABLE `cell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cell_type`
--

DROP TABLE IF EXISTS `cell_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cell_type` (
  `id_cell_type` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_cell_type`),
  UNIQUE KEY `id_cell_type_UNIQUE` (`id_cell_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cell_type`
--

LOCK TABLES `cell_type` WRITE;
/*!40000 ALTER TABLE `cell_type` DISABLE KEYS */;
INSERT INTO `cell_type` VALUES (1,'Empty'),(2,'Plant'),(3,'Building');
/*!40000 ALTER TABLE `cell_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fields` (
  `id_field` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`id_field`),
  KEY `PlayerToFldFK_idx` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
INSERT INTO `fields` VALUES (1,1),(2,2),(19,27),(20,28),(21,30),(22,32);
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plants`
--

DROP TABLE IF EXISTS `plants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plants` (
  `id_plant` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `proceed` int(11) DEFAULT NULL,
  `grow_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_plant`),
  UNIQUE KEY `id_plant_UNIQUE` (`id_plant`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plants`
--

LOCK TABLES `plants` WRITE;
/*!40000 ALTER TABLE `plants` DISABLE KEYS */;
INSERT INTO `plants` VALUES (1,'КАРТОФЕЛЬ',20,25,1),(2,'ЛУК',50,200,10),(3,'АРБУЗ',100,500,1),(4,'КОНОПЛЯ',500,10000,2);
/*!40000 ALTER TABLE `plants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `id_player` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `ballance` int(20) DEFAULT '1000' COMMENT '1000 денег стартовый капитал',
  PRIMARY KEY (`id_player`),
  UNIQUE KEY `id_player_UNIQUE` (`id_player`),
  UNIQUE KEY `nick_name_UNIQUE` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'Q','11',1500),(2,'W','11',1000),(32,'ca','123',300);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'farmdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-17 17:53:31
