package org.jdk.project.service.configquery;

import static org.jooq.generated.project.Tables.DEVICE;
import static org.jooq.generated.project.Tables.LOUNGE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.config.DeviceDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceConfigQueryService {

  private final DSLContext dsl;
  private final ConfigQueryMapper mapper;

  public ListResponse<DeviceDto> listDevices() {
    List<DeviceDto> rows =
        dsl.select(
                DEVICE.ID,
                DEVICE.NAME,
                DEVICE.DEVICE_TYPE,
                DEVICE.EXTERNAL_DEVICE_ID,
                LOUNGE.CODE,
                LOUNGE.NAME.as("lounge_name"),
                DEVICE.ENABLED,
                DEVICE.REMARK)
            .from(DEVICE)
            .join(LOUNGE)
            .on(DEVICE.LOUNGE_ID.eq(LOUNGE.ID))
            .orderBy(DEVICE.ID.asc())
            .fetch(mapper::toDeviceDto);
    return ListResponse.of(rows.size(), rows);
  }
}
