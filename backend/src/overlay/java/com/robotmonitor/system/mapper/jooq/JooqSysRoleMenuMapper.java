package com.robotmonitor.system.mapper.jooq;

import static com.robotmonitor.jooq.generated.Tables.SYS_ROLE_MENU;

import com.robotmonitor.system.domain.SysRoleMenu;
import com.robotmonitor.system.mapper.SysRoleMenuMapper;
import java.util.Arrays;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JooqSysRoleMenuMapper implements SysRoleMenuMapper {
    private final DSLContext dsl;

    public JooqSysRoleMenuMapper(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int checkMenuExistRole(Long menuId) {
        Integer count = this.dsl.selectCount()
            .from(SYS_ROLE_MENU)
            .where(SYS_ROLE_MENU.MENU_ID.eq(menuId))
            .fetchOne(0, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public int deleteRoleMenuByRoleId(Long roleId) {
        return this.dsl.deleteFrom(SYS_ROLE_MENU)
            .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId))
            .execute();
    }

    @Override
    public int deleteRoleMenu(Long[] roleIds) {
        return this.dsl.deleteFrom(SYS_ROLE_MENU)
            .where(SYS_ROLE_MENU.ROLE_ID.in(Arrays.asList(roleIds)))
            .execute();
    }

    @Override
    public int batchRoleMenu(List<SysRoleMenu> roleMenus) {
        if (roleMenus == null || roleMenus.isEmpty()) {
            return 0;
        }
        return this.dsl.batch(roleMenus.stream()
            .map(item -> this.dsl.insertInto(SYS_ROLE_MENU)
                .set(SYS_ROLE_MENU.ROLE_ID, item.getRoleId())
                .set(SYS_ROLE_MENU.MENU_ID, item.getMenuId()))
            .toList())
            .execute().length;
    }
}
