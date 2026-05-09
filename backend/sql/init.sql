-- robot_monitor admin backend bootstrap sql
-- purpose:
-- 1. create the core tables required by the management backend
-- 2. insert a minimum set of seed data so login, router, menu, dict and common pages can work
-- 3. insert small demo business data for robot/config/flight/food modules
--
-- scope:
-- this is a practical bootstrap sql derived from decompiled mapper xml files.
-- it targets "backend/admin" first, not a full production restoration.
--
-- tested assumptions:
-- - mysql 8.x
-- - utf8mb4
-- - backend default db name: robot_monitor
--
-- default admin account:
-- username: admin
-- password: admin123
-- bcrypt hash generated from current backend code

CREATE DATABASE IF NOT EXISTS `robot_monitor`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE `robot_monitor`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_user_role`;
DROP TABLE IF EXISTS `sys_user_post`;
DROP TABLE IF EXISTS `sys_role_menu`;
DROP TABLE IF EXISTS `sys_role_dept`;
DROP TABLE IF EXISTS `sys_job_log`;
DROP TABLE IF EXISTS `sys_job`;
DROP TABLE IF EXISTS `sys_oper_log`;
DROP TABLE IF EXISTS `sys_logininfor`;
DROP TABLE IF EXISTS `sys_notice`;
DROP TABLE IF EXISTS `sys_config`;
DROP TABLE IF EXISTS `sys_dict_data`;
DROP TABLE IF EXISTS `sys_dict_type`;
DROP TABLE IF EXISTS `sys_post`;
DROP TABLE IF EXISTS `sys_menu`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_dept`;

DROP TABLE IF EXISTS `config_audio`;
DROP TABLE IF EXISTS `config_robot_audio`;
DROP TABLE IF EXISTS `config_img`;
DROP TABLE IF EXISTS `config_region`;
DROP TABLE IF EXISTS `config_area`;
DROP TABLE IF EXISTS `config_area_detail`;
DROP TABLE IF EXISTS `config_area_log`;
DROP TABLE IF EXISTS `config_device`;
DROP TABLE IF EXISTS `config_device_region`;
DROP TABLE IF EXISTS `config_table`;
DROP TABLE IF EXISTS `config_robot`;
DROP TABLE IF EXISTS `config_task`;
DROP TABLE IF EXISTS `message_log`;

DROP TABLE IF EXISTS `flight_info`;
DROP TABLE IF EXISTS `flight_gate`;
DROP TABLE IF EXISTS `flight_kafka_log`;
DROP TABLE IF EXISTS `flight_warning`;
DROP TABLE IF EXISTS `t_flight_change`;
DROP TABLE IF EXISTS `get_in_tmp`;
DROP TABLE IF EXISTS `passenger`;
DROP TABLE IF EXISTS `passenger_location_log`;
DROP TABLE IF EXISTS `passenger_log`;
DROP TABLE IF EXISTS `passenger_out_log`;
DROP TABLE IF EXISTS `passenger_warning_log`;

DROP TABLE IF EXISTS `food_config`;
DROP TABLE IF EXISTS `food_daily`;
DROP TABLE IF EXISTS `food_order`;
DROP TABLE IF EXISTS `food_order_detail`;
DROP TABLE IF EXISTS `food_plan`;

DROP TABLE IF EXISTS `guide_log`;
DROP TABLE IF EXISTS `robot_cmd_log`;
DROP TABLE IF EXISTS `robot_task`;
DROP TABLE IF EXISTS `insp_task`;
DROP TABLE IF EXISTS `insp_task_result`;
DROP TABLE IF EXISTS `update_notice_result`;

DROP TABLE IF EXISTS `ai_chat_log`;
DROP TABLE IF EXISTS `ai_knowledge_base`;

