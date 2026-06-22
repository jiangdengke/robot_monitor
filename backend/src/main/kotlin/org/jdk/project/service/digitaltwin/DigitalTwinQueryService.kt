package org.jdk.project.service.digitaltwin

import org.jdk.project.dto.digitaltwin.DigitalTwinInspectionDto
import org.jdk.project.dto.digitaltwin.DigitalTwinPassengerDto
import org.jdk.project.dto.digitaltwin.DigitalTwinRegionDto
import org.jdk.project.dto.digitaltwin.DigitalTwinRobotDto
import org.jdk.project.dto.digitaltwin.DigitalTwinWarningDto
import org.jdk.project.service.digitaltwin.DigitalTwinSupport.trimToNull
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.generated.project.Tables.AREA
import org.jooq.generated.project.Tables.FLIGHT_INFO
import org.jooq.generated.project.Tables.LOUNGE
import org.jooq.generated.project.Tables.PASSENGER
import org.jooq.generated.project.Tables.PASSENGER_WARNING_LOG
import org.jooq.generated.project.Tables.REGION
import org.jooq.generated.project.Tables.ROBOT
import org.jooq.impl.DSL
import org.springframework.stereotype.Service

@Service
class DigitalTwinQueryService(
    private val dsl: DSLContext,
    private val mapper: DigitalTwinMapper,
) {
    fun listRegions(roomCode: String?): List<DigitalTwinRegionDto> {
        val curCapacity: Field<Int> =
            DSL.selectCount()
                .from(PASSENGER)
                .where(PASSENGER.REGION_ID.eq(REGION.ID).and(PASSENGER.ACCESS_STATUS.eq("IN")))
                .asField("cur_capacity")
        var condition: Condition = REGION.ENABLED.eq(true)
        val normalizedRoomCode = trimToNull(roomCode)
        if (normalizedRoomCode != null) {
            condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode))
        }
        return dsl.select(
            REGION.ID,
            REGION.NAME,
            REGION.COORDINATE,
            REGION.MAX_CAPACITY,
            LOUNGE.CODE,
            DigitalTwinMapper.LOUNGE_NAME,
            DigitalTwinMapper.AREA_NAME,
            curCapacity,
        ).from(REGION)
            .leftJoin(LOUNGE)
            .on(REGION.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(AREA)
            .on(REGION.AREA_ID.eq(AREA.ID))
            .where(condition)
            .orderBy(REGION.ID.asc())
            .fetch { record -> mapper.toRegionDto(record, curCapacity) }
    }

    fun listRobots(roomCode: String?): List<DigitalTwinRobotDto> {
        var condition: Condition = ROBOT.ENABLED.eq(true)
        val normalizedRoomCode = trimToNull(roomCode)
        if (normalizedRoomCode != null) {
            condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode))
        }
        return dsl.select(
            ROBOT.ID,
            ROBOT.ROBOT_CODE,
            ROBOT.NAME,
            ROBOT.REGION_ID,
            ROBOT.INITIAL_COORDINATE,
            ROBOT.WORKING_STATE,
            ROBOT.BATTERY_PERCENT,
            LOUNGE.CODE,
            DigitalTwinMapper.REGION_NAME,
        ).from(ROBOT)
            .leftJoin(LOUNGE)
            .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(REGION)
            .on(ROBOT.REGION_ID.eq(REGION.ID))
            .where(condition)
            .orderBy(ROBOT.ID.asc())
            .fetch { record -> mapper.toRobotDto(record) }
    }

    fun listPassengers(roomCode: String?): List<DigitalTwinPassengerDto> {
        val warnings = listWarnings()
        var condition: Condition = PASSENGER.ACCESS_STATUS.eq("IN")
        val normalizedRoomCode = trimToNull(roomCode)
        if (normalizedRoomCode != null) {
            condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode))
        }
        return dsl.select(
            PASSENGER.ID,
            PASSENGER.PASSENGER_NAME,
            PASSENGER.CARD_NO,
            PASSENGER.FLIGHT_NO,
            PASSENGER.FLIGHT_ID,
            PASSENGER.REGION_ID,
            PASSENGER.COORDINATE,
            PASSENGER.MEMBER_LEVEL,
            PASSENGER.ACCESS_STATUS,
            LOUNGE.CODE,
            FLIGHT_INFO.ESTIMATED_TAKEOFF_AT,
        ).from(PASSENGER)
            .leftJoin(LOUNGE)
            .on(PASSENGER.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(FLIGHT_INFO)
            .on(PASSENGER.FLIGHT_ID.eq(FLIGHT_INFO.ID))
            .where(condition)
            .orderBy(PASSENGER.ID.asc())
            .fetch { record -> mapper.toPassengerDto(record, warnings) }
    }

    fun listInspections(roomCode: String?): List<DigitalTwinInspectionDto> {
        var condition: Condition = ROBOT.ERROR_MESSAGE.isNotNull.and(ROBOT.ERROR_MESSAGE.ne(""))
        val normalizedRoomCode = trimToNull(roomCode)
        if (normalizedRoomCode != null) {
            condition = condition.and(LOUNGE.CODE.eq(normalizedRoomCode))
        }
        return dsl.select(
            ROBOT.ID,
            ROBOT.ROBOT_CODE,
            ROBOT.ERROR_MESSAGE,
            ROBOT.INITIAL_COORDINATE,
            LOUNGE.CODE,
            DigitalTwinMapper.REGION_NAME,
        ).from(ROBOT)
            .leftJoin(LOUNGE)
            .on(ROBOT.LOUNGE_ID.eq(LOUNGE.ID))
            .leftJoin(REGION)
            .on(ROBOT.REGION_ID.eq(REGION.ID))
            .where(condition)
            .orderBy(ROBOT.ID.asc())
            .fetch { record -> mapper.toInspectionDto(record) }
    }

    private fun listWarnings(): Map<Long, List<DigitalTwinWarningDto>> {
        val warnings = linkedMapOf<Long, MutableList<DigitalTwinWarningDto>>()
        dsl.select(
            PASSENGER_WARNING_LOG.ID,
            PASSENGER_WARNING_LOG.PASSENGER_ID,
            PASSENGER_WARNING_LOG.WARNING_TYPE,
            PASSENGER_WARNING_LOG.WARNING_INFO,
            PASSENGER_WARNING_LOG.NOTICE_TYPE,
            PASSENGER_WARNING_LOG.RESULT_STATUS,
            PASSENGER_WARNING_LOG.CREATED_AT,
        ).from(PASSENGER_WARNING_LOG)
            .orderBy(PASSENGER_WARNING_LOG.ID.desc())
            .fetch { record ->
                val passengerId = record.get(PASSENGER_WARNING_LOG.PASSENGER_ID)
                if (passengerId != null) {
                    warnings.getOrPut(passengerId) { mutableListOf() }.add(mapper.toWarningDto(record))
                }
            }
        return warnings
    }
}
