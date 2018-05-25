
CREATE DATABASE `tftp`;

USE `tftp`;


DROP TABLE IF EXISTS `tftp_connects`;

CREATE TABLE `tftp_connects` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `connects_ip` varchar(100) NOT NULL COMMENT '连接IP',
  `connects_port` varchar(100) NOT NULL COMMENT '连接端口',
  `remote_file` varchar(100) NOT NULL COMMENT '目标路径',
  `block_size` varchar(100) DEFAULT NULL COMMENT 'block_size',
  `local_file` varchar(100) DEFAULT NULL COMMENT '本地路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


