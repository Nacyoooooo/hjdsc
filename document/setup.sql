SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE IF EXISTS `hjdsc`;
CREATE DATABASE `hjdsc`;
USE `hjdsc`;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `id` int auto_increment COMMENT  '主键，统一账号',
                         `name` varchar(20) COMMENT '姓名',
                         `password` varchar(100) COMMENT '密码',
                         `phoneNumber` varchar(20) COMMENT '手机号',
                         `email` varchar(20) COMMENT '邮箱',
                         `url` varchar(100) comment '头像的url地址',
                         `gender` INT comment '性别 1是男 2是女',
                         `status` INT comment '账号状态 1是正常 2是异常',
                         `createTime` DATETIME comment '账号创建时间',
                         `updateTime` DATETIME comment '账号更新时间',
                         PRIMARY KEY (`id`) USING BTREE
);
-- 密码是123456
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime)
values (1,'云举瑶鹿','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime)
values (2,'青缨','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',2,2,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime)
values (3,'云原','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00');

-- ----------------------------
-- Table structure for pets
-- 宠物信息配置表
-- ----------------------------
DROP TABLE IF EXISTS `petsConfig`;

CREATE TABLE `petsConfig` (
                         `id` int auto_increment COMMENT  '主键,宠物的编号',
                         `name` varchar(20) COMMENT '宠物的名字',
                         `description` varchar(100) COMMENT '宠物的描述',
                         `healthPoint` DECIMAL COMMENT '血量',
                         `physicalDamagePoint` DECIMAL COMMENT '物理攻击力',
                         `magicalDamagePoin` DECIMAL comment '法术攻击力',
                         `physicalDefence` DECIMAL comment '物理防御力',
                         `magicalDefence` DECIMAL comment '法术防御力',
                         `speed` DECIMAL comment '速度',
                         `attribute` INT comment '主属性（不能没有）',
                         `secondaryAttribute` INT comment '副属性（可以没有）',
                         `createTime` DATETIME comment '宠物创建时间',
                         `updateTime` DATETIME comment '宠物更新时间',
                         PRIMARY KEY (`id`)USING BTREE
);

insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute, createTime, updateTime)
VALUES ('喵喵','是一只猫',82,82,83,80,100,100,1,0,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute, createTime, updateTime)
VALUES ('火花','火元素生物',82,82,83,80,100,100,2,0,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute, createTime, updateTime)
VALUES ('水蓝蓝','水元素生物',82,82,83,80,100,100,3,0,'2023-10-18 09:00:00','2023-10-18 09:00:00');
-- ----------------------------
-- Table structure for petStore
-- 用户宠物仓库表
-- ----------------------------
DROP TABLE IF EXISTS `petStore`;

CREATE TABLE `petStore` (
                              `id` int auto_increment COMMENT  '主键自增',
                              `pid` int COMMENT '宠物的编号',
                              `uid` int COMMENT '玩家的编号',
                              `healthPoint` DECIMAL COMMENT '血量 0-31',
                              `physicalDamagePoint` DECIMAL COMMENT '物理攻击力 0-31',
                              `magicalDamagePoin` DECIMAL comment '法术攻击力 0-31',
                              `physicalDefence` DECIMAL comment '物理防御力 0-31',
                              `magicalDefence` DECIMAL comment '法术防御力 0-31',
                              `speed` DECIMAL comment '速度 0-31',
                              `createTime` DATETIME comment '宠物获取时间',
                              `updateTime` DATETIME comment '宠物更新时间',
                              `performed` int comment '是否上阵 0表示不在背包 1-6或更多表示背包中的位置',
                              PRIMARY KEY (`id`)USING BTREE
);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
(1,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
    (2,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',2);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
    (3,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',0);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
    (3,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',0);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
    (3,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',0);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed) VALUES
    (3,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',0);
-- ----------------------------
-- Table structure for signs
-- 用户签到信息记录表
-- ----------------------------
DROP TABLE IF EXISTS `signs`;

CREATE TABLE `signs` (
                              `id` int auto_increment COMMENT  '主键,宠物的编号',
                              `uid` int COMMENT '用户id',
                              `signYear` int COMMENT '签到的年份',
                              `signMonth`  int COMMENT '签到的月份',
                              `signData` long COMMENT '签到的数据，转化为十进制后',
                              `createTime` DATETIME comment '签到数据创建时间',
                              `updateTime` DATETIME comment '签到数据更新时间',
                              PRIMARY KEY (`id`)USING BTREE
);
insert into signs (uid, signYear, signMonth, signData, createTime, updateTime) VALUES
(1,2023,11,3524,'2023-10-18 09:00:00','2023-10-18 09:00:00');