package com.example.demo.repository;

import com.example.demo.model.WaterLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterLevelRepository extends JpaRepository<WaterLevel, Long> {}
