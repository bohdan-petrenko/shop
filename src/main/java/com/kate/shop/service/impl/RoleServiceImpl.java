package com.kate.shop.service.impl;

import com.kate.shop.entity.Role;
import com.kate.shop.repository.RoleRepository;
import com.kate.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO Why you decided to remove transactional here?
//@Transactional(readOnly = true)
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role findRoleById(Short id) {
        return repository.findRoleById(id);
    }

    @Override
    public Role saveRole(Role role) {
        return repository.saveRole(role);
    }

    @Override
    public Role updateRole(Role role) {
        return repository.updateRole(role);
    }


    @Override
    public boolean deleteRole(Short id) {
        //TODO You can't delete role while any user uses it. See explanation in PermissionServiceImpl.delete
        return repository.deleteRole(id);
    }

}
