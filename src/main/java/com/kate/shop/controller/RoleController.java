package com.kate.shop.controller;

import com.kate.shop.entity.Role;
import com.kate.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//TODO you forgot to implement update method (@PutMapping)
@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("find-role-by-id")
    public Role findRoleById(@RequestParam Short id) {
        return roleService.findRoleById(id);
    }

    @PostMapping("save-role")
    public Role save(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @DeleteMapping("delete-role")
    public boolean delete (@RequestParam Short id) {
        return roleService.deleteRole(id);
    }
}
