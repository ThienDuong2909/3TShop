package com.project._TShop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project._TShop.Entities.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>{



}