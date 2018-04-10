package com.kate.shop.service.impl;

import com.kate.shop.entity.User;
import com.kate.shop.repository.UserRepository;
import com.kate.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${shop.default.page.size}")
    private String pageSize;

    @Autowired
    private UserRepository repository;


    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer pageNumber) {
        return null;
    }

    @Override
    public Integer count(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer pageNumber) {
        return null;
    }

    @Override
    public Integer count() {
        return repository.count();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer userId) {
        return false;
    }
}
