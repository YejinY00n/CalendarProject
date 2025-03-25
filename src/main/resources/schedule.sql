CREATE DATABASE IF NOT EXISTS schedule;
USE schedule;
CREATE TABLE IF NOT EXISTS event
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '고유 식별자',
    task         VARCHAR(50),
    created_time TIMESTAMP   NOT NULL COMMENT '생성 날짜',
    edited_time  TIMESTAMP   NOT NULL COMMENT '수정된 날짜',
    owner        VARCHAR(25) NOT NULL COMMENT '작성자명',
    password     VARCHAR(25) NOT NULL COMMENT '비밀번호'
);