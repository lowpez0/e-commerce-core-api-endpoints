package com.lopez.ecommerce_api.service.user_service;

import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.repository.CartRepository;
import com.lopez.ecommerce_api.repository.RoleRepository;
import com.lopez.ecommerce_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    @Override
    public User saveUser(User user) {
        log.info("Saving new User {} to the database", user.getUsername());

        return userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new Role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding Role {} to the user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {} from the database", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching list of Users from the database");
        return userRepo.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepo.findByName(name);
    }

}
