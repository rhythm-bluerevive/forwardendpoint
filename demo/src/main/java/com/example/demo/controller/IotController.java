package com.example.demo.controller;

import com.example.demo.dto.IotPayloadDTO;
import com.example.demo.service.DeviceDataService;
import com.example.demo.service.ReverseGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
public class IotController {

    private final DeviceDataService deviceDataService;
    private final ReverseGeocodingService reverseGeocodingService;

    // ✅ POST: Receive and route incoming payload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDeviceData(@RequestBody IotPayloadDTO dto) {
        // 1. Insert into dynamic per-device table
        deviceDataService.saveDeviceData(dto);

        // 2. Save location only if not already stored
        reverseGeocodingService.saveLocationIfNew(dto.getDeviceId(), dto.getLatitude(), dto.getLongitude());

        return ResponseEntity.ok("✅ Payload stored for " + dto.getDeviceId());
    }

    // Optional: Quick health check
    @GetMapping("/")
    public String health() {
        return "✅ IoT Middleware is running";
    }
}
