package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_MENU;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE;
import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE_MENU;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER;
import static com.robotmonitor.jooq.generated.Tables.SYS_USER_ROLE;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.contains;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.equalsIfPresent;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toDate;
import static com.robotmonitor.system.mapper.jooq.JooqSystemMapperSupport.toLocalDateTime;

import com.robotmonitor.common.core.domain.entity.SysMenu;
import com.robotmonitor.system.mapper.SysMenuMapper;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysMenuMapper implements SysMenuMapper {
    private final DSLContext dsl;

    public JooqSysMenuMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return this.dsl.selectFrom(SYS_MENU)
            .where(menuCondition(menu))
            .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<String> selectMenuPerms() {
        return this.dsl.selectDistinct(SYS_MENU.PERMS)
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
            .fetch(SYS_MENU.PERMS);
    }

    @Override
    public List<SysMenu> selectMenuListByUserId(SysMenu menu) {
        Long userId = menu == null ? null : (Long) menu.getParams().get("userId");
        return menuUserBase(userId)
            .and(menuCondition(menu))
            .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<String> selectMenuPermsByUserId(Long userId) {
        return this.dsl.selectDistinct(SYS_MENU.PERMS)
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(SYS_MENU.STATUS.eq("0"))
            .and(SYS_ROLE.STATUS.eq("0"))
            .and(SYS_USER_ROLE.USER_ID.eq(userId))
            .fetch(SYS_MENU.PERMS);
    }

    @Override
    public List<SysMenu> selectMenuTreeAll() {
        return this.dsl.selectDistinct(SYS_MENU.fields())
            .from(SYS_MENU)
            .where(SYS_MENU.MENU_TYPE.in("M", "C"))
            .and(SYS_MENU.STATUS.eq("0"))
            .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        return menuUserBase(userId)
            .and(SYS_MENU.MENU_TYPE.in("M", "C"))
            .and(SYS_MENU.STATUS.eq("0"))
            .and(SYS_ROLE.STATUS.eq("0"))
            .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
            .fetch(this::map);
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId, boolean menuCheckStrictly) {
        Condition condition = SYS_ROLE_MENU.ROLE_ID.eq(roleId);
        if (menuCheckStrictly) {
            condition = condition.and(SYS_MENU.MENU_ID.notIn(
                DSL.select(SYS_MENU.PARENT_ID)
                    .from(SYS_MENU)
                    .join(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
                    .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId))
            ));
        }
        return this.dsl.select(SYS_MENU.MENU_ID)
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
            .where(condition)
            .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
            .fetch(SYS_MENU.MENU_ID);
    }

    @Override
    public SysMenu selectMenuById(Long menuId) {
        return this.dsl.selectFrom(SYS_MENU)
            .where(SYS_MENU.MENU_ID.eq(menuId))
            .fetchOne(this::map);
    }

    @Override
    public int hasChildByMenuId(Long menuId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_MENU)
            .where(SYS_MENU.PARENT_ID.eq(menuId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int insertMenu(SysMenu menu) {
        Long menuId = this.dsl.insertInto(SYS_MENU)
            .set(SYS_MENU.PARENT_ID, menu.getParentId())
            .set(SYS_MENU.MENU_NAME, menu.getMenuName())
            .set(SYS_MENU.ORDER_NUM, menu.getOrderNum())
            .set(SYS_MENU.PATH, menu.getPath())
            .set(SYS_MENU.COMPONENT, menu.getComponent())
            .set(SYS_MENU.QUERY, menu.getQuery())
            .set(SYS_MENU.IS_FRAME, menu.getIsFrame())
            .set(SYS_MENU.IS_CACHE, menu.getIsCache())
            .set(SYS_MENU.MENU_TYPE, menu.getMenuType())
            .set(SYS_MENU.VISIBLE, menu.getVisible())
            .set(SYS_MENU.STATUS, menu.getStatus())
            .set(SYS_MENU.PERMS, menu.getPerms())
            .set(SYS_MENU.ICON, menu.getIcon())
            .set(SYS_MENU.CREATE_BY, menu.getCreateBy())
            .set(SYS_MENU.CREATE_TIME, toLocalDateTime(menu.getCreateTime()))
            .set(SYS_MENU.REMARK, menu.getRemark())
            .returningResult(SYS_MENU.MENU_ID)
            .fetchOne(SYS_MENU.MENU_ID);
        menu.setMenuId(menuId);
        return menuId == null ? 0 : 1;
    }

    @Override
    public int updateMenu(SysMenu menu) {
        return this.dsl.update(SYS_MENU)
            .set(SYS_MENU.PARENT_ID, menu.getParentId())
            .set(SYS_MENU.MENU_NAME, menu.getMenuName())
            .set(SYS_MENU.ORDER_NUM, menu.getOrderNum())
            .set(SYS_MENU.PATH, menu.getPath())
            .set(SYS_MENU.COMPONENT, menu.getComponent())
            .set(SYS_MENU.QUERY, menu.getQuery())
            .set(SYS_MENU.IS_FRAME, menu.getIsFrame())
            .set(SYS_MENU.IS_CACHE, menu.getIsCache())
            .set(SYS_MENU.MENU_TYPE, menu.getMenuType())
            .set(SYS_MENU.VISIBLE, menu.getVisible())
            .set(SYS_MENU.STATUS, menu.getStatus())
            .set(SYS_MENU.PERMS, menu.getPerms())
            .set(SYS_MENU.ICON, menu.getIcon())
            .set(SYS_MENU.UPDATE_BY, menu.getUpdateBy())
            .set(SYS_MENU.UPDATE_TIME, toLocalDateTime(menu.getUpdateTime()))
            .set(SYS_MENU.REMARK, menu.getRemark())
            .where(SYS_MENU.MENU_ID.eq(menu.getMenuId()))
            .execute();
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return this.dsl.deleteFrom(SYS_MENU)
            .where(SYS_MENU.MENU_ID.eq(menuId))
            .execute();
    }

    @Override
    public SysMenu checkMenuNameUnique(String menuName, Long parentId) {
        return this.dsl.selectFrom(SYS_MENU)
            .where(SYS_MENU.MENU_NAME.eq(menuName))
            .and(SYS_MENU.PARENT_ID.eq(parentId))
            .limit(1)
            .fetchOne(this::map);
    }

    private SelectConditionStep<Record> menuUserBase(Long userId) {
        return this.dsl.selectDistinct(SYS_MENU.fields())
            .from(SYS_MENU)
            .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.MENU_ID))
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
            .leftJoin(SYS_ROLE).on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .leftJoin(SYS_USER).on(SYS_USER.USER_ID.eq(SYS_USER_ROLE.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId));
    }

    private Condition menuCondition(SysMenu menu) {
        return contains(SYS_MENU.MENU_NAME, menu == null ? null : menu.getMenuName())
            .and(equalsIfPresent(SYS_MENU.VISIBLE, menu == null ? null : menu.getVisible()))
            .and(equalsIfPresent(SYS_MENU.STATUS, menu == null ? null : menu.getStatus()));
    }

    private SysMenu map(Record record) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(record.get(SYS_MENU.MENU_ID));
        menu.setMenuName(record.get(SYS_MENU.MENU_NAME));
        menu.setParentId(record.get(SYS_MENU.PARENT_ID));
        menu.setOrderNum(record.get(SYS_MENU.ORDER_NUM));
        menu.setPath(record.get(SYS_MENU.PATH));
        menu.setComponent(record.get(SYS_MENU.COMPONENT));
        menu.setQuery(record.get(SYS_MENU.QUERY));
        menu.setIsFrame(record.get(SYS_MENU.IS_FRAME));
        menu.setIsCache(record.get(SYS_MENU.IS_CACHE));
        menu.setMenuType(record.get(SYS_MENU.MENU_TYPE));
        menu.setVisible(record.get(SYS_MENU.VISIBLE));
        menu.setStatus(record.get(SYS_MENU.STATUS));
        menu.setPerms(record.get(SYS_MENU.PERMS));
        menu.setIcon(record.get(SYS_MENU.ICON));
        menu.setCreateBy(record.get(SYS_MENU.CREATE_BY));
        menu.setCreateTime(toDate(record.get(SYS_MENU.CREATE_TIME)));
        menu.setUpdateBy(record.get(SYS_MENU.UPDATE_BY));
        menu.setUpdateTime(toDate(record.get(SYS_MENU.UPDATE_TIME)));
        menu.setRemark(record.get(SYS_MENU.REMARK));
        return menu;
    }
}
