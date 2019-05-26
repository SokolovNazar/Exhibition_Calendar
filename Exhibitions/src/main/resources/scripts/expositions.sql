-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: ExpositionProject
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.18.04.2

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
-- Table structure for table `exhibitionHalls`
--

DROP TABLE IF EXISTS `exhibitionHalls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exhibitionHalls` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `information` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `state` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`id`),
  KEY `state` (`state`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibitionHalls`
--

LOCK TABLES `exhibitionHalls` WRITE;
/*!40000 ALTER TABLE `exhibitionHalls` DISABLE KEYS */;
INSERT INTO `exhibitionHalls` VALUES (1,'Міжнародний виставковий центр','Броварський просп, 15, Київ, 02000','OK'),(2,'ACCO International','Проспект Перемоги, 40-Б, Київ, 03057, Україна','OK'),(3,'Експодонбас','вул. Челюскінців 189б, м. Донецьк 83000, Донецька обл., Україна','OK'),(4,'Одеський палац спорту','Проспект Шевченка, 31, 65058, Україна, Одеська обл., Одеса','OK'),(5,'Виставка рухомого складу історичних локомотивів та вагонів','станція Київ-Пасажирський','OK'),(6,'КиївЕкспоПлаза','вул. Салютна, 2-Б, Київ','OK'),(7,'Конгресно-виставковий центр «Парковий»','Паркова дорога 16-а, Київ','OK'),(8,'Національний експоцентр України','Проспект Академіка Глушкова, 1, Київ','OK'),(9,'Палац спорту (Київ)','Спортивна площа, 1, Київ, 02000','OK');
/*!40000 ALTER TABLE `exhibitionHalls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expositions`
--

DROP TABLE IF EXISTS `expositions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expositions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` date NOT NULL DEFAULT '1999-01-01',
  `shortDescription` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fullDescription` varchar(10000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` int(11) NOT NULL,
  `hall_id` int(11) NOT NULL,
  `state` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'OK',
  `date_to` date NOT NULL DEFAULT '1999-01-01',
  PRIMARY KEY (`id`),
  KEY `expositions_exhibitionHalls_id_fk` (`hall_id`),
  CONSTRAINT `expositions_exhibitionHalls_id_fk` FOREIGN KEY (`hall_id`) REFERENCES `exhibitionHalls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expositions`
--

LOCK TABLES `expositions` WRITE;
/*!40000 ALTER TABLE `expositions` DISABLE KEYS */;
INSERT INTO `expositions` VALUES (1,'ЛАК&ФАРБА EXPO UA ‑ 2019','2019-05-02','З 2 по 5 квітня на території Міжнародного виставкового центру відбулися міжнародні спеціалізовані виставки Addit Expo 3D, Plast Expo Ua, Київський Технічний Ярмарок та Лак&Фарба EXPO UA ‑ 2019, присвячені новітнім технологіям переробки полімерів, машинобудуванню, тривимірному друку та лакофарбовій продукції.','',5,1,'OK','2019-05-05'),(2,'PLAST EXPO UA ‑ 2019','2019-05-17','З 17 по 20 квітня на території Міжнародного виставкового центру відбулися міжнародні спеціалізовані виставки Addit Expo 3D, Plast Expo Ua, Київський Технічний Ярмарок та Лак&Фарба EXPO UA ‑ 2019, присвячені новітнім технологіям переробки полімерів, машинобудуванню, тривимірному друку та лакофарбовій продукції.','',3,1,'OK','2019-05-20'),(3,'ЮВЕЛІР МАШ ЕКСПО - 2019','2019-04-23','Тривалість 2 дні.\r\nОрганізатор: ТОВ \"Київський міжнародний контрактовий ярмарок\"','тел.:+38044 490-6219',5,1,'OK','2019-04-25'),(4,'АВТОДОРЕКСПО 2019','2019-11-06','17-й Міжнародний форум з будівництва, експлуатації, проектування автомобільних доріг і мостів у Києві.','Коли: 06.11.2019 - 08.11.2019',10,2,'OK','2019-11-12'),(5,'Нова виставка','2019-04-30','Короткий опис','',20,9,'DELETED','2019-04-30'),(6,'Тестова виставка №1','2019-04-30','Короткий опис №1','',3,8,'DELETED','2019-06-14');
/*!40000 ALTER TABLE `expositions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `exposition_id` int(11) NOT NULL,
  `count` int(11) DEFAULT '1',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `exposition_fk` (`exposition_id`),
  KEY `user_fk` (`user_id`),
  CONSTRAINT `exposition_fk` FOREIGN KEY (`exposition_id`) REFERENCES `expositions` (`id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,4,2,3,'2019-04-14 17:57:53'),(2,4,1,2,'2019-04-18 08:19:46'),(3,4,5,2,'2019-04-19 14:43:14'),(4,4,6,2,'2019-04-19 17:21:45'),(5,4,3,1,'2019-04-19 18:35:41'),(6,10,4,4,'2019-04-19 18:40:47');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `login` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_login_uindex` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Taras','Genadievich','TarasG@post.net','admin','5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8','ADMIN'),(4,'Testname','Testsurname','test@test.test','user1','6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B','USER'),(10,'Taras','Hurniak','Taras@gmail.com','Taras','5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8','USER');
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

-- Dump completed on 2019-04-19 23:29:45
