package com.lopez.ecommerce_api.repository;

import com.lopez.ecommerce_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
