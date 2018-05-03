package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

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
    public Role saveRole(Role role) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Set<Permission> permissions = new HashSet<>(role.getPermissions());
        params.addValue("role", role.getName());

        KeyHolder holder = new GeneratedKeyHolder();
        template.update("insert into roles (role) values (:role) returning id", params, holder);

        for (Permission p : permissions) {
            params.addValue("permissions", p.getName());
            List<Permission> resultList = template.query("select * from permissions where permission = :permissions", params, PermissionRepositoryImpl.createMapper());

            if (resultList.size() == 0)
                throw new IllegalArgumentException("no such permission");
        }
        role.setId(holder.getKey().shortValue());
        params.addValue("roleId", role.getId());

        for (Permission p : permissions) {
            params.addValue("permissionName", p.getName());
            Permission permission = template.query("select * from permissions where permission = :permissionName", params, PermissionRepositoryImpl.createMapper()).get(0);
            params.addValue("permissionId", permission.getId());

            template.update("insert into roles_permissions (role_id, permission_id) values (:roleId, :permissionId) returning role_id", params, holder);
        }

        //TODO You completely forgot about permissions here. So, if I put role with permissions here you just ignore them.
        //TODO But you have to put new values into `roles_permissions` table.
        // And also you have to check if permission table contains given permissions ids before save them into `roles_permissions`

        return role;
    }

    @Override
    public boolean deleteRole(Short roleId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", roleId);
        template.update("delete from roles where id = :id", params);
        return true;
    }


    private RowMapper<Role> mapper = (rs, rowNum) -> new Role()
            .setId(rs.getShort("id"))
            .setName(rs.getString("role"));


}
