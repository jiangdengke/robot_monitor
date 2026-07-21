SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE SCHEMA IF NOT EXISTS project;

DROP TABLE IF EXISTS project.platform_bootstrap_config;
DROP TABLE IF EXISTS project.knowledge_base;
DROP TABLE IF EXISTS project.inquiry_stat;
DROP TABLE IF EXISTS project.guide_log;
DROP TABLE IF EXISTS project.passenger_warning_log;
DROP TABLE IF EXISTS project.passenger_checkout_log;
DROP TABLE IF EXISTS project.passenger_access_log;
DROP TABLE IF EXISTS project.passenger_location_log;
DROP TABLE IF EXISTS project.passenger;
DROP TABLE IF EXISTS project.passenger_access_temp;
DROP TABLE IF EXISTS project.flight_change_record;
DROP TABLE IF EXISTS project.flight_info;
DROP TABLE IF EXISTS project.complaint_record;
DROP TABLE IF EXISTS project.robot_task_template_audio;
DROP TABLE IF EXISTS project.robot_task_template_media;
DROP TABLE IF EXISTS project.robot_audio_binding;
DROP TABLE IF EXISTS project.device_region_binding;
DROP TABLE IF EXISTS project.region_audio;
DROP TABLE IF EXISTS project.region_media;
DROP TABLE IF EXISTS project.area_i18n;
DROP TABLE IF EXISTS project.media_audio;
DROP TABLE IF EXISTS project.media_image;
DROP TABLE IF EXISTS project.region;
DROP TABLE IF EXISTS project.lounge;
DROP TABLE IF EXISTS project.operation_log;
DROP TABLE IF EXISTS project.login_log;
DROP TABLE IF EXISTS project.robot_task_log;
DROP TABLE IF EXISTS project.robot_task_template;
DROP TABLE IF EXISTS project.robot;
DROP TABLE IF EXISTS project.device_point_binding;
DROP TABLE IF EXISTS project.device;
DROP TABLE IF EXISTS project.point;
DROP TABLE IF EXISTS project.area;
DROP TABLE IF EXISTS project.site;
DROP TABLE IF EXISTS project.`user`;

CREATE TABLE project.`user`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `nickname` VARCHAR(64) NOT NULL DEFAULT '',
    `email` VARCHAR(100) DEFAULT '',
    `phone` VARCHAR(20) DEFAULT '',
    `sex` CHAR(1) DEFAULT '2',
    `avatar_url` VARCHAR(255) DEFAULT '',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `password` VARCHAR(255) NOT NULL,
    `enable` BOOLEAN NOT NULL DEFAULT TRUE,
    `remark` VARCHAR(500) DEFAULT '',
    PRIMARY KEY (`id`)
);

CREATE TABLE project.site
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(100) NOT NULL,
    location_desc VARCHAR(255) DEFAULT '',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id),
    UNIQUE KEY uk_site_code (code)
);

CREATE TABLE project.area
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    coordinate TEXT,
    max_capacity INT NOT NULL DEFAULT 0,
    visible BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.point
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    area_id BIGINT NULL,
    name VARCHAR(100) NOT NULL,
    coordinate TEXT,
    max_capacity INT NOT NULL DEFAULT 0,
    visible BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.device
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    device_type VARCHAR(32) NOT NULL DEFAULT 'CAMERA',
    external_device_id VARCHAR(128) DEFAULT '',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.device_point_binding
(
    device_id BIGINT NOT NULL,
    point_id BIGINT NOT NULL,
    coordinate TEXT,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (device_id, point_id)
);

CREATE TABLE project.robot
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    point_id BIGINT NULL,
    robot_code VARCHAR(64) NOT NULL,
    name VARCHAR(100) NOT NULL,
    mac VARCHAR(100) DEFAULT '',
    ip_address VARCHAR(64) DEFAULT '',
    robot_type VARCHAR(64) DEFAULT '',
    battery_percent INT NOT NULL DEFAULT 0,
    charging_state VARCHAR(32) DEFAULT '',
    working_state VARCHAR(32) DEFAULT '',
    standby_state VARCHAR(32) DEFAULT '',
    positioning_state VARCHAR(32) DEFAULT '',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    initial_coordinate TEXT,
    admin_mode BOOLEAN NOT NULL DEFAULT FALSE,
    error_code VARCHAR(64) DEFAULT '',
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark TEXT,
    PRIMARY KEY (id),
    UNIQUE KEY uk_robot_code (robot_code)
);

