/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.JSON
 *  com.alibaba.fastjson2.JSONArray
 *  com.alibaba.fastjson2.JSONFactory
 *  com.alibaba.fastjson2.JSONObject
 *  com.alibaba.fastjson2.JSONReader$Feature
 *  com.alibaba.fastjson2.JSONWriter$Feature
 *  com.alibaba.fastjson2.TypeReference
 *  com.alibaba.fastjson2.reader.ObjectReaderProvider
 *  com.alibaba.fastjson2.util.ParameterizedTypeImpl
 *  com.alibaba.fastjson2.util.TypeUtils
 *  lombok.NonNull
 */
package com.robotmonitor.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.alibaba.fastjson2.util.ParameterizedTypeImpl;
import com.alibaba.fastjson2.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.NonNull;

public class FastJsonUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final ThreadLocal<SimpleDateFormat> __dateFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final Function<LocalDateTime, String> dateTimeSerializer = dateTimeFormatter::format;
    private static final Function<LocalDate, String> dateSerializer = dateFormatter::format;
    private static final Function<LocalTime, String> timeSerializer = timeFormatter::format;
    private static final Function<Date, String> __dateSerializer = json -> __dateFormatter.get().format((Date)json);
    private static final Function<String, LocalDateTime> dateTimeDeserializer = json -> LocalDateTime.parse(json, dateTimeFormatter);
    private static final Function<String, LocalDate> dateDeserializer = json -> LocalDate.parse(json, dateFormatter);
    private static final Function<String, LocalTime> timeDeserializer = json -> LocalTime.parse(json, timeFormatter);
    private static final Function<String, Date> __dateDeserializer = json -> {
        try {
            return __dateFormatter.get().parse((String)json);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
    private static final ObjectReaderProvider provider = JSONFactory.getDefaultObjectReaderProvider();

    public static ParameterizedType makeJavaType(Type rawType, Type ... typeArguments) {
        return new ParameterizedTypeImpl(typeArguments, null, rawType);
    }

    public static String toString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        return FastJsonUtils.toJSONString(value);
    }

    public static String toJSONString(Object value) {
        return JSON.toJSONString((Object)value);
    }

    public static String toPrettyString(Object value) {
        return JSON.toJSONString((Object)value, (JSONWriter.Feature[])new JSONWriter.Feature[]{JSONWriter.Feature.PrettyFormat});
    }

    public static Object fromJavaObject(Object value) {
        Object result = null;
        result = Objects.nonNull(value) && value instanceof String ? FastJsonUtils.parseObject((String)value) : JSON.toJSON((Object)value);
        return result;
    }

    public static Object parseObject(String content) {
        return JSON.parseObject((String)content, Object.class);
    }

    public static Object getJsonElement(JSONObject node, String name) {
        return node.get(name);
    }

    public static Object getJsonElement(JSONArray node, int index) {
        return node.get(index);
    }

    public static <T> T toJavaObject(Object node, Class<T> clazz) {
        return (T)JSON.to(clazz, (Object)node);
    }

    public static <T> T toJavaObject(@NonNull Object node, Type type) {
        if (node == null) {
            throw new NullPointerException("node is marked non-null but is null");
        }
        if (node instanceof JSONObject) {
            return (T)((JSONObject)node).to(type, new JSONReader.Feature[0]);
        }
        if (node instanceof JSONArray) {
            return (T)((JSONArray)node).to(type);
        }
        if (node instanceof String) {
            return (T)JSON.parseObject((String)((String)node), (Type)type);
        }
        return (T)TypeUtils.cast((Object)node, (Class)TypeUtils.getClass((Type)type));
    }

    public static <T> T toJavaObject(@NonNull Object node, TypeReference<T> typeReference) {
        if (node == null) {
            throw new NullPointerException("node is marked non-null but is null");
        }
        if (node instanceof JSONObject) {
            return (T)typeReference.to((JSONObject)node, new JSONReader.Feature[0]);
        }
        if (node instanceof JSONArray) {
            return (T)typeReference.to((JSONArray)node);
        }
        if (node instanceof String) {
            return (T)JSON.parseObject((String)((String)node), typeReference, (JSONReader.Feature[])new JSONReader.Feature[0]);
        }
        return (T)TypeUtils.cast((Object)node, (Class)typeReference.getRawType());
    }

    public static <E> List<E> toJavaList(Object node, Class<E> clazz) {
        return (List)FastJsonUtils.toJavaObject(node, (Type)FastJsonUtils.makeJavaType(List.class, new Type[]{clazz}));
    }

    public static List<Object> toJavaList(Object node) {
        return FastJsonUtils.toJavaObject(node, new TypeReference<List<Object>>(){});
    }

    public static <V> Map<String, V> toJavaMap(Object node, Class<V> clazz) {
        return (Map)FastJsonUtils.toJavaObject(node, (Type)FastJsonUtils.makeJavaType(Map.class, new Type[]{String.class, clazz}));
    }

    public static Map<String, Object> toJavaMap(Object node) {
        return FastJsonUtils.toJavaObject(node, new TypeReference<Map<String, Object>>(){});
    }

    public static <T> T toJavaObject(String content, Class<T> clazz) {
        return (T)JSON.parseObject((String)content, clazz);
    }

    public static <T> T toJavaObject(String content, Type type) {
        return (T)JSON.parseObject((String)content, (Type)type);
    }

    public static <T> T toJavaObject(String content, TypeReference<T> typeReference) {
        return (T)JSON.parseObject((String)content, typeReference, (JSONReader.Feature[])new JSONReader.Feature[0]);
    }

    public static <E> List<E> toJavaList(String content, Class<E> clazz) {
        return (List)JSON.parseObject((String)content, (Type)FastJsonUtils.makeJavaType(List.class, new Type[]{clazz}));
    }

    public static List<Object> toJavaList(String content) {
        return (List)JSON.parseObject((String)content, (TypeReference)new TypeReference<List<Object>>(){}, (JSONReader.Feature[])new JSONReader.Feature[0]);
    }

    public static <V> Map<String, V> toJavaMap(String content, Class<V> clazz) {
        return (Map)JSON.parseObject((String)content, (Type)FastJsonUtils.makeJavaType(Map.class, new Type[]{String.class, clazz}));
    }

    public static Map<String, Object> toJavaMap(String content) {
        return (Map)JSON.parseObject((String)content, (TypeReference)new TypeReference<Map<String, Object>>(){}, (JSONReader.Feature[])new JSONReader.Feature[0]);
    }

    static {
        provider.registerTypeConvert(String.class, Date.class, __dateDeserializer);
        provider.registerTypeConvert(String.class, LocalDateTime.class, dateTimeDeserializer);
        provider.registerTypeConvert(String.class, LocalDate.class, dateDeserializer);
        provider.registerTypeConvert(String.class, LocalTime.class, timeDeserializer);
        provider.registerTypeConvert(Date.class, String.class, __dateSerializer);
        provider.registerTypeConvert(LocalDateTime.class, String.class, dateTimeSerializer);
        provider.registerTypeConvert(LocalDate.class, String.class, dateSerializer);
        provider.registerTypeConvert(LocalTime.class, String.class, timeSerializer);
    }
}
