package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.USER
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class UserRepository(
    private val dsl: DSLContext,
) {
    fun findAllUsers(): List<UserRow> =
        dsl
            .selectFrom(USER)
            .orderBy(USER.ID.asc())
            .fetch(::mapUser)

    fun findUserById(id: Long): UserRow? =
        dsl
            .selectFrom(USER)
            .where(USER.ID.eq(id))
            .fetchOne(::mapUser)

    fun findUserByUsername(username: String?): UserRow? =
        dsl
            .selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOne(::mapUser)

    fun findEnabledUserByUsername(username: String?): UserRow? =
        dsl
            .selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .and(USER.ENABLE.eq(true))
            .fetchOne(::mapUser)

    fun insertUser(user: UserWriteData): Long? =
        dsl
            .insertInto(USER)
            .set(USER.USERNAME, user.username)
            .set(USER.NICKNAME, user.nickname)
            .set(USER.EMAIL, user.email)
            .set(USER.PHONE, user.phone)
            .set(USER.SEX, user.sex)
            .set(USER.AVATAR_URL, user.avatarUrl)
            .set(USER.PASSWORD, user.password)
            .set(USER.ENABLE, user.enable)
            .set(USER.REMARK, user.remark)
            .returningResult(USER.ID)
            .fetchOne(USER.ID)

    fun insertCredentials(
        username: String?,
        password: String?,
    ): Long? =
        dsl
            .insertInto(USER)
            .set(USER.USERNAME, username)
            .set(USER.PASSWORD, password)
            .set(USER.ENABLE, true)
            .returningResult(USER.ID)
            .fetchOne(USER.ID)

    fun updateUser(
        id: Long,
        user: UserWriteData,
    ): Int =
        dsl
            .update(USER)
            .set(USER.USERNAME, user.username)
            .set(USER.NICKNAME, user.nickname)
            .set(USER.EMAIL, user.email)
            .set(USER.PHONE, user.phone)
            .set(USER.SEX, user.sex)
            .set(USER.AVATAR_URL, user.avatarUrl)
            .set(USER.PASSWORD, user.password)
            .set(USER.ENABLE, user.enable)
            .set(USER.REMARK, user.remark)
            .where(USER.ID.eq(id))
            .execute()

    fun deleteUsersByIds(ids: Collection<Long>): Int =
        dsl
            .deleteFrom(USER)
            .where(USER.ID.`in`(ids))
            .execute()

    private fun mapUser(record: org.jooq.generated.project.tables.records.UserRecord): UserRow =
        UserRow(
            id = record.id,
            username = record.username,
            nickname = record.nickname,
            email = record.email,
            phone = record.phone,
            sex = record.sex,
            avatarUrl = record.avatarUrl,
            createTime = record.createTime,
            updateTime = record.updateTime,
            password = record.password,
            enable = record.enable,
            remark = record.remark,
        )
}

data class UserRow(
    val id: Long?,
    val username: String?,
    val nickname: String?,
    val email: String?,
    val phone: String?,
    val sex: String?,
    val avatarUrl: String?,
    val createTime: OffsetDateTime?,
    val updateTime: OffsetDateTime?,
    val password: String?,
    val enable: Boolean?,
    val remark: String?,
)

data class UserWriteData(
    val username: String?,
    val nickname: String?,
    val email: String?,
    val phone: String?,
    val sex: String?,
    val avatarUrl: String?,
    val password: String?,
    val enable: Boolean?,
    val remark: String?,
)
