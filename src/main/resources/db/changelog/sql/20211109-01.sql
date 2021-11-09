--liquibase formatted sql
--changeset hoonjin:20211109-01

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    deleted_at DATETIME(3),
    primary key (id),
    unique index `uix_email` (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
