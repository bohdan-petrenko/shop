package com.kate.shop.service;

import com.kate.shop.entity.Role;

public interface RoleService {

    Role findRoleById(Short id);

    Role saveRole(Role role);

    Role updateRole(Role role);

    boolean deleteRole(Short id);
}
