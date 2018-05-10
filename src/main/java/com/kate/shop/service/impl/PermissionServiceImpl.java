package com.kate.shop.service.impl;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.repository.PermissionRepository;
import com.kate.shop.repository.impl.PermissionRepositoryImpl;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO Why you decided to remove transactional?
// because when i'm trying to save new permission there's a message that i can't do this
// ОШИБКА: в транзакции в режиме \"только чтение\" нельзя выполнить INSERT;
// nested exception is org.postgresql.util.PSQLException: ОШИБКА: в транзакции в режиме \"только чтение\" нельзя выполнить INSERT"
// but i'we just tried to write it without readOnly = true, it works
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
    public Permission updatePermission(Permission permission) {
        return repository.updatePermissions(permission);
    }

    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public boolean deletePermission(Short id) {
        return repository.deletePermission(id);

        //TODO You can't delete permission from the DB if any role uses it.
        // There are two possibilities to implement it:
        //  1 Add foreign keys to the DB for `roles_permissions` table (https://www.postgresql.org/docs/10/static/tutorial-fk.html)
        //  2 Make similar check from code

    }

}
