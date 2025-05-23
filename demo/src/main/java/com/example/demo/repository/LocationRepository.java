package com.example.demo.repository;

import com.example.demo.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    @Query("SELECT DISTINCT l.deviceId FROM location l")
    List<String> findAllDeviceIds();
}