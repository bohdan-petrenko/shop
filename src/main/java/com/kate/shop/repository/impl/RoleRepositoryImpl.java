package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
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
import java.util.Set;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private Role role = new Role();

    @Override
    public Role findRoleById(Short id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Role permissionId = DaoUtils.one(template.query("select * from roles_permissions where id = :role_id", params, mapper));
        Set<Permission> permissions = (Set<Permission>)DaoUtils.one(template.query("select * from permissions where id = :permissionId", params, mapper));
        role.setPermissions(permissions);
        return DaoUtils.one(template.query("select * from roles where id = :id", params, mapper));
    }

    @Override
    public Role saveRole(Role role) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("role", role.getName());
        KeyHolder holder = new GeneratedKeyHolder();
        template.update("insert into roles (role) values (:role) returning id", params, holder);
        role.setId(holder.getKey().shortValue());
        return role;
    }

    @Override
    public boolean deleteRole(Short roleId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", roleId);
        template.update("delete from roles where id = :id", params);
        return true;
    }


    private RowMapper<Role> mapper = new RowMapper<Role>() {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Role()
                    .setId(rs.getShort("id"))
                    .setName(rs.getString("role"));
        }
    };


}
