/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80035
Source Host           : localhost:3306
Source Database       : book_manager

Target Server Type    : MYSQL
Target Server Version : 80035
File Encoding         : 65001

Date: 2025-07-07 13:24:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `device_info`
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `deviceId` int NOT NULL AUTO_INCREMENT,
  `deviceName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `deviceAuthor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `devicePrice` decimal(10,2) NOT NULL,
  `deviceTypeId` int NOT NULL,
  `deviceDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '涔︾睄鎻忚堪',
  `isBorrowed` tinyint NOT NULL COMMENT '1表示借出，0表示已还',
  `deviceImg` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '涔︾睄鍥剧墖',
  PRIMARY KEY (`deviceId`) USING BTREE,
  KEY `fk_device_info_book_type_1` (`deviceTypeId`) USING BTREE,
  CONSTRAINT `device_info_ibfk_1` FOREIGN KEY (`deviceTypeId`) REFERENCES `device_type` (`deviceTypeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of device_info
-- ----------------------------
INSERT INTO device_info VALUES ('1', '佳能 EOS 5D Mark IV', '佳能', '15999.00', '1', '全画幅单反，高像素高感表现佳，对焦迅速，适合专业摄影及风光、人像拍摄，性能均衡。', '0', '/pic/0001.png');
INSERT INTO device_info VALUES ('2', '尼康 D850', '尼康', '17999.00', '1', '高分辨率单反，连拍速度快，宽容度出色，适配多种镜头，满足商业摄影需求。', '0', '/pic/0002.png');
INSERT INTO device_info VALUES ('3', '索尼 A7M4', '索尼', '14999.00', '2', '全画幅微单，对焦强画质好，视频功能出色，轻便易携，适合旅行与日常创作。', '0', '/pic/0003.png');
INSERT INTO device_info VALUES ('4', '富士 X-T5', '富士', '8999.00', '2', 'APS-C 画幅微单，复古外观，色彩直出优秀，对焦精准，适合人文与街头摄影。', '0', '/pic/0004.png');
INSERT INTO device_info VALUES ('5', '佳能 EOS R5', '佳能', '25999.00', '2', '全画幅微单，8K 视频录制，高速连拍，防抖出色，专业级影像创作设备。', '0', '/pic/0005.png');
INSERT INTO device_info VALUES ('6', '尼康 Z7 II', '尼康', '19999.00', '2', '全画幅微单，高分辨率，对焦系统升级，画质细腻，适合风光与静态摄影。', '0', '/pic/0006.png');
INSERT INTO device_info VALUES ('7', '索尼 RX10 IV', '索尼', '8499.00', '3', '长焦相机，24-600mm 焦段，高速对焦连拍，适合抓拍野生动物、体育赛事。', '0', '/pic/0007.png');
INSERT INTO device_info VALUES ('8', '佳能 SX70 HS', '佳能', '3499.00', '3', '超长焦相机，21-1365mm 焦段，轻便易携，适合旅行中远摄风光、鸟类拍摄。', '0', '/pic/0008.png');
INSERT INTO device_info VALUES ('9', '尼康 P1000', '尼康', '5999.00', '3', '超强变焦相机，24-3000mm 焦段，可拍月亮细节，适合天文、野生动物摄影。', '0', '/pic/0009.png');
INSERT INTO device_info VALUES ('10', '富士 S100FS', '富士', '4299.00', '3', '长焦旗舰，28-400mm 焦段，手动功能丰富，成像锐利，适合专业长焦创作。', '0', '/pic/0010.png');
INSERT INTO device_info VALUES ('11', '徕卡 M6', '徕卡', '32999.00', '4', '经典胶片旁轴，全金属机身，画质浓郁，机械快门可靠，胶片摄影爱好者首选。', '0', '/pic/0011.png');
INSERT INTO device_info VALUES ('12', '尼康 F3', '尼康', '8999.00', '4', '经典胶片单反，耐用性强，测光精准，适配多款老镜头，体验胶片摄影魅力。', '0', '/pic/0012.png');
INSERT INTO device_info VALUES ('13', '佳能 AE-1', '佳能', '3599.00', '4', '入门胶片单反，操作简单，自动曝光，适合新手学习胶片摄影，性价比高。', '0', '/pic/0013.png');
INSERT INTO device_info VALUES ('14', '奥林巴斯 OM-1', '奥林巴斯', '6799.00', '4', '轻便胶片单反，机身小巧，镜头素质佳，复古设计，适合日常胶片拍摄。', '0', '/pic/0014.png');
INSERT INTO device_info VALUES ('15', 'GoPro HERO11 Black', 'GoPro', '3498.00', '5', '旗舰运动相机，5.3K 视频，防水防抖，可裸机潜水，适合极限运动记录。', '0', '/pic/0015.png');
INSERT INTO device_info VALUES ('16', '大疆 Osmo Action 3', '大疆', '2299.00', '5', '双屏运动相机，耐寒防水，防抖出色，续航持久，适合骑行、潜水等场景。', '0', '/pic/0016.png');
INSERT INTO device_info VALUES ('17', '索尼 HDR-AS50', '索尼', '1599.00', '5', '紧凑型运动相机，1080P 视频，防抖不错，轻便易携，适合日常运动记录。', '0', '/pic/0017.png');
INSERT INTO device_info VALUES ('18', 'Insta360 ONE X2', 'Insta360', '2798.00', '5', '360 度运动相机，全景拍摄，防抖强，可生成创意视频，适合 vlog 创作。', '0', '/pic/0018.png');
INSERT INTO device_info VALUES ('19', '禄来双反 2.8F', '禄来', '28999.00', '6', '经典双反相机，120 胶片，蔡司镜头，画质细腻，收藏与实用兼具的胶片利器。', '0', '/pic/0019.png');
INSERT INTO device_info VALUES ('20', '海鸥 4A', '海鸥', '1299.00', '6', '国产经典双反，120 胶片，操作简单，性价比高，体验双反摄影的复古感。', '0', '/pic/0020.png');
INSERT INTO device_info VALUES ('21', '雅西卡 124G', '雅西卡', '5699.00', '6', '中画幅双反，120 胶片，镜头锐利，机械性能稳定，适合胶片人像、风光拍摄。', '0', '/pic/0021.png');
INSERT INTO device_info VALUES ('22', '玛米亚 C330', '玛米亚', '7899.00', '6', '可换镜头双反，中画幅胶片，灵活性高，成像优秀，专业胶片摄影选择。', '0', '/pic/0022.png');
INSERT INTO device_info VALUES ('23', '徕卡 Q2', '徕卡', '42999.00', '7', '全画幅旁轴，28mm 定焦，高像素，对焦快，便携且画质出色，适合街头摄影。', '0', '/pic/0023.png');
INSERT INTO device_info VALUES ('24', '富士 X-Pro3', '富士', '12799.00', '7', '旁轴造型微单，混合取景器，复古设计，色彩独特，适合人文与文艺创作。', '0', '/pic/0024.png');
INSERT INTO device_info VALUES ('25', '柯尼卡 Hexar AF', '柯尼卡', '4599.00', '7', '自动对焦旁轴，35mm 胶片，静音快门，对焦快，适合街头抓拍与日常记录。', '0', '/pic/0025.png');
INSERT INTO device_info VALUES ('26', '福伦达 Bessa R3A', '福伦达', '6299.00', '7', '手动旁轴胶片相机，兼容多种镜头，做工精良，适合追求手动操控的摄影爱好者。', '0', '/pic/0026.png');
INSERT INTO device_info VALUES ('27', '宾得 K-1 Mark II', '宾得', '13999.00', '1', '全画幅单反，防抖出色，高感好，适配宾得老镜头，适合风光与星空摄影。', '0', '/pic/0027.png');
INSERT INTO device_info VALUES ('28', '佳能 EOS 90D', '佳能', '6499.00', '1', 'APS-C 画幅单反，高像素连拍快，视频功能强，适合入门到进阶摄影用户。', '0', '/pic/0028.png');
INSERT INTO device_info VALUES ('29', '尼康 D780', '尼康', '9799.00', '1', '全画幅单反，兼顾拍照与视频，对焦优秀，操作便捷，适合多场景创作。', '0', '/pic/0029.png');
INSERT INTO device_info VALUES ('30', '索尼 A6600', '索尼', '7299.00', '2', 'APS-C 微单，续航出色，防抖强，对焦快，适合旅行、运动等场景拍摄。', '0', '/pic/0030.png');
INSERT INTO device_info VALUES ('31', '松下 LUMIX S5', '松下', '9499.00', '2', '全画幅微单，视频性能佳，轻便小巧，画质均衡，适合视频创作者与摄影师。', '0', '/pic/0031.png');
INSERT INTO device_info VALUES ('32', '佳能 PowerShot SX60 HS', '佳能', '2999.00', '3', '长焦相机，21-1365mm 焦段，光学防抖，操作简单，适合家庭旅行远摄。', '0', '/pic/0032.png');
INSERT INTO device_info VALUES ('33', '索尼 ILCE-7C', '索尼', '8999.00', '2', '全画幅微单，超轻便，对焦快，画质好，适合女性用户及旅行便携拍摄。', '0', '/pic/0033.png');
INSERT INTO device_info VALUES ('34', '柯达 M35', '柯达', '299.00', '4', '入门胶片相机，35mm 胶片，傻瓜操作，轻便小巧，适合新手体验胶片摄影。', '0', '/pic/0034.png');
INSERT INTO device_info VALUES ('35', '大疆 Pocket 2', '大疆', '2499.00', '5', '手持运动相机，小巧便携，防抖极强，适合 vlog 拍摄，轻松记录生活。', '0', '/pic/0035.png');
INSERT INTO device_info VALUES ('36', '理光 GR III', '理光', '4799.00', '7', '便携旁轴风格，28mm 定焦，画质好，对焦快，街头摄影与日常记录神器。', '0', '/pic/0036.png');
INSERT INTO device_info VALUES ('37', '宾得 645Z', '宾得', '29999.00', '1', '中画幅单反，高分辨率，色彩还原好，适合商业人像、风光等专业摄影。', '0', '/pic/0037.png');
INSERT INTO device_info VALUES ('38', '富士 GFX 50R', '富士', '24999.00', '2', '中画幅无反，复古设计，画质细腻，色彩独特，适合专业静态摄影创作。', '0', '/pic/0038.png');
INSERT INTO device_info VALUES ('39', '尼康 COOLPIX P950', '尼康', '4599.00', '3', '超长焦相机，24-2000mm 焦段，可拍月亮，适合天文、野生动物远距离拍摄。', '0', '/pic/0039.png');
INSERT INTO device_info VALUES ('40', '宝丽来 Now+', '宝丽来', '1599.00', '4', '即时成像胶片相机，可连接手机，创意拍摄，即拍即得，复古与科技结合。', '0', '/pic/0040.png');
-- ----------------------------
-- Table structure for `device_type`
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
CREATE TABLE `device_type` (
  `deviceTypeId` int NOT NULL AUTO_INCREMENT,
  `deviceTypeName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `deviceTypeDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '涔︾睄绫诲瀷鎻忚堪',
  PRIMARY KEY (`deviceTypeId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES ('1', '单反相机', '光学取景，画质较高');
INSERT INTO `device_type` VALUES ('2', '微单相机', '小巧轻便，可换镜头');
INSERT INTO `device_type` VALUES ('3', '长焦相机', '长焦段抓拍远处景物');
INSERT INTO `device_type` VALUES ('4', '胶片相机', '使用底片记录图像');
INSERT INTO `device_type` VALUES ('5', '运动相机', '防震防水适合运动');
INSERT INTO `device_type` VALUES ('6', '双反相机', '双镜头取景成像分离');
INSERT INTO `device_type` VALUES ('7', '旁轴相机', '取景光轴旁于镜头光轴');

-- ----------------------------
-- Table structure for `borrow`
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `borrowId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `deviceId` int NOT NULL,
  `borrowTime` datetime NOT NULL,
  `returnTime` datetime DEFAULT NULL,
  PRIMARY KEY (`borrowId`) USING BTREE,
  KEY `fk_borrow_user_1` (`userId`) USING BTREE,
  KEY `fk_borrow_book_info_1` (`deviceId`) USING BTREE,
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`deviceId`) REFERENCES `book_info` (`deviceId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('26', '11', '2', '2021-11-18 14:24:06', '2021-11-18 16:07:03');
INSERT INTO `borrow` VALUES ('27', '11', '1', '2021-11-18 15:01:31', '2021-11-18 16:07:06');
INSERT INTO `borrow` VALUES ('28', '11', '4', '2021-11-18 15:22:05', '2021-11-18 16:07:08');
INSERT INTO `borrow` VALUES ('30', '14', '2', '2021-11-18 16:52:05', '2021-11-19 20:55:10');
INSERT INTO `borrow` VALUES ('32', '14', '4', '2021-11-18 16:52:17', '2021-11-18 16:52:41');
INSERT INTO `borrow` VALUES ('38', '14', '1', '2021-11-19 22:19:43', '2021-11-19 22:19:48');
INSERT INTO `borrow` VALUES ('39', '14', '1', '2021-11-19 22:46:14', '2021-11-19 22:46:18');
INSERT INTO `borrow` VALUES ('40', '14', '1', '2021-11-19 22:57:21', '2021-11-19 22:57:26');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `userPassword` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `isAdmin` tinyint NOT NULL COMMENT '1是管理员，0非管理员',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '1');
INSERT INTO `user` VALUES ('2', '李明', '123456', '0');
INSERT INTO `user` VALUES ('11', 'zhang', '123', '0');
INSERT INTO `user` VALUES ('13', 'zhao', 'abc', '1');
INSERT INTO `user` VALUES ('14', 'user', '123456', '0');
