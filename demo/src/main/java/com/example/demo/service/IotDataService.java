package com.example.demo.service;

import com.example.demo.dto.IotPayloadDTO;
import com.example.demo.dto.IotDataResponse;
import com.example.demo.model.Device;
import com.example.demo.model.Pipe;
import com.example.demo.model.WaterLevel;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.PipeRepository;
import com.example.demo.repository.WaterLevelRepository;
import org.springframework.stereotype.Service;

@Service
public class IotDataService {

    private final DeviceRepository deviceRepository;
    private final PipeRepository pipeRepository;
    private final WaterLevelRepository waterLevelRepository;

    public IotDataService(DeviceRepository deviceRepository, PipeRepository pipeRepository, WaterLevelRepository waterLevelRepository) {
        this.deviceRepository = deviceRepository;
        this.pipeRepository = pipeRepository;
        this.waterLevelRepository = waterLevelRepository;
    }

    // Saving logic from POST /api/iot/upload
    public void saveIotPayload(IotPayloadDTO dto) {
        Device device = Device.builder()
                .deviceId(dto.getDeviceId())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .installationDate(dto.getInstallationDate())
                .boxStatus(dto.getBoxStatus())
                .build();
        deviceRepository.save(device);

        Pipe pipe = Pipe.builder()
                .pipeId(dto.getPipeId())
                .boxId(dto.getBoxId())
                .pipeLength(dto.getPipeLength())
                .valveStatus(dto.getValveStatus())
                .build();
        pipeRepository.save(pipe);

        WaterLevel waterLevel = WaterLevel.builder()
                .deviceId(dto.getDeviceId())
                .timestamp(dto.getTimestamp())
                .waterLevel(dto.getWaterLevel())
                .build();
        waterLevelRepository.save(waterLevel);
    }

    // Retrieval logic for GET /api/iot/data
    public IotDataResponse getSegregatedData() {
        return new IotDataResponse(
                deviceRepository.findAll(),
                pipeRepository.findAll(),
                waterLevelRepository.findAll()
        );
    }
}
