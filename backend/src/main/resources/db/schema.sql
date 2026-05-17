CREATE SCHEMA IF NOT EXISTS project;

DROP TABLE IF EXISTS project.knowledge_base;
DROP TABLE IF EXISTS project.operation_log;
DROP TABLE IF EXISTS project.login_log;
DROP TABLE IF EXISTS project.robot_task_log;
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
DROP TABLE IF EXISTS project.robot_task_template;
DROP TABLE IF EXISTS project.robot_audio_binding;
DROP TABLE IF EXISTS project.robot;
DROP TABLE IF EXISTS project.device_region_binding;
DROP TABLE IF EXISTS project.device;
DROP TABLE IF EXISTS project.region_audio;
DROP TABLE IF EXISTS project.region_media;
DROP TABLE IF EXISTS project.region;
DROP TABLE IF EXISTS project.area_i18n;
DROP TABLE IF EXISTS project.area;
DROP TABLE IF EXISTS project.media_audio;
DROP TABLE IF EXISTS project.media_image;
DROP TABLE IF EXISTS project.lounge;
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

CREATE TABLE project.lounge (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(64) NOT NULL,
    name VARCHAR(100) NOT NULL,
    terminal VARCHAR(64) DEFAULT '',
    location_desc VARCHAR(255) DEFAULT '',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id),
    UNIQUE KEY uk_lounge_code (code)
);

CREATE TABLE project.media_image (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(32) NOT NULL DEFAULT 'MAP',
    content LONGTEXT,
    width INT NOT NULL DEFAULT 0,
    height INT NOT NULL DEFAULT 0,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.media_audio (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    audio_key VARCHAR(100) NOT NULL,
    category VARCHAR(32) NOT NULL DEFAULT 'COMMON',
    language_code VARCHAR(16) NOT NULL DEFAULT 'CN',
    text_content TEXT,
    audio_content TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id),
    UNIQUE KEY uk_media_audio_key_language (audio_key, language_code, category)
);

