package com.lopez.ecommerce_api.service.user_service;


import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    User findByUsername(String username);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Role findRoleByName(String name);

}
