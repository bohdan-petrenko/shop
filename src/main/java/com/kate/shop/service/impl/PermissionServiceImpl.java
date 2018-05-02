package com.kate.shop.service.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO Why you decided to remove transactional?
//@Transactional(readOnly = true)
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public Permission findPermissionById(Short id) {
        return repository.findPermissionById(id);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return repository.savePermission(permission);
    }

    @Override
    public boolean deletePermission(Short id) {
        //TODO You can't delete permission from the DB if any role uses it.
        // There are two possibilities to implement it:
        //  1 Add foreign keys to the DB for `roles_permissions` table (https://www.postgresql.org/docs/10/static/tutorial-fk.html)
        //  2 Make similar check from code
        return repository.deletePermission(id);
    }
}
