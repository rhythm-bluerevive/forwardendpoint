package com.example.demo.controller;

import com.example.demo.dto.IotPayloadDTO;
import com.example.demo.model.Location;
import com.example.demo.service.DeviceDataService;
import com.example.demo.service.ReverseGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
public class IotController {

    private final DeviceDataService deviceDataService;
    private final ReverseGeocodingService reverseGeocodingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDeviceData(@RequestBody IotPayloadDTO dto) {
        // 1. Insert into dynamic per-device table
        deviceDataService.saveDeviceData(dto);

        // 2. Save location only if not already stored
        reverseGeocodingService.saveLocationIfNew(dto.getDeviceId(), dto.getLatitude(), dto.getLongitude());

        return ResponseEntity.ok("Payload stored for " + dto.getDeviceId());
    }

    // Optional: Quick health check
    @GetMapping()
    public String health() {
        return "IoT Middleware is running";
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<Map<String, Object>>> getDeviceData(@PathVariable String deviceId) {
        List<Map<String, Object>> data = deviceDataService.fetchDeviceData(deviceId);
        return ResponseEntity.ok(data);
    }


    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllDeviceLocations() {
        List<Location> locations = reverseGeocodingService.getAllLocations();
        return ResponseEntity.ok(locations);
    }


    @GetMapping("/device-ids")
    public ResponseEntity<List<String>> getAllDeviceIds() {
        List<String> ids = deviceDataService.getAllDeviceTableNames();
        return ResponseEntity.ok(ids);
    }
}
