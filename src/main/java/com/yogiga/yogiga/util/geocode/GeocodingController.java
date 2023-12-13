package com.yogiga.yogiga.util.geocode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @PostMapping("/update-coordinates")
    public ResponseEntity<String> updateRestaurantCoordinates() {
        try {
            geocodingService.updateRestaurantsWithCoordinates();
            return ResponseEntity.ok("Coordinates update initiated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating coordinates: " + e.getMessage());
        }
    }
}

