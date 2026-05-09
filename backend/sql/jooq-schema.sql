-- jOOQ schema source
-- generated from init.sql with only DDL retained for DDLDatabase/H2 parsing

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
DROP TABLE IF EXISTS `flight_complaint`;

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
);

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
);

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
);

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
);

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
);

CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
);

CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`)
);

CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
);

CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`)
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

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
);

CREATE TABLE `config_area_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_id` bigint NOT NULL,
  `robot_id` varchar(64) DEFAULT '',
  `room_code` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `config_device_region` (
  `device_id` bigint NOT NULL,
  `region_id` bigint NOT NULL,
  `region_name` varchar(100) DEFAULT '',
  `coordinate` text,
  `img_id` bigint DEFAULT NULL,
  `img` longtext,
  `remark` varchar(500) DEFAULT '',
  PRIMARY KEY (`device_id`,`region_id`)
);

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
);

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
  `admin_mode` varchar(32) DEFAULT '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `config_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `robot_id` bigint DEFAULT NULL,
  `command` bigint DEFAULT NULL,
  `command_cn` varchar(255) DEFAULT '',
  `region` varchar(255) DEFAULT '',
  `priority` varchar(32) DEFAULT '',
  `enable` char(1) DEFAULT '1',
  `execute_type` varchar(32) DEFAULT 'immediately',
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
  `task_type` varchar(32) DEFAULT '0',
  `task_subtype` varchar(32) DEFAULT '0',
  `task_mode` varchar(32) DEFAULT '0',
  `direct_execution` varchar(32) DEFAULT '0',
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `flight_complaint` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `room_code` varchar(64) DEFAULT '',
  `card_service` varchar(64) DEFAULT '',
  `card_no` varchar(64) DEFAULT '',
  `complaint_content` longtext,
  `complaint_feedback` longtext,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

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
);

CREATE TABLE `flight_kafka_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airport_code` varchar(16) DEFAULT '',
  `sub_type` varchar(64) DEFAULT '',
  `msg` longtext,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

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
);

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
);

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
);

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
  `cts` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `passenger_out_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `passenger_id` bigint DEFAULT NULL,
  `user_name` varchar(100) DEFAULT '',
  `room_code` varchar(64) DEFAULT NULL,
  `flight_no` varchar(32) DEFAULT '',
  `flight_date` date DEFAULT NULL,
  `reid` varchar(64) DEFAULT '',
  `pid` varchar(64) DEFAULT '',
  `recognition_type` varchar(32) DEFAULT '',
  `ori_image_url` varchar(255) DEFAULT '',
  `register_image_url` varchar(255) DEFAULT '',
  `out_time` datetime DEFAULT NULL,
  `cts` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
);

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
);

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
);

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
);

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
);

CREATE TABLE `food_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `food_name` varchar(100) DEFAULT '',
  `food_id` bigint DEFAULT NULL,
  `num` int DEFAULT 1,
  `price` decimal(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `guide_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `region_id` bigint DEFAULT NULL,
  `coordinate` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `robot_cmd_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `cmd` longtext,
  `cmd_type` varchar(32) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `insp_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `robot_id` varchar(64) DEFAULT '',
  `status` varchar(32) DEFAULT '',
  `task_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `update_notice_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notice_id` bigint DEFAULT NULL,
  `status` varchar(32) DEFAULT '',
  `message` varchar(500) DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

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
);

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
);

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
);

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
);
