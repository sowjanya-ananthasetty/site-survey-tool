-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: site_survey
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attachments`
--

DROP TABLE IF EXISTS `attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attachments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `uploaded_at` datetime(6) DEFAULT NULL,
  `space_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf4lamjg6gecwqtv56uxhlaccs` (`space_id`),
  CONSTRAINT `FKf4lamjg6gecwqtv56uxhlaccs` FOREIGN KEY (`space_id`) REFERENCES `space` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachments`
--

LOCK TABLES `attachments` WRITE;
/*!40000 ALTER TABLE `attachments` DISABLE KEYS */;
INSERT INTO `attachments` VALUES (1,'Sowjanya.photo.jpg','C:\\Users\\Sowjanya\\Documents\\workspace-spring-tools-for-eclipse-5.0.1.RELEASE\\SiteSurveyTool\\uploads\\Sowjanya.photo.jpg','image/jpeg','2026-01-19 14:10:29.530052',1);
/*!40000 ALTER TABLE `attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `entity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `building_name` varchar(255) NOT NULL,
  `property_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3m7yu8fp6seyuracfq6i22i4g` (`property_id`),
  CONSTRAINT `FK3m7yu8fp6seyuracfq6i22i4g` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'Block A',1),(2,'Block B',1),(3,'Admin Block',1),(4,'Block A',1),(5,'Block A',2),(6,'Block B',2),(7,'Main Block',3),(8,'Admin Block',4);
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checklist_responses`
--

DROP TABLE IF EXISTS `checklist_responses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checklist_responses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answers_json` json DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `submitted_at` datetime(6) DEFAULT NULL,
  `target_id` bigint NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `template_id` bigint NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28psfwfil3b98gji3rdkqrirm` (`template_id`),
  CONSTRAINT `FK28psfwfil3b98gji3rdkqrirm` FOREIGN KEY (`template_id`) REFERENCES `checklist_templates` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checklist_responses`
--

