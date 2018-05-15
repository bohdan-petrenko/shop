package com.kate.shop.service.impl;

import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role findRoleById(Short id) {
        return repository.findRoleById(id);
    }

    @Transactional
    @Override
    public Role saveRole(Role role) {
        return repository.saveRole(role);
    }

    @Transactional
    @Override
    public Role updateRole(Role role) {
        return repository.updateRole(role);
    }

    @Transactional
    @Override
    public boolean deleteRole(Short id) {
        return repository.deleteRole(id);
    }

}
