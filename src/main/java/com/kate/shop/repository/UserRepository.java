package com.kate.shop.repository;

import com.kate.shop.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

//TODO do the same for roles and permissions repositories
public interface UserRepository {

    User findById(Integer id);

    //TODO email case insensitive. first and last names - case insensitive and with use of like keyword.
    //TODO all arguments are optional, so any combination of them can be present in real request
    List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName,
                        Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo,
                        Integer limit, Integer offset);

    Integer count(Optional<String> email, Optional<String> firstName, Optional<String> lastName,
                     Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo,
                     Integer limit, Integer offset);

    Integer count();

    User save(User user);

    User update(User user);

    boolean delete(Integer userId);
}
