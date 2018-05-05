package com.kate.shop.repository.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class RowMappers {

    @Bean
    public RowMapper<Role> roleRowMapper() {
        return (rs, rowNum) -> new Role()
                .setId(rs.getShort("id"))
                .setName(rs.getString("role"));
    }

    @Bean
    public RowMapper<Permission> permissionRowMapper() {
        return (rs, rowNum) -> new Permission()
                .setId(rs.getShort("id"))
                .setName(rs.getString("permission"));
    }
}
