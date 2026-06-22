package org.jdk.project.repository

import org.jooq.Configuration
import org.jooq.generated.project.tables.User.USER
import org.jooq.generated.project.tables.daos.UserDao
import org.jooq.generated.project.tables.pojos.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserRepository(
    configuration: Configuration,
) : UserDao(configuration) {
    @Transactional
    fun deleteUserBy(username: String?) {
        ctx().delete(USER).where(USER.USERNAME.eq(username)).execute()
    }

    fun fetchEnabledUserByUsername(username: String?): User? =
        ctx()
            .selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .and(USER.ENABLE.eq(true))
            .fetchOneInto(User::class.java)
}
