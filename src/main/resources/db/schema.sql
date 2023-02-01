drop table if exists `account` cascade;
drop table if exists feed cascade;
drop table if exists `order` cascade;
drop table if exists product cascade;
drop table if exists coupon_event cascade;
drop table if exists buying_bid cascade;
drop table if exists delivery_info cascade;
drop table if exists coupon cascade;
drop table if exists feed_like cascade;
drop table if exists feed_product cascade;
drop table if exists feed_tag cascade;
drop table if exists follow cascade;
drop table if exists image cascade;
drop table if exists product_option cascade;
drop table if exists selling_bid cascade;
drop table if exists `transaction_history` cascade;
drop table if exists `member` cascade;
drop table if exists feed_comment cascade;


CREATE TABLE `member`
(
    `id`         BIGINT             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`       VARCHAR(20)        NOT NULL,
    `email`      VARCHAR(20) UNIQUE NOT NULL,
    `phone`      VARCHAR(20)        NOT NULL,
    `password`   VARCHAR(60)        NOT NULL,
    `is_male`    BIT(1)             NOT NULL,
    `authority`  VARCHAR(20)        NOT NULL,
    `created_at` TIMESTAMP          NOT NULL,
    `updated_at` TIMESTAMP          NOT NULL
);

CREATE TABLE `feed`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author_id`  BIGINT       NOT NULL,
    `content`    VARCHAR(255) NOT NULL,
    `likes`      BIGINT       NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP    NOT NULL,
    `updated_at` TIMESTAMP    NOT NULL
);

CREATE TABLE `product`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`          VARCHAR(30) NOT NULL,
    `release_price` INT         NOT NULL,
    `description`   VARCHAR(50) NOT NULL,
    `created_at`    TIMESTAMP   NOT NULL,
    `updated_at`    TIMESTAMP   NOT NULL
);

CREATE TABLE `order`
(
    `id`                        BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `bid_id`                    BIGINT      NOT NULL,
    `is_based_on_selling_bid`   BIT   NOT NULL,
    `buyer_id`                  BIGINT      NOT NULL,
    `seller_id`                 BIGINT      NOT NULL,
    `product_option_id`         BIGINT      NOT NULL,
    `price`                     INT         NOT NULL,
    `order_status`              VARCHAR(10) NOT NULL,
    `order_request`             VARCHAR(50) NOT NULL,
    `created_at`                TIMESTAMP   NOT NULL,
    `updated_at`                TIMESTAMP   NOT NULL
);

CREATE TABLE `coupon`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `discount_value` TINYINT     NOT NULL,
    `name`           VARCHAR(20) NOT NULL,
    `amount`         INT         NOT NULL,
    `created_at`     TIMESTAMP   NOT NULL,
    `updated_at`     TIMESTAMP   NOT NULL
);

CREATE TABLE `account`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`  BIGINT    NOT NULL,
    `balance`    INT       NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL
);

CREATE TABLE `transaction_history`
(
    `id`               BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `account_id`       BIGINT      NOT NULL,
    `amount`           INT         NOT NULL,
    `transaction_type` VARCHAR(10) NOT NULL,
    `created_at`       TIMESTAMP   NOT NULL,
    `updated_at`       TIMESTAMP   NOT NULL
);

CREATE TABLE `feed_like`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `feed_id`    BIGINT    NOT NULL,
    `member_id`  BIGINT    NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL
);

CREATE TABLE `delivery_info`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`  BIGINT      NOT NULL,
    `name`       VARCHAR(20) NOT NULL,
    `phone`      VARCHAR(20) NOT NULL,
    `post_code`  VARCHAR(10) NOT NULL,
    `address`    VARCHAR(50) NOT NULL,
    `detail`     VARCHAR(30) NOT NULL,
    `created_at` TIMESTAMP   NOT NULL,
    `updated_at` TIMESTAMP   NOT NULL
);

CREATE TABLE `coupon_event`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `coupon_id`  BIGINT    NOT NULL,
    `member_id`  BIGINT    NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL
);

CREATE TABLE `feed_tag`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `feed_id`    BIGINT      NOT NULL,
    `tag`        VARCHAR(20) NOT NULL,
    `created_at` TIMESTAMP   NOT NULL,
    `updated_at` TIMESTAMP   NOT NULL
);

CREATE TABLE `feed_product`
(
    `id`         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `feed_id`    BIGINT    NOT NULL,
    `product_id` BIGINT    NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL
);

CREATE TABLE `product_option`
(
    `id`            BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id`    BIGINT    NOT NULL,
    `size`          SMALLINT  NOT NULL,
    `highest_price` SMALLINT  NOT NULL DEFAULT 0,
    `lowest_price`  SMALLINT  NOT NULL DEFAULT 0,
    `created_at`    TIMESTAMP NOT NULL,
    `updated_at`    TIMESTAMP NOT NULL
);

CREATE TABLE `image`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `full_path`     VARCHAR(255) NOT NULL,
    `original_name` VARCHAR(40)  NOT NULL,
    `reference_id`  BIGINT       NOT NULL,
    `domain_type`   VARCHAR(20)  NOT NULL,
    `created_at`    TIMESTAMP    NOT NULL,
    `updated_at`    TIMESTAMP    NOT NULL
);

CREATE TABLE `selling_bid`
(
    `id`                BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`         BIGINT    NOT NULL,
    `product_option_id` BIGINT    NOT NULL,
    `price`             INT       NOT NULL,
    `valid_until`       TIMESTAMP NOT NULL,
    `is_deleted`        BIT       NOT NULL,
    `version`           BIGINT    NOT NULL,
    `created_at`        TIMESTAMP NOT NULL,
    `updated_at`        TIMESTAMP NOT NULL
);

CREATE TABLE `buying_bid`
(
    `id`                BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`         BIGINT    NOT NULL,
    `product_option_id` BIGINT    NOT NULL,
    `price`             INT       NOT NULL,
    `valid_until`       TIMESTAMP NOT NULL,
    `is_deleted`        BIT       NOT NULL,
    `version`           BIGINT    NOT NULL,
    `created_at`        TIMESTAMP NOT NULL,
    `updated_at`        TIMESTAMP NOT NULL
);

CREATE TABLE `follow`
(
    `id`                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `following_member_id` BIGINT NOT NULL,
    `followed_member_id`  BIGINT NOT NULL
);

CREATE INDEX index_product ON product (id DESC);

CREATE TABLE `feed_comment`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`  BIGINT       NOT NULL,
    `feed_id`    BIGINT       NOT NULL,
    `content`    VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP    NOT NULL,
    `updated_at` TIMESTAMP    NOT NULL
);