package com.example.unique.wear.repositories;

import com.example.unique.wear.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository  extends JpaRepository<Order, UUID> {
}