LOCK TABLES `checklist_responses` WRITE;
/*!40000 ALTER TABLE `checklist_responses` DISABLE KEYS */;
INSERT INTO `checklist_responses` VALUES (1,'{\"powerAvailable\": true}','2026-01-19 16:59:20.089790','2026-01-19 16:59:42.452039',1,'SPACE','2026-01-19 16:59:42.454040',2,''),(2,'{\"powerAvailable\": true}','2026-01-20 08:50:38.458282','2026-01-21 03:36:29.634583',1,'SPACE','2026-01-20 08:50:38.458282',2,'SUBMITTED'),(3,'{\"powerAvailable\": true}','2026-01-20 08:52:56.101832','2026-01-21 05:59:25.041200',1,'SPACE','2026-01-20 08:52:56.101832',2,'SUBMITTED'),(4,'{\"powerAvailable\": true}','2026-01-20 08:57:13.411489','2026-01-21 03:55:30.892001',999,'SPACE','2026-01-20 08:57:13.411489',2,'SUBMITTED'),(5,'{\"powerAvailable\": true}','2026-01-20 09:09:30.200800',NULL,777,'SPACE','2026-01-20 09:09:30.200800',2,''),(6,'{\"powerAvailable\": true}','2026-01-21 03:03:22.674710',NULL,1,'SPACE','2026-01-21 03:03:22.674710',2,''),(7,'{\"powerAvailable\": true}','2026-01-21 03:19:41.941147',NULL,34,'SPACE','2026-01-21 03:19:41.941147',2,''),(8,'{\"powerAvailable\": true}',NULL,NULL,34,'SPACE',NULL,2,'DRAFT'),(9,'{\"powerAvailable\": true}',NULL,NULL,2,'SPACE',NULL,2,'DRAFT'),(10,'{\"powerAvailable\": true}',NULL,NULL,3,'SPACE',NULL,2,'DRAFT'),(11,'{\"powerAvailable\": true}',NULL,NULL,4,'SPACE',NULL,2,'DRAFT'),(12,'{\"powerAvailable\": true}',NULL,NULL,3,'SPACE',NULL,2,'DRAFT'),(13,'{\"powerAvailable\": true}',NULL,NULL,9,'SPACE',NULL,2,'DRAFT'),(14,'{\"powerAvailable\": true}',NULL,NULL,11,'SPACE',NULL,2,'DRAFT'),(15,'{\"powerAvailable\": true}',NULL,NULL,2,'SPACE',NULL,2,'DRAFT'),(16,'{\"powerAvailable\": true}',NULL,NULL,3,'SPACE',NULL,2,'DRAFT');
/*!40000 ALTER TABLE `checklist_responses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checklist_templates`
--

DROP TABLE IF EXISTS `checklist_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checklist_templates` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  `schema_json` json DEFAULT NULL,
  `scope` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checklist_templates`
--

LOCK TABLES `checklist_templates` WRITE;
/*!40000 ALTER TABLE `checklist_templates` DISABLE KEYS */;
INSERT INTO `checklist_templates` VALUES (2,'2026-01-19 16:51:57.575083',_binary '','Power Checklist',1,'{\"powerAvailable\": true}','SPACE','2026-01-19 16:51:57.575083',1);
/*!40000 ALTER TABLE `checklist_templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floor`
--

DROP TABLE IF EXISTS `floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `floor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `floor_name` varchar(255) NOT NULL,
  `building_id` bigint NOT NULL,
  `floor_number` int NOT NULL,
  `floor_plan_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfvb11l7lpgqc6qdrg3bm24kr3` (`building_id`),
  CONSTRAINT `FKfvb11l7lpgqc6qdrg3bm24kr3` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floor`
--

LOCK TABLES `floor` WRITE;
/*!40000 ALTER TABLE `floor` DISABLE KEYS */;
INSERT INTO `floor` VALUES (1,'Second Floor',1,2,'uploads\\floorplans\\floor_1_nec-logo.png'),(2,'Third Floor',1,3,NULL),(3,'Ground Floor',2,1,NULL),(4,'First Floor',2,2,NULL),(5,'Ground Floor',3,1,NULL),(6,'First Floor',3,2,NULL),(7,'Ground Floor',4,1,NULL),(8,'First Floor',4,2,NULL),(9,'Ground Floor',5,1,NULL),(10,'Ground Floor',7,1,NULL),(11,'First Floor',7,2,NULL),(12,'Second Floor',7,3,NULL),(13,'Ground Floor',8,1,NULL),(14,'First Floor',8,2,NULL),(18,'Ground Floor',1,1,NULL),(19,'First Floor',1,2,NULL),(20,'Ground Floor',2,1,NULL),(21,'First Floor',2,2,NULL),(22,'Ground Floor',3,1,NULL),(23,'Ground Floor',4,1,NULL),(24,'First Floor',4,2,NULL),(25,'Ground Floor',5,1,NULL),(26,'First Floor',5,2,NULL),(27,'Second Floor',5,3,NULL),(28,'Ground Floor',6,1,NULL),(29,'First Floor',6,2,NULL),(30,'Ground Floor',7,1,NULL),(31,'First Floor',7,2,NULL),(32,'Second Floor',7,3,NULL),(33,'Ground Floor',8,1,NULL),(34,'First Floor',8,2,NULL);
/*!40000 ALTER TABLE `floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `property_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (1,'address','Nellore','Tower'),(2,'MG Road','Bangalore','Tower A'),(3,'HiTech City','Hyderabad','Tower B'),(4,'Gachibowli','Hyderabad','Tower C');
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `space`
--

DROP TABLE IF EXISTS `space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `space` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `space_name` varchar(255) NOT NULL,
  `space_type` varchar(255) DEFAULT NULL,
  `floor_id` bigint NOT NULL,
  `geometry_data` longtext,
  `geometry_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfy5v2gwpkp9rubbuw8u8rbwl` (`floor_id`),
  CONSTRAINT `FKgfy5v2gwpkp9rubbuw8u8rbwl` FOREIGN KEY (`floor_id`) REFERENCES `floor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `space`
--

LOCK TABLES `space` WRITE;
/*!40000 ALTER TABLE `space` DISABLE KEYS */;
INSERT INTO `space` VALUES (1,'Room-101','Office',1,NULL,NULL),(2,'Room-102','Office',1,NULL,NULL),(3,'Room-201','Conference',2,'{\"points\":[[10,20],[50,20],[50,60],[10,60]]}','POLYGON'),(4,'Room-101','Office',2,NULL,NULL),(5,'Room-102','Office',2,NULL,NULL),(6,'Room-201','Conference',1,NULL,NULL),(7,'Room-301','Lab',2,NULL,NULL),(8,'Room-101','Office',2,NULL,NULL),(9,'Room-102','Office',2,NULL,NULL),(10,'Room-201','Conference',1,NULL,NULL),(11,'Room-301','Lab',2,NULL,NULL);
/*!40000 ALTER TABLE `space` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
INSERT INTO `survey` VALUES (1,'sowjanya.updated@gmail.com','Chennai','Sowjanya Updated'),(4,'updated@gmail.com','Chennai','Updated Name'),(5,'rahul@gmail.com','Mumbai','Rahul'),(6,'kiran@gmail.com','Pune','Kiran');
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sowjanyaananthasetty07@gmail.com','Ananthasetty Sowjanya','Sowjanya@17'),(2,'saiananthasetty@gmail.com','Ananthasetty Sai','Sairam'),(3,'susmitha@gmail.com','Anchala Susmitha','Susmitha'),(4,'admin@gmail.com','Admin2','Admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-21 12:56:41
