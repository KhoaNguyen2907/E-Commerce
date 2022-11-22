

DROP DATABASE IF EXISTS `online_shop_db`;
CREATE DATABASE `online_shop_db`;
USE `online_shop_db`;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL UNIQUE,
  `name` varchar(255) DEFAULT NULL,
  `avatar` text DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL,
  `role_id` int NOT NULL,
  `created_date` timestamp,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_users_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `subtitle` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `content` text,
  `users_id` int,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_post_users` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
);

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` text,
  `users_id` int NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_comment_user` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_comment_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
);



DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` timestamp,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  `code` varchar(25) DEFAULT NULL,
  `image_url` text,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` long NOT NULL,
  `description` text,
  `image_url` text,
  `stock` int,
  `created_date` timestamp,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
);

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
`product_id` int NOT NULL,
`category_id` int NOT NULL
);

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `users_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_review_user` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_review_post` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address_id` int NOT NULL UNIQUE,
  `comment` varchar(255) DEFAULT NULL,
  `created_date` timestamp,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` long DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `users_id` int,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_order_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
);


DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_price` long,
  `quantity` int,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `FK_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);



