package com.example.dobbyga_station.order.repository;

import com.example.dobbyga_station.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
