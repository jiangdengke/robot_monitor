/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.core.domain.robot;

import com.robotmonitor.common.core.domain.config.ConfigTask;
import java.util.List;

public class RobotCmd {
    private long robot_id;
    private long task_id;
    private String execution_time;
    private String task_priority;
    private String location_information;
    private Boolean is_return;
    private String supplementary_information;
    private List<String> points;
    private String voice_url;
    private Long robot_task_id;
    private String time_sleep;

    public RobotCmd() {
    }

    public RobotCmd(RobotCmd robotCmd) {
        this.robot_id = robotCmd.getRobot_id();
        this.task_id = robotCmd.getTask_id();
        this.execution_time = robotCmd.getExecution_time();
        this.task_priority = robotCmd.getTask_priority();
        this.location_information = robotCmd.getLocation_information();
        this.is_return = robotCmd.isIs_return();
        this.supplementary_information = robotCmd.getSupplementary_information();
        this.points = robotCmd.getPoints();
        this.voice_url = robotCmd.getVoice_url();
        this.robot_task_id = robotCmd.getRobot_task_id();
        this.time_sleep = robotCmd.getTime_sleep();
    }

    public RobotCmd(long robot_id, long task_id, String execution_time, String task_priority, String location_information, Boolean is_return, String supplementary_information) {
        this.robot_id = robot_id;
        this.task_id = task_id;
        this.execution_time = execution_time;
        this.task_priority = task_priority;
        this.location_information = location_information;
        this.is_return = is_return;
        this.supplementary_information = supplementary_information;
    }

    public RobotCmd(ConfigTask configTask, long robot_id) {
        this.robot_id = robot_id;
        this.task_id = configTask.getCommand();
        this.execution_time = configTask.getExecuteType();
        this.task_priority = configTask.getPriority();
        this.location_information = null;
        this.is_return = "1".equals(configTask.getIsReturn());
        this.supplementary_information = null;
    }

    public long getRobot_id() {
        return this.robot_id;
    }

    public void setRobot_id(long robot_id) {
        this.robot_id = robot_id;
    }

    public long getTask_id() {
        return this.task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getExecution_time() {
        return this.execution_time;
    }

    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    public String getTask_priority() {
        return this.task_priority;
    }

    public void setTask_priority(String task_priority) {
        this.task_priority = task_priority;
    }

    public String getLocation_information() {
        return this.location_information;
    }

    public void setLocation_information(String location_information) {
        this.location_information = location_information;
    }

    public Boolean isIs_return() {
        return this.is_return;
    }

    public void setIs_return(Boolean is_return) {
        this.is_return = is_return;
    }

    public String getSupplementary_information() {
        return this.supplementary_information;
    }

    public void setSupplementary_information(String supplementary_information) {
        this.supplementary_information = supplementary_information;
    }

    public List<String> getPoints() {
        return this.points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public String getVoice_url() {
        return this.voice_url;
    }

    public void setVoice_url(String voice_url) {
        this.voice_url = voice_url;
    }

    public Long getRobot_task_id() {
        return this.robot_task_id;
    }

    public void setRobot_task_id(Long robot_task_id) {
        this.robot_task_id = robot_task_id;
    }

    public String getTime_sleep() {
        return this.time_sleep;
    }

    public void setTime_sleep(String time_sleep) {
        this.time_sleep = time_sleep;
    }
}
