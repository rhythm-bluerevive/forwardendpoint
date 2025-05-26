package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class IotPayloadDTO {
    // Device fields
    private String deviceId;
    private Double latitude;
    private Double longitude;
    private LocalDate installationDate;
    private String boxStatus;
    private String connectedToDeviceId;
    private String pipeId;
    private String boxId;
    private Double pipeLength;
    private String valveStatus;
    private LocalDateTime timestamp;
    private Double waterLevel;
}
