--liquibase formatted sql
--changeset hoonjin:20211110-01

DROP TABLE IF EXISTS items;

CREATE TABLE items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    stock BIGINT NOT NULL,
    created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    deleted_at DATETIME(3),
    primary key (id),
    unique index `uix_name` (name),
    index `ix_price`(price)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS carts;

CREATE TABLE carts (
   id BIGINT NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   item_id BIGINT NOT NULL,
   amount BIGINT NOT NULL,
   created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
   updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
   deleted_at DATETIME(3),
   primary key (id),
   index `ix_user_id` (user_id),
   index `ix_item_id` (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
   id BIGINT NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   total BIGINT NOT NULL,
   method VARCHAR(16) NOT NULL,
   memo VARCHAR(255),
   created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
   updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
   deleted_at DATETIME(3),
   primary key (id),
   index `ix_user_id_created_at` (user_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS order_details;

CREATE TABLE order_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    amount BIGINT NOT NULL,
    created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    deleted_at DATETIME(3),
    primary key (id),
    index `ix_user_id_created_at` (user_id, created_at),
    index `ix_order_id` (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;