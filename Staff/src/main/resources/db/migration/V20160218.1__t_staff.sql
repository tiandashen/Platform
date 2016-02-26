CREATE TABLE IF NOT EXISTS t_staff (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(11) DEFAULT NULL COMMENT '用户名',
  password varchar(32) DEFAULT NULL COMMENT '密码',
  identify_salt varchar(4) DEFAULT NULL COMMENT 'salt',
  register_time DATETIME DEFAULT NULL COMMENT '注册时间',
  role_id int(2) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (id),
  UNIQUE KEY unique_username (username)
)  COMMENT='员工登陆账户表';

insert into t_staff(username,password,identify_salt,register_time,role_id) values('admin','B9ED25A0EBB6CE4784D0C6A677BFC9A1','8XkO',SYSDATE(),1);
