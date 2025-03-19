/*
 Navicat Premium Data Transfer

 Source Server         : rooki
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : library_ssm

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 25/03/2025 21:34:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bigAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fullAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `isDefault` int NULL DEFAULT NULL,
  `freight` double(10, 2) NULL DEFAULT 8.00,
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `fk_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `commonuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` int NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `awatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `verify` enum('first','second') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `buildTime` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `account`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bookcollection
-- ----------------------------
DROP TABLE IF EXISTS `bookcollection`;
CREATE TABLE `bookcollection`  (
  `collection_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`collection_id`, `user_id`, `book_id`) USING BTREE,
  INDEX `collect_user`(`user_id` ASC) USING BTREE,
  INDEX `collect_book`(`book_id` ASC) USING BTREE,
  CONSTRAINT `collect_book` FOREIGN KEY (`book_id`) REFERENCES `booklist` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collect_user` FOREIGN KEY (`user_id`) REFERENCES `commonuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for booklist
-- ----------------------------
DROP TABLE IF EXISTS `booklist`;
CREATE TABLE `booklist`  (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `main_picture` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `press` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `picture` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category` enum('文学类','科学类','历史类','科普类','艺术类','社科类','生活类') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` double(10, 2) NOT NULL,
  `old_price` double(10, 2) NULL DEFAULT NULL,
  `buildTime` date NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  `sale_number` int NULL DEFAULT 0,
  PRIMARY KEY (`book_id`) USING BTREE,
  INDEX `book_id`(`book_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `detail_number` int NOT NULL,
  `isSelected` int NULL DEFAULT 1,
  `buildTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`cart_id`, `user_id`, `book_id`) USING BTREE,
  INDEX `cart_user`(`user_id` ASC) USING BTREE,
  INDEX `cart_book`(`book_id` ASC) USING BTREE,
  CONSTRAINT `cart_book` FOREIGN KEY (`book_id`) REFERENCES `booklist` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cart_user` FOREIGN KEY (`user_id`) REFERENCES `commonuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for commonuser
-- ----------------------------
DROP TABLE IF EXISTS `commonuser`;
CREATE TABLE `commonuser`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `awatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `verify` enum('common') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `buildTime` date NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `account`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics`  (
  `logistics_id` int NOT NULL AUTO_INCREMENT,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `logistics_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `company_tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`logistics_id`) USING BTREE,
  INDEX `Logistics_order`(`order_id` ASC) USING BTREE,
  CONSTRAINT `Logistics_order` FOREIGN KEY (`order_id`) REFERENCES `orderlist` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail`  (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NULL DEFAULT NULL,
  `order_id` int NULL DEFAULT NULL,
  `detail_number` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`) USING BTREE,
  INDEX `orderDetail_book_id`(`book_id` ASC) USING BTREE,
  INDEX `orderDetail_order_id`(`order_id` ASC) USING BTREE,
  CONSTRAINT `orderDetail_book_id` FOREIGN KEY (`book_id`) REFERENCES `booklist` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orderDetail_order_id` FOREIGN KEY (`order_id`) REFERENCES `orderlist` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for orderlist
-- ----------------------------
DROP TABLE IF EXISTS `orderlist`;
CREATE TABLE `orderlist`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_money` double(10, 2) NULL DEFAULT NULL,
  `number` int NULL DEFAULT NULL,
  `order_status` enum('待付款','待发货','待评价','已退款','已完成','已取消','待收货') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '待付款',
  `summary_status` enum('已汇总','未汇总') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '未汇总',
  `buildTime` datetime NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `delivery` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `order_freight` double NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cancelReason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `orderlist_ibfk_2`(`user_id` ASC) USING BTREE,
  CONSTRAINT `orderlist_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `commonuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for summarylist
-- ----------------------------
DROP TABLE IF EXISTS `summarylist`;
CREATE TABLE `summarylist`  (
  `summary_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `buildTime` date NULL DEFAULT NULL,
  `totalMoney` double NULL DEFAULT NULL,
  `totalCount` int NULL DEFAULT NULL,
  PRIMARY KEY (`summary_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `summarylist_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `commonuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
