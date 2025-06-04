package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReverseGeocodingService {

    private final LocationRepository locationRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public void saveLocationIfNew(String deviceId, double latitude, double longitude) {
        // Only run geocoding if deviceId is new
        if (locationRepository.existsById(deviceId)) {
            return;
        }

        try {
            String url = String.format(
                    "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f",
                    latitude, longitude
            );

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, String> address = (Map<String, String>) response.get("address");

            // Debug: print all address components to console (optional)
            System.out.println("Full address response for " + deviceId + ": " + address);

            Location location = new Location();
            location.setDeviceId(deviceId);
            location.setLatitude(latitude);
            location.setLongitude(longitude);


            String village = address.get("village");
            String town = address.get("town");
            String city = address.get("city");
            String county = address.get("county");

            if (village != null) {
                if (city != null) {
                    location.setCity(village + ", " + city);
                } else if (county != null) {
                    location.setCity(village + ", " + county);
                } else {
                    location.setCity(village);
                }
            } else if (town != null) {
                location.setCity(town);
            } else if (city != null) {
                location.setCity(city);
            } else if (county != null) {
                location.setCity(county);
            } else {
                location.setCity("N/A");
            }

//            location.setDistrict(address.getOrDefault("state_district", "N/A"));

            String district = address.get("state_district");
            if (district == null || district.isEmpty()) {
                district = address.get("county");
                if (district == null || district.isEmpty()) {
                    district = "N/A";
                }
            }
            location.setDistrict(district);


            location.setState(address.getOrDefault("state", "N/A"));
            location.setCountry(address.getOrDefault("country", "N/A"));
            location.setPincode(address.getOrDefault("postcode", "N/A"));

            locationRepository.save(location);

        } catch (Exception e) {
            System.err.println(" Reverse geocoding failed for device " + deviceId + ": " + e.getMessage());
        }
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
