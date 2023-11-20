SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE IF EXISTS `hjdsc`;
CREATE DATABASE `hjdsc`;
USE `hjdsc`;

-- ----------------------------
-- Table structure for users
-- 用户表
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
                         `authority` INT comment '权限等级',
                         PRIMARY KEY (`id`) USING BTREE
);
-- 密码是123456
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime,authority)
values (1,'云举瑶鹿','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime,authority)
values (2,'青缨','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',2,2,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
insert into users (id, name, password, phoneNumber, email, url, gender, status, createTime, updateTime,authority)
values (3,'云原','e10adc3949ba59abbe56e057f20f883e','12345678910','939832920@qq.com',
        'logo.png',1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
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
                         `level` int comment '等级',
                             PRIMARY KEY (`id`)USING BTREE
);

insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute,
                        createTime, updateTime,level)
VALUES ('喵喵','是一只猫',82,82,83,80,100,100,1,0,'2023-10-18 09:00:00','2023-10-18 09:00:00',100);
insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute,
                        createTime, updateTime,level)
VALUES ('火花','火元素生物',82,82,83,80,100,100,2,0,'2023-10-18 09:00:00','2023-10-18 09:00:00',100);
insert into petsConfig (name, description, healthPoint, physicalDamagePoint, magicalDamagePoin,
                        physicalDefence, magicalDefence, speed, attribute, secondaryAttribute,
                        createTime, updateTime,level)
VALUES ('水蓝蓝','水元素生物',82,82,83,80,100,100,3,0,'2023-10-18 09:00:00','2023-10-18 09:00:00',100);
-- ----------------------------
-- Table structure for petPark
-- 宠物园配置表
-- ----------------------------
DROP TABLE IF EXISTS `petPark`;

CREATE TABLE `petPark` (
                              `id` int  COMMENT  '主键',
                              `pid` int COMMENT '宠物的编号',
                              `count` int COMMENT '宠物的数量',
                              `catched` int COMMENT '是否可被捕捉 1是可以 2是不可以',
                              `createTime` DATETIME comment '宠物创建时间',
                              `updateTime` DATETIME comment '宠物更新时间',
                              `level` int comment '捕捉时的初始等级'
);
insert into petPark (pid, count, catched, createTime, updateTime,id,level) VALUES
(1,100,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1,10);
-- ----------------------------
-- Table structure for captureRecord
-- 宠物园捕捉记录
-- ----------------------------
DROP TABLE IF EXISTS `captureRecord`;

CREATE TABLE `captureRecord` (
                           `id` int  COMMENT  '主键',
                           `cid` int COMMENT '宠物园捕捉项目的编号',
                           `uid` int COMMENT '玩家的id',
                           `pid` int COMMENT '宠物的编号',
                           `createTime` DATETIME comment '记录创建时间',
                           `updateTime` DATETIME comment '记录更新时间',
                            `level` int comment '捕捉时的初始等级',
                           `status` int comment '该捕捉记录是否转化成了宠物'
);
-- ----------------------------
-- Table structure for skills
-- 宠物技能表
-- ----------------------------
DROP TABLE IF EXISTS `skills`;

