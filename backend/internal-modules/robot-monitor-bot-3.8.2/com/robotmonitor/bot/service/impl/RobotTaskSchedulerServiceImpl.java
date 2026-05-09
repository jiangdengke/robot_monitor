/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.core.domain.ai.PushMessage
 *  com.robotmonitor.common.core.domain.config.ConfigRobot
 *  com.robotmonitor.common.core.domain.config.ConfigTask
 *  com.robotmonitor.common.core.domain.robot.RobotHttpCmd
 *  com.robotmonitor.common.core.domain.robot.RobotTask
 *  com.robotmonitor.common.core.domain.robot.RobotTaskCmd
 *  com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd
 *  com.robotmonitor.common.core.redis.RedisCache
 *  com.robotmonitor.common.utils.DateUtils
 *  com.robotmonitor.common.utils.JsonUtils
 *  com.robotmonitor.config.service.IConfigRobotService
 *  com.robotmonitor.config.service.IConfigTaskService
 *  org.apache.logging.log4j.util.Strings
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.stereotype.Service
 *  org.springframework.util.CollectionUtils
 */
package com.robotmonitor.bot.service.impl;

import com.robotmonitor.bot.domain.GuideFinishResponse;
import com.robotmonitor.bot.mapper.UpdateNoticeResultMapper;
import com.robotmonitor.bot.service.IRobotTaskService;
import com.robotmonitor.bot.service.RobotCmdService;
import com.robotmonitor.bot.service.RobotTaskSchedulerService;
import com.robotmonitor.common.core.domain.ai.PushMessage;
import com.robotmonitor.common.core.domain.config.ConfigRobot;
import com.robotmonitor.common.core.domain.config.ConfigTask;
import com.robotmonitor.common.core.domain.robot.RobotHttpCmd;
import com.robotmonitor.common.core.domain.robot.RobotTask;
import com.robotmonitor.common.core.domain.robot.RobotTaskCmd;
import com.robotmonitor.common.core.domain.robot.RobotVideoStreamCmd;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.DateUtils;
import com.robotmonitor.common.utils.JsonUtils;
import com.robotmonitor.config.service.IConfigRobotService;
import com.robotmonitor.config.service.IConfigTaskService;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RobotTaskSchedulerServiceImpl
implements RobotTaskSchedulerService {
    private static final Logger log = LoggerFactory.getLogger(RobotTaskSchedulerServiceImpl.class);
    @Autowired
    private IRobotTaskService robotTaskService;
    @Autowired
    private RobotCmdService robotCmdService;
    @Autowired
    private IConfigRobotService configRobotService;
    @Autowired
    private IConfigTaskService configTaskService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UpdateNoticeResultMapper updateNoticeResultMapper;
    private final ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);
    private final ConcurrentMap<String, ScheduledFuture<?>> delayedGoHomeMap = new ConcurrentHashMap();

    @Override
    public void stop(String robotId, Long taskId, String message) {
        RobotTask robotTask = this.getCurrentTask(robotId);
        if (null == robotTask || !robotTask.getId().equals(taskId)) {
            log.error("\u4efb\u52a1\u4e0d\u5b58\u5728");
            return;
        }
        if (-3L == robotTask.getTaskId()) {
            this.setHomeStatus(robotId, true);
        }
        if (-2L == robotTask.getTaskId()) {
            GuideFinishResponse messageBody = new GuideFinishResponse(true, taskId, message);
            PushMessage pushMessage = new PushMessage(robotId, "guide", JsonUtils.obj2String((Object)messageBody));
            this.redisTemplate.convertAndSend("redis.websocket.push", (Object)JsonUtils.obj2String((Object)pushMessage));
        }
        if (-4L == robotTask.getTaskId()) {
            this.updateNoticeResultMapper.updatePassengerWarningStatus(taskId, "1");
        }
        this.endTask(robotTask, message);
        this.removeCurrentTask(robotTask.getRobotId());
        Runnable nextTaskRunnable = () -> this.startNow(robotId);
        if (this.isMoveTask(robotTask.getTaskId())) {
            this.scheduledExecutor.schedule(() -> {
                nextTaskRunnable.run();
                this.scheduleGoHomeIfIdle(robotId);
            }, 5L, TimeUnit.SECONDS);
        } else {
            this.taskExecutor.submit(() -> {
                nextTaskRunnable.run();
                this.scheduleGoHomeIfIdle(robotId);
            });
        }
    }

    @Override
    public void scheduleGoHomeIfIdle(String robotId) {
        if (this.delayedGoHomeMap.containsKey(robotId)) {
            return;
        }
        ScheduledFuture<?> future = this.scheduledExecutor.schedule(() -> {
            this.delayedGoHomeMap.remove(robotId);
            if (this.isAllQueueEmpty(robotId) && this.getCurrentTask(robotId) == null) {
                log.info("\u673a\u5668\u4eba {} \u6240\u6709\u4efb\u52a1\u5b8c\u6210\uff0c\u6267\u884c\u56de\u5bb6\u4efb\u52a1", (Object)robotId);
                this.geHome(robotId);
            } else {
                log.info("\u673a\u5668\u4eba {} \u5ef6\u8fdf\u56de\u5bb6\u4efb\u52a1\u53d6\u6d88\uff0c\u56e0\u4e3a\u6709\u65b0\u4efb\u52a1\u52a0\u5165", (Object)robotId);
            }
        }, 5L, TimeUnit.SECONDS);
        this.delayedGoHomeMap.put(robotId, future);
    }

    private void endTask(RobotTask robotTask, String message) {
        robotTask.setReturnInfo(message);
        robotTask.setTaskStatus("9");
        if (null == robotTask.getStartTime()) {
            robotTask.setStartTime(DateUtils.getNowDate());
        }
        robotTask.setEndTime(DateUtils.getNowDate());
        this.robotTaskService.updateRobotTask(robotTask);
    }

    private List<RobotTask> findTaskList(String robotId) {
        return this.robotTaskService.findTaskListByRobotId(robotId);
    }

    private void runTask(RobotTask robotTask) {
        this.setCurrentTask(robotTask);
        robotTask.setStartTime(DateUtils.getNowDate());
        try {
            this.runCmd(robotTask.getCmd(), robotTask.getTaskSubtype());
            if ("0".equals(robotTask.getTaskType())) {
                robotTask.setTaskStatus("9");
                robotTask.setEndTime(DateUtils.getNowDate());
                robotTask.setReturnInfo("\u6267\u884c\u6210\u529f");
                this.robotTaskService.updateRobotTask(robotTask);
                this.removeCurrentTask(robotTask.getRobotId());
            } else {
                robotTask.setTaskStatus("2");
                this.robotTaskService.updateRobotTask(robotTask);
            }
            if (this.isMoveTask(robotTask.getTaskId())) {
                this.setHomeStatus(robotTask.getRobotId(), false);
            }
        }
        catch (Exception e) {
            log.error("\u673a\u5668\u4eba\u6307\u4ee4\u6267\u884c\u9519\u8bef", (Throwable)e);
            robotTask.setTaskStatus("4");
            robotTask.setEndTime(DateUtils.getNowDate());
            robotTask.setReturnInfo("\u6267\u884c\u5931\u8d25");
            this.robotTaskService.updateRobotTask(robotTask);
            this.removeCurrentTask(robotTask.getRobotId());
        }
    }

    private void runCmd(String cmd, String taskSubType) {
        if (Strings.isNotBlank((String)taskSubType) && Strings.isNotBlank((String)cmd)) {
            switch (taskSubType) {
                case "0": {
                    this.robotCmdService.sendCmd((RobotTaskCmd)JsonUtils.string2Obj((String)cmd, RobotTaskCmd.class));
                    break;
                }
                case "1": {
                    this.robotCmdService.sendVoiceCmd((RobotTaskCmd)JsonUtils.string2Obj((String)cmd, RobotTaskCmd.class));
                    break;
                }
                case "2": {
                    this.robotCmdService.sendVideoStreamCmd((RobotVideoStreamCmd)JsonUtils.string2Obj((String)cmd, RobotVideoStreamCmd.class));
                    break;
                }
                case "3": {
                    this.robotCmdService.sendHttpCmd((RobotHttpCmd)JsonUtils.string2Obj((String)cmd, RobotHttpCmd.class));
                    break;
                }
                default: {
                    log.error("\u4efb\u52a1\u7c7b\u578b\u9519\u8bef");
                }
            }
        }
    }

    @Override
    public void addQueue(RobotTask robotTask) {
        boolean isInUse;
        robotTask.setTaskStatus("1");
        this.robotTaskService.insertRobotTask(robotTask);
        if ("0".equals(robotTask.getTaskSubtype()) && Strings.isNotBlank((String)robotTask.getCmd())) {
            RobotTaskCmd robotCmd = (RobotTaskCmd)JsonUtils.string2Obj((String)robotTask.getCmd(), RobotTaskCmd.class);
            robotCmd.setRobot_task_id(robotTask.getId());
            robotTask.setCmd(JsonUtils.obj2String((Object)robotCmd));
            this.robotTaskService.updateRobotTask(robotTask);
        }
        if (!(isInUse = this.getRobotInUseFlag(robotTask.getRobotId())) && "1".equals(robotTask.getTaskMode())) {
            this.endTask(robotTask, "\u9519\u8bef\u7684\u4efb\u52a1\uff0c\u524d\u7aef\u672a\u4f7f\u7528");
            return;
        }
        String queueKey = this.getQueueKey(robotTask.getRobotId(), robotTask.getTaskMode());
        this.redisCache.addQueue(queueKey, (Object)robotTask);
        this.startNow(robotTask.getRobotId());
    }

    @Override
    public void clearQueue(String robotId, String message) {
        List<RobotTask> taskList = this.findTaskList(robotId);
        if (!CollectionUtils.isEmpty(taskList)) {
            for (RobotTask robotTask : taskList) {
                robotTask.setTaskStatus("4");
                robotTask.setReturnInfo(message);
                Date now = DateUtils.getNowDate();
                if (null == robotTask.getStartTime()) {
                    robotTask.setStartTime(now);
                }
                robotTask.setEndTime(now);
                this.robotTaskService.updateRobotTask(robotTask);
            }
        }
        this.redisCache.clearQueue("robot_current_back_task_queue:robot_id_" + robotId);
        this.redisCache.clearQueue("robot_current_front_task_queue:robot_id_" + robotId);
        this.redisCache.deleteObject("robot_current_task:robot_id_" + robotId);
    }

    @Override
    public void startNow(String robotId) {
        boolean locked = this.getLock(robotId);
        if (!locked) {
            log.info("\u673a\u5668\u4eba {} \u5df2\u7ecf\u6709\u4efb\u52a1\u5728\u6267\u884c", (Object)robotId);
            return;
        }
        this.taskExecutor.submit(() -> {
            try {
                this.executeTaskLoop(robotId);
            }
            catch (Exception e) {
                log.error("\u6267\u884c\u673a\u5668\u4eba {} \u4efb\u52a1\u5f02\u5e38", (Object)robotId, (Object)e);
            }
            finally {
                this.releaseLock(robotId);
                this.check(robotId);
            }
        });
    }

    private void check(String robotId) {
        log.info("\u673a\u5668\u4eba {} \u4efb\u52a1\u6821\u9a8c", (Object)robotId);
        if (this.hasNextTask(robotId)) {
            log.info("\u673a\u5668\u4eba {} \u4efb\u52a1\u6821\u9a8c\uff0c\u6709\u9057\u6f0f\u7684\u4efb\u52a1", (Object)robotId);
            this.startNow(robotId);
        } else {
            log.info("\u673a\u5668\u4eba {} \u4efb\u52a1\u6821\u9a8c\uff0c\u65e0\u9057\u6f0f\u7684\u4efb\u52a1", (Object)robotId);
        }
    }

    private void executeTaskLoop(String robotId) {
        while (true) {
            RobotTask task;
            if ((task = this.getNextTask(robotId)) == null) break;
            this.runTask(task);
        }
        log.info("\u673a\u5668\u4eba {} \u6ca1\u6709\u9700\u8981\u6267\u884c\u4efb\u52a1", (Object)robotId);
        log.info("\u673a\u5668\u4eba {} \u6ca1\u6709\u9700\u8981\u6267\u884c\u7684\u4efb\u52a1", (Object)robotId);
    }

    private RobotTask getNextTask(String robotId) {
        boolean isInUse = this.getRobotInUseFlag(robotId);
        if (isInUse) {
            return (RobotTask)this.redisCache.getQueue("robot_current_front_task_queue:robot_id_" + robotId);
        }
        List frontTasks = this.redisCache.getAllQueue("robot_current_front_task_queue:robot_id_" + robotId);
        if (frontTasks != null) {
            for (RobotTask t : frontTasks) {
                this.endTask(t, "\u524d\u53f0\u672a\u4f7f\u7528\uff0c\u4efb\u52a1\u4e22\u5f03");
            }
        }
        this.redisCache.clearQueue("robot_current_front_task_queue:robot_id_" + robotId);
        return (RobotTask)this.redisCache.getQueue("robot_current_back_task_queue:robot_id_" + robotId);
    }

    private boolean hasNextTask(String robotId) {
        boolean isInUse = this.getRobotInUseFlag(robotId);
        if (isInUse) {
            List frontTasks = this.redisCache.getAllQueue("robot_current_front_task_queue:robot_id_" + robotId);
            return !CollectionUtils.isEmpty((Collection)frontTasks);
        }
        List backTasks = this.redisCache.getAllQueue("robot_current_front_task_queue:robot_id_" + robotId);
        return !CollectionUtils.isEmpty((Collection)backTasks);
    }

    @Override
    public void setRobotInUseFlag(String robotId, String flag) {
        this.redisCache.setCacheObject("robot_in_use_flag:robot_id_" + robotId, (Object)flag);
    }

    @Override
    public boolean getRobotInUseFlag(String robotId) {
        return "0".equals(this.redisCache.getCacheObject("robot_in_use_flag:robot_id_" + robotId));
    }

    @Override
    public void geHome(String robotId) {
        boolean isAtHome = this.isAtHome(robotId);
        boolean isInUse = this.getRobotInUseFlag(robotId);
        RobotTask currentTask = this.getCurrentTask(robotId);
        if (!isAtHome && !isInUse && null == this.getCurrentTask(robotId)) {
            ConfigRobot configRobot = this.configRobotService.getConfigRobotByRobotId(robotId);
            String homePoint = configRobot.getOriCoordinate();
            ConfigTask configTask = this.configTaskService.findDefaultTask(Long.valueOf(-3L));
            RobotTaskCmd robotTaskCmd = new RobotTaskCmd(Long.parseLong(robotId), configTask.getCommand().longValue(), configTask.getExecuteType(), configTask.getPriority(), homePoint, Boolean.valueOf(false), null, null, null, null);
            this.addQueue(new RobotTask(robotId, configTask, robotTaskCmd));
        } else {
            log.info("\u673a\u5668\u4eba({})\u5f53\u524d\u5728\u4f7f\u7528\u72b6\u6001\uff1a{}\uff0c\u662f\u5426\u5728\u539f\u70b9\uff1a{}\uff0c\u5f53\u524d\u4efb\u52a1\uff1a{}", new Object[]{robotId, isInUse, isAtHome, currentTask});
        }
    }

    @Override
    public void checkCurrentTask(String robotId) {
        RobotTask currentTask = this.getCurrentTask(robotId);
        if (null != currentTask) {
            if (Strings.isNotBlank((String)currentTask.getReturnInfo())) {
                this.stop(robotId, currentTask.getId(), "\u673a\u5668\u4eba\u4e0a\u4efb\u52a1\u7ed3\u675f\uff0c\u540c\u6b65\u5f53\u524d\u4efb\u52a1\u72b6\u6001");
            } else {
                currentTask.setReturnInfo("\u540c\u6b65\u673a\u5668\u4eba\u4efb\u52a1\u72b6\u6001\u65f6\uff0c\u673a\u5668\u4eba\u4efb\u52a1\u4e3a\u7a7a\uff0c\u4f46\u5f53\u524d\u72b6\u6001\u4e0d\u4e3a\u7a7a");
                this.setCurrentTask(currentTask);
            }
        }
    }

    public RobotTask getTaskFromQueue(String robotId) {
        boolean isInUse = this.getRobotInUseFlag(robotId);
        if (isInUse) {
            return (RobotTask)this.redisCache.getQueue("robot_current_front_task_queue:robot_id_" + robotId);
        }
        return (RobotTask)this.redisCache.getQueue("robot_current_back_task_queue:robot_id_" + robotId);
    }

    private boolean isAllQueueEmpty(String robotId) {
        boolean frontEmpty = CollectionUtils.isEmpty((Collection)this.redisCache.getAllQueue("robot_current_front_task_queue:robot_id_" + robotId));
        boolean backEmpty = CollectionUtils.isEmpty((Collection)this.redisCache.getAllQueue("robot_current_back_task_queue:robot_id_" + robotId));
        return frontEmpty && backEmpty;
    }

    private String getQueueKey(String robotId, String mode) {
        if ("1".equals(mode)) {
            return "robot_current_front_task_queue:robot_id_" + robotId;
        }
        return "robot_current_back_task_queue:robot_id_" + robotId;
    }

    private void setCurrentTask(RobotTask robotTask) {
        this.redisCache.setCacheObject("robot_current_task:robot_id_" + robotTask.getRobotId(), (Object)robotTask);
    }

    private RobotTask getCurrentTask(String robotId) {
        return (RobotTask)this.redisCache.getCacheObject("robot_current_task:robot_id_" + robotId);
    }

    private void removeCurrentTask(String robotId) {
        this.redisCache.deleteObject("robot_current_task:robot_id_" + robotId);
    }

    private boolean getLock(String robotId) {
        return this.redisCache.setIfAbsent((Object)("robot_current_task_lock:robot_id_" + robotId), (Object)robotId, 3600L);
    }

    private boolean isAtHome(String robotId) {
        return Boolean.TRUE.equals(this.redisCache.getCacheObject("robot_is_at_home_flag:" + robotId));
    }

    @Override
    public void setHomeStatus(String robotId, boolean status) {
        this.redisCache.setCacheObject("robot_is_at_home_flag:" + robotId, (Object)status);
    }

    private void releaseLock(String robotId) {
        String lockKey = "robot_current_task_lock:robot_id_" + robotId;
        String currentValue = (String)this.redisCache.getCacheObject(lockKey);
        if (robotId.equals(currentValue)) {
            this.redisCache.deleteObject(lockKey);
        }
    }

    private boolean isMoveTask(long taskId) {
        return -2L == taskId || -4L == taskId || taskId > 0L;
    }
}
