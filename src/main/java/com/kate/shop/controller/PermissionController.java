package com.kate.shop.controller;

import com.kate.shop.entity.Permission;
import com.kate.shop.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("find-permission-by-id")
    public Permission findById(@RequestParam Short id) {
        return permissionService.findPermissionById(id);
    }
}
