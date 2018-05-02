package com.kate.shop.service.impl;

import com.kate.shop.entity.User;
import com.kate.shop.repository.UserRepository;
import com.kate.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    @Value("${shop.default.page.size}")
    private String pageSize;

    @Autowired
    private UserRepository repository;


    @Override
    public User findById(Integer id) {
        return repository.findById(id);
    }

    // todo calculate limit and offset based on page number
    @Override
    public List<User> findPage(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer pageNumber) {
        return null;
    }

    @Override
    public Integer pagesCount(Optional<String> email, Optional<String> firstName, Optional<String> lastName, Optional<Boolean> enabled, Optional<OffsetDateTime> createdFrom, Optional<OffsetDateTime> createdTo, Integer pageNumber) {
        return null;
    }

    @Override
    public Integer pagesCount() {
        return repository.count();
    }

    //@PostMapping
    @Transactional
    @Override
    public User save(User user) {
        return repository.save(user);
    }

    //@PutMapping
    @Transactional
    @Override
    public User update(User user) {
        return repository.update(user);
    }


    //@DeleteMapping
    @Transactional
    @Override
    public boolean delete(Integer userId) {
        return repository.delete(userId);
    }
}
