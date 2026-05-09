package com.robotmonitor.common.jooq;

import com.robotmonitor.common.core.domain.BaseEntity;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;

public abstract class GenericJooqCrudSupport<T> {
    protected final DSLContext dsl;
    protected final Table<? extends UpdatableRecord<?>> table;
    protected final TableField<?, Long> idField;
    protected final Class<T> domainType;
    private final Map<String, PropertyDescriptor> properties = new HashMap<>();
    private final Map<String, TableField<?, ?>> fields = new HashMap<>();

    protected GenericJooqCrudSupport(
        DSLContext dsl,
        Table<? extends UpdatableRecord<?>> table,
        TableField<?, Long> idField,
        Class<T> domainType
    ) {
        this.dsl = dsl;
        this.table = table;
        this.idField = idField;
        this.domainType = domainType;
        initProperties(domainType);
        for (Field<?> field : table.fields()) {
            if (field instanceof TableField<?, ?> tableField) {
                fields.put(field.getName(), tableField);
            }
        }
    }

    protected T selectById(Long id) {
        return dsl.select(table.fields())
            .from(table)
            .where(idField.eq(id))
            .fetchOne(this::map);
    }

    protected List<T> selectList(T query) {
        return dsl.select(table.fields())
            .from(table)
            .where(filterByNonNullProperties(query))
            .fetch(this::map);
    }

    protected int insert(T entity) {
        Map<Field<?>, Object> values = writeValues(entity, false);
        if (values.isEmpty()) {
            return 0;
        }
        Long id = dsl.insertInto(table)
            .set(values)
            .returningResult(idField)
            .fetchOne(idField);
        setProperty(entity, propertyName(idField), id);
        return id == null ? 0 : 1;
    }

    protected int update(T entity) {
        Long id = asLong(getProperty(entity, propertyName(idField)));
        if (id == null) {
            return 0;
        }
        Map<Field<?>, Object> values = writeValues(entity, true);
        values.remove(idField);
        if (values.isEmpty()) {
            return 0;
        }
        return dsl.update(table)
            .set(values)
            .where(idField.eq(id))
            .execute();
    }

    protected int deleteById(Long id) {
        return dsl.deleteFrom(table)
            .where(idField.eq(id))
            .execute();
    }

