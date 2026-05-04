-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: intelligent_inventory_dashboard
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `action_type`
--

DROP TABLE IF EXISTS `action_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `action_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_type`
--

LOCK TABLES `action_type` WRITE;
/*!40000 ALTER TABLE `action_type` DISABLE KEYS */;
INSERT INTO `action_type` VALUES (1,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533',NULL,'Price Reduction Planned'),(2,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533',NULL,'Promotion Applied'),(3,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533',NULL,'Other');
/*!40000 ALTER TABLE `action_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dealerships`
--

DROP TABLE IF EXISTS `dealerships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dealerships` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `status` enum('ACTIVE','INACTIVE','SUSPENDED') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dealerships`
--

LOCK TABLES `dealerships` WRITE;
/*!40000 ALTER TABLE `dealerships` DISABLE KEYS */;
INSERT INTO `dealerships` VALUES (1,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533','Ho Chi Minh City, VN','dealershipT1@gmail.com','Dealership T1',' (+84) 911 225 411','ACTIVE'),(2,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533','Ho Chi Minh City, VN','dealershipT2@gmail.com','Dealership T2',' (+84) 911 123 123','ACTIVE'),(3,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533','Ho Chi Minh City, VN','dealershipT3@gmail.com','Dealership T3',' (+84) 911 321 321','ACTIVE');
/*!40000 ALTER TABLE `dealerships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','SUSPENDED') DEFAULT NULL,
  `dealership_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4fxjp56sta229o0xe7s6fmcc` (`dealership_id`),
  CONSTRAINT `FKbmjw9dysrqvn2lri44g6p3fci` FOREIGN KEY (`dealership_id`) REFERENCES `dealerships` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533','123 Main St','example@gmail.com','John Doe','$2a$10$o6xLG2qqRTjfOC4//Xu/yeQizxv81rTbvLN/SLZtBVl09NAArjML2','1234567890','USER','ACTIVE',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_actions`
--

DROP TABLE IF EXISTS `vehicle_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle_actions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `action_type` bigint NOT NULL,
  `create_by_user` bigint NOT NULL,
  `vehicle` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdmkuq3unfdq02r8adxa8xvp6j` (`action_type`),
  KEY `FKpr1kcemfk06chmq4ulju48is` (`create_by_user`),
  KEY `FKbb2tqpio40lkduqs5sjtv1ai3` (`vehicle`),
  CONSTRAINT `FKbb2tqpio40lkduqs5sjtv1ai3` FOREIGN KEY (`vehicle`) REFERENCES `vehicles` (`id`),
  CONSTRAINT `FKdmkuq3unfdq02r8adxa8xvp6j` FOREIGN KEY (`action_type`) REFERENCES `action_type` (`id`),
  CONSTRAINT `FKpr1kcemfk06chmq4ulju48is` FOREIGN KEY (`create_by_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_actions`
--

LOCK TABLES `vehicle_actions` WRITE;
/*!40000 ALTER TABLE `vehicle_actions` DISABLE KEYS */;
INSERT INTO `vehicle_actions` VALUES (1,'2026-05-02 21:39:20.572533','2026-05-02 21:39:20.572533',1,1,1),(2,'2026-05-04 17:13:32.494217','2026-05-04 17:13:32.494217',1,1,1);
/*!40000 ALTER TABLE `vehicle_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `arrival_date` datetime(6) NOT NULL,
  `color` varchar(255) NOT NULL,
  `make` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `status` enum('AVAILABLE','PENDING','SOLD') DEFAULT NULL,
  `vin` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `dealership_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk1e24jgpkauh91o97tnp9vi4p` (`dealership_id`),
  CONSTRAINT `FKk1e24jgpkauh91o97tnp9vi4p` FOREIGN KEY (`dealership_id`) REFERENCES `dealerships` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'2025-12-01 00:00:00.000000','2025-12-01 00:00:00.000000','2025-11-01 00:00:00.000000','Red','Toyota','Camry',800000000.00,'AVAILABLE','VIN000000001',2023,1),(2,'2026-03-01 00:00:00.000000','2026-03-01 00:00:00.000000','2026-02-15 00:00:00.000000','Black','Honda','Civic',700000000.00,'AVAILABLE','VIN000000002',2024,1),(3,'2026-04-01 00:00:00.000000','2026-04-01 00:00:00.000000','2026-03-20 00:00:00.000000','White','Mazda','CX-5',900000000.00,'PENDING','VIN000000003',2025,1),(4,'2025-12-10 00:00:00.000000','2025-12-10 00:00:00.000000','2025-11-10 00:00:00.000000','Blue','BMW','X5',2200000000.00,'AVAILABLE','VIN000000004',2023,2),(5,'2026-03-05 00:00:00.000000','2026-03-05 00:00:00.000000','2026-02-20 00:00:00.000000','Gray','Audi','A4',1500000000.00,'AVAILABLE','VIN000000005',2024,2),(6,'2026-04-10 00:00:00.000000','2026-04-10 00:00:00.000000','2026-03-25 00:00:00.000000','Black','Mercedes','C200',1800000000.00,'SOLD','VIN000000006',2025,2),(7,'2025-11-20 00:00:00.000000','2025-11-20 00:00:00.000000','2025-10-20 00:00:00.000000','Silver','Hyundai','SantaFe',1200000000.00,'AVAILABLE','VIN000000007',2022,3),(8,'2026-02-15 00:00:00.000000','2026-02-15 00:00:00.000000','2026-01-20 00:00:00.000000','Red','Kia','Sorento',1100000000.00,'PENDING','VIN000000008',2024,3),(9,'2026-04-20 00:00:00.000000','2026-04-20 00:00:00.000000','2026-03-30 00:00:00.000000','White','Ford','Everest',1300000000.00,'AVAILABLE','VIN000000009',2025,3);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'intelligent_inventory_dashboard'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-04 21:26:31
