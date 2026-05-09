package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_DICT_DATA;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysDictData;
import com.robotmonitor.system.mapper.SysDictDataMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysDictDataMapper implements SysDictDataMapper {
    private final DSLContext dsl;

    public JooqSysDictDataMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return this.dsl.selectFrom(SYS_DICT_DATA)
            .where(equalsIfPresent(SYS_DICT_DATA.DICT_TYPE, dictData == null ? null : dictData.getDictType()))
            .and(contains(SYS_DICT_DATA.DICT_LABEL, dictData == null ? null : dictData.getDictLabel()))
            .and(equalsIfPresent(SYS_DICT_DATA.STATUS, dictData == null ? null : dictData.getStatus()))
            .orderBy(SYS_DICT_DATA.DICT_SORT.asc(), SYS_DICT_DATA.DICT_CODE.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return this.dsl.selectFrom(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_TYPE.eq(dictType))
            .and(SYS_DICT_DATA.STATUS.eq("0"))
            .orderBy(SYS_DICT_DATA.DICT_SORT.asc(), SYS_DICT_DATA.DICT_CODE.asc())
            .fetch(this::map);
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return this.dsl.select(SYS_DICT_DATA.DICT_LABEL)
            .from(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_TYPE.eq(dictType))
            .and(SYS_DICT_DATA.DICT_VALUE.eq(dictValue))
            .limit(1)
            .fetchOne(SYS_DICT_DATA.DICT_LABEL);
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return this.dsl.selectFrom(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_CODE.eq(dictCode))
            .fetchOne(this::map);
    }

    @Override
    public int countDictDataByType(String dictType) {
        Integer count = this.dsl.selectCount()
            .from(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_TYPE.eq(dictType))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int deleteDictDataById(Long dictCode) {
        return this.dsl.deleteFrom(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_CODE.eq(dictCode))
            .execute();
    }

    @Override
    public int deleteDictDataByIds(Long[] dictCodes) {
        return this.dsl.deleteFrom(SYS_DICT_DATA)
            .where(SYS_DICT_DATA.DICT_CODE.in(Arrays.asList(dictCodes)))
            .execute();
    }

    @Override
    public int insertDictData(SysDictData dictData) {
        return this.dsl.insertInto(SYS_DICT_DATA)
            .set(SYS_DICT_DATA.DICT_SORT, dictData.getDictSort() == null ? null : dictData.getDictSort().intValue())
            .set(SYS_DICT_DATA.DICT_LABEL, dictData.getDictLabel())
            .set(SYS_DICT_DATA.DICT_VALUE, dictData.getDictValue())
            .set(SYS_DICT_DATA.DICT_TYPE, dictData.getDictType())
            .set(SYS_DICT_DATA.CSS_CLASS, dictData.getCssClass())
            .set(SYS_DICT_DATA.LIST_CLASS, dictData.getListClass())
            .set(SYS_DICT_DATA.IS_DEFAULT, dictData.getIsDefault())
            .set(SYS_DICT_DATA.STATUS, dictData.getStatus())
            .set(SYS_DICT_DATA.CREATE_BY, dictData.getCreateBy())
            .set(SYS_DICT_DATA.CREATE_TIME, toLocalDateTime(dictData.getCreateTime()))
            .set(SYS_DICT_DATA.REMARK, dictData.getRemark())
            .execute();
    }

    @Override
    public int updateDictData(SysDictData dictData) {
        return this.dsl.update(SYS_DICT_DATA)
            .set(SYS_DICT_DATA.DICT_SORT, dictData.getDictSort() == null ? null : dictData.getDictSort().intValue())
            .set(SYS_DICT_DATA.DICT_LABEL, dictData.getDictLabel())
            .set(SYS_DICT_DATA.DICT_VALUE, dictData.getDictValue())
            .set(SYS_DICT_DATA.DICT_TYPE, dictData.getDictType())
            .set(SYS_DICT_DATA.CSS_CLASS, dictData.getCssClass())
            .set(SYS_DICT_DATA.LIST_CLASS, dictData.getListClass())
            .set(SYS_DICT_DATA.IS_DEFAULT, dictData.getIsDefault())
            .set(SYS_DICT_DATA.STATUS, dictData.getStatus())
            .set(SYS_DICT_DATA.UPDATE_BY, dictData.getUpdateBy())
            .set(SYS_DICT_DATA.UPDATE_TIME, toLocalDateTime(dictData.getUpdateTime()))
            .set(SYS_DICT_DATA.REMARK, dictData.getRemark())
            .where(SYS_DICT_DATA.DICT_CODE.eq(dictData.getDictCode()))
            .execute();
    }

    @Override
    public int updateDictDataType(String oldDictType, String newDictType) {
        return this.dsl.update(SYS_DICT_DATA)
            .set(SYS_DICT_DATA.DICT_TYPE, newDictType)
            .where(SYS_DICT_DATA.DICT_TYPE.eq(oldDictType))
            .execute();
    }

    private SysDictData map(Record record) {
        SysDictData data = new SysDictData();
        data.setDictCode(record.get(SYS_DICT_DATA.DICT_CODE));
        Integer dictSort = record.get(SYS_DICT_DATA.DICT_SORT);
        data.setDictSort(dictSort == null ? null : dictSort.longValue());
        data.setDictLabel(record.get(SYS_DICT_DATA.DICT_LABEL));
        data.setDictValue(record.get(SYS_DICT_DATA.DICT_VALUE));
        data.setDictType(record.get(SYS_DICT_DATA.DICT_TYPE));
        data.setCssClass(record.get(SYS_DICT_DATA.CSS_CLASS));
        data.setListClass(record.get(SYS_DICT_DATA.LIST_CLASS));
        data.setIsDefault(record.get(SYS_DICT_DATA.IS_DEFAULT));
        data.setStatus(record.get(SYS_DICT_DATA.STATUS));
        data.setCreateBy(record.get(SYS_DICT_DATA.CREATE_BY));
        data.setCreateTime(toDate(record.get(SYS_DICT_DATA.CREATE_TIME)));
        data.setUpdateBy(record.get(SYS_DICT_DATA.UPDATE_BY));
        data.setUpdateTime(toDate(record.get(SYS_DICT_DATA.UPDATE_TIME)));
        data.setRemark(record.get(SYS_DICT_DATA.REMARK));
        return data;
    }
}
