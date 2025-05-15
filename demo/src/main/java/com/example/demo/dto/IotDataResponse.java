package com.example.demo.dto;

import com.example.demo.model.Device;
import com.example.demo.model.Pipe;
import com.example.demo.model.WaterLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IotDataResponse {
    private List<Device> devices;
    private List<Pipe> pipes;
    private List<WaterLevel> waterLevels;
}
