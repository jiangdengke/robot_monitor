package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_TYPE;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysDictType;
import com.robotmonitor.system.mapper.SysDictTypeMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysDictTypeMapper implements SysDictTypeMapper {
    private final DSLContext dsl;

    public JooqSysDictTypeMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return this.dsl.selectFrom(SYS_DICT_TYPE)
            .where(contains(SYS_DICT_TYPE.DICT_NAME, dictType == null ? null : dictType.getDictName()))
            .and(equalsIfPresent(SYS_DICT_TYPE.STATUS, dictType == null ? null : dictType.getStatus()))
            .and(contains(SYS_DICT_TYPE.DICT_TYPE, dictType == null ? null : dictType.getDictType()))
            .orderBy(SYS_DICT_TYPE.DICT_ID.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return this.dsl.selectFrom(SYS_DICT_TYPE)
            .orderBy(SYS_DICT_TYPE.DICT_ID.asc())
            .fetch(this::map);
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return this.dsl.selectFrom(SYS_DICT_TYPE)
            .where(SYS_DICT_TYPE.DICT_ID.eq(dictId))
            .fetchOne(this::map);
    }

    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return this.dsl.selectFrom(SYS_DICT_TYPE)
            .where(SYS_DICT_TYPE.DICT_TYPE.eq(dictType))
            .fetchOne(this::map);
    }

    @Override
    public int deleteDictTypeById(Long dictId) {
        return this.dsl.deleteFrom(SYS_DICT_TYPE)
            .where(SYS_DICT_TYPE.DICT_ID.eq(dictId))
            .execute();
    }

    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        return this.dsl.deleteFrom(SYS_DICT_TYPE)
            .where(SYS_DICT_TYPE.DICT_ID.in(Arrays.asList(dictIds)))
            .execute();
    }

    @Override
    public int insertDictType(SysDictType dictType) {
        return this.dsl.insertInto(SYS_DICT_TYPE)
            .set(SYS_DICT_TYPE.DICT_NAME, dictType.getDictName())
            .set(SYS_DICT_TYPE.DICT_TYPE, dictType.getDictType())
            .set(SYS_DICT_TYPE.STATUS, dictType.getStatus())
            .set(SYS_DICT_TYPE.CREATE_BY, dictType.getCreateBy())
            .set(SYS_DICT_TYPE.CREATE_TIME, toLocalDateTime(dictType.getCreateTime()))
            .set(SYS_DICT_TYPE.REMARK, dictType.getRemark())
            .execute();
    }

    @Override
    public int updateDictType(SysDictType dictType) {
        return this.dsl.update(SYS_DICT_TYPE)
            .set(SYS_DICT_TYPE.DICT_NAME, dictType.getDictName())
            .set(SYS_DICT_TYPE.DICT_TYPE, dictType.getDictType())
            .set(SYS_DICT_TYPE.STATUS, dictType.getStatus())
            .set(SYS_DICT_TYPE.UPDATE_BY, dictType.getUpdateBy())
            .set(SYS_DICT_TYPE.UPDATE_TIME, toLocalDateTime(dictType.getUpdateTime()))
            .set(SYS_DICT_TYPE.REMARK, dictType.getRemark())
            .where(SYS_DICT_TYPE.DICT_ID.eq(dictType.getDictId()))
            .execute();
    }

    @Override
    public SysDictType checkDictTypeUnique(String dictType) {
        return this.selectDictTypeByType(dictType);
    }

    private SysDictType map(Record record) {
        SysDictType dictType = new SysDictType();
        dictType.setDictId(record.get(SYS_DICT_TYPE.DICT_ID));
        dictType.setDictName(record.get(SYS_DICT_TYPE.DICT_NAME));
        dictType.setDictType(record.get(SYS_DICT_TYPE.DICT_TYPE));
        dictType.setStatus(record.get(SYS_DICT_TYPE.STATUS));
        dictType.setCreateBy(record.get(SYS_DICT_TYPE.CREATE_BY));
        dictType.setCreateTime(toDate(record.get(SYS_DICT_TYPE.CREATE_TIME)));
        dictType.setUpdateBy(record.get(SYS_DICT_TYPE.UPDATE_BY));
        dictType.setUpdateTime(toDate(record.get(SYS_DICT_TYPE.UPDATE_TIME)));
        dictType.setRemark(record.get(SYS_DICT_TYPE.REMARK));
        return dictType;
    }
}
