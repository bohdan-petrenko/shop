package com.kate.shop.repository.impl;

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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer limit, Integer offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("enabled", enabled);
        params.put("createdFrom", createdFrom);
        params.put("createdTo", createdTo);
        params.put("limit", limit);
        params.put("offset", offset);

        template.query("select * from users where email = :email or first_name = :first_name or last_name = :last_name or " +
                "enabled = :enabled or created = :created or expired = :expired order by id offset 0 limit 0", params, mapper);

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
        KeyHolder holder = new GeneratedKeyHolder();
        template.update("insert into users (email, phone, first_name, last_name, password, enabled, created, expired) " +
                "values (:email, :phone, :first_name, :last_name, :password, :enabled, :created, :expired) returning id",
                new MapSqlParameterSource()
                        .addValue("email", user.getEmail())
                        .addValue("phone", user.getPhone())
                        .addValue("first_name", user.getFirstName())
                        .addValue("last_name", user.getLastName())
                        .addValue("password", user.getPassword())
                        .addValue("enabled", user.getEnabled())
                        .addValue("created", user.getCreated())
                        .addValue("expired", user.getExpired())
                , holder);
        user.setId(holder.getKey().intValue());

        new MapSqlParameterSource()
            .addValue("userId", user.getId());

        template.batchUpdate("insert into users_roles values (:userId, :roleId)",
                user.getRoles().stream()
                        .map(r -> new MapSqlParameterSource()
                        .addValue("userId", user.getId())
                        .addValue("roleId", r.getId()))
                .toArray(MapSqlParameterSource[]::new));

        return user;
    }

    @Override
    public User update(User user) {
        User dbUser = findById(user.getId());
        if (dbUser == null)
            throw new IllegalArgumentException(String.format("user with id: %d not found", user.getId()));

        template.update("update users set email = :email, phone = :phone, first_name = :first_name, last_name = :last_name, password = :password," +
                "enabled = :enabled, created = :created, expired = :expired where id = :id",
                new MapSqlParameterSource()
                        .addValue("email", user.getEmail())
                        .addValue("phone", user.getPhone())
                        .addValue("first_name", user.getFirstName())
                        .addValue("last_name", user.getLastName())
                        .addValue("password", user.getPassword())
                        .addValue("enabled", user.getEnabled())
                        .addValue("created", user.getCreated())
                        .addValue("expired", user.getExpired())
                        .addValue("id", user.getId()));

            template.update("delete from users_roles where user_id = :userId",
                    new MapSqlParameterSource()
                    .addValue("userId", user.getId()));
            template.batchUpdate("insert into users_roles values (:userId, :roleId)",
                    user.getRoles().stream()
                            .map(r -> new MapSqlParameterSource()
                                    .addValue("userId", user.getId())
                                    .addValue("roleId", r.getId()))
                            .toArray(MapSqlParameterSource[] :: new));
        return user;
    }

    @Override
    public boolean delete(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", userId);
        template.update("delete from users where id = :id", params);
        template.update("delete from users_roles where user_id = :id", params);
        return true;
    }

    private RowMapper<User> mapper = (rs, rowNum) -> new User()
            .setId(rs.getInt("id"))
            .setEmail(rs.getString("email"))
            .setPhone(rs.getString("phone"))
            .setLastName(rs.getString("last_name"))
            .setFirstName(rs.getString("first_name"))
            .setPassword(rs.getString("password"))
            .setEnabled(rs.getBoolean("enabled"))
            .setCreated(OffsetDateTime.parse(rs.getString("created"), DateTimeFormatter.ofPattern("yyyy MM dd")))
            .setExpired(OffsetDateTime.parse(rs.getString("expired"), DateTimeFormatter.ofPattern("yyyy MM dd")));
}
