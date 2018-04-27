package com.kate.shop.service.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public Permission findPermissionById(Short id) {
        return repository.findPermissionById(id);
    }
}
