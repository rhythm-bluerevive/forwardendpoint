package com.example.demo.service;

import com.example.demo.dto.IotPayloadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceDataService {

    private final JdbcTemplate jdbcTemplate;

    public void saveDeviceData(IotPayloadDTO dto) {
        String table = dto.getDeviceId().toLowerCase().replace("-", "_");

        // 1️⃣ Create device table if not exists
        String createTable = String.format("""
            CREATE TABLE IF NOT EXISTS %s (
                timestamp TIMESTAMP PRIMARY KEY,
                latitude DOUBLE PRECISION,
                longitude DOUBLE PRECISION,
                installation_date DATE,
                box_status VARCHAR(50),
                pipe_id VARCHAR(100),
                box_id VARCHAR(100),
                pipe_length DOUBLE PRECISION,
                valve_status VARCHAR(50),
                water_level DOUBLE PRECISION,
                connected_to_device_id VARCHAR(100)
            );
        """, table);
        jdbcTemplate.execute(createTable);

        // 2️⃣ Insert data (skip if same timestamp already exists)
        String insert = String.format("""
            INSERT INTO %s (
                timestamp, latitude, longitude, installation_date, box_status,
                pipe_id, box_id, pipe_length, valve_status,
                water_level, connected_to_device_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (timestamp) DO NOTHING;
        """, table);

        jdbcTemplate.update(insert,
                dto.getTimestamp(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getInstallationDate(),
                dto.getBoxStatus(),
                dto.getPipeId(),
                dto.getBoxId(),
                dto.getPipeLength(),
                dto.getValveStatus(),
                dto.getWaterLevel(),
                dto.getConnectedToDeviceId()
        );
    }
}
