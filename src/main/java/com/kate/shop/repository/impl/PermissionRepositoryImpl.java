package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Permission findPermissionById(Short id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return DaoUtils.one(template.query("select * from permissions where id = :id", params, mapper));
    }

    @Override
    public Permission savePermission(Permission permission) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("permission", permission.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        template.update("insert into permissions (permission) values (:permission) returning id", params, holder);
        permission.setId(holder.getKey().shortValue());
        return permission;
    }

    @Override
    public boolean deletePermission(Short permissionId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("permissionId", permissionId);
        /*Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("id", permissionId);*/
        if(template.queryForObject("select count(*) from roles_permissions where permission_id in(" + permissionId + ")", params, Integer.class) == 0) {
            template.update("delete from permissions where id = :permissionId", params);
            return true;
        }
        throw new IllegalArgumentException("permission is used");
    }

    private RowMapper<Permission> mapper = createMapper();

    static RowMapper<Permission> createMapper() {
        return (rs, rowNum) -> new Permission()
                .setId(rs.getShort("id"))
                .setName(rs.getString("permission"));
    }
}
