package org.jdk.project.repository;

import static org.jooq.generated.project.tables.User.USER;

import org.jooq.Configuration;
import org.jooq.generated.project.tables.daos.UserDao;
import org.jooq.generated.project.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** 用户仓储：基于 jOOQ 的数据访问（精简版，仅保留必要方法）。 */
@Repository
public class UserRepository extends UserDao {

  @Autowired
  public UserRepository(Configuration configuration) {
    super(configuration);
  }

  /** 根据用户名删除用户。 */
  @Transactional
  public void deleteUserBy(String username) {
    ctx().delete(USER).where(USER.USERNAME.eq(username)).execute();
  }

  public User fetchEnabledUserByUsername(String username) {
    return ctx()
        .selectFrom(USER)
        .where(USER.USERNAME.eq(username))
        .and(USER.ENABLE.eq(true))
        .fetchOneInto(User.class);
  }
}
