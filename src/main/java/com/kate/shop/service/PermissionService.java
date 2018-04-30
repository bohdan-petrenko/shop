package com.kate.shop.service;

import com.kate.shop.entity.Permission;

public interface PermissionService {

    Permission findPermissionById(Short id);

    Permission savePermission(Permission role);

    boolean deletePermission(Short id);
}
