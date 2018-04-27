package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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


    private RowMapper<Role> mapper = new RowMapper<Role>() {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Role()
                    .setId(rs.getShort("id"))
                    .setName(rs.getString("role"));
        }
    };


}
