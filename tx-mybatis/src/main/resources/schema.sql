CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `country_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `country_code` varchar(255) DEFAULT NULL COMMENT '代码',
  `country_type` varchar(255) DEFAULT NULL COMMENT '国家类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国家信息';