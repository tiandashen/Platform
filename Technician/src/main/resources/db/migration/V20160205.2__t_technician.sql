

CREATE TABLE `technician` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `gender` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '性别',
  `id_no` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `id_photo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `bank` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开户银行',
  `bank_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开户行地址',
  `bank_card_no` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行卡号',
  `status` int(11) DEFAULT NULL COMMENT '0: 未审核，1：审核通过，2：审核未过，3：帐户禁用',
  `verify_at` datetime DEFAULT NULL COMMENT '审核时间',
  `last_login_at` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上次登录IP',
  `create_at` datetime DEFAULT NULL,
  `star` int(11) DEFAULT NULL COMMENT '星级',
  `vote_rate` float DEFAULT NULL COMMENT '好评率',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;