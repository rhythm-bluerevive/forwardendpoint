package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "location")
@Data
public class Location {
    @Id
    private String deviceId;

    private Double latitude;
    private Double longitude;
    private String city;
    private String district;
    private String state;
    private String country;
    private String pincode;
}

