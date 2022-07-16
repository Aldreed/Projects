-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `transakcije`
--

DROP TABLE IF EXISTS `transakcije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcije` (
  `idtransakcije` int NOT NULL AUTO_INCREMENT,
  `brojStavke` int NOT NULL,
  `Tip` int NOT NULL,
  `Datum` datetime NOT NULL,
  `Iznos` int NOT NULL,
  `Svrha` varchar(200) DEFAULT NULL,
  `idFilijala` int DEFAULT NULL,
  `Racun` int NOT NULL,
  PRIMARY KEY (`idtransakcije`),
  KEY `Racun_idx` (`Racun`),
  CONSTRAINT `Racun` FOREIGN KEY (`Racun`) REFERENCES `racuni` (`idracuni`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcije`
--

LOCK TABLES `transakcije` WRITE;
/*!40000 ALTER TABLE `transakcije` DISABLE KEYS */;
INSERT INTO `transakcije` VALUES (27,1,0,'2022-07-06 09:37:08',100,'dd',4,15),(28,2,0,'2022-07-06 09:54:32',10,'dd',NULL,15),(29,1,1,'2022-07-06 09:54:32',10,'dd',NULL,16),(30,3,0,'2022-07-06 09:55:46',20,'dd',NULL,15),(31,2,1,'2022-07-06 09:55:46',20,'dd',NULL,16);
/*!40000 ALTER TABLE `transakcije` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-06 14:59:20
