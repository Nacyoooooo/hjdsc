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