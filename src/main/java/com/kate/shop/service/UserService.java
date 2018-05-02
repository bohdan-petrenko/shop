package com.kate.shop.service;

import com.kate.shop.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

//TODO do the same for roles and permissions repositories
public interface UserService {

    User findById(Integer id);

    //TODO transform page number to the limit and offset here
    List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName,
                        Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo,
                        Integer pageNumber);

    Integer pagesCount(Optional<String> email, Optional<String> firstName, Optional<String> lastName,
                       Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo,
                       Integer pageNumber);

    Integer pagesCount();

    User save(User user);

    User update(User user);

    boolean delete(Integer userId);
}