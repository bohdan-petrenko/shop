package com.kate.shop.repository;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;

public interface PermissionRepository {

    Permission findPermissionById(Short id);

    Permission savePermission(Permission role);

    Permission updatePermissions(Permission permission);

    boolean deletePermission(Short permissionId);

}
