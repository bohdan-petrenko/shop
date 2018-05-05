package com.kate.shop.repository.impl;

import com.kate.shop.entity.Role;
import com.kate.shop.entity.User;
import com.kate.shop.repository.UserRepository;
import com.kate.shop.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public User findById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return DaoUtils.one(template.query("select * from users where id = :id", params, mapper));
    }

    //todo order by id
    @Override
    public List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer limit, Integer offset) {
        return null;
    }

    @Override
    public Integer count(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer limit, Integer offset) {
        return null;
    }

    @Override
    public Integer count() {
        return template.queryForObject("select count(1) from users", new EmptySqlParameterSource(), Integer.class);
    }


    @Override
    public User save(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", user.getEmail());
        params.addValue("phone", user.getPhone());
        params.addValue("first_name", user.getFirstName());
        params.addValue("last_name", user.getLastName());
        params.addValue("password", user.getPassword());
        params.addValue("enabled", user.getEnabled());
        params.addValue("created", user.getCreated());
        params.addValue("expired", user.getExpired());
        /*StringBuilder stringBuilder = new StringBuilder();
        for (Role r: user.getRoles()) {
            stringBuilder.append(r.getId());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);*/

        KeyHolder holder = new GeneratedKeyHolder();
        template.update("insert into users (email, phone, first_name, last_name, password, enabled, created, expired) values (:email, :phone, :first_name, :last_name, :password, :enabled, :created, :expired) returning id", params, holder);
        user.setId(holder.getKey().intValue());

        params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        //params.addValue("roleId", stringBuilder.toString());
        for (Role r: user.getRoles()) {
            params.addValue("roleId", r.getId());
            template.update("insert into users_roles values (:userId, :roleId)", params, holder);
        }

        return user;
    }

    @Override
    public User update(User user) {
        User dbUser = findById(user.getId());
        if (dbUser == null)
            throw new IllegalArgumentException(String.format("user with id: %d not found", user.getId()));
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", user.getFirstName());
        params.addValue("last_name", user.getLastName());
        params.addValue("id", user.getId());
        template.update("update users set first_name = :first_name, last_name = :last_name where id = :id", params);
        return user;
    }

    @Override
    public boolean delete(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);
        template.update("delete from users where id = :id", params);
        return true;
    }

    private RowMapper<User> mapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User()
                    .setId(rs.getInt("id"))
                    .setLastName(rs.getString("last_name"))
                    .setFirstName(rs.getString("first_name"));
        }
    };
}