    protected int deleteByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.deleteFrom(table)
            .where(idField.in(Arrays.asList(ids)))
            .execute();
    }

    protected int softDeleteById(Long id, TableField<?, String> flagField, String value) {
        return dsl.update(table)
            .set(cast(flagField), value)
            .where(idField.eq(id))
            .execute();
    }

    protected int softDeleteByIds(Long[] ids, TableField<?, String> flagField, String value) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return dsl.update(table)
            .set(cast(flagField), value)
            .where(idField.in(Arrays.asList(ids)))
            .execute();
    }

    protected Condition eqIfPresent(TableField<?, ?> field, Object value) {
        if (isEmpty(value)) {
            return DSL.noCondition();
        }
        return cast(field).eq(convertForField(field, value));
    }

    protected Condition likeIfPresent(TableField<?, ?> field, String value) {
        if (isBlank(value)) {
            return DSL.noCondition();
        }
        return field.cast(String.class).like("%" + value + "%");
    }

    protected T map(Record record) {
        try {
            T entity = domainType.getDeclaredConstructor().newInstance();
            for (TableField<?, ?> field : fields.values()) {
                setProperty(entity, propertyName(field), convertForProperty(record.get(field), propertyType(propertyName(field))));
            }
            return entity;
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Cannot map record to " + domainType.getName(), ex);
        }
    }

    protected Object getProperty(Object target, String property) {
        PropertyDescriptor descriptor = properties.get(property);
        if (target == null || descriptor == null || descriptor.getReadMethod() == null) {
            return null;
        }
        try {
            return descriptor.getReadMethod().invoke(target);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Cannot read property " + property + " from " + target.getClass().getName(), ex);
        }
    }

    protected void setProperty(Object target, String property, Object value) {
        PropertyDescriptor descriptor = properties.get(property);
        if (target == null || descriptor == null || descriptor.getWriteMethod() == null) {
            return;
        }
        try {
            descriptor.getWriteMethod().invoke(target, convertForProperty(value, descriptor.getPropertyType()));
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Cannot set property " + property + " on " + target.getClass().getName(), ex);
        }
    }

    protected LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    protected String stringValue(Long value) {
        return value == null ? null : String.valueOf(value);
    }

    protected Integer intValue(Long value) {
        return value == null ? null : value.intValue();
    }

    protected Integer intValue(String value) {
        if (isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    protected Long longValue(Integer value) {
        return value == null ? null : value.longValue();
    }

    protected String propertyName(Field<?> field) {
        String name = field.getName().toLowerCase(Locale.ROOT);
        StringBuilder builder = new StringBuilder();
        boolean upperNext = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                upperNext = true;
                continue;
            }
            builder.append(upperNext ? Character.toUpperCase(c) : c);
            upperNext = false;
        }
        return builder.toString();
    }

    protected boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    protected boolean isEmpty(Object value) {
        return value == null || value instanceof String text && isBlank(text);
    }

    protected void addBaseConditions(List<Condition> conditions, T query) {
        for (TableField<?, ?> field : fields.values()) {
            String property = propertyName(field);
            Object value = getProperty(query, property);
            if (isEmpty(value) || Objects.equals(field, idField)) {
                continue;
            }
            conditions.add(eqIfPresent(field, value));
        }
    }

    protected TableField<?, ?> field(String columnName) {
        TableField<?, ?> field = fields.get(columnName);
        if (field == null) {
            throw new IllegalArgumentException("Unknown column " + columnName + " on table " + table.getName());
        }
        return field;
    }

    private Condition filterByNonNullProperties(T query) {
        List<Condition> conditions = new ArrayList<>();
        addBaseConditions(conditions, query);
        return conditions.isEmpty() ? DSL.noCondition() : DSL.and(conditions);
    }

    private Map<Field<?>, Object> writeValues(T entity, boolean forUpdate) {
        Map<Field<?>, Object> values = new HashMap<>();
        for (TableField<?, ?> field : fields.values()) {
            if (!forUpdate && Objects.equals(field, idField)) {
                continue;
            }
            String property = propertyName(field);
            Object value = getProperty(entity, property);
            if (value == null) {
                continue;
            }
            values.put(field, convertForField(field, value));
        }
        return values;
    }

    private void initProperties(Class<?> type) {
        Class<?> current = type;
        while (current != null && current != Object.class) {
            try {
                for (PropertyDescriptor descriptor : Introspector.getBeanInfo(current).getPropertyDescriptors()) {
                    properties.putIfAbsent(descriptor.getName(), descriptor);
                }
            } catch (IntrospectionException ex) {
                throw new IllegalStateException("Cannot inspect " + current.getName(), ex);
            }
            current = current.getSuperclass();
        }
    }

    private Class<?> propertyType(String property) {
        PropertyDescriptor descriptor = properties.get(property);
        return descriptor == null ? Object.class : descriptor.getPropertyType();
    }

    private Object convertForField(Field<?> field, Object value) {
        if (value == null) {
            return null;
        }
        Class<?> fieldType = field.getType();
        if (fieldType.isInstance(value)) {
            return value;
        }
        if (fieldType == LocalDateTime.class && value instanceof Date date) {
            return toLocalDateTime(date);
        }
        if (fieldType == LocalDate.class && value instanceof Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (fieldType == LocalDate.class && value instanceof String text && !isBlank(text)) {
            return LocalDate.parse(text.substring(0, Math.min(10, text.length())));
        }
        if (fieldType == LocalDateTime.class && value instanceof String text && !isBlank(text)) {
            return parseDateTime(text);
        }
        if ((fieldType == Integer.class || fieldType == int.class) && value instanceof Number number) {
            return number.intValue();
        }
        if ((fieldType == Long.class || fieldType == long.class) && value instanceof Number number) {
            return number.longValue();
        }
        if ((fieldType == String.class) && value instanceof Number number) {
            return String.valueOf(number);
        }
        if ((fieldType == Integer.class || fieldType == int.class) && value instanceof String text && !isBlank(text)) {
            return Integer.valueOf(text);
        }
        if ((fieldType == Long.class || fieldType == long.class) && value instanceof String text && !isBlank(text)) {
            return Long.valueOf(text);
        }
        return value;
    }

    private Object convertForProperty(Object value, Class<?> propertyType) {
        if (value == null || propertyType == Object.class || propertyType.isInstance(value)) {
            return value;
        }
        if (propertyType == Date.class && value instanceof LocalDateTime dateTime) {
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        if (propertyType == Date.class && value instanceof LocalDate date) {
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (propertyType == Date.class && value instanceof Timestamp timestamp) {
            return new Date(timestamp.getTime());
        }
        if ((propertyType == Long.class || propertyType == long.class) && value instanceof Number number) {
            return number.longValue();
        }
        if ((propertyType == Long.class || propertyType == long.class) && value instanceof String text && !isBlank(text)) {
            return Long.valueOf(text);
        }
        if ((propertyType == Integer.class || propertyType == int.class) && value instanceof Number number) {
            return number.intValue();
        }
        if ((propertyType == Integer.class || propertyType == int.class) && value instanceof String text && !isBlank(text)) {
            return Integer.valueOf(text);
        }
        if ((propertyType == Double.class || propertyType == double.class) && value instanceof Number number) {
            return number.doubleValue();
        }
        if ((propertyType == Double.class || propertyType == double.class) && value instanceof String text && !isBlank(text)) {
            return Double.valueOf(text);
        }
        if ((propertyType == BigDecimal.class) && value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        if ((propertyType == BigDecimal.class) && value instanceof String text && !isBlank(text)) {
            return new BigDecimal(text);
        }
        if ((propertyType == BigInteger.class) && value instanceof Number number) {
            return BigInteger.valueOf(number.longValue());
        }
        if ((propertyType == BigInteger.class) && value instanceof String text && !isBlank(text)) {
            return new BigInteger(text);
        }
        if ((propertyType == Boolean.class || propertyType == boolean.class) && value instanceof Boolean bool) {
            return bool;
        }
        if ((propertyType == Boolean.class || propertyType == boolean.class) && value instanceof Number number) {
            return number.intValue() != 0;
        }
        if ((propertyType == Boolean.class || propertyType == boolean.class) && value instanceof String text && !isBlank(text)) {
            return "1".equals(text) || "true".equalsIgnoreCase(text) || "yes".equalsIgnoreCase(text);
        }
        if (propertyType == String.class) {
            if (value instanceof LocalDateTime dateTime) {
                return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            }
            if (value instanceof LocalDate date) {
                return date.toString();
            }
            return String.valueOf(value);
        }
        return value;
    }

    private LocalDateTime parseDateTime(String text) {
        if (text.length() == 14 && text.chars().allMatch(Character::isDigit)) {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        if (text.length() == 10) {
            return LocalDate.parse(text).atStartOfDay();
        }
        return LocalDateTime.parse(text.replace(' ', 'T'));
    }

    private Long asLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String text && !isBlank(text)) {
            return Long.valueOf(text);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <R extends UpdatableRecord<R>, V> TableField<R, V> cast(TableField<?, ?> field) {
        return (TableField<R, V>) field;
    }
}
