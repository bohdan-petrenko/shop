package com.kate.shop.repository;

import com.kate.shop.entity.Permission;

public interface PermissionRepository {

    Permission findPermissionById(Short id);

    Permission savePermission(Permission role);

    Permission updatePermissions(Permission permission);

    boolean deletePermission(Short permissionId);

}
