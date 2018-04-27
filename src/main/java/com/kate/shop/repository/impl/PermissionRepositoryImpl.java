package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.User;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private RowMapper<Permission> mapper = new RowMapper<Permission>() {
        @Override
        public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Permission()
                    .setId(rs.getShort("id"))
                    .setName(rs.getString("permission"));
        }
    };
}
