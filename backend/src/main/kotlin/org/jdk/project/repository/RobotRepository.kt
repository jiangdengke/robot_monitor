package org.jdk.project.repository

import org.jooq.DSLContext
import org.jooq.generated.project.Tables.POINT
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.generated.project.Tables.SITE
import org.springframework.stereotype.Repository

@Repository
class RobotRepository(
    private val dsl: DSLContext,
) {
    fun findRobotSiteIdById(id: Long): Long? =
        dsl
            .select(ROBOT.SITE_ID)
            .from(ROBOT)
            .where(ROBOT.ID.eq(id))
            .fetchOne(ROBOT.SITE_ID)

    fun hasRobotsBySiteId(siteId: Long): Boolean = dsl.fetchExists(ROBOT, ROBOT.SITE_ID.eq(siteId))

    fun hasRobotsByPointId(pointId: Long): Boolean = dsl.fetchExists(ROBOT, ROBOT.POINT_ID.eq(pointId))

    fun findAllRobots(): List<RobotRow> =
        dsl
            .select(
                ROBOT.ID,
                ROBOT.ROBOT_CODE,
                ROBOT.NAME,
                ROBOT.MAC,
                ROBOT.IP_ADDRESS,
                ROBOT.SITE_ID,
                SITE.CODE,
                SITE.NAME,
                ROBOT.POINT_ID,
                POINT.NAME,
                ROBOT.ROBOT_TYPE,
                ROBOT.BATTERY_PERCENT,
                ROBOT.CHARGING_STATE,
                ROBOT.WORKING_STATE,
                ROBOT.STANDBY_STATE,
                ROBOT.POSITIONING_STATE,
                ROBOT.ENABLED,
                ROBOT.INITIAL_COORDINATE,
                ROBOT.ADMIN_MODE,
                ROBOT.REMARK,
            ).from(ROBOT)
            .join(SITE)
            .on(ROBOT.SITE_ID.eq(SITE.ID))
            .leftJoin(POINT)
            .on(ROBOT.POINT_ID.eq(POINT.ID))
            .orderBy(ROBOT.ID.asc())
            .fetch { record ->
                RobotRow(
                    id = record.get(ROBOT.ID),
                    robotCode = record.get(ROBOT.ROBOT_CODE),
                    robotName = record.get(ROBOT.NAME),
                    mac = record.get(ROBOT.MAC),
                    ipAddress = record.get(ROBOT.IP_ADDRESS),
                    siteId = record.get(ROBOT.SITE_ID),
                    siteCode = record.get(SITE.CODE),
                    siteName = record.get(SITE.NAME),
                    pointId = record.get(ROBOT.POINT_ID),
                    pointName = record.get(POINT.NAME),
                    robotType = record.get(ROBOT.ROBOT_TYPE),
                    batteryPercent = record.get(ROBOT.BATTERY_PERCENT),
                    chargingState = record.get(ROBOT.CHARGING_STATE),
                    workingState = record.get(ROBOT.WORKING_STATE),
                    standbyState = record.get(ROBOT.STANDBY_STATE),
                    positioningState = record.get(ROBOT.POSITIONING_STATE),
                    enabled = record.get(ROBOT.ENABLED),
                    initialCoordinate = record.get(ROBOT.INITIAL_COORDINATE),
                    adminMode = record.get(ROBOT.ADMIN_MODE),
                    remark = record.get(ROBOT.REMARK),
                )
            }

    fun insertRobot(robot: RobotWriteData): Long? =
        dsl
            .insertInto(ROBOT)
            .set(ROBOT.SITE_ID, robot.siteId)
            .set(ROBOT.POINT_ID, robot.pointId)
            .set(ROBOT.ROBOT_CODE, robot.robotCode)
            .set(ROBOT.NAME, robot.robotName)
            .set(ROBOT.MAC, robot.mac)
            .set(ROBOT.IP_ADDRESS, robot.ipAddress)
            .set(ROBOT.ROBOT_TYPE, robot.robotType)
            .set(ROBOT.BATTERY_PERCENT, robot.batteryPercent)
            .set(ROBOT.CHARGING_STATE, robot.chargingState)
            .set(ROBOT.WORKING_STATE, robot.workingState)
            .set(ROBOT.STANDBY_STATE, robot.standbyState)
            .set(ROBOT.POSITIONING_STATE, robot.positioningState)
            .set(ROBOT.ENABLED, robot.enabled)
            .set(ROBOT.INITIAL_COORDINATE, robot.initialCoordinate)
            .set(ROBOT.ADMIN_MODE, robot.adminMode)
            .set(ROBOT.ERROR_CODE, robot.errorCode)
            .set(ROBOT.ERROR_MESSAGE, robot.errorMessage)
            .set(ROBOT.REMARK, robot.remark)
            .returningResult(ROBOT.ID)
            .fetchOne(ROBOT.ID)

    fun updateRobot(
        id: Long,
        robot: RobotWriteData,
    ): Int =
        dsl
            .update(ROBOT)
            .set(ROBOT.SITE_ID, robot.siteId)
            .set(ROBOT.POINT_ID, robot.pointId)
            .set(ROBOT.ROBOT_CODE, robot.robotCode)
            .set(ROBOT.NAME, robot.robotName)
            .set(ROBOT.MAC, robot.mac)
            .set(ROBOT.IP_ADDRESS, robot.ipAddress)
            .set(ROBOT.ROBOT_TYPE, robot.robotType)
            .set(ROBOT.BATTERY_PERCENT, robot.batteryPercent)
            .set(ROBOT.CHARGING_STATE, robot.chargingState)
            .set(ROBOT.WORKING_STATE, robot.workingState)
            .set(ROBOT.STANDBY_STATE, robot.standbyState)
            .set(ROBOT.POSITIONING_STATE, robot.positioningState)
            .set(ROBOT.ENABLED, robot.enabled)
            .set(ROBOT.INITIAL_COORDINATE, robot.initialCoordinate)
            .set(ROBOT.ADMIN_MODE, robot.adminMode)
            .set(ROBOT.ERROR_CODE, robot.errorCode)
            .set(ROBOT.ERROR_MESSAGE, robot.errorMessage)
            .set(ROBOT.REMARK, robot.remark)
            .where(ROBOT.ID.eq(id))
            .execute()

    fun deleteRobotById(id: Long): Int = dsl.deleteFrom(ROBOT).where(ROBOT.ID.eq(id)).execute()
}

data class RobotRow(
    val id: Long?,
    val robotCode: String?,
    val robotName: String?,
    val mac: String?,
    val ipAddress: String?,
    val siteId: Long?,
    val siteCode: String?,
    val siteName: String?,
    val pointId: Long?,
    val pointName: String?,
    val robotType: String?,
    val batteryPercent: Int?,
    val chargingState: String?,
    val workingState: String?,
    val standbyState: String?,
    val positioningState: String?,
    val enabled: Boolean?,
    val initialCoordinate: String?,
    val adminMode: Boolean?,
    val remark: String?,
)

data class RobotWriteData(
    val siteId: Long,
    val pointId: Long?,
    val robotCode: String?,
    val robotName: String?,
    val mac: String,
    val ipAddress: String,
    val robotType: String,
    val batteryPercent: Int,
    val chargingState: String,
    val workingState: String,
    val standbyState: String,
    val positioningState: String,
    val enabled: Boolean,
    val initialCoordinate: String,
    val adminMode: Boolean,
    val errorCode: String,
    val errorMessage: String,
    val remark: String,
)
