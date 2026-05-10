package com.example.inventorysystem.Location;

import com.example.inventorysystem.Exceptions.ResourceNotFoundException;
import com.example.inventorysystem.SanitizationUtil;
import com.example.inventorysystem.Tag.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public ResponseEntity<?> location(){
        return ResponseEntity.ok(locationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> newLocation(Location location) {
        Location savedLocation = null;

        location.setName(SanitizationUtil.sanitize(location.getName()));

        try {
            savedLocation = this.locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            savedLocation = this.locationRepository.findByName(location.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found."));
        }
        return ResponseEntity.ok(savedLocation);
    }
}
