-- ----------------------------
-- 1、用户表
-- ----------------------------
CREATE TABLE user
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(30)  NOT NULL COMMENT '用户名（账号）',
    `password`    VARCHAR(100) NOT NULL COMMENT '用户密码',
    `nickname`    VARCHAR(50)  NOT NULL COMMENT '用户昵称',
    `email`       VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '用户邮箱',
    `sex`         TINYINT(1) NOT NULL COMMENT '用户性别（0-女 1-男 2-未知）',
    `status`      TINYINT (4) DEFAULT 0 COMMENT '状态，0-正常, 1-停用',
    `creator`     VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`     TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0-否，1-是',
    PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '用户信息表';

-- ----------------------------
-- 2、角色信息表
-- ----------------------------
create table role
(
    `id`          BIGINT(20) NOT NULL auto_increment COMMENT '角色ID',
    `name`        VARCHAR(50) NOT NULL COMMENT '角色名称',
    `order`       INT(4) DEFAULT 0 COMMENT '显示顺序',
    `status`      TINYINT (4) DEFAULT 1 COMMENT '状态,0-正常, 1-停用',
    `remark`      VARCHAR(500)         DEFAULT '' COMMENT '备注',
    `creator`     VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`     TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0-否，1-是',
    primary key (id)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '角色信息表';

-- ----------------------------
-- 3、用户和角色关联表
-- ----------------------------
create table user_role
(
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    primary key (user_id, role_id)
) ENGINE=INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '用户和角色关联表';

-- ----------------------------
-- 4、菜单表
-- ----------------------------
create table menu
(
    `id`           BIGINT(20) NOT NULL auto_increment COMMENT '菜单ID',
    `name`         VARCHAR(50) NOT NULL COMMENT '菜单名称',
    `parent_id`    BIGINT(20) DEFAULT 0 COMMENT '父菜单ID',
    `order`        INT(4) DEFAULT 0 COMMENT '显示顺序',
    `path`         VARCHAR(200)         DEFAULT '' COMMENT '路由地址',
    `component`    VARCHAR(255)         DEFAULT '' COMMENT '组件路径',
    `route_params` VARCHAR(255)         DEFAULT '' COMMENT '路由参数',
    `frameable`    TINYINT(1) DEFAULT 0 COMMENT '是否为外链（0-否 1-是）',
    `type`         TINYINT(1) NOT NULL COMMENT '菜单类型（0-目录 1-菜单 2-按钮）',
    `visible`      TINYINT(1) DEFAULT 0 COMMENT '菜单状态（0-显示 1-隐藏）',
    `status`       TINYINT(1) DEFAULT 0 COMMENT '菜单状态（0-正常 1-停用）',
    `perms`        VARCHAR(100)         DEFAULT '' COMMENT '权限标识',
    `icon`         VARCHAR(100)         DEFAULT '#' COMMENT '菜单图标',
    `remark`       VARCHAR(500)         DEFAULT '' COMMENT '备注',
    `creator`      VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
    `update_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`      TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0-否，1-是',
    primary key (id)
) ENGINE=INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '菜单表';

-- ----------------------------
-- 5、角色和菜单关联表
-- ----------------------------
create table role_menu
(
    role_id bigint(20) NOT NULL COMMENT '角色ID',
    menu_id bigint(20) NOT NULL COMMENT '菜单ID',
    primary key (role_id, menu_id)
) ENGINE=INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '角色和菜单关联表';

-- ----------------------------
-- 6、图书表
-- ----------------------------
CREATE TABLE `book`
(
    `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
    `name`               VARCHAR(50) NOT NULL COMMENT '图书名称',
    `author`             VARCHAR(50) NOT NULL COMMENT '作者',
    `available_quantity` INT (11) NOT NULL DEFAULT 0 COMMENT '可借阅数量',
    `borrowing_quantity` INT (11) NOT NULL DEFAULT 0 COMMENT '借阅中数量',
    `lost_quantity`      INT (11) NOT NULL DEFAULT 0 COMMENT '丢失数量',
    `price`              BIGINT(20) NOT NULL COMMENT '图书价格，单位:分',
    `publishing_house`   VARCHAR(50)          DEFAULT '' COMMENT '出版社',
    `publishing_time`    DATETIME             DEFAULT NULL COMMENT '出版时间',
    `status`             TINYINT (4) DEFAULT 1 COMMENT '状态，0-正常, 1-无效, 2-不允许借阅',
    `creator`            VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`            VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
    `update_time`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`            TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0-否，1-是',
    PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '图书信息表';

-- ----------------------------
-- 7、图书借阅记录表
-- ----------------------------
CREATE TABLE `book_borrow`
(
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '借阅ID',
    `book_id`           BIGINT(20) NOT NULL COMMENT '图书ID',
    `user_id`           BIGINT(20) NOT NULL COMMENT '用户ID',
    `borrow_quantity`   INT (11) NOT NULL COMMENT '借阅数量',
    `return_quantity`   INT (11) NOT NULL DEFAULT 0 COMMENT '归还数量',
    `borrow_time`       DATETIME NOT NULL COMMENT '借阅时间',
    `plan_return_time`  DATETIME NOT NULL COMMENT '计划归还时间',
    `final_return_time` DATETIME NOT NULL COMMENT '实际归还时间',
    `operator`          VARCHAR(64) NULL DEFAULT NULL COMMENT '借阅操作人',
    `creator`           VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           VARCHAR(64) NULL DEFAULT NULL COMMENT '修改人',
    `update_time`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`           TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0-否，1-是',
    PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '图书信息表';

-- ----------------------------
-- 初始化-用户数据
-- ----------------------------
INSERT INTO `user`VALUES (1, 'admin', '$2a$10$mC/d/Nii.dn8EBmTrTcFB.zJTotN0msFfLqM0G51voI5j1BwqlTem', '超级管理员', 'admin@163.com', 1, 0, '0', '2024-11-10 15:48:51', '0', '2024-11-10 15:50:43', 0);

-- ----------------------------
-- 初始化-角色数据
-- ----------------------------
INSERT INTO `role` VALUES (1, '游客', 0, 0, '普普通通的旅人', '0', '2024-11-10 15:53:21', '0', '2024-11-10 15:53:21', 0);

-- ----------------------------
-- 初始化-菜单数据
-- ----------------------------
INSERT INTO `menu` VALUES (1, '用户管理', 0, 0, '/user', '', '', 0, 0, 0, 0, '', '', '', '0', '2024-11-10 15:55:10', '0', '2024-11-10 15:55:10', 0);
INSERT INTO `menu` VALUES (2, '角色管理', 0, 0, '/role', '', '', 0, 0, 0, 0, '', '', '', '0', '2024-11-10 15:57:44', '0', '2024-11-10 15:57:44', 0);
