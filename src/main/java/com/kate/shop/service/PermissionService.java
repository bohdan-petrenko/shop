package com.kate.shop.service;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;

public interface PermissionService {

    Permission findPermissionById(Short id);

    Permission savePermission(Permission role);

    Permission updatePermission(Permission permission);

    boolean deletePermission(Short id);
}