CREATE TABLE project.area (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    coordinate TEXT,
    max_capacity INT NOT NULL DEFAULT 0,
    visible BOOLEAN NOT NULL DEFAULT TRUE,
    guide_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.area_i18n (
    id BIGINT NOT NULL AUTO_INCREMENT,
    area_id BIGINT NOT NULL,
    language_code VARCHAR(16) NOT NULL DEFAULT 'CN',
    display_name VARCHAR(100) DEFAULT '',
    label_text VARCHAR(100) DEFAULT '',
    arrival_text TEXT,
    speech_text TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_area_i18n (area_id, language_code)
);

CREATE TABLE project.region (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NOT NULL,
    area_id BIGINT NULL,
    name VARCHAR(100) NOT NULL,
    coordinate TEXT,
    max_capacity INT NOT NULL DEFAULT 0,
    visible BOOLEAN NOT NULL DEFAULT TRUE,
    guide_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.region_media (
    region_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    PRIMARY KEY (region_id, image_id)
);

CREATE TABLE project.region_audio (
    region_id BIGINT NOT NULL,
    audio_id BIGINT NOT NULL,
    PRIMARY KEY (region_id, audio_id)
);

CREATE TABLE project.device (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    device_type VARCHAR(32) NOT NULL DEFAULT 'CAMERA',
    external_device_id VARCHAR(128) DEFAULT '',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.device_region_binding (
    device_id BIGINT NOT NULL,
    region_id BIGINT NOT NULL,
    coordinate TEXT,
    image_id BIGINT NULL,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (device_id, region_id)
);

CREATE TABLE project.robot (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NOT NULL,
    region_id BIGINT NULL,
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

CREATE TABLE project.robot_audio_binding (
    robot_id BIGINT NOT NULL,
    audio_id BIGINT NOT NULL,
    PRIMARY KEY (robot_id, audio_id)
);

CREATE TABLE project.robot_task_template (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NOT NULL,
    robot_id BIGINT NULL,
    name VARCHAR(100) NOT NULL,
    command_code BIGINT NULL,
    command_name VARCHAR(100) DEFAULT '',
    target_region VARCHAR(255) DEFAULT '',
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

CREATE TABLE project.robot_task_template_media (
    task_template_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    PRIMARY KEY (task_template_id, image_id)
);

CREATE TABLE project.robot_task_template_audio (
    task_template_id BIGINT NOT NULL,
    audio_id BIGINT NOT NULL,
    PRIMARY KEY (task_template_id, audio_id)
);

CREATE TABLE project.complaint_record (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    card_provider VARCHAR(64) DEFAULT '',
    card_no VARCHAR(64) DEFAULT '',
    content LONGTEXT,
    feedback LONGTEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.flight_info (
    id VARCHAR(64) NOT NULL,
    flight_no VARCHAR(32) NOT NULL,
    airline_code VARCHAR(16) DEFAULT '',
    execute_date DATE NULL,
    flight_attr VARCHAR(32) DEFAULT '',
    craft_type VARCHAR(32) DEFAULT '',
    craft_no VARCHAR(32) DEFAULT '',
    departure_status VARCHAR(16) DEFAULT 'SCH',
    arrival_status VARCHAR(16) DEFAULT 'ON',
    station_code VARCHAR(64) DEFAULT '',
    station_name VARCHAR(64) DEFAULT '',
    scheduled_takeoff_at TIMESTAMP NULL DEFAULT NULL,
    estimated_takeoff_at TIMESTAMP NULL DEFAULT NULL,
    actual_takeoff_at TIMESTAMP NULL DEFAULT NULL,
    gate_code VARCHAR(64) DEFAULT '',
    gate_attr VARCHAR(64) DEFAULT '',
    carousel_code VARCHAR(64) DEFAULT '',
    carousel_class VARCHAR(64) DEFAULT '',
    carousel_attr VARCHAR(64) DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.flight_change_record (
    id BIGINT NOT NULL AUTO_INCREMENT,
    flight_no VARCHAR(32) DEFAULT '',
    passenger_name VARCHAR(100) DEFAULT '',
    arrival_name VARCHAR(100) DEFAULT '',
    change_status VARCHAR(32) DEFAULT '',
    change_status_name VARCHAR(100) DEFAULT '',
    arrival_at TIMESTAMP NULL DEFAULT NULL,
    departure_at TIMESTAMP NULL DEFAULT NULL,
    lounge_name VARCHAR(100) DEFAULT '',
    carrier VARCHAR(32) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger_access_temp (
    id BIGINT NOT NULL AUTO_INCREMENT,
    passenger_name VARCHAR(100) DEFAULT '',
    flight_no VARCHAR(32) DEFAULT '',
    origin_code VARCHAR(32) DEFAULT '',
    destination_code VARCHAR(32) DEFAULT '',
    cabin VARCHAR(32) DEFAULT '',
    seat_no VARCHAR(32) DEFAULT '',
    segment_no VARCHAR(32) DEFAULT '',
    card_provider VARCHAR(64) DEFAULT '',
    star_level VARCHAR(64) DEFAULT '',
    access_type VARCHAR(32) DEFAULT '',
    access_code VARCHAR(32) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    region_id BIGINT NULL,
    flight_id VARCHAR(64) DEFAULT '',
    passenger_name VARCHAR(100) NOT NULL,
    flight_no VARCHAR(32) DEFAULT '',
    flight_date DATE NULL,
    origin_code VARCHAR(32) DEFAULT '',
    destination_code VARCHAR(32) DEFAULT '',
    cabin VARCHAR(32) DEFAULT '',
    seat_no VARCHAR(32) DEFAULT '',
    card_provider VARCHAR(64) DEFAULT '',
    card_no VARCHAR(64) DEFAULT '',
    member_level VARCHAR(64) DEFAULT '',
    star_level VARCHAR(64) DEFAULT '',
    access_type VARCHAR(32) DEFAULT '',
    access_status VARCHAR(16) NOT NULL DEFAULT 'IN',
    check_in_at TIMESTAMP NULL DEFAULT NULL,
    check_out_at TIMESTAMP NULL DEFAULT NULL,
    coordinate TEXT,
    region_name VARCHAR(100) DEFAULT '',
    original_image_url VARCHAR(255) DEFAULT '',
    registered_image_url VARCHAR(255) DEFAULT '',
    photo LONGTEXT,
    robot_code VARCHAR(64) DEFAULT '',
    follower_count INT NOT NULL DEFAULT 0,
    member_flag BOOLEAN NOT NULL DEFAULT FALSE,
    warning_type VARCHAR(64) DEFAULT '',
    change_before VARCHAR(255) DEFAULT '',
    change_after VARCHAR(255) DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger_location_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    passenger_id BIGINT NOT NULL,
    lounge_id BIGINT NULL,
    region_id BIGINT NULL,
    device_id BIGINT NULL,
    recognition_type VARCHAR(32) DEFAULT '',
    coordinate TEXT,
    original_image_url VARCHAR(255) DEFAULT '',
    registered_image_url VARCHAR(255) DEFAULT '',
    out_flag BOOLEAN NOT NULL DEFAULT FALSE,
    recognized_at TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger_access_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    passenger_id BIGINT NULL,
    robot_code VARCHAR(64) DEFAULT '',
    lounge_code VARCHAR(64) DEFAULT '',
    request_payload LONGTEXT,
    response_payload LONGTEXT,
    access_method VARCHAR(32) DEFAULT '',
    success_flag BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger_checkout_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    passenger_id BIGINT NOT NULL,
    recognition_type VARCHAR(32) DEFAULT '',
    checkout_at TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.passenger_warning_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    passenger_id BIGINT NOT NULL,
    flight_id VARCHAR(64) DEFAULT '',
    region_id BIGINT NULL,
    warning_type VARCHAR(64) DEFAULT '',
    warning_info VARCHAR(500) DEFAULT '',
    notice_type VARCHAR(32) DEFAULT '',
    robot_task_id BIGINT NULL,
    result_status VARCHAR(16) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.guide_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    robot_id BIGINT NULL,
    passenger_id BIGINT NULL,
    region_id BIGINT NULL,
    result_status VARCHAR(16) NOT NULL DEFAULT 'CREATED',
    coordinate TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.inquiry_stat (
    id BIGINT NOT NULL AUTO_INCREMENT,
    lounge_id BIGINT NULL,
    robot_id BIGINT NULL,
    passenger_id BIGINT NULL,
    topic VARCHAR(255) DEFAULT '',
    channel VARCHAR(32) DEFAULT 'MANUAL',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE project.robot_task_log (
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

CREATE TABLE project.login_log (
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

CREATE TABLE project.operation_log (
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

CREATE TABLE project.knowledge_base (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) DEFAULT '',
    content LONGTEXT,
    source VARCHAR(255) DEFAULT '',
    knowledge_type VARCHAR(64) NOT NULL DEFAULT 'FAQ',
    process_status VARCHAR(16) NOT NULL DEFAULT 'PENDING',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    vector_ref VARCHAR(255) DEFAULT '',
    created_by BIGINT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark VARCHAR(500) DEFAULT '',
    PRIMARY KEY (id)
);

INSERT INTO project.`user`
    (`username`, `nickname`, `email`, `phone`, `sex`, `password`, `enable`, `remark`)
VALUES
    ('admin', '管理员', 'admin@example.com', '13800000000', '2', 'admin123', TRUE, '默认管理员'),
    ('operator', '值班员', 'operator@example.com', '13800000001', '2', 'operator123', TRUE, '示例值班账号');

INSERT INTO project.lounge (`code`, `name`, `terminal`, `location_desc`, `enabled`, `remark`)
VALUES
    ('PEK2DX1', 'T2 国内贵宾室', 'T2', '二层东侧', TRUE, '默认贵宾室'),
    ('PEK2GX1', 'T2 国际贵宾室', 'T2', '三层国际区', TRUE, '默认贵宾室');

INSERT INTO project.media_image (`lounge_id`, `name`, `category`, `content`, `width`, `height`, `enabled`, `remark`)
VALUES
    (1, '休息室总览图', 'MAP', NULL, 1920, 1080, TRUE, '示例地图'),
    (1, '登机口指引图', 'GUIDE', NULL, 800, 600, TRUE, '示例指引图');

INSERT INTO project.media_audio (`lounge_id`, `audio_key`, `category`, `language_code`, `text_content`, `audio_content`, `remark`)
VALUES
    (1, 'WELCOME_CN', 'COMMON', 'CN', '欢迎来到国航智慧贵宾室', 'welcome-cn.mp3', '欢迎播报'),
    (1, 'BOARDING_REMIND_CN', 'ROBOT', 'CN', '您的航班即将登机，请前往登机口', 'boarding-remind-cn.mp3', '机器人播报');

INSERT INTO project.area (`lounge_id`, `name`, `coordinate`, `max_capacity`, `visible`, `guide_enabled`, `enabled`, `remark`)
VALUES
    (1, '休息区', '[[120,80],[340,220]]', 24, TRUE, TRUE, TRUE, '默认休息区'),
    (1, '登机等候区', '[[400,90],[680,260]]', 36, TRUE, TRUE, TRUE, '默认等候区');

INSERT INTO project.area_i18n (`area_id`, `language_code`, `display_name`, `label_text`, `arrival_text`, `speech_text`)
VALUES
    (1, 'CN', '休息区', '休息区', '您已到达休息区', '请前往休息区'),
    (2, 'CN', '登机等候区', '等候区', '您已到达登机等候区', '请前往登机等候区');

INSERT INTO project.region (`lounge_id`, `area_id`, `name`, `coordinate`, `max_capacity`, `visible`, `guide_enabled`, `enabled`, `remark`)
VALUES
    (1, 1, 'A区', '[[140,90],[260,180]]', 12, TRUE, TRUE, TRUE, '默认区域'),
    (1, 2, 'B区', '[[430,120],[620,230]]', 16, TRUE, TRUE, TRUE, '默认区域');

INSERT INTO project.region_media (`region_id`, `image_id`) VALUES (1, 1), (2, 1);
INSERT INTO project.region_audio (`region_id`, `audio_id`) VALUES (1, 1), (2, 1);

INSERT INTO project.device (`lounge_id`, `name`, `device_type`, `external_device_id`, `enabled`, `remark`)
VALUES
    (1, '东侧摄像头', 'CAMERA', 'CAM-PEK2-001', TRUE, '默认设备'),
    (1, '西侧摄像头', 'CAMERA', 'CAM-PEK2-002', TRUE, '默认设备');

INSERT INTO project.device_region_binding (`device_id`, `region_id`, `coordinate`, `image_id`, `remark`)
VALUES
    (1, 1, '[180,110]', 1, 'A区绑定'),
    (2, 2, '[500,160]', 1, 'B区绑定');

INSERT INTO project.robot (`lounge_id`, `region_id`, `robot_code`, `name`, `mac`, `ip_address`, `robot_type`, `battery_percent`, `charging_state`, `working_state`, `standby_state`, `positioning_state`, `enabled`, `initial_coordinate`, `admin_mode`, `remark`)
VALUES
    (1, 1, 'ROBOT-001', '迎宾机器人1号', '00:11:22:33:44:55', '192.168.10.21', 'Unitree', 86, '0', '0', '1', 'OK', TRUE, '[180,120]', FALSE, '默认机器人'),
    (1, 2, 'ROBOT-002', '巡检机器人1号', '00:11:22:33:44:66', '192.168.10.22', 'Keenon', 72, '0', '1', '0', 'OK', TRUE, '[520,170]', TRUE, '默认机器人');

INSERT INTO project.robot_audio_binding (`robot_id`, `audio_id`) VALUES (1, 2), (2, 2);

INSERT INTO project.robot_task_template
    (`lounge_id`, `robot_id`, `name`, `command_code`, `command_name`, `target_region`, `priority`, `execute_type`, `task_type`, `task_subtype`, `task_mode`, `direct_execution`, `return_required`, `enabled`, `remark`)
VALUES
    (1, 1, '引导到休息区', 1001, 'GUIDE', 'A区', 'HIGH', 'IMMEDIATELY', 'GUIDE', 'ROBOT', 'AUTO', FALSE, TRUE, TRUE, '默认引导任务'),
    (1, 2, '登机口提醒', 2001, 'BOARDING_REMIND', 'A02', 'NORMAL', 'IMMEDIATELY', 'NOTICE', 'ROBOT', 'AUTO', TRUE, TRUE, TRUE, '默认提醒任务');

INSERT INTO project.robot_task_template_media (`task_template_id`, `image_id`) VALUES (1, 1), (2, 2);
INSERT INTO project.robot_task_template_audio (`task_template_id`, `audio_id`) VALUES (1, 2), (2, 2);

INSERT INTO project.complaint_record (`lounge_id`, `passenger_name`, `card_provider`, `card_no`, `content`, `feedback`)
VALUES
    (1, '张三', '国航金卡', 'CA123456', '登机提醒不够及时', '已安排值班员复核提醒流程');

INSERT INTO project.flight_info
    (`id`, `flight_no`, `airline_code`, `execute_date`, `flight_attr`, `craft_type`, `craft_no`, `departure_status`, `arrival_status`, `station_code`, `station_name`, `scheduled_takeoff_at`, `estimated_takeoff_at`, `gate_code`, `gate_attr`, `carousel_code`, `carousel_class`, `carousel_attr`)
VALUES
    ('CA1234-20260514', 'CA1234', 'CA', '2026-05-14', 'DOM', 'A333', 'B-1234', 'SCH', 'ON', 'PEK', '北京', '2026-05-14 18:00:00', '2026-05-14 18:20:00', 'G12', '近机位', 'C1', '国内', '正常'),
    ('CA5678-20260514', 'CA5678', 'CA', '2026-05-14', 'INT', 'B789', 'B-5678', 'ETD', 'ETA', 'LHR', '伦敦', '2026-05-14 19:30:00', '2026-05-14 20:00:00', 'G18', '远机位', 'C3', '国际', '正常');

INSERT INTO project.flight_change_record
    (`flight_no`, `passenger_name`, `arrival_name`, `change_status`, `change_status_name`, `arrival_at`, `departure_at`, `lounge_name`, `carrier`)
VALUES
    ('CA1234', '李四', '王五', 'PENDING', '待引导', '2026-05-14 17:30:00', '2026-05-14 18:20:00', 'T2 国内贵宾室', 'CA');

INSERT INTO project.passenger_access_temp
    (`passenger_name`, `flight_no`, `origin_code`, `destination_code`, `cabin`, `seat_no`, `segment_no`, `card_provider`, `star_level`, `access_type`, `access_code`)
VALUES
    ('赵六', 'CA1234', 'PEK', 'SHA', 'C', '2A', '001', '国航白金卡', 'PLATINUM', 'QRCODE', 'TMP001');

INSERT INTO project.passenger
    (`lounge_id`, `region_id`, `flight_id`, `passenger_name`, `flight_no`, `flight_date`, `origin_code`, `destination_code`, `cabin`, `seat_no`, `card_provider`, `card_no`, `member_level`, `star_level`, `access_type`, `access_status`, `check_in_at`, `coordinate`, `region_name`, `original_image_url`, `registered_image_url`, `robot_code`, `follower_count`, `member_flag`, `warning_type`, `change_before`, `change_after`, `remark`)
VALUES
    (1, 1, 'CA1234-20260514', '张三', 'CA1234', '2026-05-14', 'PEK', 'SHA', 'C', '1A', '国航金卡', 'VIP0001', 'GOLD', 'GOLD', 'FACE', 'IN', '2026-05-14 16:20:00', '[190,120]', 'A区', '/images/p1.jpg', '/images/p1-reg.jpg', 'ROBOT-001', 0, TRUE, '', '', '', '在厅旅客'),
    (1, 2, 'CA5678-20260514', '李四', 'CA5678', '2026-05-14', 'PEK', 'LHR', 'F', '3C', '国航白金卡', 'VIP0002', 'PLATINUM', 'PLATINUM', 'QRCODE', 'OUT', '2026-05-14 15:10:00', '[520,170]', 'B区', '/images/p2.jpg', '/images/p2-reg.jpg', 'ROBOT-002', 1, TRUE, 'GATE_CHANGE', 'G12', 'G18', '已离厅旅客');

INSERT INTO project.passenger_location_log
    (`passenger_id`, `lounge_id`, `region_id`, `device_id`, `recognition_type`, `coordinate`, `original_image_url`, `registered_image_url`, `out_flag`, `recognized_at`)
VALUES
    (1, 1, 1, 1, 'FACE', '[188,118]', '/images/p1.jpg', '/images/p1-reg.jpg', FALSE, '2026-05-14 16:25:00'),
    (2, 1, 2, 2, 'FACE', '[525,175]', '/images/p2.jpg', '/images/p2-reg.jpg', TRUE, '2026-05-14 17:00:00');

INSERT INTO project.passenger_access_log
    (`passenger_id`, `robot_code`, `lounge_code`, `request_payload`, `response_payload`, `access_method`, `success_flag`)
VALUES
    (1, 'ROBOT-001', 'PEK2DX1', '{"type":"FACE"}', '{"result":"OK"}', 'FACE', TRUE);

INSERT INTO project.passenger_checkout_log (`passenger_id`, `recognition_type`, `checkout_at`)
VALUES
    (2, 'FACE', '2026-05-14 17:05:00');

INSERT INTO project.passenger_warning_log
    (`passenger_id`, `flight_id`, `region_id`, `warning_type`, `warning_info`, `notice_type`, `robot_task_id`, `result_status`)
VALUES
    (1, 'CA1234-20260514', 1, 'BOARDING', '航班即将登机，请尽快前往登机口', 'ROBOT', NULL, 'PENDING'),
    (2, 'CA5678-20260514', 2, 'GATE_CHANGE', '登机口变更为 G18', 'MANUAL', NULL, 'SUCCESS');

INSERT INTO project.guide_log (`lounge_id`, `robot_id`, `passenger_id`, `region_id`, `result_status`, `coordinate`)
VALUES
    (1, 1, 1, 1, 'SUCCESS', '[188,118]');

INSERT INTO project.inquiry_stat (`lounge_id`, `robot_id`, `passenger_id`, `topic`, `channel`)
VALUES
    (1, 1, 1, '航班登机口咨询', 'ROBOT'),
    (1, NULL, NULL, '贵宾室开放时间咨询', 'MANUAL');

INSERT INTO project.robot_task_log
    (`robot_id`, `task_template_id`, `task_name`, `task_type`, `task_subtype`, `task_mode`, `task_status`, `direct_execution`, `command_payload`, `return_payload`, `started_at`, `finished_at`)
VALUES
    (1, 1, '引导到休息区', 'GUIDE', 'ROBOT', 'AUTO', 'FINISHED', FALSE, '{"target":"A区"}', '{"result":"ok"}', '2026-05-14 16:30:00', '2026-05-14 16:32:00');

INSERT INTO project.login_log (`username`, `success_flag`, `ip_address`, `location`, `browser`, `os`, `message`)
VALUES
    ('admin', TRUE, '127.0.0.1', '本机', 'Chrome', 'Linux', '登录成功');

INSERT INTO project.operation_log
    (`module_name`, `business_type`, `method_name`, `request_method`, `operator_name`, `request_url`, `ip_address`, `location`, `request_payload`, `response_payload`, `success_flag`, `error_message`, `cost_millis`)
VALUES
    ('用户管理', 2, 'UserController.list', 'GET', 'admin', '/api/users', '127.0.0.1', '本机', '{}', '{"total":1}', TRUE, '', 12);

INSERT INTO project.knowledge_base (`title`, `content`, `source`, `knowledge_type`, `process_status`, `enabled`, `vector_ref`, `created_by`, `remark`)
VALUES
    ('贵宾室登机指引', '旅客可在航班起飞前 40 分钟前往登机口。', 'manual', 'FAQ', 'DONE', TRUE, 'vec-001', 1, '默认知识'),
    ('贵宾室服务时间', '贵宾室开放时间以当日航班保障计划为准。', 'manual', 'FAQ', 'DONE', TRUE, 'vec-002', 1, '默认知识');
