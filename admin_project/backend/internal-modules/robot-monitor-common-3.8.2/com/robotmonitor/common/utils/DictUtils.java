/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSONArray
 *  com.alibaba.fastjson2.JSONReader$Feature
 */
package com.robotmonitor.common.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONReader;
import com.robotmonitor.common.core.domain.entity.SysDictData;
import com.robotmonitor.common.core.redis.RedisCache;
import com.robotmonitor.common.utils.StringUtils;
import com.robotmonitor.common.utils.spring.SpringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DictUtils {
    public static final String SEPARATOR = ",";

    public static void setDictCache(String key, List<SysDictData> dictDatas) {
        SpringUtils.getBean(RedisCache.class).setCacheObject(DictUtils.getCacheKey(key), dictDatas);
    }

    public static List<SysDictData> getDictCache(String key) {
        JSONArray arrayCache = (JSONArray)SpringUtils.getBean(RedisCache.class).getCacheObject(DictUtils.getCacheKey(key));
        if (StringUtils.isNotNull(arrayCache)) {
            return arrayCache.toList(SysDictData.class, new JSONReader.Feature[0]);
        }
        return null;
    }

    public static String getDictLabel(String dictType, String dictValue) {
        return DictUtils.getDictLabel(dictType, dictValue, SEPARATOR);
    }

    public static String getDictValue(String dictType, String dictLabel) {
        return DictUtils.getDictValue(dictType, dictLabel, SEPARATOR);
    }

    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder propertyString;
        block5: {
            propertyString = new StringBuilder();
            List<SysDictData> datas = DictUtils.getDictCache(dictType);
            if (!StringUtils.isNotNull(datas)) break block5;
            if (StringUtils.containsAny((CharSequence)separator, (CharSequence)dictValue)) {
                block0: for (SysDictData dict : datas) {
                    for (String value : dictValue.split(separator)) {
                        if (!value.equals(dict.getDictValue())) continue;
                        propertyString.append(dict.getDictLabel()).append(separator);
                        continue block0;
                    }
                }
            } else {
                for (SysDictData dict : datas) {
                    if (!dictValue.equals(dict.getDictValue())) continue;
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd((String)propertyString.toString(), (String)separator);
    }

    public static List<Integer> getRegionDeviceIds(String regionId) {
        List<SysDictData> dictRegionDevice = DictUtils.getDictCache("insp_region_device");
        String separator = SEPARATOR;
        List<Object> deviceIds = new ArrayList();
        if (StringUtils.containsAny((CharSequence)separator, (CharSequence)regionId)) {
            for (String value : regionId.split(separator)) {
                deviceIds = DictUtils.getDeviceIds(dictRegionDevice, value);
            }
        } else {
            deviceIds = DictUtils.getDeviceIds(dictRegionDevice, regionId);
        }
        return deviceIds;
    }

    private static List<Integer> getDeviceIds(List<SysDictData> dictRegionDevice, String regionId) {
        ArrayList<Integer> deviceIds = new ArrayList<Integer>();
        SysDictData sysDictData = dictRegionDevice.stream().filter(dict -> dict.getDictValue().equalsIgnoreCase(regionId)).findFirst().orElse(null);
        String remark = sysDictData.getRemark();
        String commaSeparator = SEPARATOR;
        String dashSeparator = "-";
        if (StringUtils.containsAny((CharSequence)commaSeparator, (CharSequence)remark)) {
            for (String value : remark.split(commaSeparator)) {
                DictUtils.treateRegionDeviceRemark(deviceIds, value, dashSeparator);
            }
        } else {
            DictUtils.treateRegionDeviceRemark(deviceIds, remark, dashSeparator);
        }
        return deviceIds;
    }

    private static void treateRegionDeviceRemark(List<Integer> deviceIds, String remark, String dashSeparator) {
        if (StringUtils.containsAny((CharSequence)dashSeparator, (CharSequence)remark)) {
            String[] split = remark.split(dashSeparator);
            for (int i = Integer.valueOf(split[0]).intValue(); i <= Integer.valueOf(split[1]); ++i) {
                deviceIds.add(i);
            }
        } else {
            deviceIds.add(Integer.valueOf(remark));
        }
    }

    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = DictUtils.getDictCache(dictType);
        if (StringUtils.containsAny((CharSequence)separator, (CharSequence)dictLabel) && StringUtils.isNotEmpty(datas)) {
            block0: for (SysDictData dict : datas) {
                for (String label : dictLabel.split(separator)) {
                    if (!label.equals(dict.getDictLabel())) continue;
                    propertyString.append(dict.getDictValue()).append(separator);
                    continue block0;
                }
            }
        } else {
            for (SysDictData dict : datas) {
                if (!dictLabel.equals(dict.getDictLabel())) continue;
                return dict.getDictValue();
            }
        }
        return StringUtils.stripEnd((String)propertyString.toString(), (String)separator);
    }

    public static void removeDictCache(String key) {
        SpringUtils.getBean(RedisCache.class).deleteObject(DictUtils.getCacheKey(key));
    }

    public static void clearDictCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys("sys_dict:*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    public static String getCacheKey(String configKey) {
        return "sys_dict:" + configKey;
    }
}