CREATE TABLE project.robot_task_template
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    robot_id BIGINT NULL,
    name VARCHAR(100) NOT NULL,
    command_code BIGINT NULL,
    command_name VARCHAR(100) DEFAULT '',
    target_point VARCHAR(255) DEFAULT '',
    priority VARCHAR(32) NOT NULL DEFAULT 'NORMAL',
    execute_type VARCHAR(32) NOT NULL DEFAULT 'IMMEDIATELY',
    execute_day VARCHAR(64) DEFAULT '',
    execute_at TIMESTAMP NULL DEFAULT NULL,
    task_type VARCHAR(64) DEFAULT '',
    task_subtype VARCHAR(64) DEFAULT '',
    task_mode VARCHAR(64) DEFAULT '',
    direct_execution BOOLEAN NOT NULL DEFAULT FALSE,
    return_required BOOLEAN NOT NULL DEFAULT FALSE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.robot_task_log
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    robot_id BIGINT NULL,
    task_template_id BIGINT NULL,
    task_name VARCHAR(100) DEFAULT '',
    task_type VARCHAR(64) DEFAULT '',
    task_subtype VARCHAR(64) DEFAULT '',
    task_mode VARCHAR(64) DEFAULT '',
    task_status VARCHAR(32) DEFAULT '',
    direct_execution BOOLEAN NOT NULL DEFAULT FALSE,
    command_payload LONGTEXT,
    return_payload TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at TIMESTAMP NULL DEFAULT NULL,
    finished_at TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE project.login_log
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(64) DEFAULT '',
    success_flag BOOLEAN NOT NULL DEFAULT TRUE,
    ip_address VARCHAR(128) DEFAULT '',
    location VARCHAR(255) DEFAULT '',
    browser VARCHAR(255) DEFAULT '',
    os VARCHAR(255) DEFAULT '',
    message VARCHAR(500) DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.operation_log
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    module_name VARCHAR(100) DEFAULT '',
    business_type INT NOT NULL DEFAULT 0,
    method_name VARCHAR(255) DEFAULT '',
    request_method VARCHAR(16) DEFAULT '',
    operator_name VARCHAR(64) DEFAULT '',
    request_url VARCHAR(255) DEFAULT '',
    ip_address VARCHAR(128) DEFAULT '',
    location VARCHAR(255) DEFAULT '',
    request_payload LONGTEXT,
    response_payload LONGTEXT,
    success_flag BOOLEAN NOT NULL DEFAULT TRUE,
    error_message LONGTEXT,
    cost_millis BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

INSERT INTO project.`user`
    (`username`, `nickname`, `email`, `phone`, `sex`, `password`, `enable`, `remark`)
VALUES
    ('admin', '管理员', 'admin@example.com', '13800000000', '2', 'admin123', TRUE, '默认管理员'),
    ('operator', '操作员', 'operator@example.com', '13800000001', '2', 'operator123', TRUE, '示例操作账号');

INSERT INTO project.site (`code`, `name`, `location_desc`, `enabled`, `remark`)
VALUES ('SITE-001', '默认场地', '一层', TRUE, '通用演示场地');

INSERT INTO project.area (`site_id`, `name`, `coordinate`, `max_capacity`, `visible`, `enabled`, `remark`)
VALUES (1, '公共区域', '[[120,80],[340,220]]', 24, TRUE, TRUE, '通用演示区域');

INSERT INTO project.point (`site_id`, `area_id`, `name`, `coordinate`, `max_capacity`, `visible`, `enabled`, `remark`)
VALUES (1, 1, '入口点位', '[180,120]', 4, TRUE, TRUE, '通用演示点位');

INSERT INTO project.device (`site_id`, `name`, `device_type`, `external_device_id`, `enabled`, `remark`)
VALUES (1, '入口摄像头', 'CAMERA', 'CAM-001', TRUE, '通用演示设备');

INSERT INTO project.device_point_binding (`device_id`, `point_id`, `coordinate`, `remark`)
VALUES (1, 1, '[180,120]', '入口设备点位绑定');

INSERT INTO project.robot
    (`site_id`, `point_id`, `robot_code`, `name`, `mac`, `ip_address`, `robot_type`, `battery_percent`, `charging_state`, `working_state`, `standby_state`, `positioning_state`, `enabled`, `initial_coordinate`, `admin_mode`, `remark`)
VALUES
    (1, 1, 'ROBOT-001', '通用机器人一号', '00:11:22:33:44:55', '192.168.10.21', 'SERVICE', 86, 'IDLE', 'READY', 'STANDBY', 'OK', TRUE, '[180,120]', FALSE, '通用演示机器人');

INSERT INTO project.robot_task_template
    (`site_id`, `robot_id`, `name`, `command_code`, `command_name`, `target_point`, `priority`, `execute_type`, `task_type`, `task_subtype`, `task_mode`, `direct_execution`, `return_required`, `enabled`, `remark`)
VALUES
    (1, 1, '前往入口点位', 1001, 'MOVE_TO_POINT', '入口点位', 'NORMAL', 'IMMEDIATELY', 'MOVE', 'POINT', 'AUTO', TRUE, FALSE, TRUE, '通用演示任务');

INSERT INTO project.robot_task_log
    (`robot_id`, `task_template_id`, `task_name`, `task_type`, `task_subtype`, `task_mode`, `task_status`, `direct_execution`, `command_payload`, `return_payload`, `started_at`, `finished_at`)
VALUES
    (1, 1, '前往入口点位', 'MOVE', 'POINT', 'AUTO', 'FINISHED', TRUE, '{"targetPoint":"入口点位"}', '{"result":"ok"}', '2026-07-13 10:00:00', '2026-07-13 10:01:00');

INSERT INTO project.login_log (`username`, `success_flag`, `ip_address`, `location`, `browser`, `os`, `message`)
VALUES ('admin', TRUE, '127.0.0.1', '本机', 'Chrome', 'Linux', '登录成功');

INSERT INTO project.operation_log
    (`module_name`, `business_type`, `method_name`, `request_method`, `operator_name`, `request_url`, `ip_address`, `location`, `request_payload`, `response_payload`, `success_flag`, `error_message`, `cost_millis`)
VALUES
    ('用户管理', 2, 'UserController.list', 'GET', 'admin', '/api/users', '127.0.0.1', '本机', '{}', '{"total":2}', TRUE, '', 12);
