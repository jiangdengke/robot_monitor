package org.jdk.project.service.config;

import static org.jdk.project.service.config.ConfigCommandSupport.defaultString;
import static org.jdk.project.service.config.ConfigCommandSupport.ensureUpdated;
import static org.jdk.project.service.config.ConfigCommandSupport.requiredId;
import static org.jooq.generated.project.Tables.DEVICE;
import static org.jooq.generated.project.Tables.DEVICE_REGION_BINDING;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.config.DeviceRegionBindingUpsertRequest;
import org.jdk.project.dto.config.DeviceUpsertRequest;
import org.jooq.DSLContext;
import org.jooq.generated.project.tables.pojos.Device;
import org.jooq.generated.project.tables.pojos.DeviceRegionBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceCommandService {

  private final DSLContext dsl;

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
}
