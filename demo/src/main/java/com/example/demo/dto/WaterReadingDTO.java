package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WaterReadingDTO {
    private LocalDateTime timestamp;
    private Double waterLevel;
}
