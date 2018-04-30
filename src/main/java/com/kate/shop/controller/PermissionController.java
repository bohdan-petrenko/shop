package com.kate.shop.controller;

import com.kate.shop.entity.Permission;
import com.kate.shop.entity.Role;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("find-permission-by-id")
    public Permission findById(@RequestParam Short id) {
        return permissionService.findPermissionById(id);
    }

    @PostMapping("save-permission")
    public Permission save(@RequestBody Permission permission) {
        return permissionService.savePermission(permission);
    }

    @DeleteMapping("delete-permission")
    public boolean delete (@RequestParam Short id) {
        return permissionService.deletePermission(id);
    }
}
