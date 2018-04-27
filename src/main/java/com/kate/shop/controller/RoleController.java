package com.kate.shop.controller;

import com.kate.shop.entity.Role;
import com.kate.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("find-role-by-id")
    public Role findRoleById(@RequestParam Short id) {
        return roleService.findRoleById(id);
    }
}
