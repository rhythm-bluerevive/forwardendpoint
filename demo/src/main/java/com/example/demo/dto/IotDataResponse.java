package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class IotDataResponse {
    private String deviceId;
    private Double latitude;
    private Double longitude;
    private LocalDate installationDate;
    private String boxStatus;

    private String pipeId;
    private String boxId;
    private Double pipeLength;
    private String valveStatus;

    private List<WaterReadingDTO> readings;
}
