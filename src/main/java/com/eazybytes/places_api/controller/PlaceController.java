package com.eazybytes.places_api.controller;

import com.eazybytes.places_api.exceptions_handler.ResourceNotFoundException;
import com.eazybytes.places_api.models.Place;
import com.eazybytes.places_api.repo.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PlaceController {
    @Autowired
    private PlaceRepository repository;

    //get places
    @CrossOrigin
    @GetMapping("/places")
    public List<Place> getAll() {
        return  this.repository.findAll();
    }
    //get place {id}
    @CrossOrigin
    @GetMapping("/places/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable(value = "id") Long placeId) throws ResourceNotFoundException {
        Place byId = repository.findById(placeId).orElseThrow(() ->
                new ResourceNotFoundException("Place not found of this id :: " + placeId));
        return ResponseEntity.ok().body(byId);
    }

    //add place
    @CrossOrigin
    @PostMapping("/places")
    public Place addPlace(@RequestBody Place place) {
        return this.repository.save(place);
    }
    //delete

    //update
    @CrossOrigin
    @PutMapping("places/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable(value = "id") Long placeId, @Valid @RequestBody Place placeDetails) throws ResourceNotFoundException {
        Place place = repository.findById(placeId).orElseThrow(() -> new ResourceNotFoundException("place not found"));
        assert place != null;
        place.setName(placeDetails.getName());
        place.setDescription(placeDetails.getDescription());
        place.setImageUrl(placeDetails.getImageUrl());
        return ResponseEntity.ok().body(this.repository.save(place));
    }
    @CrossOrigin
    @DeleteMapping("places/{id}")
    public Map<String, Boolean> deletePlace(@PathVariable(value = "id") Long placeId) throws ResourceNotFoundException {
        Place place = repository.findById(placeId).orElseThrow(() -> new ResourceNotFoundException("place not found"));
        this.repository.delete(place);

        Map <String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}
