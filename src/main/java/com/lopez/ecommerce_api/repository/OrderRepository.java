package com.lopez.ecommerce_api.repository;

import com.lopez.ecommerce_api.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
