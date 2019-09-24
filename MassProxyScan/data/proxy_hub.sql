/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : proxy_hub

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 21/09/2019 21:10:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ph_loc_city
-- ----------------------------
DROP TABLE IF EXISTS `ph_loc_city`;
CREATE TABLE `ph_loc_city`  (
  `city_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `city_region_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `city_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`city_id`) USING BTREE,
  INDEX `idx_city_region_id`(`city_region_id`) USING BTREE,
  INDEX `idx_city_name`(`city_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ph_loc_country
-- ----------------------------
DROP TABLE IF EXISTS `ph_loc_country`;
CREATE TABLE `ph_loc_country`  (
  `country_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `country_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `country_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`country_id`) USING BTREE,
  INDEX `idx_country_code`(`country_code`) USING BTREE,
  INDEX `idx_country_name`(`country_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ph_loc_range
-- ----------------------------
DROP TABLE IF EXISTS `ph_loc_range`;
CREATE TABLE `ph_loc_range`  (
  `range_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `range_from` int(10) UNSIGNED NULL DEFAULT NULL,
  `range_to` int(10) UNSIGNED NULL DEFAULT NULL,
  `range_city_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `range_latitude` decimal(11, 6) NULL DEFAULT NULL,
  `range_longitude` decimal(11, 6) NULL DEFAULT NULL,
  PRIMARY KEY (`range_id`) USING BTREE,
  INDEX `idx_range_from`(`range_from`) USING BTREE,
  INDEX `idx_range_to`(`range_to`) USING BTREE,
  INDEX `idx_range_city_id`(`range_city_id`) USING BTREE,
  INDEX `idx_range_from_to`(`range_from`, `range_to`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for ph_loc_region
-- ----------------------------
DROP TABLE IF EXISTS `ph_loc_region`;
CREATE TABLE `ph_loc_region`  (
  `region_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `region_country_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `region_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`region_id`) USING BTREE,
  INDEX `idx_region_country_id`(`region_country_id`) USING BTREE,
  INDEX `idx_region_name`(`region_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ph_proxy
-- ----------------------------
DROP TABLE IF EXISTS `ph_proxy`;
CREATE TABLE `ph_proxy`  (
  `proxy_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `proxy_ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `proxy_port` int(5) UNSIGNED NULL DEFAULT NULL,
  `proxy_type` int(1) UNSIGNED NULL DEFAULT NULL,
  `proxy_anonymity` int(1) UNSIGNED NULL DEFAULT NULL,
  `proxy_country_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_region_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_city_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_connect_time` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_response_time` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_score` int(10) UNSIGNED NULL DEFAULT NULL,
  `proxy_check_time` datetime(0) NULL DEFAULT NULL,
  `proxy_record_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`proxy_id`) USING BTREE,
  UNIQUE INDEX `uk_proxy_ip_port`(`proxy_ip`, `proxy_port`) USING BTREE,
  INDEX `idx_proxy_ip`(`proxy_ip`) USING BTREE,
  INDEX `idx_proxy_port`(`proxy_port`) USING BTREE,
  INDEX `idx_proxy_type`(`proxy_type`) USING BTREE,
  INDEX `idx_proxy_anonymity`(`proxy_anonymity`) USING BTREE,
  INDEX `idx_proxy_country_id`(`proxy_country_id`) USING BTREE,
  INDEX `idx_proxy_region_id`(`proxy_region_id`) USING BTREE,
  INDEX `idx_proxy_city_id`(`proxy_city_id`) USING BTREE,
  INDEX `idx_proxy_connect_time`(`proxy_connect_time`) USING BTREE,
  INDEX `idx_proxy_response_time`(`proxy_response_time`) USING BTREE,
  INDEX `idx_proxy_score`(`proxy_score`) USING BTREE,
  INDEX `idx_proxy_check_time`(`proxy_check_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
