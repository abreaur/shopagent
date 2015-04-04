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
  `credit_limit` double DEFAULT NULL,
  `reliability` double DEFAULT NULL,
  `cui` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `fiscal_code` varchar(255) DEFAULT NULL,
  `delivery_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  KEY `FK_smrp6gi0tckq1w5rnd7boyowu` (`user_id`),
  CONSTRAINT `FK_smrp6gi0tckq1w5rnd7boyowu` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Geometry Global',2,20000,0.5,'RO24020204','Str. Frumoasa, sector 1, București','J11/2304/2004',''),(2,'Saatchi & Saatchi',NULL,18017,0.8,'RO13436705','Nicolae Iorga nr. 13, sector 1, București','',''),(3,'MSPS Marketing',NULL,15000,0.1,'RO34789243','Nicolae Caramfil nr.71-73, sector 1, București',NULL,NULL),(4,'Hello Communication',NULL,250000,0.9,'RO56709823','Str. Soldat Radu Mihaila nr. 15 București',NULL,NULL),(5,'HIPPOS',NULL,10000,0.2,NULL,'Str. Vaselor 60, et. 4, București',NULL,NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item_stock_usages`
--

DROP TABLE IF EXISTS `order_item_stock_usages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item_stock_usages` (
  `order_item_stock_usage_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `used_quantity` double DEFAULT NULL,
  `order_item_id` bigint(20) NOT NULL,
  `used_from_id` bigint(20) NOT NULL,
  `cancelled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`order_item_stock_usage_id`),
  KEY `FK_rtddgxhicr7a1i8nur7k3qrcu` (`order_item_id`),
  KEY `FK_nnlu0ol6axm6mm8bpu7sxkc5p` (`used_from_id`),
  CONSTRAINT `FK_nnlu0ol6axm6mm8bpu7sxkc5p` FOREIGN KEY (`used_from_id`) REFERENCES `stocks` (`stock_id`),
  CONSTRAINT `FK_rtddgxhicr7a1i8nur7k3qrcu` FOREIGN KEY (`order_item_id`) REFERENCES `order_items` (`order_item`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_stock_usages`
--

LOCK TABLES `order_item_stock_usages` WRITE;
/*!40000 ALTER TABLE `order_item_stock_usages` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item_stock_usages` ENABLE KEYS */;
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
  KEY `FK_bq31gjrvv7o8csc9cq7px6tmd` (`stock_id`),
  KEY `FK_q8momd2kqu5mmaf1m5k027wwc` (`unit_of_measure_id`),
  CONSTRAINT `FK_3fea23hxar30bx7m7h8ed25n9` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK_9gap2fmw66v092ntb58rtohwh` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_bq31gjrvv7o8csc9cq7px6tmd` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`),
  CONSTRAINT `FK_q8momd2kqu5mmaf1m5k027wwc` FOREIGN KEY (`unit_of_measure_id`) REFERENCES `unit_of_measures` (`unit_of_measure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
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
  `cancel_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_ktwyfbqs32h2qw22odq9pqmex` (`client_id`),
  KEY `FK_k8kupdtcdpqd57b6j4yq9uvdj` (`user_id`),
  CONSTRAINT `FK_k8kupdtcdpqd57b6j4yq9uvdj` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_ktwyfbqs32h2qw22odq9pqmex` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `person_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cell_phone` varchar(255) DEFAULT NULL,
  `contact_type` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `landline` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `client_id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `FK_6b6euufurlajtqob631ql2uml` (`client_id`),
  CONSTRAINT `FK_6b6euufurlajtqob631ql2uml` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'+40 123 122 312',0,'louis.defunes@client2.ro','Louis','+40 232 222 444','De Funes',2,'Director vanzari'),(2,'+40 744 122 312',1,'max.payne@gmail.com','Maxim','+40 332 777 550','Payne',2,'Resurse umane'),(3,'+40 747 133 435',1,'ion.ovidiu@yahoo.com','Ovidiu','+40 332 777 550','Ion',1,'Director vanzari');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
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
  `description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Rola tip 1','images/products/RolaTip1.jpg','Rola tip 1. 1 bucata = 200 ml. Se poate achizitiona si la ML.'),(2,'Profile pentru Policarbonat','images/products/profile-pentru-policarbonat-12609-p-0.jpg','Profilele din policarbonat sunt ideale pentru realizarea structurilor arhitecturale, a decoraţiunilor interioare etc., ce utilizează ca material policarbonatul. Fiind niste profile din aluminiu, de calitate superioară, acestea reprezinta cea mai buna varianta pentru fixarea sau realizarea de structuri arhitecturale.'),(3,'3M Blockout 3635','images/products/3M-Blockout-3635-0-771.jpg','Autocolantul de inalta performanta 3M Blockout 3635 este ideal pentru producerea semnelor luminoase, acesta oferind un efect vizual extraordinar. Acest autocolant are o componenta deosebita care ii confera o durabilitate crescuta, aderenta buna, stabilitate dimensionala excelenta si calitate de lux a imaginii si a culorilor. Autocolantul 3M Blockout 3635 poate fi decupat pe orice tip de cutter plotter si poate fi printat pe echipamente care functioneaza cu cerneluri pe baza de solvent sau ecosolvent.'),(4,'Arlon Wrapping Film 2300X','images/products/arlon-wrapping-film-2300x-13424-p-0.png','Seria 2300 X este un autocolant cast flexibil, texturat şi colorat care permite personalizarea vehiculelor cu efecte speciale, menţinând, în acelaşi timp, un aspect profesional. Acesta serie deţine tehnologia X-Scape care permite o instalare uşoară fără bule de aer, şi se regăseste într-o gamă largă de culori şi texturi, printre care şi culori Candy de mare efect şi finisaje de carbon şi metal.'),(5,'Natura M/P Photo Paper','images/products/Natura-M-P-Premium-Photo-Glossy-Paper-18-745.jpg','Natura M/P Premium Photo Paper 240/190 este o hârtie fotografică de calitate superioară, cu finisare lucioasa pentru printere care funcţionează cu cerneală pe bază de apă sau pigment. Materialul este ideal pentru lucrări ce necesită o calitate înaltă, cu timp de uscare rapid. Suprafaţa lucioasă dă viaţă culorilor, materialul nu se curbează şi are rezistenţă îndelungată. Este cea mai bună alegere pentru reproducerile de artă. Se poate printa pe: HP, Epson, Canon etc.'),(6,'Luminus White Back','images/products/Luminus-White-Back-51-1352.jpg','Acum puteţi produce cele mai elegante citylight-uri cu hârtia translucentă ce are partea din spate albă. Suprafaţa sa este special tratată, putându-se printa fără probleme pe imprimante care funcţionează cu cerneluri pe bază de solvent sau ecosolvent. Foarte rezistentă la umiditate, zgâriere sau rupere, cu o excelentă calitate a printului - culori vii, strălucitoare şi contraste puternice, chiar dacă materialul este sau nu iluminat, Luminus White Back este soluţia optimă pentru decorări. Puteţi printa această hârtie pe: Vutek, HP, Roland, Epson, Agfa, Mimaki, Mutoh etc.'),(7,'Rola tip 2','images/products/RolaTip2.jpg','Rola tip 2. 1 buc = 50 ML. Se vinde doar la bucata.'),(8,'Sublitex satin','images/products/panza-premium-subli-satin-11675-p-0.jpg','Material textil de culoare alba, fabricat 100% din poliester, imprimabil atat cu cerneluri sublimatice, cat si pe printere latex sau care functioneaza cu cerneluri UV. Are opacitate buna, textura fina satinata si este foarte greu sifonabil.'),(9,'Natura PVC Vinyl','images/products/Natura-Grey-Adhesive-PVC-Vinyl-45-1351.jpg','Natura PVC Vinyl este o folie PVC autoadeziva universala cu adeziv gri sau clear, care se poate printa pe echipamentele cu cerneluri pe baza de apa. Aceasta folie are suprafata opaca si proprietati fizice ideale pentru aplicarea pe suprafete plane, dar si curbe. Durabilitatea excelenta si stabilitatea  buna a adezivului recomanda aceast autocolant pentru o gama larga de lucrari publicitare. Materialul se poate printa pe: HP, Epson, Canon etc.'),(10,'3M Cleaner Spray','images/products/3M-Cleaner-Spray-46-1312.jpg','3M Cleaner Spray este un excelent spray curatitor/degresant care actioneaza foarte eficient asupra depunerilor greu de indepartat, cum ar fi: adezivi intariti, resturi de benzi adezive, materiale autocolante, uleiuri, grasimi, urme de murdarie, cerneluri uscate etc. Substanta activa se bazeaza pe ulei de citrice si nu contine compusi clorinati, solventi provenind din distilarea petrolului, acizi sau substante caustice. Curata suprafete din lemn, sticla, cauciuc, metale (inclusiv aluminiu si inox), mase plastice etc. Este incolor si emulsioneaza in contact cu apa. Dupa folosirea 3M Cleaner Spray este recomandata degresarea suprafetelor cu alcool izopropilic (IPA).'),(11,'Kit curatare folii pentru tuning','images/products/Kit-curatare-folii-pentru-tuning-39-1703.jpg','Usor de folosit si eficient, Kitul (format dintr-o solutie speciala si doi bureti) asigura o curatare in profunzime a foliilor pentru tuning auto. Mai intai spalati folia cu apa si sapun pentru a inlatura cea mai mare parte a mizeriei. Odata ce suprafata s-a uscat, aplicati solutia speciala si frecati cu un burete pentru a scoate mizeria din porii foliei. Apoi, pur si simplu, clatiti cu apa.'),(12,'Altuglas Cleaner','images/products/altuglas-cleaner-13156-p-0.jpg','Altuglas Cleaner este o emulsie de ulei si detergent care are un caracter antistatic si care formeaza o pelicula protectoare pe suprafata aplicata. Fiind o solutie apoasa, Altuglas Cleaner nu este inflamabil si nu este supus la niciun regim in ceea ce priveste substantele periculoase.');
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocks`
--

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;
INSERT INTO `stocks` VALUES (1,1,1,10,150,''),(2,7,1,2,3,''),(3,1,2,15,1,'\0'),(4,1,2,25,1,'\0'),(5,2,2,5,2.4,''),(6,3,3,14,22,''),(7,4,3,7,22,''),(8,5,3,12,2.9,''),(9,6,3,26,1.05,''),(10,8,3,10,2.5,''),(11,9,3,5,4.9,''),(12,10,1,21,16.96,''),(13,11,1,0,15,''),(14,12,1,3,34,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_of_measures`
--

LOCK TABLES `unit_of_measures` WRITE;
/*!40000 ALTER TABLE `unit_of_measures` DISABLE KEYS */;
INSERT INTO `unit_of_measures` VALUES (1,'buc','Bucata'),(2,'ML','Metru Liniar'),(3,'MP','Metru Patrat');
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

-- Dump completed on 2015-04-04 19:55:18
