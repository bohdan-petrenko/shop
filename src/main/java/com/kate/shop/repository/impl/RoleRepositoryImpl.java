package com.kate.shop.repository.impl;

import com.kate.shop.controller.UserController;
import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.utils.DaoUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    private Role role = new Role();

    @Override
    public Role findRoleById(Short id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        //TODO completely incorrect. I provide you correct implementation just for example, because I see that it's difficult for you now.
        // Try to understand my code and compare it with your variant.  Also my code isn't tested, so it can contains some bugs.
        Role role = DaoUtils.one(template.query("select * from roles where id = :id", params, mapper));
        Set<Permission> permissions = new HashSet<>(
                template.query("select distinct on (p.id) p.* from permissions p inner join roles_permissions rp on p.id = rp.permission_id where rp.role_id = :id",
                        params, PermissionRepositoryImpl.createMapper()));
        role.setPermissions(permissions);
        return role;
    }

    @Override
    public Role saveRole(Role role){
        //TODO You completely forgot about permissions here. So, if I put role with permissions here you just ignore them.
        //TODO But you have to put new values into `roles_permissions` table.
        // And also you have to check if permission table contains given permissions ids before save them into `roles_permissions`

        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuffer stringBuffer = new StringBuffer();

        for (Permission p : role.getPermissions()) {
            stringBuffer.append(p.getId().toString());
            stringBuffer.append(",");
        }

        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        // checking if permissions are present in permissions table

        log.info("STRINGBUFFER   " + stringBuffer);
        params.addValue("permissionId", stringBuffer.toString());
        int counter = template.queryForObject("select count(*) from permissions where id in("+ stringBuffer+ ")", params, Integer.class);

        if (counter != role.getPermissions().size())
            throw new IllegalArgumentException("no such permission");

        params = new MapSqlParameterSource();
        params.addValue("role", role.getName());
        params.addValue("permissions", role.getPermissions());

        KeyHolder holder = new GeneratedKeyHolder();
        // insert roles into roles table
        template.update("insert into roles (role) values (:role) returning id", params, holder);
        role.setId(holder.getKey().shortValue());

        params = new MapSqlParameterSource();
        params.addValue("roleId", role.getId());

        // insert role_id and permission_id into roles_permissions table
        for (Permission p : role.getPermissions()) {
            params.addValue("permissionId", p.getId());
            template.update("insert into roles_permissions (role_id, permission_id) values (:roleId, :permissionId)", params, holder);
        }

        return role;
    }

    // todo updateRole
    @Override
    public Role updateRole(Role role) {
        Role dbRole = findRoleById(role.getId());
        if (dbRole == null)
            throw new IllegalArgumentException("no such role");
        StringBuilder stringBuilder = new StringBuilder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        stringBuilder.append("(");
        for (Permission p : role.getPermissions()) {
            stringBuilder.append(p.getId().toString());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");

        params.addValue("roleId", role.getId());
        template.update("delete from roles_permissions where role_id = :roleId", params);
        for (Permission p : role.getPermissions()) {
            params.addValue("permissionId", p.getId());
            template.update("insert into roles_permissions (role_id, permission_id) values (:roleId, :permissionId)", params);
        }
        return null;
    }

    @Override
    public boolean deleteRole(Short roleId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", roleId);
        if (template.queryForObject("select count(*) from users_roles where role_id = :id", params, Integer.class) == 0) {
            template.update("delete from roles where id = :id", params);
            return true;
        }
        throw new IllegalArgumentException("role is used");
    }

    private RowMapper<Role> mapper = (rs, rowNum) -> new Role()
            .setId(rs.getShort("id"))
            .setName(rs.getString("role"));
}
