package com.lopez.ecommerce_api.service;


import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    void addCartToUser(String username);
    User getUser(String username);
    List<User> getUsers();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Role findRoleByName(String name);

}