DROP TABLE IF EXISTS `gen_table`;
DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT 0,
  `ancestors` varchar(500) DEFAULT '',
  `dept_name` varchar(100) NOT NULL,
  `order_num` int DEFAULT 0,
  `leader` varchar(50) DEFAULT '',
  `phone` varchar(20) DEFAULT '',
  `email` varchar(100) DEFAULT '',
  `status` char(1) DEFAULT '0',
  `del_flag` char(1) DEFAULT '0',
  `room_code` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `uk_sys_dept_room_code` (`room_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `role_key` varchar(100) NOT NULL,
  `role_sort` int DEFAULT 0,
  `data_scope` char(1) DEFAULT '1',
  `menu_check_strictly` tinyint(1) DEFAULT 1,
  `dept_check_strictly` tinyint(1) DEFAULT 1,
  `status` char(1) DEFAULT '0',
  `del_flag` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `post_code` varchar(64) NOT NULL,
  `post_name` varchar(64) NOT NULL,
  `post_sort` int DEFAULT 0,
  `status` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `dept_id` bigint DEFAULT NULL,
  `user_name` varchar(64) NOT NULL,
  `nick_name` varchar(64) NOT NULL,
  `user_type` varchar(4) DEFAULT '00',
  `email` varchar(100) DEFAULT '',
  `phonenumber` varchar(20) DEFAULT '',
  `sex` char(1) DEFAULT '0',
  `avatar` varchar(255) DEFAULT '',
  `password` varchar(255) NOT NULL,
  `status` char(1) DEFAULT '0',
  `del_flag` char(1) DEFAULT '0',
  `login_ip` varchar(128) DEFAULT '',
  `login_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_sys_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) NOT NULL,
  `parent_id` bigint DEFAULT 0,
  `order_num` int DEFAULT 0,
  `path` varchar(200) DEFAULT '',
  `component` varchar(255) DEFAULT NULL,
  `query` varchar(255) DEFAULT NULL,
  `is_frame` char(1) DEFAULT '1',
  `is_cache` char(1) DEFAULT '0',
  `menu_type` char(1) DEFAULT 'M',
  `visible` char(1) DEFAULT '0',
  `status` char(1) DEFAULT '0',
  `perms` varchar(200) DEFAULT '',
  `icon` varchar(100) DEFAULT '#',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(100) NOT NULL,
  `dict_type` varchar(100) NOT NULL,
  `status` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `uk_sys_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT,
  `dict_sort` int DEFAULT 0,
  `dict_label` varchar(100) NOT NULL,
  `dict_value` varchar(100) NOT NULL,
  `dict_type` varchar(100) NOT NULL,
  `css_class` varchar(100) DEFAULT '',
  `list_class` varchar(100) DEFAULT '',
  `is_default` char(1) DEFAULT 'N',
  `status` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT,
  `config_name` varchar(100) NOT NULL,
  `config_key` varchar(100) NOT NULL,
  `config_value` varchar(500) NOT NULL,
  `config_type` char(1) DEFAULT 'N',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `uk_sys_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(100) NOT NULL,
  `notice_type` char(1) DEFAULT '1',
  `notice_content` text,
  `status` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) DEFAULT '',
  `status` char(1) DEFAULT '0',
  `ipaddr` varchar(128) DEFAULT '',
  `login_location` varchar(255) DEFAULT '',
  `browser` varchar(255) DEFAULT '',
  `os` varchar(255) DEFAULT '',
  `msg` varchar(500) DEFAULT '',
  `login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT '',
  `business_type` int DEFAULT 0,
  `method` varchar(255) DEFAULT '',
  `request_method` varchar(16) DEFAULT '',
  `operator_type` int DEFAULT 0,
  `oper_name` varchar(64) DEFAULT '',
  `dept_name` varchar(100) DEFAULT '',
  `oper_url` varchar(255) DEFAULT '',
  `oper_ip` varchar(128) DEFAULT '',
  `oper_location` varchar(255) DEFAULT '',
  `oper_param` longtext,
  `json_result` longtext,
  `status` int DEFAULT 0,
  `error_msg` longtext,
  `oper_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) NOT NULL,
  `job_group` varchar(64) NOT NULL,
  `invoke_target` varchar(500) NOT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `misfire_policy` varchar(20) DEFAULT '3',
  `concurrent` char(1) DEFAULT '1',
  `status` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT,
  `job_name` varchar(100) DEFAULT '',
  `job_group` varchar(64) DEFAULT '',
  `invoke_target` varchar(500) DEFAULT '',
  `job_message` varchar(500) DEFAULT '',
  `status` char(1) DEFAULT '0',
  `exception_info` longtext,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_img` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `img_type` varchar(32) DEFAULT '1',
  `img` longtext,
  `img_name` varchar(255) DEFAULT '',
  `width` int DEFAULT 0,
  `height` int DEFAULT 0,
  `remark` varchar(500) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `is_delete` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_audio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `audio_key` varchar(100) NOT NULL,
  `audio_value` varchar(255) DEFAULT '',
  `language_type` varchar(16) DEFAULT 'CN',
  `text_info` text,
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `audio_type` varchar(32) DEFAULT 'welcome',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_robot_audio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `audio_key` varchar(100) NOT NULL,
  `audio_value` varchar(255) DEFAULT '',
  `language_type` varchar(16) DEFAULT 'CN',
  `text_info` text,
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_region` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `region_name` varchar(100) NOT NULL,
  `coordinate` text,
  `remark` varchar(500) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `img_ids` varchar(255) DEFAULT '',
  `audio_keys` varchar(255) DEFAULT '',
  `is_guide` char(1) DEFAULT '0',
  `is_show` char(1) DEFAULT '1',
  `max_capacity` int DEFAULT 0,
  `area_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_area` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_name` varchar(100) NOT NULL,
  `coordinate` text,
  `remark` varchar(500) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `img_ids` varchar(255) DEFAULT '',
  `is_guide` char(1) DEFAULT '0',
  `is_show` char(1) DEFAULT '1',
  `max_capacity` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_area_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_id` bigint NOT NULL,
  `area_name` varchar(100) DEFAULT '',
  `language_type` varchar(16) DEFAULT 'CN',
  `label` varchar(100) DEFAULT '',
  `audio` varchar(255) DEFAULT '',
  `arr_audio` text,
  `arr_text` text,
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_area_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_id` bigint NOT NULL,
  `robot_id` varchar(64) DEFAULT '',
  `room_code` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_device` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_name` varchar(100) NOT NULL,
  `device_type` varchar(32) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `is_delete` char(1) DEFAULT '0',
  `deep_glint_device_id` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_device_region` (
  `device_id` bigint NOT NULL,
  `region_id` bigint NOT NULL,
  `region_name` varchar(100) DEFAULT '',
  `coordinate` text,
  `img_id` bigint DEFAULT NULL,
  `img` longtext,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`device_id`,`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `table_no` varchar(64) NOT NULL,
  `region_id` bigint DEFAULT NULL,
  `room_code` varchar(64) DEFAULT NULL,
  `is_enable` char(1) DEFAULT '1',
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `device_id` bigint DEFAULT NULL,
  `camera_coordinates` text,
  `status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_robot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) NOT NULL,
  `robot_name` varchar(100) NOT NULL,
  `mac` varchar(100) DEFAULT '',
  `robot_ip` varchar(64) DEFAULT '',
  `charging_state` varchar(32) DEFAULT '',
  `working_state` varchar(32) DEFAULT '',
  `standby_state` varchar(32) DEFAULT '',
  `positioning_state` varchar(32) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `battery_state` int DEFAULT 0,
  `network` int DEFAULT 0,
  `robot_error` varchar(64) DEFAULT '',
  `error_messages` text,
  `robot_type` varchar(64) DEFAULT '',
  `belonged_company` varchar(100) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `remark` text,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `task_id` bigint DEFAULT NULL,
  `task_status` varchar(32) DEFAULT '',
  `is_delete` char(1) DEFAULT '0',
  `img_ids` varchar(255) DEFAULT '',
  `audit_keys` varchar(255) DEFAULT '',
  `employee_no` varchar(64) DEFAULT '',
  `account_id` varchar(64) DEFAULT '',
  `ori_coordinate` text,
  `admin_mode` char(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_robot_robot_id` (`robot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `config_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `robot_id` bigint DEFAULT NULL,
  `command` bigint DEFAULT NULL,
  `command_cn` varchar(100) DEFAULT '',
  `region` varchar(255) DEFAULT '',
  `priority` varchar(32) DEFAULT 'normal',
  `enable` char(1) DEFAULT '1',
  `execute_type` varchar(32) DEFAULT 'manual',
  `execute_day` varchar(64) DEFAULT '',
  `execute_time` datetime DEFAULT NULL,
  `is_return` char(1) DEFAULT '0',
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `is_delete` char(1) DEFAULT '0',
  `img_ids` varchar(255) DEFAULT '',
  `audit_ids` varchar(255) DEFAULT '',
  `task_type` varchar(64) DEFAULT '',
  `task_subtype` varchar(64) DEFAULT '',
  `task_mode` varchar(64) DEFAULT '',
  `direct_execution` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `message_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT '',
  `content` longtext,
  `source` varchar(64) DEFAULT '',
  `processor` varchar(64) DEFAULT '',
  `status` char(1) DEFAULT '0',
  `room_code` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `flight_info` (
  `flight_id` varchar(64) NOT NULL,
  `send_time` datetime DEFAULT NULL,
  `airline_cd` varchar(16) DEFAULT '',
  `flight_no` varchar(32) DEFAULT '',
  `sche_exec_date` date DEFAULT NULL,
  `flight_attr` varchar(32) DEFAULT '',
  `craft_type` varchar(32) DEFAULT '',
  `craft_no` varchar(32) DEFAULT '',
  `latest_off_status` varchar(16) DEFAULT 'SCH',
  `latest_on_status` varchar(16) DEFAULT 'ON',
  `dom_flight_state` varchar(32) DEFAULT '',
  `int_flight_state` varchar(32) DEFAULT '',
  `dom_flight_abstate` varchar(32) DEFAULT '',
  `int_flight_abstate` varchar(32) DEFAULT '',
  `dom_ab_state_time` datetime DEFAULT NULL,
  `int_ab_state_time` datetime DEFAULT NULL,
  `dom_flight_abstate_reason` varchar(255) DEFAULT '',
  `int_flight_abstate_reason` varchar(255) DEFAULT '',
  `dom_inner_flight_abstate_reason` varchar(255) DEFAULT '',
  `int_inner_flight_abstate_reason` varchar(255) DEFAULT '',
  `dom_flight_abstate_reason_desc` varchar(500) DEFAULT '',
  `int_flight_abstate_reason_desc` varchar(500) DEFAULT '',
  `airline` varchar(64) DEFAULT '',
  `station` varchar(64) DEFAULT '',
  `station_cn` varchar(64) DEFAULT '',
  `sche_take_off_time` datetime DEFAULT NULL,
  `estm_take_off_time` datetime DEFAULT NULL,
  `actl_take_off_time` datetime DEFAULT NULL,
  `gate_cd` varchar(64) DEFAULT '',
  `gate_attr` varchar(64) DEFAULT '',
  `estm_start_time` datetime DEFAULT NULL,
  `estm_end_time` datetime DEFAULT NULL,
  `carousel_cd` varchar(64) DEFAULT '',
  `carousel_class` varchar(64) DEFAULT '',
  `carousel_attr` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` char(1) DEFAULT '0',
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `flight_gate` (
  `flight_id` varchar(64) NOT NULL,
  `send_time` datetime DEFAULT NULL,
  `sche_exec_date` date DEFAULT NULL,
  `terminal_cd` varchar(32) DEFAULT '',
  `gate_cd` varchar(64) DEFAULT '',
  `gate_attr` varchar(64) DEFAULT '',
  `estm_start_time` datetime DEFAULT NULL,
  `estm_end_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `flight_kafka_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airport_code` varchar(16) DEFAULT '',
  `sub_type` varchar(64) DEFAULT '',
  `msg` longtext,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `flight_warning` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `flight_id` varchar(64) DEFAULT '',
  `flight_no` varchar(32) DEFAULT '',
  `passenger_id` bigint DEFAULT NULL,
  `user_name` varchar(100) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `warning_type` varchar(64) DEFAULT '',
  `change_before` varchar(255) DEFAULT '',
  `change_after` varchar(255) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_flight_change` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `flight_no` varchar(32) DEFAULT '',
  `name` varchar(100) DEFAULT '',
  `arr_name` varchar(100) DEFAULT '',
  `change_status` varchar(32) DEFAULT '',
  `changeStatusCn` varchar(100) DEFAULT '',
  `arr_time` datetime DEFAULT NULL,
  `dept_name` varchar(100) DEFAULT '',
  `carrier` varchar(32) DEFAULT '',
  `dept_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `get_in_tmp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT '',
  `flight_no` varchar(32) DEFAULT '',
  `orig` varchar(32) DEFAULT '',
  `dest` varchar(32) DEFAULT '',
  `cabin` varchar(32) DEFAULT '',
  `seat` varchar(32) DEFAULT '',
  `seg` varchar(32) DEFAULT '',
  `card_service` varchar(64) DEFAULT '',
  `star_level` varchar(64) DEFAULT '',
  `in_type` varchar(32) DEFAULT '',
  `code` varchar(32) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `passenger` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT '',
  `room_code` varchar(64) DEFAULT NULL,
  `flight_no` varchar(32) DEFAULT '',
  `flight_date` date DEFAULT NULL,
  `orig` varchar(32) DEFAULT '',
  `dest` varchar(32) DEFAULT '',
  `cabin` varchar(32) DEFAULT '',
  `seat` varchar(32) DEFAULT '',
  `seq` varchar(32) DEFAULT '',
  `card_service` varchar(64) DEFAULT '',
  `card_no` varchar(64) DEFAULT '',
  `mem_level` varchar(64) DEFAULT '',
  `star_level` varchar(64) DEFAULT '',
  `in_type` varchar(32) DEFAULT '',
  `get_in_time` datetime DEFAULT NULL,
  `get_out_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reid` varchar(64) DEFAULT '',
  `pid` varchar(64) DEFAULT '',
  `flight_id` varchar(64) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `ori_image_url` varchar(255) DEFAULT '',
  `register_image_url` varchar(255) DEFAULT '',
  `photo` longtext,
  `robot_id` varchar(64) DEFAULT '',
  `follower_num` int DEFAULT 0,
  `is_member` char(1) DEFAULT '0',
  `colledt_id` varchar(64) DEFAULT '',
  `coordinate` text,
  `remark` varchar(500) DEFAULT '',
  `warning_type` varchar(64) DEFAULT '',
  `change_before` varchar(255) DEFAULT '',
  `change_after` varchar(255) DEFAULT '',
  `region_name` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `passenger_location_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `passenger_id` bigint DEFAULT NULL,
  `user_name` varchar(100) DEFAULT '',
  `room_code` varchar(64) DEFAULT NULL,
  `flight_no` varchar(32) DEFAULT '',
  `flight_date` date DEFAULT NULL,
  `reid` varchar(64) DEFAULT '',
  `pid` varchar(64) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `region_name` varchar(100) DEFAULT '',
  `coordinate` text,
  `device_id` bigint DEFAULT NULL,
  `device_name` varchar(100) DEFAULT '',
  `deep_glint_device_id` varchar(128) DEFAULT '',
  `recognition_type` varchar(32) DEFAULT '',
  `ori_image_url` varchar(255) DEFAULT '',
  `register_image_url` varchar(255) DEFAULT '',
  `is_out` char(1) DEFAULT '0',
  `cts` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `passenger_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `p_id` bigint DEFAULT NULL,
  `robot_id` varchar(64) DEFAULT '',
  `room_code` varchar(64) DEFAULT NULL,
  `collect_data` longtext,
  `back_info` longtext,
  `get_type` varchar(32) DEFAULT '',
  `is_success` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `passenger_out_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `passenger_id` bigint DEFAULT NULL,
  `user_name` varchar(100) DEFAULT '',
  `room_code` varchar(64) DEFAULT NULL,
  `flight_no` varchar(32) DEFAULT '',
  `flight_date` date DEFAULT NULL,
  `reid` varchar(64) DEFAULT '',
  `recognition_type` varchar(32) DEFAULT '',
  `out_time` datetime DEFAULT NULL,
  `cts` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `passenger_warning_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `passenger_id` bigint DEFAULT NULL,
  `flight_warning_id` bigint DEFAULT NULL,
  `flight_id` varchar(64) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `warning_type` varchar(64) DEFAULT '',
  `warning_info` varchar(500) DEFAULT '',
  `notice_type` varchar(32) DEFAULT '',
  `robot_task_id` bigint DEFAULT NULL,
  `is_success` char(1) DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_config` (
  `food_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `img_ids` varchar(255) DEFAULT '',
  `price` decimal(10,2) DEFAULT 0.00,
  `calorie` int DEFAULT 0,
  `dic_type_code` varchar(64) DEFAULT '',
  `remark` varchar(500) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_daily` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `food_date` date DEFAULT NULL,
  `food_id` bigint DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_code` varchar(64) NOT NULL,
  `desk_no` varchar(64) DEFAULT '',
  `remark` varchar(500) DEFAULT '',
  `status` char(1) DEFAULT '0',
  `card_no` varchar(64) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `table_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `food_name` varchar(100) DEFAULT '',
  `food_id` bigint DEFAULT NULL,
  `num` int DEFAULT 1,
  `price` decimal(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_day` date DEFAULT NULL,
  `end_day` date DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `room_code` varchar(64) DEFAULT NULL,
  `food_ids` varchar(255) DEFAULT '',
  `food_names` varchar(500) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `guide_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `coordinate` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `robot_cmd_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `cmd` longtext,
  `cmd_type` varchar(32) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `robot_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `task_id` bigint DEFAULT NULL,
  `task_name` varchar(100) DEFAULT '',
  `task_type` varchar(64) DEFAULT '',
  `task_subtype` varchar(64) DEFAULT '',
  `task_mode` varchar(64) DEFAULT '',
  `task_status` varchar(32) DEFAULT '',
  `direct_execution` char(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `return_info` text,
  `cmd` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `insp_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `status` varchar(32) DEFAULT '',
  `task_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `insp_task_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `insp_task_id` bigint DEFAULT NULL,
  `robot_id` varchar(64) DEFAULT '',
  `type` varchar(64) DEFAULT '',
  `point` varchar(255) DEFAULT '',
  `abnormal` char(1) DEFAULT '0',
  `abnormal_info` varchar(500) DEFAULT '',
  `image_base64` longtext,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `update_notice_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notice_id` bigint DEFAULT NULL,
  `status` varchar(32) DEFAULT '',
  `message` varchar(500) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ai_chat_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `question` text,
  `answer` text,
  `chat_type` varchar(64) DEFAULT '',
  `language` varchar(16) DEFAULT 'CN',
  `robot_name` varchar(100) DEFAULT '',
  `dept_name` varchar(100) DEFAULT '',
  `ai_auto_classification` char(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ai_knowledge_base` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `source` varchar(255) DEFAULT '',
  `type` varchar(64) DEFAULT 'faq',
  `status` char(1) DEFAULT '0',
  `enable` char(1) DEFAULT '1',
  `f_id` bigint DEFAULT NULL,
  `vector_id` varchar(255) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT,
  `table_name` varchar(200) NOT NULL,
  `table_comment` varchar(500) DEFAULT '',
  `sub_table_name` varchar(200) DEFAULT '',
  `sub_table_fk_name` varchar(200) DEFAULT '',
  `class_name` varchar(100) DEFAULT '',
  `tpl_category` varchar(64) DEFAULT 'crud',
  `package_name` varchar(100) DEFAULT '',
  `module_name` varchar(30) DEFAULT '',
  `business_name` varchar(30) DEFAULT '',
  `function_name` varchar(50) DEFAULT '',
  `function_author` varchar(50) DEFAULT '',
  `gen_type` char(1) DEFAULT '0',
  `gen_path` varchar(200) DEFAULT '/',
  `options` varchar(1000) DEFAULT '',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT,
  `table_id` bigint NOT NULL,
  `column_name` varchar(200) DEFAULT '',
  `column_comment` varchar(500) DEFAULT '',
  `column_type` varchar(100) DEFAULT '',
  `java_type` varchar(100) DEFAULT '',
  `java_field` varchar(100) DEFAULT '',
  `is_pk` char(1) DEFAULT '0',
  `is_increment` char(1) DEFAULT '0',
  `is_required` char(1) DEFAULT '0',
  `is_insert` char(1) DEFAULT '1',
  `is_edit` char(1) DEFAULT '1',
  `is_list` char(1) DEFAULT '1',
  `is_query` char(1) DEFAULT '0',
  `query_type` varchar(200) DEFAULT 'EQ',
  `html_type` varchar(100) DEFAULT 'input',
  `dict_type` varchar(200) DEFAULT '',
  `sort` int DEFAULT 0,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- core seed data
INSERT INTO `sys_dept`
(`dept_id`,`parent_id`,`ancestors`,`dept_name`,`order_num`,`leader`,`phone`,`email`,`status`,`del_flag`,`room_code`,`create_by`,`create_time`)
VALUES
(100,0,'0','国航智慧贵宾室',0,'admin','13800000000','admin@example.com','0','0','PEK2DX1','system',NOW()),
(101,100,'0,100','国航T2休息室A区',1,'admin','13800000000','admin@example.com','0','0','PEK2DX1-A','system',NOW());

INSERT INTO `sys_post`
(`post_id`,`post_code`,`post_name`,`post_sort`,`status`,`create_by`,`create_time`,`remark`)
VALUES
(1,'ceo','管理员',1,'0','system',NOW(),'默认岗位');

INSERT INTO `sys_role`
(`role_id`,`role_name`,`role_key`,`role_sort`,`data_scope`,`menu_check_strictly`,`dept_check_strictly`,`status`,`del_flag`,`create_by`,`create_time`,`remark`)
VALUES
(1,'超级管理员','admin',1,'1',1,1,'0','0','system',NOW(),'系统内置角色'),
(2,'普通运营','operator',2,'1',1,1,'0','0','system',NOW(),'示例运营角色');

INSERT INTO `sys_user`
(`user_id`,`dept_id`,`user_name`,`nick_name`,`user_type`,`email`,`phonenumber`,`sex`,`avatar`,`password`,`status`,`del_flag`,`login_ip`,`create_by`,`create_time`,`remark`)
VALUES
(1,100,'admin','管理员','00','admin@example.com','13800000000','1','',
'$2y$10$alm1./9FFxSqVVIN0/hLzebdkk7xESLO4ZiGADFVUtY.3iNCTZr1q',
'0','0','','system',NOW(),'默认管理员'),
(2,100,'operator','运营人员','00','operator@example.com','13800000001','1','',
'$2y$10$alm1./9FFxSqVVIN0/hLzebdkk7xESLO4ZiGADFVUtY.3iNCTZr1q',
'0','0','','system',NOW(),'示例运营用户');

INSERT INTO `sys_user_role` (`user_id`,`role_id`) VALUES
(1,1),
(2,2);

INSERT INTO `sys_user_post` (`user_id`,`post_id`) VALUES
(1,1),
(2,1);

INSERT INTO `sys_role_dept` (`role_id`,`dept_id`) VALUES
(1,100),
(1,101),
(2,100);

-- menus
INSERT INTO `sys_menu`
(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`query`,`is_frame`,`is_cache`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`remark`)
VALUES
(1,'系统管理',0,1,'system',NULL,NULL,'1','0','M','0','0','','system','system',NOW(),''),
(2,'监控管理',0,2,'monitor',NULL,NULL,'1','0','M','0','0','','monitor','system',NOW(),''),
(3,'机器人配置',0,3,'config',NULL,NULL,'1','0','M','0','0','','tool','system',NOW(),''),
(4,'航班旅客',0,4,'flight',NULL,NULL,'1','0','M','0','0','','peoples','system',NOW(),''),
(5,'休息室点餐',0,5,'food',NULL,NULL,'1','0','M','0','0','','food','system',NOW(),''),
(6,'AI知识库',0,6,'ai',NULL,NULL,'1','0','M','0','0','','documentation','system',NOW(),''),

(100,'用户管理',1,1,'user','system/user/index',NULL,'1','0','C','0','0','system:user:list,system:user:query,system:user:add,system:user:edit,system:user:remove,system:user:resetPwd,system:user:import,system:user:export','user','system',NOW(),''),
(101,'角色管理',1,2,'role','system/role/index',NULL,'1','0','C','0','0','system:role:list,system:role:query,system:role:add,system:role:edit,system:role:remove,system:role:export','peoples','system',NOW(),''),
(102,'菜单管理',1,3,'menu','system/menu/index',NULL,'1','0','C','0','0','system:menu:list,system:menu:query,system:menu:add,system:menu:edit,system:menu:remove','tree','system',NOW(),''),
(103,'部门管理',1,4,'dept','system/dept/index',NULL,'1','0','C','0','0','system:dept:list,system:dept:query,system:dept:add,system:dept:edit,system:dept:remove','tree','system',NOW(),''),
(104,'岗位管理',1,5,'post','system/post/index',NULL,'1','0','C','0','0','system:post:list,system:post:query,system:post:add,system:post:edit,system:post:remove','post','system',NOW(),''),
(105,'字典类型',1,6,'dict','system/dict/index',NULL,'1','0','C','0','0','system:dict:list,system:dict:query,system:dict:add,system:dict:edit,system:dict:remove,system:dict:export','dict','system',NOW(),''),
(106,'参数配置',1,7,'config','system/config/index',NULL,'1','0','C','0','0','system:config:list,system:config:query,system:config:add,system:config:edit,system:config:remove,system:config:export','edit','system',NOW(),''),
(107,'通知公告',1,8,'notice','system/notice/index',NULL,'1','0','C','0','0','system:notice:list,system:notice:query,system:notice:add,system:notice:edit,system:notice:remove','message','system',NOW(),''),

(200,'在线用户',2,1,'online','monitor/online/index',NULL,'1','0','C','0','0','monitor:online:list,monitor:online:forceLogout','online','system',NOW(),''),
(201,'操作日志',2,2,'operlog','monitor/operlog/index',NULL,'1','0','C','0','0','monitor:operlog:list,monitor:operlog:query,monitor:operlog:remove,monitor:operlog:export','log','system',NOW(),''),
(202,'登录日志',2,3,'logininfor','monitor/logininfor/index',NULL,'1','0','C','0','0','monitor:logininfor:list,monitor:logininfor:query,monitor:logininfor:remove,monitor:logininfor:export','logininfor','system',NOW(),''),
(203,'缓存监控',2,4,'cache','monitor/cache/index',NULL,'1','0','C','0','0','monitor:cache:list','redis','system',NOW(),''),
(204,'定时任务',2,5,'job','monitor/job/index',NULL,'1','0','C','0','0','monitor:job:list,monitor:job:query,monitor:job:add,monitor:job:edit,monitor:job:remove,monitor:job:changeStatus,monitor:job:export','job','system',NOW(),''),
(205,'调度日志',2,6,'job-log','monitor/job/log',NULL,'1','0','C','0','0','monitor:job:query','log','system',NOW(),''),
(206,'登录日志',2,7,'login-log','monitor/logininfor/index',NULL,'1','0','C','0','0','monitor:logininfor:list,monitor:logininfor:query','logininfor','system',NOW(),''),
(207,'操作日志',2,8,'oper-log','monitor/operlog/index',NULL,'1','0','C','0','0','monitor:operlog:list,monitor:operlog:query','log','system',NOW(),''),
(208,'服务监控',2,9,'server','monitor/server/index',NULL,'1','0','C','0','0','monitor:server:list','server','system',NOW(),''),
(209,'数据库监控',2,10,'druid','monitor/druid/index',NULL,'1','0','C','0','0','monitor:druid:list','druid','system',NOW(),''),

(300,'机器人配置',3,1,'robot','configManagment/robot/index',NULL,'1','0','C','0','0','config:robot:list,config:robot:query,config:robot:add,config:robot:edit,config:robot:remove','robot','system',NOW(),''),
(301,'区域配置',3,2,'region','configManagment/vipRoomRegion/index',NULL,'1','0','C','0','0','config:region:list,config:region:query,config:region:add,config:region:edit,config:region:remove,config:region:export','map','system',NOW(),''),
(302,'图片素材',3,3,'photo','configManagment/photo/index',NULL,'1','0','C','0','0','config:photo:list,config:photo:query,config:photo:add,config:photo:edit,config:photo:remove','image','system',NOW(),''),
(303,'桌台配置',3,4,'table','foodManagment/foodTable/index',NULL,'1','0','C','0','0','config:table:list,config:table:query,config:table:add,config:table:edit,config:table:remove','table','system',NOW(),''),
(304,'语音配置',3,5,'audio','configManagment/robotAudio/index',NULL,'1','0','C','0','0','config:audio:list,config:audio:query,config:audio:add,config:audio:edit,config:audio:remove','sound','system',NOW(),''),
(305,'任务配置',3,6,'task','taskManagment/taskList/index',NULL,'1','0','C','0','0','config:task:list,config:task:query,config:task:add,config:task:edit,config:task:remove','tool','system',NOW(),''),
(306,'贵宾室配置',3,7,'vipRoom','configManagment/vipRoom/index',NULL,'1','0','C','0','0','config:vipRoom:list,config:vipRoom:query','tree','system',NOW(),''),
(307,'区域管理',3,8,'areaManagment','configManagment/areaManagment/index',NULL,'1','0','C','0','0','config:area:list,config:area:query','tree-table','system',NOW(),''),
(308,'监控设备',3,9,'monitorDevice','configManagment/monitorDevice/index',NULL,'1','0','C','0','0','config:device:list,config:device:query','monitor','system',NOW(),''),

(400,'旅客信息',4,1,'passenger','statAnalysis/inLoungeList/index',NULL,'1','0','C','0','0','system:passenger:list,system:passenger:query,system:passenger:add,system:passenger:edit,system:passenger:remove,system:passenger:export','people','system',NOW(),''),
(401,'航班信息',4,2,'flightInfo','statAnalysis/moveStat/index',NULL,'1','0','C','0','0','flight:info:list,flight:info:query','documentation','system',NOW(),''),
(402,'数字孪生',4,3,'digitalTwin','digitalTwin/index',NULL,'1','0','C','0','0','flight:digitalTwin:view','digital-twin-view','system',NOW(),''),
(403,'出厅统计',4,4,'outGoing','viewManagment/outGoing/index',NULL,'1','0','C','0','0','flight:outGoing:list','walk','system',NOW(),''),
(404,'问题统计',4,5,'questionStat','statAnalysis/questionStat/index',NULL,'1','0','C','0','0','flight:questionStat:list','chart','system',NOW(),''),
(405,'预警记录',4,6,'passengerWarning','statAnalysis/passengerWarningLog/index',NULL,'1','0','C','0','0','flight:warning:list','warning','system',NOW(),''),

(500,'菜品管理',5,1,'foodConfig','foodManagment/food/index',NULL,'1','0','C','0','0','food:config:list,food:config:query,food:config:add,food:config:edit,food:config:remove','food','system',NOW(),''),
(501,'今日菜单',5,2,'dailyMenu','foodManagment/menuPlan/index',NULL,'1','0','C','0','0','food:daily:list,food:daily:query,food:daily:add,food:daily:edit,food:daily:remove','menuPlan','system',NOW(),''),
(502,'点餐订单',5,3,'foodOrder','foodManagment/foodMenu/index',NULL,'1','0','C','0','0','food:order:list,food:order:query,food:order:add,food:order:edit,food:order:remove','shopping','system',NOW(),''),
(503,'桌台视图',5,4,'foodTable','foodManagment/foodTable/index',NULL,'1','0','C','0','0','food:table:list','table','system',NOW(),''),
(504,'菜单计划',5,5,'foodPlan','foodManagment/foodPlan/index',NULL,'1','0','C','0','0','food:plan:list,food:plan:query,food:plan:add,food:plan:edit,food:plan:remove','calendar','system',NOW(),''),

(600,'知识库管理',6,1,'knowledge','knowledgeManagment/ai/knowledge/index',NULL,'1','0','C','0','0','ai:knowledge:list,ai:knowledge:query,ai:knowledge:add,ai:knowledge:edit,ai:knowledge:remove','documentation','system',NOW(),'');

INSERT INTO `sys_role_menu` (`role_id`,`menu_id`)
SELECT 1, menu_id FROM sys_menu;

INSERT INTO `sys_role_menu` (`role_id`,`menu_id`) VALUES
(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),
(2,200),(2,201),(2,202),(2,203),(2,204),
(2,300),(2,301),(2,302),(2,303),(2,304),(2,305),
(2,400),(2,401),(2,402),
(2,500),(2,501),(2,502),
(2,600);

INSERT INTO `sys_config`
(`config_id`,`config_name`,`config_key`,`config_value`,`config_type`,`create_by`,`create_time`,`remark`)
VALUES
(1,'用户登录验证码开关','sys.account.captchaOnOff','false','Y','system',NOW(),'本地演示默认关闭验证码'),
(2,'演示环境开关','sys.demo.enabled','false','Y','system',NOW(),''),
(3,'贵宾室默认编码','robotmonitor.defaultRoomCode','PEK2DX1','Y','system',NOW(),'');

INSERT INTO `sys_notice`
(`notice_id`,`notice_title`,`notice_type`,`notice_content`,`status`,`create_by`,`create_time`,`remark`)
VALUES
(1,'系统初始化完成','1','欢迎使用国航智慧贵宾室管理后台演示数据。','0','system',NOW(),'seed');

-- dict types used by frontend
INSERT INTO `sys_dict_type`
(`dict_id`,`dict_name`,`dict_type`,`status`,`create_by`,`create_time`,`remark`)
VALUES
(1,'显示/隐藏','sys_show_hide','0','system',NOW(),''),
(2,'停用/正常','sys_normal_disable','0','system',NOW(),''),
(3,'是/否','sys_yes_no','0','system',NOW(),''),
(4,'通用状态','sys_common_status','0','system',NOW(),''),
(5,'公告状态','sys_notice_status','0','system',NOW(),''),
(6,'操作类型','sys_oper_type','0','system',NOW(),''),
(7,'任务分组','sys_job_group','0','system',NOW(),''),
(8,'机器人启用状态','robot_enable_type','0','system',NOW(),''),
(9,'机器人类型','robot_type','0','system',NOW(),''),
(10,'区域引导标记','region_guide_flag','0','system',NOW(),''),
(11,'设备启用状态','device_enable_type','0','system',NOW(),''),
(12,'图片可用状态','photo_enable_type','0','system',NOW(),''),
(13,'图片类型','pic_type','0','system',NOW(),''),
(14,'任务启用状态','task_enable_type','0','system',NOW(),''),
(15,'音频启用状态','audio_enable_type','0','system',NOW(),''),
(16,'欢迎语类型','welcome_type','0','system',NOW(),''),
(17,'菜单状态','daily_menu_status','0','system',NOW(),''),
(18,'聊天事件类型','chat_event_type','0','system',NOW(),''),
(19,'知识库状态','ai_knowledge_base_status','0','system',NOW(),''),
(20,'食品类型','food_type','0','system',NOW(),'');

INSERT INTO `sys_dict_data`
(`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`list_class`,`is_default`,`status`,`create_by`,`create_time`,`remark`)
VALUES
(1,'显示','0','sys_show_hide','','primary','Y','0','system',NOW(),''),
(2,'隐藏','1','sys_show_hide','','info','N','0','system',NOW(),''),
(1,'正常','0','sys_normal_disable','','success','Y','0','system',NOW(),''),
(2,'停用','1','sys_normal_disable','','danger','N','0','system',NOW(),''),
(1,'是','Y','sys_yes_no','','success','Y','0','system',NOW(),''),
(2,'否','N','sys_yes_no','','info','N','0','system',NOW(),''),
(1,'正常','0','sys_common_status','','success','Y','0','system',NOW(),''),
(2,'停用','1','sys_common_status','','danger','N','0','system',NOW(),''),
(1,'正常','0','sys_notice_status','','success','Y','0','system',NOW(),''),
(2,'关闭','1','sys_notice_status','','info','N','0','system',NOW(),''),
(1,'新增','1','sys_oper_type','','primary','N','0','system',NOW(),''),
(2,'修改','2','sys_oper_type','','warning','N','0','system',NOW(),''),
(3,'删除','3','sys_oper_type','','danger','N','0','system',NOW(),''),
(4,'导出','5','sys_oper_type','','info','N','0','system',NOW(),''),
(1,'默认','DEFAULT','sys_job_group','','primary','Y','0','system',NOW(),''),
(2,'系统','SYSTEM','sys_job_group','','success','N','0','system',NOW(),''),
(1,'启用','1','robot_enable_type','','success','Y','0','system',NOW(),''),
(2,'禁用','0','robot_enable_type','','danger','N','0','system',NOW(),''),
(1,'多功能机器人','多功能机器人','robot_type','','primary','Y','0','system',NOW(),''),
(2,'迎宾机器人','迎宾机器人','robot_type','','success','N','0','system',NOW(),''),
(1,'可引导','1','region_guide_flag','','success','Y','0','system',NOW(),''),
(2,'不可引导','0','region_guide_flag','','info','N','0','system',NOW(),''),
(1,'启用','1','device_enable_type','','success','Y','0','system',NOW(),''),
(2,'禁用','0','device_enable_type','','danger','N','0','system',NOW(),''),
(1,'启用','1','photo_enable_type','','success','Y','0','system',NOW(),''),
(2,'禁用','0','photo_enable_type','','danger','N','0','system',NOW(),''),
(1,'图片','1','pic_type','','primary','Y','0','system',NOW(),''),
(2,'视频','2','pic_type','','warning','N','0','system',NOW(),''),
(1,'启用','1','task_enable_type','','success','Y','0','system',NOW(),''),
(2,'禁用','0','task_enable_type','','danger','N','0','system',NOW(),''),
(1,'启用','1','audio_enable_type','','success','Y','0','system',NOW(),''),
(2,'禁用','0','audio_enable_type','','danger','N','0','system',NOW(),''),
(1,'欢迎词','welcome','welcome_type','','primary','Y','0','system',NOW(),''),
(2,'播报词','broadcast','welcome_type','','success','N','0','system',NOW(),''),
(1,'上架','1','daily_menu_status','','success','Y','0','system',NOW(),''),
(2,'下架','0','daily_menu_status','','danger','N','0','system',NOW(),''),
(1,'普通聊天','NORMAL','chat_event_type','','primary','Y','0','system',NOW(),''),
(2,'问答','FAQ','chat_event_type','','success','N','0','system',NOW(),''),
(3,'无命中','NONE','chat_event_type','','warning','N','0','system',NOW(),''),
(1,'待处理','0','ai_knowledge_base_status','','warning','Y','0','system',NOW(),''),
(2,'已处理','1','ai_knowledge_base_status','','success','N','0','system',NOW(),''),
(1,'冷菜','冷菜','food_type','','primary','Y','0','system',NOW(),''),
(2,'热菜','热菜','food_type','','success','N','0','system',NOW(),''),
(3,'甜品','甜品','food_type','','warning','N','0','system',NOW(),''),
(4,'饮料','饮料','food_type','','info','N','0','system',NOW(),'');

INSERT INTO `config_img`
(`id`,`img_type`,`img`,`img_name`,`width`,`height`,`remark`,`enable`,`create_by`,`create_time`,`room_code`,`is_delete`)
VALUES
(1,'1','/static/png/demo-robot.png','机器人示例图',512,512,'seed','1','system',NOW(),'PEK2DX1','0'),
(2,'1','/static/png/demo-food.png','菜品示例图',512,512,'seed','1','system',NOW(),'PEK2DX1','0');

INSERT INTO `config_audio`
(`id`,`audio_key`,`audio_value`,`language_type`,`text_info`,`remark`,`create_by`,`create_time`,`room_code`,`audio_type`)
VALUES
(1,'welcome_cn','/audio/welcome_cn.mp3','CN','欢迎来到国航智慧贵宾室。','seed','system',NOW(),'PEK2DX1','welcome'),
(2,'guide_cn','/audio/guide_cn.mp3','CN','请跟随我前往目的区域。','seed','system',NOW(),'PEK2DX1','guide');

INSERT INTO `config_robot_audio`
(`id`,`audio_key`,`audio_value`,`language_type`,`text_info`,`remark`,`create_by`,`create_time`,`room_code`)
VALUES
(1,'robot_online_cn','/audio/robot_online_cn.mp3','CN','机器人已上线。','seed','system',NOW(),'PEK2DX1');

INSERT INTO `config_area`
(`id`,`area_name`,`coordinate`,`remark`,`enable`,`create_by`,`create_time`,`room_code`,`img_ids`,`is_guide`,`is_show`,`max_capacity`)
VALUES
(1,'A区候机区','[[116.4074,39.9042],[116.4075,39.9043]]','seed','1','system',NOW(),'PEK2DX1','1','1','1',30),
(2,'B区休息区','[[116.4076,39.9044],[116.4077,39.9045]]','seed','1','system',NOW(),'PEK2DX1','1','0','1',20);

INSERT INTO `config_area_detail`
(`id`,`area_id`,`area_name`,`language_type`,`label`,`audio`,`arr_audio`,`arr_text`,`remark`,`create_by`,`create_time`)
VALUES
(1,1,'A区候机区','CN','介绍','/audio/area_a.mp3','[]','[]','seed','system',NOW()),
(2,2,'B区休息区','CN','介绍','/audio/area_b.mp3','[]','[]','seed','system',NOW());

INSERT INTO `config_region`
(`id`,`region_name`,`coordinate`,`remark`,`enable`,`create_by`,`create_time`,`room_code`,`img_ids`,`audio_keys`,`is_guide`,`is_show`,`max_capacity`,`area_id`)
VALUES
(1,'入口区域','[[116.4074,39.9042],[116.40745,39.90425]]','seed','1','system',NOW(),'PEK2DX1','1','welcome_cn','1','1',10,1),
(2,'餐饮区域','[[116.4075,39.9043],[116.40755,39.90435]]','seed','1','system',NOW(),'PEK2DX1','2','guide_cn','0','1',20,2);

INSERT INTO `config_device`
(`id`,`device_name`,`device_type`,`enable`,`remark`,`create_by`,`create_time`,`room_code`,`is_delete`,`deep_glint_device_id`)
VALUES
(1,'入口摄像头01','camera','1','seed','system',NOW(),'PEK2DX1','0','dg-device-001');

INSERT INTO `config_device_region`
(`device_id`,`region_id`,`region_name`,`coordinate`,`img_id`,`img`,`remark`)
VALUES
(1,1,'入口区域','[[116.4074,39.9042]]',1,'/static/png/demo-robot.png','seed');

INSERT INTO `config_table`
(`id`,`table_no`,`region_id`,`room_code`,`is_enable`,`remark`,`create_by`,`create_time`,`device_id`,`camera_coordinates`,`status`)
VALUES
(1,'A01',2,'PEK2DX1','1','seed','system',NOW(),1,'[[116.4075,39.9043]]','0'),
(2,'A02',2,'PEK2DX1','1','seed','system',NOW(),1,'[[116.4076,39.9044]]','0');

INSERT INTO `config_robot`
(`id`,`robot_id`,`robot_name`,`mac`,`robot_ip`,`charging_state`,`working_state`,`standby_state`,`positioning_state`,`region_id`,`battery_state`,`network`,`robot_error`,`error_messages`,`robot_type`,`belonged_company`,`enable`,`remark`,`create_by`,`create_time`,`room_code`,`task_id`,`task_status`,`is_delete`,`img_ids`,`audit_keys`,`employee_no`,`account_id`,`ori_coordinate`,`admin_mode`)
VALUES
(1,'robot-001','贵宾室机器人01','00:11:22:33:44:55','127.0.0.1','idle','standby','1','1',1,85,1,'0','','多功能机器人','国航','1','演示机器人','system',NOW(),'PEK2DX1',NULL,'IDLE','0','1','','EMP001','ACC001','[[116.4074,39.9042]]','0');

INSERT INTO `config_task`
(`id`,`task_name`,`robot_id`,`command`,`command_cn`,`region`,`priority`,`enable`,`execute_type`,`execute_day`,`execute_time`,`is_return`,`remark`,`create_by`,`create_time`,`room_code`,`is_delete`,`img_ids`,`audit_ids`,`task_type`,`task_subtype`,`task_mode`,`direct_execution`)
VALUES
(1,'欢迎巡游',1,1001,'欢迎动作','1','normal','1','manual','',NOW(),'0','seed','system',NOW(),'PEK2DX1','0','','','guide','welcome','manual','0');

INSERT INTO `message_log`
(`id`,`title`,`content`,`source`,`processor`,`status`,`room_code`,`create_time`,`update_time`)
VALUES
(1,'系统启动消息','后台初始化完成。','system','admin','0','PEK2DX1',NOW(),NOW());

INSERT INTO `flight_info`
(`flight_id`,`send_time`,`airline_cd`,`flight_no`,`sche_exec_date`,`flight_attr`,`craft_type`,`craft_no`,`latest_off_status`,`latest_on_status`,`airline`,`station`,`station_cn`,`sche_take_off_time`,`estm_take_off_time`,`gate_cd`,`gate_attr`,`estm_start_time`,`estm_end_time`,`carousel_cd`,`carousel_class`,`carousel_attr`,`update_time`,`is_delete`)
VALUES
('CA1234-20260506',NOW(),'CA','CA1234',CURDATE(),'D','A320','B-1234','SCH','ON','国航','HGH','杭州',DATE_ADD(NOW(),INTERVAL 2 HOUR),DATE_ADD(NOW(),INTERVAL 2 HOUR),'G12','DOM',DATE_ADD(NOW(),INTERVAL 90 MINUTE),DATE_ADD(NOW(),INTERVAL 100 MINUTE),'C01','DOM','A',NOW(),'0'),
('CA5678-20260506',NOW(),'CA','CA5678',CURDATE(),'D','A321','B-5678','ETD','ON','国航','PEK','北京',DATE_ADD(NOW(),INTERVAL 3 HOUR),DATE_ADD(NOW(),INTERVAL 3 HOUR),'G08','DOM',DATE_ADD(NOW(),INTERVAL 150 MINUTE),DATE_ADD(NOW(),INTERVAL 160 MINUTE),'C02','DOM','A',NOW(),'0');

INSERT INTO `flight_gate`
(`flight_id`,`send_time`,`sche_exec_date`,`terminal_cd`,`gate_cd`,`gate_attr`,`estm_start_time`,`estm_end_time`,`update_time`)
VALUES
('CA1234-20260506',NOW(),CURDATE(),'T2','G12','DOM',DATE_ADD(NOW(),INTERVAL 90 MINUTE),DATE_ADD(NOW(),INTERVAL 100 MINUTE),NOW()),
('CA5678-20260506',NOW(),CURDATE(),'T2','G08','DOM',DATE_ADD(NOW(),INTERVAL 150 MINUTE),DATE_ADD(NOW(),INTERVAL 160 MINUTE),NOW());

INSERT INTO `flight_warning`
(`id`,`flight_id`,`flight_no`,`passenger_id`,`user_name`,`region_id`,`warning_type`,`change_before`,`change_after`,`create_time`)
VALUES
(1,'CA1234-20260506','CA1234',1,'张三',1,'boarding','计划起飞时间 20:30','预计起飞时间 20:50',NOW()),
(2,'CA5678-20260506','CA5678',2,'李四',2,'gate-change','登机口 G08','登机口 G12',NOW());

INSERT INTO `passenger`
(`id`,`user_name`,`room_code`,`flight_no`,`flight_date`,`orig`,`dest`,`cabin`,`seat`,`seq`,`card_service`,`card_no`,`mem_level`,`star_level`,`in_type`,`get_in_time`,`get_out_time`,`status`,`create_time`,`update_time`,`reid`,`pid`,`flight_id`,`region_id`,`ori_image_url`,`register_image_url`,`robot_id`,`follower_num`,`is_member`,`colledt_id`,`coordinate`,`remark`,`warning_type`,`change_before`,`change_after`,`region_name`)
VALUES
(1,'张三','PEK2DX1','CA1234',CURDATE(),'PEK','HGH','C','12A','001','贵宾卡','CARD001','GOLD','STAR','face',NOW(),NULL,'1',NOW(),NOW(),'RE001','PID001','CA1234-20260506',1,'/static/png/p1.png','/static/png/p1-reg.png','robot-001',0,'1','COL001','[[116.4074,39.9042]]','seed','','','','入口区域'),
(2,'李四','PEK2DX1','CA5678',CURDATE(),'PEK','SHA','Y','18C','002','白金卡','CARD002','PLATINUM','STAR','barcode',NOW(),NULL,'1',NOW(),NOW(),'RE002','PID002','CA5678-20260506',2,'/static/png/p2.png','/static/png/p2-reg.png','robot-001',1,'0','COL002','[[116.4075,39.9043]]','seed','','','','餐饮区域');

INSERT INTO `passenger_location_log`
(`id`,`passenger_id`,`user_name`,`room_code`,`flight_no`,`flight_date`,`reid`,`pid`,`region_id`,`region_name`,`coordinate`,`device_id`,`device_name`,`deep_glint_device_id`,`recognition_type`,`ori_image_url`,`register_image_url`,`is_out`,`cts`,`create_time`)
VALUES
(1,1,'张三','PEK2DX1','CA1234',CURDATE(),'RE001','PID001',1,'入口区域','[[116.4074,39.9042]]',1,'入口摄像头01','dg-device-001','face','/static/png/p1.png','/static/png/p1-reg.png','0',NOW(),NOW());

INSERT INTO `passenger_log`
(`id`,`p_id`,`robot_id`,`room_code`,`collect_data`,`back_info`,`get_type`,`is_success`,`create_by`,`create_time`)
VALUES
(1,1,'robot-001','PEK2DX1','{}','{}','checkin','1','system',NOW());

INSERT INTO `passenger_out_log`
(`id`,`passenger_id`,`user_name`,`room_code`,`flight_no`,`flight_date`,`reid`,`recognition_type`,`out_time`,`cts`)
VALUES
(1,2,'李四','PEK2DX1','CA5678',CURDATE(),'RE002','face',NULL,NOW());

INSERT INTO `passenger_warning_log`
(`id`,`passenger_id`,`flight_warning_id`,`flight_id`,`region_id`,`warning_type`,`warning_info`,`notice_type`,`robot_task_id`,`is_success`,`create_by`,`create_time`,`update_by`,`update_time`)
VALUES
(1,1,NULL,'CA1234-20260506',1,'boarding','航班即将登机，请提醒旅客前往登机口。','1',NULL,'0','system',NOW(),'system',NOW()),
(2,2,NULL,'CA5678-20260506',2,'gate-change','登机口已变更，请提醒旅客查看最新登机口。','2',1,'1','system',NOW(),'system',NOW());

INSERT INTO `food_config`
(`food_id`,`name`,`img_ids`,`price`,`calorie`,`dic_type_code`,`remark`,`create_by`,`create_time`,`room_code`)
VALUES
(1,'宫保鸡丁','2',58.00,520,'热菜','seed','system',NOW(),'PEK2DX1'),
(2,'水果拼盘','2',28.00,180,'甜品','seed','system',NOW(),'PEK2DX1'),
(3,'美式咖啡','2',18.00,20,'饮料','seed','system',NOW(),'PEK2DX1');

INSERT INTO `food_daily`
(`id`,`food_date`,`food_id`,`status`,`create_by`,`create_time`,`room_code`)
VALUES
(1,CURDATE(),1,'1','system',NOW(),'PEK2DX1'),
(2,CURDATE(),2,'1','system',NOW(),'PEK2DX1'),
(3,CURDATE(),3,'1','system',NOW(),'PEK2DX1');

INSERT INTO `food_plan`
(`id`,`start_day`,`end_day`,`create_by`,`create_time`,`room_code`,`food_ids`,`food_names`)
VALUES
(1,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY),'system',NOW(),'PEK2DX1','1,2,3','宫保鸡丁,水果拼盘,美式咖啡');

INSERT INTO `food_order`
(`id`,`order_code`,`desk_no`,`remark`,`status`,`card_no`,`create_by`,`create_time`,`room_code`,`table_id`)
VALUES
(1,'FO202605060001','A01','少辣','0','CARD001','system',NOW(),'PEK2DX1',1),
(2,'FO202605060002','A02','少冰','1','CARD002','system',NOW(),'PEK2DX1',2);

INSERT INTO `food_order_detail`
(`id`,`order_id`,`food_name`,`food_id`,`num`,`price`)
VALUES
(1,1,'宫保鸡丁',1,1,58.00),
(2,1,'美式咖啡',3,1,18.00),
(3,2,'水果拼盘',2,1,28.00);

INSERT INTO `guide_log`
(`id`,`robot_id`,`region_id`,`coordinate`,`create_time`)
VALUES
(1,'robot-001',1,'[[116.4074,39.9042],[116.4075,39.9043]]',NOW()),
(2,'robot-001',2,'[[116.4075,39.9043],[116.4076,39.9044]]',NOW());

INSERT INTO `robot_cmd_log`
(`id`,`robot_id`,`cmd`,`cmd_type`,`create_time`)
VALUES
(1,'robot-001','{"task":"guide"}','guide',NOW()),
(2,'robot-001','{"task":"notify"}','notify',NOW());

INSERT INTO `robot_task`
(`id`,`robot_id`,`task_id`,`task_name`,`task_type`,`task_subtype`,`task_mode`,`task_status`,`direct_execution`,`create_time`,`start_time`,`end_time`,`return_info`,`cmd`)
VALUES
(1,'robot-001',1001,'欢迎巡游','guide','welcome','manual','1','0',NOW(),NOW(),NULL,'进行中','{"command":"guide"}'),
(2,'robot-001',1002,'登机提醒','notify','boarding','manual','3','1',NOW(),NOW(),NOW(),'执行成功','{"command":"notify"}');

INSERT INTO `insp_task`
(`id`,`robot_id`,`status`,`task_id`,`create_time`,`end_time`)
VALUES
(1,'robot-001','running',9001,NOW(),NULL);

INSERT INTO `insp_task_result`
(`id`,`insp_task_id`,`robot_id`,`type`,`point`,`abnormal`,`abnormal_info`,`image_base64`,`create_time`)
VALUES
(1,1,'robot-001','temperature','P1','0','','',NOW()),
(2,1,'robot-001','camera','P2','1','摄像头视野遮挡','',NOW());

INSERT INTO `ai_chat_log`
(`id`,`robot_id`,`question`,`answer`,`chat_type`,`language`,`robot_name`,`dept_name`,`ai_auto_classification`,`create_time`)
VALUES
(1,'robot-001','贵宾室在哪里？','贵宾室就在前方左侧。','FAQ','CN','贵宾室机器人01','国航智慧贵宾室','1',NOW()),
(2,'robot-001','我想点咖啡','您可以在点餐页面选择美式咖啡。','NORMAL','CN','贵宾室机器人01','国航智慧贵宾室','0',NOW());

INSERT INTO `ai_knowledge_base`
(`id`,`content`,`source`,`type`,`status`,`enable`,`f_id`,`vector_id`,`create_by`,`create_time`,`remark`)
VALUES
(1,'国航智慧贵宾室位于T2航站楼国际出发层。','seed','faq','1','1',NULL,'vec-001','system',NOW(),'示例知识库');

INSERT INTO `sys_job`
(`job_id`,`job_name`,`job_group`,`invoke_target`,`cron_expression`,`misfire_policy`,`concurrent`,`status`,`create_by`,`create_time`,`remark`)
VALUES
(1,'演示心跳任务','SYSTEM','ryTask.ryNoParams','0 0/30 * * * ?','3','1','1','system',NOW(),'默认暂停或启动都可');

INSERT INTO `sys_job_log`
(`job_log_id`,`job_name`,`job_group`,`invoke_target`,`job_message`,`status`,`exception_info`,`create_time`)
VALUES
(1,'演示心跳任务','SYSTEM','ryTask.ryNoParams','初始化示例日志','0','',NOW());

INSERT INTO `sys_oper_log`
(`oper_id`,`title`,`business_type`,`method`,`request_method`,`operator_type`,`oper_name`,`dept_name`,`oper_url`,`oper_ip`,`oper_location`,`oper_param`,`json_result`,`status`,`error_msg`,`oper_time`)
VALUES
(1,'用户管理',1,'SysUserController.list','GET',1,'admin','国航智慧贵宾室','/system/user/list','127.0.0.1','LOCAL','{}','{}',0,'',NOW());

INSERT INTO `sys_logininfor`
(`info_id`,`user_name`,`status`,`ipaddr`,`login_location`,`browser`,`os`,`msg`,`login_time`)
VALUES
(1,'admin','0','127.0.0.1','LOCAL','Chrome','Linux','登录成功',NOW());

INSERT INTO `gen_table`
(`table_id`,`table_name`,`table_comment`,`class_name`,`tpl_category`,`package_name`,`module_name`,`business_name`,`function_name`,`function_author`,`gen_type`,`gen_path`,`options`,`create_by`,`create_time`,`remark`)
VALUES
(1,'config_robot','机器人配置表','ConfigRobot','crud','com.robotmonitor.config','config','robot','机器人配置','system','0','/','{}','system',NOW(),'seed');

INSERT INTO `gen_table_column`
(`column_id`,`table_id`,`column_name`,`column_comment`,`column_type`,`java_type`,`java_field`,`is_pk`,`is_increment`,`is_required`,`is_insert`,`is_edit`,`is_list`,`is_query`,`query_type`,`html_type`,`dict_type`,`sort`,`create_by`,`create_time`)
VALUES
(1,1,'id','主键','bigint','Long','id','1','1','1','0','0','0','0','EQ','input','',1,'system',NOW()),
(2,1,'robot_name','机器人名称','varchar(100)','String','robotName','0','0','1','1','1','1','1','LIKE','input','',2,'system',NOW());

SET FOREIGN_KEY_CHECKS = 1;
