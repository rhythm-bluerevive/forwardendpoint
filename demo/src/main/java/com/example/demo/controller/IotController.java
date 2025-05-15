package com.example.demo.controller;

import com.example.demo.dto.IotDataResponse;
import com.example.demo.dto.IotPayloadDTO;
import com.example.demo.service.IotDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot")
public class IotController {

    private final IotDataService iotDataService;

    public IotController(IotDataService iotDataService) {
        this.iotDataService = iotDataService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadIotData(@RequestBody IotPayloadDTO dto) {
        iotDataService.saveIotPayload(dto);
        return ResponseEntity.ok("Data saved successfully");
    }

    @GetMapping("/data")
    public ResponseEntity<IotDataResponse> getAllIotData() {
        return ResponseEntity.ok(iotDataService.getSegregatedData());
    }

}
