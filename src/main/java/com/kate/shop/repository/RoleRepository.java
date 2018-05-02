package com.kate.shop.repository;

import com.kate.shop.entity.Role;

public interface RoleRepository {

    Role findRoleById(Short id);

    Role saveRole(Role role);

    boolean deleteRole(Short roleId);
}
