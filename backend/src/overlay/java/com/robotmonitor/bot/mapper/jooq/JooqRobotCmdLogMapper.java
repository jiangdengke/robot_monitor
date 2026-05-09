package com.robotmonitor.bot.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.ROBOT_CMD_LOG;

import com.robotmonitor.bot.mapper.RobotCmdLogMapper;
import com.robotmonitor.common.core.domain.robot.RobotCmdLog;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqRobotCmdLogMapper extends GenericJooqCrudSupport<RobotCmdLog> implements RobotCmdLogMapper {
    public JooqRobotCmdLogMapper(DSLContext dsl) {
        super(dsl, ROBOT_CMD_LOG, ROBOT_CMD_LOG.ID, RobotCmdLog.class);
    }

    @Override
    public RobotCmdLog selectRobotCmdLogById(Long id) {
        return selectById(id);
    }

    @Override
    public List<RobotCmdLog> selectRobotCmdLogList(RobotCmdLog query) {
        return selectList(query);
    }

    @Override
    public int insertRobotCmdLog(RobotCmdLog log) {
        return insert(log);
    }

    @Override
    public int updateRobotCmdLog(RobotCmdLog log) {
        return update(log);
    }

    @Override
    public int deleteRobotCmdLogById(Long id) {
        return deleteById(id);
    }

    @Override
    public int deleteRobotCmdLogByIds(Long[] ids) {
        return deleteByIds(ids);
    }
}
