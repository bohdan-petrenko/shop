package com.kate.shop.repository.impl;

import com.kate.shop.controller.UserController;
import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.exceptions.http.BadRequestException;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.utils.DaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private RowMapper<Role> roleMapper;

    @Autowired
    private RowMapper<Permission> permissionMapper;

    @Override
    public Role findRoleById(Short id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Role role = DaoUtils.one(template.query("select * from roles where id = :id", params, roleMapper));
        if (role == null) return role;
        Set<Permission> permissions = new HashSet<>(
                template.query("select distinct on (p.id) p.* from permissions p inner join roles_permissions rp on p.id = rp.permission_id where rp.role_id = :id",
                        params, permissionMapper));
        role.setPermissions(permissions);
        return role;
    }

    @Override
    public Role saveRole(Role role) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("permissionIds", role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList()));
        int realPermissionsCount = template.queryForObject("select count(*) from permissions where id in ( :permissionIds )", params, Integer.class);
        if (realPermissionsCount != role.getPermissions().size())
            throw new BadRequestException("No such permission");

        params = new MapSqlParameterSource();
        params.addValue("role", role.getName());

        KeyHolder holder = new GeneratedKeyHolder();
        // insert roles into roles table
        template.update("insert into roles (role) values (:role) returning id", params, holder);
        role.setId(holder.getKey().shortValue());

        // insert role_id and permission_id into roles_permissions table
        template.batchUpdate(
                "insert into roles_permissions (role_id, permission_id) values (:roleId, :permissionId)",
                role.getPermissions().stream()
                        .map(p -> new MapSqlParameterSource()
                                .addValue("roleId", role.getId())
                                .addValue("permissionId", p.getId()))
                        .toArray(MapSqlParameterSource[]::new));
        params = new MapSqlParameterSource()
                .addValue("id", role.getId());
        role.setPermissions(new HashSet<>(
                template.query("select distinct on (p.id) p.* from permissions p inner join roles_permissions rp on p.id = rp.permission_id where rp.role_id = :id",
                        params, permissionMapper)));
        return role;
    }


    @Override
    public Role updateRole(Role role) {
        Role dbRole = findRoleById(role.getId());
        if (dbRole == null)
            throw new BadRequestException(String.format("Role with `id = %d` not found", role.getId()));
        List<Short> rolePermissions = role.getPermissions() == null ? new ArrayList<>()
                : role.getPermissions().stream().map(Permission::getId).collect(Collectors.toList());
        int realPermissionsCount = template.queryForObject("select count(*) from permissions where id in ( :permissionIds )",
                new MapSqlParameterSource().addValue("permissionIds", rolePermissions), Integer.class);
        if (realPermissionsCount != role.getPermissions().size())
            throw new BadRequestException("No such permission");
        List<Short> dbRolePermissions = template.query("select permission_id from roles_permissions where role_id = :roleId",
                new MapSqlParameterSource().addValue("roleId", role.getId()),
                (rs, rowNum) -> rs.getShort("permission_id"));
        List<Short> permissionsToDelete = new ArrayList<>(dbRolePermissions);
        permissionsToDelete.removeAll(rolePermissions);
        List<Short> permissionsToInsert = new ArrayList<>(rolePermissions);
        permissionsToInsert.removeAll(dbRolePermissions);
        if (role.getName() != null)
            template.update("update roles set role = :name where id = :id",
                    new MapSqlParameterSource().addValue("name", role.getName()).addValue("id", role.getId()));
        if (!permissionsToDelete.isEmpty())
            template.update("delete from roles_permissions where role_id = :roleId and permission_id in (:permissionIds)",
                    new MapSqlParameterSource()
                            .addValue("roleId", role.getId())
                            .addValue("permissionIds", permissionsToDelete));
        if (!permissionsToInsert.isEmpty())
            template.batchUpdate("insert into roles_permissions (role_id, permission_id) values (:roleId, :permissionId)",
                    permissionsToInsert.stream()
                            .map(p -> new MapSqlParameterSource()
                                    .addValue("roleId", role.getId())
                                    .addValue("permissionId", p))
                            .toArray(MapSqlParameterSource[]::new));
        return role.setPermissions(new HashSet<>(
                template.query("select distinct on (p.id) p.* from permissions p inner join roles_permissions rp on p.id = rp.permission_id where rp.role_id = :id",
                        new MapSqlParameterSource().addValue("id", role.getId()),
                        permissionMapper)));
    }

    @Override
    public boolean deleteRole(Short roleId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", roleId);
        if (template.queryForObject("select count(*) from users_roles where role_id = :id", params, Integer.class) == 0) {
            template.update("delete from roles where id = :id", params);
            return true;
        }
        throw new BadRequestException("Role is used");
    }
}
