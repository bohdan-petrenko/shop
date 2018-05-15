package com.kate.shop.entity;

import com.kate.shop.controller.UserController;
import com.kate.shop.utils.DigestUtils;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Set;

public class User {
    private Integer id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean enabled;
    private OffsetDateTime created;
    private OffsetDateTime expired;
    private Set<Role> roles;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(UserController.class);

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        log.info("GOT EMAIL  ", email);
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = DigestUtils.sha1(password);
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public User setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public User setCreated(OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public OffsetDateTime getExpired() {
        return expired;
    }

    public User setExpired(OffsetDateTime expired) {
        this.expired = expired;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}
