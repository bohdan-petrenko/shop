package com.kate.shop.service.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    @Transactional
    @Override
    public Permission savePermission(Permission permission) {
        return repository.savePermission(permission);
    }

    @Transactional
    @Override
    public Permission updatePermission(Permission permission) {
        return repository.updatePermissions(permission);
    }

    @Autowired
    NamedParameterJdbcTemplate template;

    @Transactional
    @Override
    public boolean deletePermission(Short id) {
        return repository.deletePermission(id);

    }

}