CREATE TABLE `skills` (
                                 `id` int  auto_increment COMMENT  '主键',
                                 `name` varchar(100) COMMENT '技能名称',
                                 `type` int COMMENT '技能类型 1是威力技能 2是变化技能，回血',
                                 `power` int COMMENT '技能威力 威力技能为正值 变化技能为负值',
                                 `attribute` int COMMENT '技能的属性',
                                 `description` varchar(100) COMMENT '技能文本描述',
                                 `usetimes` int COMMENT '技能使用次数',
                                 `createTime` DATETIME comment '记录创建时间',
                                 `updateTime` DATETIME comment '记录更新时间',
                                 `powertype` int comment '威力技能的伤害类型 1是物理，2是魔法',
                                 PRIMARY KEY (`id`) USING BTREE
);
insert into skills (name, type, power, attribute, description, usetimes, createTime, updateTime,powertype) VALUES
('抓挠',1,100,1,'造成伤害',40,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
insert into skills (name, type, power, attribute, description, usetimes, createTime, updateTime,powertype) VALUES
    ('水之打击',1,120,2,'造成大量伤害',30,'2023-10-18 09:00:00','2023-10-18 09:00:00',2);
insert into skills (name, type, power, attribute, description, usetimes, createTime, updateTime,powertype) VALUES
    ('魔焰瞬击',1,150,3,'造成大量伤害',5,'2023-10-18 09:00:00','2023-10-18 09:00:00',2);
-- ----------------------------
-- Table structure for restrains
-- 宠物属性克制表
-- ----------------------------
DROP TABLE IF EXISTS `restrains`;

CREATE TABLE `restrains` (
                          `id` int  auto_increment COMMENT  '主键',
                          `name` varchar(10) COMMENT '属性名',
                          `restrainId` int comment '克制的属性',
                          `createTime` DATETIME comment '记录创建时间',
                          `updateTime` DATETIME comment '记录更新时间',
                          PRIMARY KEY (`id`) USING BTREE
);
# 草1 水2 火3 光4 恶魔5
insert into restrains (name, restrainId, createTime, updateTime) VALUES
('草',2,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into restrains (name, restrainId, createTime, updateTime) VALUES
    ('水',3,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into restrains (name, restrainId, createTime, updateTime) VALUES
    ('火',1,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into restrains (name, restrainId, createTime, updateTime) VALUES
    ('光',5,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into restrains (name, restrainId, createTime, updateTime) VALUES
    ('恶魔',4,'2023-10-18 09:00:00','2023-10-18 09:00:00');
-- ----------------------------
-- Table structure for resists
-- 宠物属性抵抗表
-- ----------------------------
DROP TABLE IF EXISTS `resists`;

CREATE TABLE `resists` (
                             `id` int  auto_increment COMMENT  '主键',
                             `name` varchar(10) COMMENT '属性名',
                             `resistId` int comment '克制的属性',
                             `createTime` DATETIME comment '记录创建时间',
                             `updateTime` DATETIME comment '记录更新时间',
                             PRIMARY KEY (`id`) USING BTREE
);
# 草1 水2 火3 光4 恶魔5
insert into resists (name, resistId, createTime, updateTime) VALUES
    ('草',2,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into resists (name, resistId, createTime, updateTime) VALUES
    ('水',3,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into resists (name, resistId, createTime, updateTime) VALUES
    ('火',1,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into resists (name, resistId, createTime, updateTime) VALUES
    ('光',5,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into resists (name, resistId, createTime, updateTime) VALUES
    ('恶魔',4,'2023-10-18 09:00:00','2023-10-18 09:00:00');
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
                              `skillone` int comment '首格技能 0代表没有',
                              `skilltwo` int comment '第二格技能',
                              `skillthree` int comment '第三格技能',
                              `skillfour` int comment '第四格技能',
                              `level` int comment '等级',
                              `experience` int comment '经验',
                              PRIMARY KEY (`id`)USING BTREE
);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed,skillone,skilltwo,skillthree,skillfour,level,experience) VALUES
(1,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1,1,0,0,0,100,0);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed,skillone,skilltwo,skillthree,skillfour,level,experience) VALUES
    (2,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',2,1,0,0,0,100,0);
insert into petStore (pid, uid, healthPoint, physicalDamagePoint, magicalDamagePoin, physicalDefence,
                      magicalDefence, speed, createTime, updateTime,performed,skillone,skilltwo,skillthree,skillfour,level,experience) VALUES
    (3,1,1,1,1,1,1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',0,2,0,0,0,100,0);
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
-- ----------------------------
-- Table structure for signs
-- 公告信息记录表
-- ----------------------------
DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
                         `id` int auto_increment COMMENT  '主键,宠物的编号',
                         `uid` int COMMENT '发布者id',
                         `content` varchar(1000) COMMENT '签到的年份',
                         `createTime` DATETIME comment '签到数据创建时间',
                         `updateTime` DATETIME comment '签到数据更新时间',
                         PRIMARY KEY (`id`)USING BTREE
);
insert into notice (uid, content, createTime, updateTime) VALUES
(1,'这是一条测试数据','2023-10-18 09:00:00','2023-10-18 09:00:00');
-- ----------------------------
-- Table structure for mainpet
-- 三主宠信息登记表
-- ----------------------------
DROP TABLE IF EXISTS `mainpet`;

CREATE TABLE `mainpet` (
                          `id` int auto_increment COMMENT  '主键',
                          `uid` int COMMENT '领取者的id',
                          `pid` int comment '玩家选择的三主宠的序列号',
                          `createTime` DATETIME comment '签到数据创建时间',
                          `updateTime` DATETIME comment '签到数据更新时间',
                          `status` int comment '状态，用于约束是否已经放入玩家仓库,1是没放，2是放了',
                          PRIMARY KEY (`id`)USING BTREE
);
insert into mainpet (uid, pid, createTime, updateTime,status) VALUES
                                                           (1,1,'2023-10-18 09:00:00','2023-10-18 09:00:00',1);
-- ----------------------------
-- Table structure for mainpet
-- 三主宠信息登记表
-- ----------------------------
DROP TABLE IF EXISTS `money`;

CREATE TABLE `money` (
                           `id` int auto_increment COMMENT  '主键',
                           `uid` int COMMENT '玩家id',
                           `count` int comment '金币的数量大小',
                           `createTime` DATETIME comment '签到数据创建时间',
                           `updateTime` DATETIME comment '签到数据更新时间',
                           PRIMARY KEY (`id`)USING BTREE
);
insert into money (uid, count, createTime, updateTime)
values (1,10000,'2023-10-18 09:00:00','2023-10-18 09:00:00');
insert into money (uid, count, createTime, updateTime)
values (2,10000,'2023-10-18 09:00:00','2023-10-18 09:00:00');
-- ----------------------------
-- Table structure for reason
-- 封禁信息，解禁申请登记表
-- ----------------------------
DROP TABLE IF EXISTS `reason`;

CREATE TABLE `reason` (
                         `id` int auto_increment COMMENT  '主键',
                         `uid` int COMMENT '玩家id',
                         `description` varchar(100) comment '原因/理由',
                         `type` int comment '该申请的原因',
                         `createTime` DATETIME comment '签到数据创建时间',
                         `updateTime` DATETIME comment '签到数据更新时间',
                         PRIMARY KEY (`id`)USING BTREE
);