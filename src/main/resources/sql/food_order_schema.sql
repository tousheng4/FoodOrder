-- =====================================================
--  网上订餐系统 - 数据库初始化脚本
--  文件名: food_order_schema.sql
--  说明: 创建 food_order 库及核心 8 张业务表
-- =====================================================

-- 如果需要，先创建数据库
CREATE DATABASE IF NOT EXISTS food_order
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE food_order;

-- 为了安全，按依赖顺序先删表（如果存在）
DROP TABLE IF EXISTS dish_review;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS order_info;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS user_address;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS sys_user;

-- =====================================================
-- 1. 系统用户表 sys_user
-- =====================================================
CREATE TABLE sys_user (
                          id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          username        VARCHAR(50)  NOT NULL COMMENT '登录用户名',
                          password_hash   VARCHAR(100) NOT NULL COMMENT '密码哈希',
                          nickname        VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
                          phone           VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
                          avatar          VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                          role            TINYINT      NOT NULL DEFAULT 0 COMMENT '角色：0=普通用户，1=管理员',
                          status          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0=禁用，1=正常',
                          last_login_at   DATETIME     DEFAULT NULL COMMENT '最后登录时间',
                          created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (id),
                          UNIQUE KEY uk_username (username),
                          UNIQUE KEY uk_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- =====================================================
-- 2. 菜品分类表 category
-- =====================================================
CREATE TABLE category (
                          id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          name        VARCHAR(50)  NOT NULL COMMENT '分类名称',
                          sort        INT          NOT NULL DEFAULT 0 COMMENT '排序值，越大越靠前',
                          status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0=禁用，1=启用',
                          created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (id),
                          UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品分类表';

-- =====================================================
-- 3. 菜品表 dish
-- =====================================================
USE food_order;

CREATE TABLE dish (
                      id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                      category_id     BIGINT UNSIGNED NOT NULL COMMENT '分类ID',
                      name            VARCHAR(100) NOT NULL COMMENT '菜品名称',
                      price           DECIMAL(10,2) NOT NULL COMMENT '单价',
                      image           VARCHAR(255) DEFAULT NULL COMMENT '菜品图片URL',
                      description     VARCHAR(500) DEFAULT NULL COMMENT '菜品描述',
                      status          TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0=下架，1=上架',
                      stock           INT NOT NULL DEFAULT 9999 COMMENT '库存',
                      sales           INT NOT NULL DEFAULT 0 COMMENT '销量',
                      deleted         TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0=否，1=是',
                      created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                      PRIMARY KEY (id),
                      KEY idx_category (category_id),
                      KEY idx_status (status),
                      KEY idx_name (name),
                      CONSTRAINT fk_dish_category
                          FOREIGN KEY (category_id) REFERENCES category (id)
                              ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';


-- =====================================================
-- 4. 用户收货地址表 user_address
-- =====================================================
CREATE TABLE user_address (
                              id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              user_id          BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                              receiver_name    VARCHAR(50) NOT NULL COMMENT '收货人姓名',
                              receiver_phone   VARCHAR(20) NOT NULL COMMENT '收货人电话',
                              detail_address   VARCHAR(255) NOT NULL COMMENT '详细地址',
                              is_default       TINYINT NOT NULL DEFAULT 0 COMMENT '是否默认地址：0=否，1=是',
                              deleted          TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0=否，1=是',
                              created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              updated_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                              PRIMARY KEY (id),
                              KEY idx_user (user_id),
                              KEY idx_default (user_id, is_default)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址';

-- =====================================================
-- 5. 购物车条目表 cart_item
-- =====================================================
CREATE TABLE cart_item (
                           id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           user_id      BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                           dish_id      BIGINT UNSIGNED NOT NULL COMMENT '菜品ID',
                           quantity     INT NOT NULL DEFAULT 1 COMMENT '数量',
                           checked      TINYINT NOT NULL DEFAULT 1 COMMENT '是否选中：0=未选中，1=选中',
                           created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
                           updated_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (id),
                           UNIQUE KEY uk_user_dish (user_id, dish_id),
                           KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车条目表';

-- =====================================================
-- 6. 订单主表 order_info
-- =====================================================
CREATE TABLE order_info (
                            id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                            order_no         VARCHAR(64)  NOT NULL COMMENT '订单编号',
                            user_id          BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                            status           TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态：0=待支付，1=已支付，2=已取消，3=已完成',
                            total_amount     DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
                            pay_amount       DECIMAL(10,2) NOT NULL COMMENT '实际支付金额',
                            freight_amount   DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '运费金额',
                            pay_time         DATETIME DEFAULT NULL COMMENT '支付时间',
                            cancel_time      DATETIME DEFAULT NULL COMMENT '取消时间',
                            cancel_reason    VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
                            receiver_name    VARCHAR(50)  NOT NULL COMMENT '收货人姓名',
                            receiver_phone   VARCHAR(20)  NOT NULL COMMENT '收货人电话',
                            receiver_address VARCHAR(255) NOT NULL COMMENT '收货地址',
                            remark           VARCHAR(255) DEFAULT NULL COMMENT '订单备注',
                            deleted          TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0=正常，1=删除',
                            created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            updated_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (id),
                            UNIQUE KEY uk_order_no (order_no),
                            KEY idx_user_status (user_id, status),
                            KEY idx_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- =====================================================
-- 7. 订单明细表 order_item
-- =====================================================
CREATE TABLE order_item (
                            id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
                            order_id    BIGINT UNSIGNED NOT NULL COMMENT '所属订单ID',
                            dish_id     BIGINT UNSIGNED NOT NULL COMMENT '菜品ID',
                            dish_name   VARCHAR(100) NOT NULL COMMENT '下单时菜品名称',
                            dish_image  VARCHAR(255) DEFAULT NULL COMMENT '下单时菜品图片',
                            unit_price  DECIMAL(10,2) NOT NULL COMMENT '下单时单价',
                            quantity    INT NOT NULL COMMENT '数量',
                            sub_total   DECIMAL(10,2) NOT NULL COMMENT '小计金额',
                            created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (id),
                            KEY idx_order (order_id),
                            KEY idx_dish (dish_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- =====================================================
-- 8. 菜品评价表 dish_review
-- =====================================================
CREATE TABLE dish_review (
                             id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             user_id         BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
                             order_id        BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
                             order_item_id   BIGINT UNSIGNED NOT NULL COMMENT '订单明细ID',
                             dish_id         BIGINT UNSIGNED NOT NULL COMMENT '菜品ID',
                             rating          TINYINT NOT NULL COMMENT '评分：1~5',
                             content         VARCHAR(500)  DEFAULT NULL COMMENT '评价内容',
                             images          VARCHAR(1000) DEFAULT NULL COMMENT '图片URL列表（预留）',
                             status          TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1=显示，0=隐藏',
                             created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (id),
                             UNIQUE KEY uk_user_order_dish (user_id, order_id, dish_id),
                             KEY idx_dish_status (dish_id, status),
                             KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品评价表';

-- =====================================================
-- 结束
-- =====================================================
