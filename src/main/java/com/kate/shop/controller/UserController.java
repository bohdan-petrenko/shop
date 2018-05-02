package com.kate.shop.controller;

import com.kate.shop.entity.User;
import com.kate.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //@RequestParam, @RequestBody
    @GetMapping("count")
    public Integer count() {
        log.info("Get users pagesCount request");
        return userService.pagesCount();
    }

    @GetMapping("find-by-id")
    public User findById(@RequestParam Integer id) {
        log.info("...");
        return userService.findById(id);
    }

    @PostMapping("save")
    public User save (@RequestBody User user){
            return userService.save(user);
    }

    @PutMapping("update")
    public User update (@RequestBody User user){
        return userService.update(user);

    }

    @DeleteMapping("delete")
    public boolean delete (@RequestParam Integer id) {
        return userService.delete(id);
    }
}
