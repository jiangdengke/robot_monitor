package org.jdk.project.service;

import static org.jooq.generated.project.Tables.AREA;
import static org.jooq.generated.project.Tables.AREA_I18N;
import static org.jooq.generated.project.Tables.COMPLAINT_RECORD;
import static org.jooq.generated.project.Tables.DEVICE;
import static org.jooq.generated.project.Tables.DEVICE_REGION_BINDING;
import static org.jooq.generated.project.Tables.DINING_TABLE;
import static org.jooq.generated.project.Tables.LOUNGE;
import static org.jooq.generated.project.Tables.MEDIA_AUDIO;
import static org.jooq.generated.project.Tables.MEDIA_IMAGE;
import static org.jooq.generated.project.Tables.REGION;
import static org.jooq.generated.project.Tables.ROBOT;
import static org.jooq.generated.project.Tables.ROBOT_TASK_TEMPLATE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.AreaDetailUpsertRequest;
import org.jdk.project.dto.config.AreaUpsertRequest;
import org.jdk.project.dto.config.AudioUpsertRequest;
import org.jdk.project.dto.config.ComplaintUpsertRequest;
import org.jdk.project.dto.config.DeviceUpsertRequest;
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest;
import org.jdk.project.dto.config.ImageUpsertRequest;
import org.jdk.project.dto.config.LoungeUpsertRequest;
import org.jdk.project.dto.config.RegionUpsertRequest;
import org.jdk.project.dto.config.RobotUpsertRequest;
import org.jdk.project.dto.config.TableUpsertRequest;
import org.jdk.project.dto.config.TaskUpsertRequest;
import org.jdk.project.exception.BusinessException;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.Area;
import org.jooq.generated.project.tables.pojos.AreaI18n;
import org.jooq.generated.project.tables.pojos.ComplaintRecord;
import org.jooq.generated.project.tables.pojos.Device;
import org.jooq.generated.project.tables.pojos.DeviceRegionBinding;
import org.jooq.generated.project.tables.pojos.DiningTable;
import org.jooq.generated.project.tables.pojos.Lounge;
import org.jooq.generated.project.tables.pojos.MediaAudio;
import org.jooq.generated.project.tables.pojos.MediaImage;
import org.jooq.generated.project.tables.pojos.Region;
import org.jooq.generated.project.tables.pojos.Robot;
import org.jooq.generated.project.tables.pojos.RobotTaskTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfigCommandService {

  private final DSLContext dsl;

  @Transactional
  public Long createLounge(LoungeUpsertRequest request) {
    Lounge lounge = new Lounge();
    lounge.setCode(request.getRoomCode());
    lounge.setName(request.getDeptName());
    lounge.setTerminal(defaultString(request.getTerminal(), ""));
    lounge.setLocationDesc(defaultString(request.getLocationDesc(), ""));
    lounge.setEnabled(request.getEnabled() == null || request.getEnabled());
    lounge.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(LOUNGE)
        .set(dsl.newRecord(LOUNGE, lounge))
        .returningResult(LOUNGE.ID)
        .fetchOne(LOUNGE.ID);
  }

  @Transactional
  public void updateLounge(Long id, LoungeUpsertRequest request) {
    int updated =
        dsl.update(LOUNGE)
            .set(LOUNGE.CODE, request.getRoomCode())
            .set(LOUNGE.NAME, request.getDeptName())
            .set(LOUNGE.TERMINAL, defaultString(request.getTerminal(), ""))
            .set(LOUNGE.LOCATION_DESC, defaultString(request.getLocationDesc(), ""))
            .set(LOUNGE.ENABLED, request.getEnabled() == null || request.getEnabled())
            .set(LOUNGE.REMARK, defaultString(request.getRemark(), ""))
            .where(LOUNGE.ID.eq(id))
            .execute();
    ensureUpdated(updated, "贵宾室不存在");
  }

  @Transactional
  public void deleteLounge(Long id) {
    dsl.deleteFrom(LOUNGE).where(LOUNGE.ID.eq(id)).execute();
  }

  @Transactional
  public Long createRegion(RegionUpsertRequest request) {
    Region region = new Region();
    region.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    region.setAreaId(request.getAreaId());
    region.setName(request.getRegionName());
    region.setCoordinate(defaultString(request.getCoordinate(), ""));
    region.setMaxCapacity(defaultInt(request.getMaxCapacity(), 0));
    region.setGuideEnabled("1".equals(request.getIsGuide()));
    region.setVisible(!"0".equals(request.getIsShow()));
    region.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    region.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(REGION)
        .set(dsl.newRecord(REGION, region))
        .returningResult(REGION.ID)
        .fetchOne(REGION.ID);
  }

  @Transactional
  public void updateRegion(Long id, RegionUpsertRequest request) {
    int updated =
        dsl.update(REGION)
            .set(REGION.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(REGION.AREA_ID, request.getAreaId())
            .set(REGION.NAME, request.getRegionName())
            .set(REGION.COORDINATE, defaultString(request.getCoordinate(), ""))
            .set(REGION.MAX_CAPACITY, defaultInt(request.getMaxCapacity(), 0))
            .set(REGION.GUIDE_ENABLED, "1".equals(request.getIsGuide()))
            .set(REGION.VISIBLE, !"0".equals(request.getIsShow()))
            .set(REGION.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(REGION.REMARK, defaultString(request.getRemark(), ""))
            .where(REGION.ID.eq(id))
            .execute();
    ensureUpdated(updated, "区域不存在");
  }

  @Transactional
  public void deleteRegion(Long id) {
    dsl.deleteFrom(REGION).where(REGION.ID.eq(id)).execute();
  }

  @Transactional
  public Long createArea(AreaUpsertRequest request) {
    Area area = new Area();
    area.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    area.setName(request.getAreaName());
    area.setCoordinate(defaultString(request.getCoordinate(), ""));
    area.setMaxCapacity(defaultInt(request.getMaxCapacity(), 0));
    area.setGuideEnabled("1".equals(request.getIsGuide()));
    area.setVisible(!"0".equals(request.getIsShow()));
    area.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    area.setRemark(defaultString(request.getRemark(), ""));
    Long areaId =
        dsl.insertInto(AREA)
            .set(dsl.newRecord(AREA, area))
            .returningResult(AREA.ID)
            .fetchOne(AREA.ID);
    replaceAreaDetails(areaId, request.getConfigAreaDetailList());
    return areaId;
  }

  @Transactional
  public void updateArea(Long id, AreaUpsertRequest request) {
    int updated =
        dsl.update(AREA)
            .set(AREA.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(AREA.NAME, request.getAreaName())
            .set(AREA.COORDINATE, defaultString(request.getCoordinate(), ""))
            .set(AREA.MAX_CAPACITY, defaultInt(request.getMaxCapacity(), 0))
            .set(AREA.GUIDE_ENABLED, "1".equals(request.getIsGuide()))
            .set(AREA.VISIBLE, !"0".equals(request.getIsShow()))
            .set(AREA.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(AREA.REMARK, defaultString(request.getRemark(), ""))
            .where(AREA.ID.eq(id))
            .execute();
    ensureUpdated(updated, "功能区不存在");
    replaceAreaDetails(id, request.getConfigAreaDetailList());
  }

  @Transactional
  public void deleteArea(Long id) {
    dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(id)).execute();
    dsl.deleteFrom(AREA).where(AREA.ID.eq(id)).execute();
  }

  @Transactional
  public Long createImage(ImageUpsertRequest request) {
    MediaImage image = new MediaImage();
    image.setLoungeId(request.getLoungeId());
    image.setName(request.getImgName());
    image.setCategory(defaultString(request.getImgType(), "MAP"));
    image.setContent(request.getImg());
    image.setWidth(defaultInt(request.getWidth(), 0));
    image.setHeight(defaultInt(request.getHeight(), 0));
    image.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    image.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(MEDIA_IMAGE)
        .set(dsl.newRecord(MEDIA_IMAGE, image))
        .returningResult(MEDIA_IMAGE.ID)
        .fetchOne(MEDIA_IMAGE.ID);
  }

  @Transactional
  public void updateImage(Long id, ImageUpsertRequest request) {
    int updated =
        dsl.update(MEDIA_IMAGE)
            .set(MEDIA_IMAGE.LOUNGE_ID, request.getLoungeId())
            .set(MEDIA_IMAGE.NAME, request.getImgName())
            .set(MEDIA_IMAGE.CATEGORY, defaultString(request.getImgType(), "MAP"))
            .set(MEDIA_IMAGE.CONTENT, request.getImg())
            .set(MEDIA_IMAGE.WIDTH, defaultInt(request.getWidth(), 0))
            .set(MEDIA_IMAGE.HEIGHT, defaultInt(request.getHeight(), 0))
            .set(MEDIA_IMAGE.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(MEDIA_IMAGE.REMARK, defaultString(request.getRemark(), ""))
            .where(MEDIA_IMAGE.ID.eq(id))
            .execute();
    ensureUpdated(updated, "图片不存在");
  }

  @Transactional
  public void deleteImage(Long id) {
    dsl.deleteFrom(MEDIA_IMAGE).where(MEDIA_IMAGE.ID.eq(id)).execute();
  }

  @Transactional
  public Long createAudio(AudioUpsertRequest request) {
    MediaAudio audio = new MediaAudio();
    audio.setLoungeId(request.getLoungeId());
    audio.setAudioKey(request.getAudioKey());
    audio.setCategory(defaultString(request.getAudioType(), "COMMON"));
    audio.setLanguageCode(defaultString(request.getLanguageType(), "CN"));
    audio.setTextContent(request.getTextInfo());
    audio.setAudioContent(request.getAudioValue());
    audio.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(MEDIA_AUDIO)
        .set(dsl.newRecord(MEDIA_AUDIO, audio))
        .returningResult(MEDIA_AUDIO.ID)
        .fetchOne(MEDIA_AUDIO.ID);
  }

  @Transactional
  public void updateAudio(Long id, AudioUpsertRequest request) {
    int updated =
        dsl.update(MEDIA_AUDIO)
            .set(MEDIA_AUDIO.LOUNGE_ID, request.getLoungeId())
            .set(MEDIA_AUDIO.AUDIO_KEY, request.getAudioKey())
            .set(MEDIA_AUDIO.CATEGORY, defaultString(request.getAudioType(), "COMMON"))
            .set(MEDIA_AUDIO.LANGUAGE_CODE, defaultString(request.getLanguageType(), "CN"))
            .set(MEDIA_AUDIO.TEXT_CONTENT, request.getTextInfo())
            .set(MEDIA_AUDIO.AUDIO_CONTENT, request.getAudioValue())
            .set(MEDIA_AUDIO.REMARK, defaultString(request.getRemark(), ""))
            .where(MEDIA_AUDIO.ID.eq(id))
            .execute();
    ensureUpdated(updated, "音频不存在");
  }

  @Transactional
  public void deleteAudio(Long id) {
    dsl.deleteFrom(MEDIA_AUDIO).where(MEDIA_AUDIO.ID.eq(id)).execute();
  }

  @Transactional
  public Long createDevice(DeviceUpsertRequest request) {
    Device device = new Device();
    device.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    device.setName(request.getDeviceName());
    device.setDeviceType(defaultString(request.getDeviceType(), "CAMERA"));
    device.setExternalDeviceId(defaultString(request.getDeepGlintDeviceId(), ""));
    device.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    device.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(DEVICE)
        .set(dsl.newRecord(DEVICE, device))
        .returningResult(DEVICE.ID)
        .fetchOne(DEVICE.ID);
  }

  @Transactional
  public void updateDevice(Long id, DeviceUpsertRequest request) {
    ensureUpdated(
        dsl.update(DEVICE)
            .set(DEVICE.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(DEVICE.NAME, request.getDeviceName())
            .set(DEVICE.DEVICE_TYPE, defaultString(request.getDeviceType(), "CAMERA"))
            .set(DEVICE.EXTERNAL_DEVICE_ID, defaultString(request.getDeepGlintDeviceId(), ""))
            .set(DEVICE.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(DEVICE.REMARK, defaultString(request.getRemark(), ""))
            .where(DEVICE.ID.eq(id))
            .execute(),
        "设备不存在");
  }

  @Transactional
  public void deleteDevice(Long id) {
    dsl.deleteFrom(DEVICE).where(DEVICE.ID.eq(id)).execute();
  }

  @Transactional
  public void saveDeviceRegionBinding(DeviceRegionBindingUpsertRequest request) {
    DeviceRegionBinding binding = new DeviceRegionBinding();
    binding.setDeviceId(requiredId(request.getDeviceId(), "设备不能为空"));
    binding.setRegionId(requiredId(request.getRegionId(), "区域不能为空"));
    binding.setImageId(request.getImageId());
    binding.setCoordinate(defaultString(request.getCoordinate(), ""));
    binding.setRemark(defaultString(request.getRemark(), ""));
    dsl.deleteFrom(DEVICE_REGION_BINDING)
        .where(DEVICE_REGION_BINDING.DEVICE_ID.eq(binding.getDeviceId()))
        .and(DEVICE_REGION_BINDING.REGION_ID.eq(binding.getRegionId()))
        .execute();
    dsl.insertInto(DEVICE_REGION_BINDING).set(dsl.newRecord(DEVICE_REGION_BINDING, binding)).execute();
  }

  @Transactional
  public void deleteDeviceRegionBinding(Long deviceId, Long regionId) {
    dsl.deleteFrom(DEVICE_REGION_BINDING)
        .where(DEVICE_REGION_BINDING.DEVICE_ID.eq(deviceId))
        .and(DEVICE_REGION_BINDING.REGION_ID.eq(regionId))
        .execute();
  }

  @Transactional
  public Long createTable(TableUpsertRequest request) {
    DiningTable table = new DiningTable();
    table.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    table.setRegionId(request.getRegionId());
    table.setDeviceId(request.getDeviceId());
    table.setTableNo(request.getTableNo());
    table.setCameraCoordinate(defaultString(request.getCameraCoordinates(), ""));
    table.setStatus(defaultString(request.getStatus(), "IDLE"));
    table.setEnabled(!"0".equals(request.getIsEnable()));
    table.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(DINING_TABLE)
        .set(dsl.newRecord(DINING_TABLE, table))
        .returningResult(DINING_TABLE.ID)
        .fetchOne(DINING_TABLE.ID);
  }

  @Transactional
  public void updateTable(Long id, TableUpsertRequest request) {
    ensureUpdated(
        dsl.update(DINING_TABLE)
            .set(DINING_TABLE.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(DINING_TABLE.REGION_ID, request.getRegionId())
            .set(DINING_TABLE.DEVICE_ID, request.getDeviceId())
            .set(DINING_TABLE.TABLE_NO, request.getTableNo())
            .set(DINING_TABLE.CAMERA_COORDINATE, defaultString(request.getCameraCoordinates(), ""))
            .set(DINING_TABLE.STATUS, defaultString(request.getStatus(), "IDLE"))
            .set(DINING_TABLE.ENABLED, !"0".equals(request.getIsEnable()))
            .set(DINING_TABLE.REMARK, defaultString(request.getRemark(), ""))
            .where(DINING_TABLE.ID.eq(id))
            .execute(),
        "餐桌不存在");
  }

  @Transactional
  public void deleteTable(Long id) {
    dsl.deleteFrom(DINING_TABLE).where(DINING_TABLE.ID.eq(id)).execute();
  }

  @Transactional
  public Long createRobot(RobotUpsertRequest request) {
    Robot robot = new Robot();
    robot.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    robot.setRegionId(request.getRegionId());
    robot.setRobotCode(request.getRobotId());
    robot.setName(request.getRobotName());
    robot.setMac(defaultString(request.getMac(), ""));
    robot.setIpAddress(defaultString(request.getRobotIp(), ""));
    robot.setRobotType(defaultString(request.getRobotType(), ""));
    robot.setBatteryPercent(defaultInt(request.getBatteryState(), 0));
    robot.setChargingState(defaultString(request.getChargingState(), ""));
    robot.setWorkingState(defaultString(request.getWorkingState(), ""));
    robot.setStandbyState(defaultString(request.getStandbyState(), ""));
    robot.setPositioningState(defaultString(request.getPositioningState(), ""));
    robot.setEnabled(request.getEnable() == null || request.getEnable() == 1);
    robot.setInitialCoordinate(defaultString(request.getOriCoordinate(), ""));
    robot.setAdminMode(Boolean.TRUE.equals(request.getAdminMode()));
    robot.setErrorCode(defaultString(request.getErrorCode(), ""));
    robot.setErrorMessage(defaultString(request.getErrorMessage(), ""));
    robot.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(ROBOT)
        .set(dsl.newRecord(ROBOT, robot))
        .returningResult(ROBOT.ID)
        .fetchOne(ROBOT.ID);
  }

  @Transactional
  public void updateRobot(Long id, RobotUpsertRequest request) {
    ensureUpdated(
        dsl.update(ROBOT)
            .set(ROBOT.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(ROBOT.REGION_ID, request.getRegionId())
            .set(ROBOT.ROBOT_CODE, request.getRobotId())
            .set(ROBOT.NAME, request.getRobotName())
            .set(ROBOT.MAC, defaultString(request.getMac(), ""))
            .set(ROBOT.IP_ADDRESS, defaultString(request.getRobotIp(), ""))
            .set(ROBOT.ROBOT_TYPE, defaultString(request.getRobotType(), ""))
            .set(ROBOT.BATTERY_PERCENT, defaultInt(request.getBatteryState(), 0))
            .set(ROBOT.CHARGING_STATE, defaultString(request.getChargingState(), ""))
            .set(ROBOT.WORKING_STATE, defaultString(request.getWorkingState(), ""))
            .set(ROBOT.STANDBY_STATE, defaultString(request.getStandbyState(), ""))
            .set(ROBOT.POSITIONING_STATE, defaultString(request.getPositioningState(), ""))
            .set(ROBOT.ENABLED, request.getEnable() == null || request.getEnable() == 1)
            .set(ROBOT.INITIAL_COORDINATE, defaultString(request.getOriCoordinate(), ""))
            .set(ROBOT.ADMIN_MODE, Boolean.TRUE.equals(request.getAdminMode()))
            .set(ROBOT.ERROR_CODE, defaultString(request.getErrorCode(), ""))
            .set(ROBOT.ERROR_MESSAGE, defaultString(request.getErrorMessage(), ""))
            .set(ROBOT.REMARK, defaultString(request.getRemark(), ""))
            .where(ROBOT.ID.eq(id))
            .execute(),
        "机器人不存在");
  }

  @Transactional
  public void deleteRobot(Long id) {
    dsl.deleteFrom(ROBOT).where(ROBOT.ID.eq(id)).execute();
  }

  @Transactional
  public Long createTask(TaskUpsertRequest request) {
    RobotTaskTemplate task = new RobotTaskTemplate();
    task.setLoungeId(requiredId(request.getLoungeId(), "贵宾室不能为空"));
    task.setRobotId(request.getRobotId());
    task.setName(request.getTaskName());
    task.setCommandCode(request.getCommandCode());
    task.setCommandName(defaultString(request.getCommandName(), ""));
    task.setTargetRegion(defaultString(request.getTargetRegion(), ""));
    task.setPriority(defaultString(request.getPriority(), "NORMAL"));
    task.setExecuteType(defaultString(request.getExecuteType(), "IMMEDIATELY"));
    task.setExecuteDay(defaultString(request.getExecuteDay(), ""));
    task.setTaskType(defaultString(request.getTaskType(), ""));
    task.setTaskSubtype(defaultString(request.getTaskSubtype(), ""));
    task.setTaskMode(defaultString(request.getTaskMode(), ""));
    task.setDirectExecution(Boolean.TRUE.equals(request.getDirectExecution()));
    task.setReturnRequired(Boolean.TRUE.equals(request.getReturnRequired()));
    task.setEnabled(request.getEnabled() == null || request.getEnabled());
    task.setRemark(defaultString(request.getRemark(), ""));
    return dsl.insertInto(ROBOT_TASK_TEMPLATE)
        .set(dsl.newRecord(ROBOT_TASK_TEMPLATE, task))
        .returningResult(ROBOT_TASK_TEMPLATE.ID)
        .fetchOne(ROBOT_TASK_TEMPLATE.ID);
  }

  @Transactional
  public void updateTask(Long id, TaskUpsertRequest request) {
    ensureUpdated(
        dsl.update(ROBOT_TASK_TEMPLATE)
            .set(ROBOT_TASK_TEMPLATE.LOUNGE_ID, requiredId(request.getLoungeId(), "贵宾室不能为空"))
            .set(ROBOT_TASK_TEMPLATE.ROBOT_ID, request.getRobotId())
            .set(ROBOT_TASK_TEMPLATE.NAME, request.getTaskName())
            .set(ROBOT_TASK_TEMPLATE.COMMAND_CODE, request.getCommandCode())
            .set(ROBOT_TASK_TEMPLATE.COMMAND_NAME, defaultString(request.getCommandName(), ""))
            .set(ROBOT_TASK_TEMPLATE.TARGET_REGION, defaultString(request.getTargetRegion(), ""))
            .set(ROBOT_TASK_TEMPLATE.PRIORITY, defaultString(request.getPriority(), "NORMAL"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_TYPE, defaultString(request.getExecuteType(), "IMMEDIATELY"))
            .set(ROBOT_TASK_TEMPLATE.EXECUTE_DAY, defaultString(request.getExecuteDay(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_TYPE, defaultString(request.getTaskType(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_SUBTYPE, defaultString(request.getTaskSubtype(), ""))
            .set(ROBOT_TASK_TEMPLATE.TASK_MODE, defaultString(request.getTaskMode(), ""))
            .set(ROBOT_TASK_TEMPLATE.DIRECT_EXECUTION, Boolean.TRUE.equals(request.getDirectExecution()))
            .set(ROBOT_TASK_TEMPLATE.RETURN_REQUIRED, Boolean.TRUE.equals(request.getReturnRequired()))
            .set(ROBOT_TASK_TEMPLATE.ENABLED, request.getEnabled() == null || request.getEnabled())
            .set(ROBOT_TASK_TEMPLATE.REMARK, defaultString(request.getRemark(), ""))
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .execute(),
        "任务不存在");
  }

  @Transactional
  public void deleteTask(Long id) {
    dsl.deleteFrom(ROBOT_TASK_TEMPLATE).where(ROBOT_TASK_TEMPLATE.ID.eq(id)).execute();
  }

  @Transactional
  public Long runTask(Long id) {
    RobotTaskTemplate template =
        dsl.selectFrom(ROBOT_TASK_TEMPLATE)
            .where(ROBOT_TASK_TEMPLATE.ID.eq(id))
            .fetchOneInto(RobotTaskTemplate.class);
    if (template == null) {
      throw new BusinessException("任务不存在");
    }
    var record =
        dsl.insertInto(org.jooq.generated.project.Tables.ROBOT_TASK_LOG)
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.ROBOT_ID, template.getRobotId())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_TEMPLATE_ID, template.getId())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_NAME, template.getName())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_TYPE, template.getTaskType())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_SUBTYPE, template.getTaskSubtype())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_MODE, template.getTaskMode())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.TASK_STATUS, "SUBMITTED")
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.DIRECT_EXECUTION, template.getDirectExecution())
            .set(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.COMMAND_PAYLOAD, template.getCommandName())
            .returningResult(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.ID)
            .fetchOne();
    return record == null ? null : record.get(org.jooq.generated.project.Tables.ROBOT_TASK_LOG.ID);
  }

  @Transactional
  public Long createComplaint(ComplaintUpsertRequest request) {
    ComplaintRecord complaint = new ComplaintRecord();
    complaint.setLoungeId(request.getLoungeId());
    complaint.setPassengerName(request.getUserName());
    complaint.setCardProvider(defaultString(request.getCardService(), ""));
    complaint.setCardNo(defaultString(request.getCardNo(), ""));
    complaint.setContent(defaultString(request.getComplaintContent(), ""));
    complaint.setFeedback(defaultString(request.getComplaintFeedback(), ""));
    return dsl.insertInto(COMPLAINT_RECORD)
        .set(dsl.newRecord(COMPLAINT_RECORD, complaint))
        .returningResult(COMPLAINT_RECORD.ID)
        .fetchOne(COMPLAINT_RECORD.ID);
  }

  @Transactional
  public void updateComplaint(Long id, ComplaintUpsertRequest request) {
    ensureUpdated(
        dsl.update(COMPLAINT_RECORD)
            .set(COMPLAINT_RECORD.LOUNGE_ID, request.getLoungeId())
            .set(COMPLAINT_RECORD.PASSENGER_NAME, request.getUserName())
            .set(COMPLAINT_RECORD.CARD_PROVIDER, defaultString(request.getCardService(), ""))
            .set(COMPLAINT_RECORD.CARD_NO, defaultString(request.getCardNo(), ""))
            .set(COMPLAINT_RECORD.CONTENT, defaultString(request.getComplaintContent(), ""))
            .set(COMPLAINT_RECORD.FEEDBACK, defaultString(request.getComplaintFeedback(), ""))
            .where(COMPLAINT_RECORD.ID.eq(id))
            .execute(),
        "投诉记录不存在");
  }

  @Transactional
  public void deleteComplaint(Long id) {
    dsl.deleteFrom(COMPLAINT_RECORD).where(COMPLAINT_RECORD.ID.eq(id)).execute();
  }

  private void replaceAreaDetails(Long areaId, List<AreaDetailUpsertRequest> details) {
    dsl.deleteFrom(AREA_I18N).where(AREA_I18N.AREA_ID.eq(areaId)).execute();
    if (details == null || details.isEmpty()) {
      return;
    }
    for (AreaDetailUpsertRequest detail : details) {
      AreaI18n record = new AreaI18n();
      record.setAreaId(areaId);
      record.setLanguageCode(defaultString(detail.getLanguageType(), "CN"));
      record.setDisplayName(defaultString(detail.getAreaName(), ""));
      record.setLabelText(defaultString(detail.getLabel(), ""));
      record.setArrivalText(defaultString(detail.getArrText(), ""));
      record.setSpeechText(defaultString(detail.getRemark(), ""));
      dsl.insertInto(AREA_I18N).set(dsl.newRecord(AREA_I18N, record)).execute();
    }
  }

  private Long requiredId(Long value, String message) {
    if (value == null) {
      throw new BusinessException(message);
    }
    return value;
  }

  private int defaultInt(Integer value, int fallback) {
    return value == null ? fallback : value;
  }

  private String defaultString(String value, String fallback) {
    return value == null ? fallback : value;
  }

  private void ensureUpdated(int updated, String message) {
    if (updated == 0) {
      throw new BusinessException(message);
    }
  }
}
