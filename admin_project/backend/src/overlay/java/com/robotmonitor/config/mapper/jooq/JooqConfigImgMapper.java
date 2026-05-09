package com.robotmonitor.config.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.CONFIG_IMG;
import static com.robotmonitor.jooq.generated.Tables.SYS_DEPT;

import com.robotmonitor.common.core.domain.config.ConfigImg;
import com.robotmonitor.common.jooq.GenericJooqCrudSupport;
import com.robotmonitor.config.mapper.ConfigImgMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqConfigImgMapper extends GenericJooqCrudSupport<ConfigImg> implements ConfigImgMapper {
    public JooqConfigImgMapper(DSLContext dsl) {
        super(dsl, CONFIG_IMG, CONFIG_IMG.ID, ConfigImg.class);
    }

    @Override
    public ConfigImg selectConfigImgById(Long id) {
        return base(true)
            .where(CONFIG_IMG.ID.eq(id))
            .fetchOne(this::mapImg);
    }

    @Override
    public List<ConfigImg> selectConfigImgList(ConfigImg query) {
        return base(false)
            .where(imgConditions(query).and(CONFIG_IMG.IS_DELETE.eq("0")))
            .fetch(this::mapImg);
    }

    @Override
    public int insertConfigImg(ConfigImg img) {
        return insert(img);
    }

    @Override
    public int updateConfigImg(ConfigImg img) {
        return update(img);
    }

    @Override
    public int deleteConfigImgById(Long id) {
        return softDeleteById(id, CONFIG_IMG.IS_DELETE, "1");
    }

    @Override
    public int deleteConfigImgByIds(Long[] ids) {
        return softDeleteByIds(ids, CONFIG_IMG.IS_DELETE, "1");
    }

    @Override
    public List<ConfigImg> selectConfigImgListByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return List.of();
        }
        return base(false)
            .where(CONFIG_IMG.ID.in(Arrays.asList(ids)))
            .fetch(this::mapImg);
    }

    private org.jooq.SelectJoinStep<Record> base(boolean includeImg) {
        if (includeImg) {
            return dsl.select(CONFIG_IMG.fields())
                .select(SYS_DEPT.DEPT_NAME)
                .from(CONFIG_IMG)
                .leftJoin(SYS_DEPT).on(CONFIG_IMG.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
        }
        return dsl.select(
                CONFIG_IMG.ID,
                CONFIG_IMG.IMG_TYPE,
                CONFIG_IMG.IMG_NAME,
                CONFIG_IMG.WIDTH,
                CONFIG_IMG.HEIGHT,
                CONFIG_IMG.REMARK,
                CONFIG_IMG.ENABLE,
                CONFIG_IMG.CREATE_BY,
                CONFIG_IMG.CREATE_TIME,
                CONFIG_IMG.UPDATE_BY,
                CONFIG_IMG.UPDATE_TIME,
                CONFIG_IMG.ROOM_CODE,
                CONFIG_IMG.IS_DELETE
            )
            .select(SYS_DEPT.DEPT_NAME)
            .from(CONFIG_IMG)
            .leftJoin(SYS_DEPT).on(CONFIG_IMG.ROOM_CODE.eq(SYS_DEPT.ROOM_CODE));
    }

    private Condition imgConditions(ConfigImg img) {
        if (img == null) {
            return DSL.noCondition();
        }
        return DSL.and(Arrays.asList(
            eqIfPresent(CONFIG_IMG.IMG_TYPE, img.getImgType()),
            eqIfPresent(CONFIG_IMG.IMG, img.getImg()),
            likeIfPresent(CONFIG_IMG.IMG_NAME, img.getImgName()),
            eqIfPresent(CONFIG_IMG.WIDTH, img.getWidth()),
            eqIfPresent(CONFIG_IMG.HEIGHT, img.getHeight()),
            eqIfPresent(CONFIG_IMG.ENABLE, img.getEnable()),
            eqIfPresent(CONFIG_IMG.ROOM_CODE, img.getRoomCode())
        ));
    }

    private ConfigImg mapImg(Record record) {
        ConfigImg img = map(record);
        img.setDeptName(record.get(SYS_DEPT.DEPT_NAME));
        return img;
    }
}
