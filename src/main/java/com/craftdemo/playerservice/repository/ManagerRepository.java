package com.craftdemo.playerservice.repository;

import com.craftdemo.playerservice.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
