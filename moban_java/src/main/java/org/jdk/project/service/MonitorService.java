package org.jdk.project.service;

import static org.jooq.generated.project.Tables.LOGIN_LOG;
import static org.jooq.generated.project.Tables.OPERATION_LOG;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.LoginLog;
import org.jooq.generated.project.tables.pojos.OperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonitorService {

  private final DSLContext dsl;

  public ListResponse<LoginLog> listLoginLogs() {
    List<LoginLog> rows =
        dsl.selectFrom(LOGIN_LOG).orderBy(LOGIN_LOG.ID.desc()).fetchInto(LoginLog.class);
    return ListResponse.of(rows.size(), rows);
  }

  public ListResponse<OperationLog> listOperationLogs() {
    List<OperationLog> rows =
        dsl.selectFrom(OPERATION_LOG).orderBy(OPERATION_LOG.ID.desc()).fetchInto(OperationLog.class);
    return ListResponse.of(rows.size(), rows);
  }

  @Transactional
  public void clearLoginLogs() {
    dsl.deleteFrom(LOGIN_LOG).execute();
  }

  @Transactional
  public void clearOperationLogs() {
    dsl.deleteFrom(OPERATION_LOG).execute();
  }
}
