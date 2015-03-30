-- MySQL dump 10.13  Distrib 5.5.40, for Win64 (x86)
--
-- Host: localhost    Database: shopagent
-- ------------------------------------------------------
-- Server version	5.5.40

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `credit_limit` double DEFAULT 0,
  `reliability` double DEFAULT 0,
  PRIMARY KEY (`client_id`),
  KEY `FK_smrp6gi0tckq1w5rnd7boyowu` (`user_id`),
  CONSTRAINT `FK_smrp6gi0tckq1w5rnd7boyowu` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Client 1',2,1000,1000),(2,'Client 2',NULL,1000,1000);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `order_item` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  `unit_of_measure_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_item`),
  KEY `FK_9gap2fmw66v092ntb58rtohwh` (`order_id`),
  KEY `FK_3fea23hxar30bx7m7h8ed25n9` (`product_id`),
  CONSTRAINT `FK_3fea23hxar30bx7m7h8ed25n9` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK_9gap2fmw66v092ntb58rtohwh` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (5,10,0,10,1,5,1,0,0),(6,2,0,2,2,5,3,0,0),(7,3,0,3,3,5,4,0,0),(8,0.28,0,0.28,1,5,12,0,0),(9,12,0,12,2,5,2,0,0),(14,90,0,45,2,6,2,5,1),(15,750,0.5,150,10,6,1,1,1);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `client_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `expected_delivery_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_ktwyfbqs32h2qw22odq9pqmex` (`client_id`),
  KEY `FK_k8kupdtcdpqd57b6j4yq9uvdj` (`user_id`),
  CONSTRAINT `FK_k8kupdtcdpqd57b6j4yq9uvdj` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_ktwyfbqs32h2qw22odq9pqmex` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,0,'2015-03-26 21:37:56',0,2,2,NULL),(6,840,'2015-03-28 17:44:23',0,1,2,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Rola 1','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/I/M/IMG_8717.JPG'),(2,'Product 2','http://www.ikea.com/PIAimages/0151521_PE309574_S3.JPG'),(3,'Product 3','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1018300.jpg'),(4,'Product 4','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/10347_large.jpg'),(5,'Product 5','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1002051.jpg'),(6,'Product 6','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1003367_1.jpg'),(7,'Rola 7','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1027681.jpg'),(8,'Product 8','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1024332_1.jpg'),(9,'Product 9','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1018502.jpg'),(10,'Product 10','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/3/4/34112.jpg'),(11,'Product 11','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1019117_2.jpg'),(12,'Product 12','http://i.dedeman.ro/media/catalog/product/cache/dedeman/small_image/135x/9df78eab33525d08d6e5fb8d27136e95/1/0/1032638.jpg');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_description` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (3,NULL,'AGENT'),(4,NULL,'CLIENT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_converters`
--

DROP TABLE IF EXISTS `stock_converters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_converters` (
  `stock_converter_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_unit_of_measure_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `to_unit_of_measure_id` bigint(20) NOT NULL,
  `rate` double DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  PRIMARY KEY (`stock_converter_id`),
  KEY `FK_4bycuhijbdga696ni6a1nwsa7` (`from_unit_of_measure_id`),
  KEY `FK_i01g5dr3vxkafrmhmnt6mp4rl` (`product_id`),
  KEY `FK_ilmyjhms5xm1aqg4wh9k8n5al` (`to_unit_of_measure_id`),
  CONSTRAINT `FK_4bycuhijbdga696ni6a1nwsa7` FOREIGN KEY (`from_unit_of_measure_id`) REFERENCES `unit_of_measures` (`unit_of_measure_id`),
  CONSTRAINT `FK_i01g5dr3vxkafrmhmnt6mp4rl` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK_ilmyjhms5xm1aqg4wh9k8n5al` FOREIGN KEY (`to_unit_of_measure_id`) REFERENCES `unit_of_measures` (`unit_of_measure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_converters`
--

LOCK TABLES `stock_converters` WRITE;
/*!40000 ALTER TABLE `stock_converters` DISABLE KEYS */;
INSERT INTO `stock_converters` VALUES (1,1,1,2,200,1);
/*!40000 ALTER TABLE `stock_converters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocks`
--

DROP TABLE IF EXISTS `stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stocks` (
  `stock_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `unit_of_measure_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `main` bit(1) DEFAULT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `FK_htp625bmmsb6gay567r5sdfoc` (`product_id`),
  KEY `FK_t4dk2ens7morbtjktcpy5xoe7` (`unit_of_measure_id`),
  CONSTRAINT `FK_htp625bmmsb6gay567r5sdfoc` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK_t4dk2ens7morbtjktcpy5xoe7` FOREIGN KEY (`unit_of_measure_id`) REFERENCES `unit_of_measures` (`unit_of_measure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocks`
--

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;
INSERT INTO `stocks` VALUES (1,1,1,10,150,''),(2,7,1,2,3,''),(3,1,2,15,1,'\0'),(4,1,2,25,1,'\0'),(5,2,1,2,45,''),(6,3,1,0,345,''),(7,4,1,0,12,''),(8,5,1,2,19.99,''),(9,6,2,200,2,''),(10,8,1,0,1999,''),(11,9,1,1,298,''),(12,10,1,22,12,''),(13,11,1,0,10,''),(14,12,1,0,22,'');
/*!40000 ALTER TABLE `stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_of_measures`
--

DROP TABLE IF EXISTS `unit_of_measures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit_of_measures` (
  `unit_of_measure_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`unit_of_measure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_of_measures`
--

LOCK TABLES `unit_of_measures` WRITE;
/*!40000 ALTER TABLE `unit_of_measures` DISABLE KEYS */;
INSERT INTO `unit_of_measures` VALUES (1,'buc','Bucata'),(2,'m','Metru');
/*!40000 ALTER TABLE `unit_of_measures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_5q4rc4fh1on6567qk69uesvyf` (`role_id`),
  CONSTRAINT `FK_5q4rc4fh1on6567qk69uesvyf` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `FK_g1uebn6mqk9qiaw45vnacmyo2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,3),(2,4);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Andrei','POPESCU','a','agent'),(2,'Marius','VASILE','a','client');
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

-- Dump completed on 2015-03-28 18:42:20
