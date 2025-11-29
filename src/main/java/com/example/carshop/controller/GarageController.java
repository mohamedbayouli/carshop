package com.example.carshop.controller;

import com.example.carshop.model.Garage;
import com.example.carshop.model.Mecanicien;
import com.example.carshop.model.Reparation;
import com.example.carshop.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
@RequiredArgsConstructor
public class GarageController {

    private final GarageService garageService;

    @GetMapping
    public List<Garage> getAll() {
        return garageService.getAllGarages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> getById(@PathVariable String id) {
        Garage g = garageService.getGarageById(id);
        return (g != null) ? ResponseEntity.ok(g) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Garage> create(@RequestBody Garage garage) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(garageService.addGarage(garage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garage> update(@PathVariable String id, @RequestBody Garage garage) {
        return ResponseEntity.ok(garageService.updateGarage(id, garage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{garageId}/mecaniciens")
    public ResponseEntity<Garage> addMecanicien(
            @PathVariable String garageId,
            @RequestBody Mecanicien mecanicien
    ) {
        return ResponseEntity.ok(garageService.addMecanicien(garageId, mecanicien));
    }

    @PostMapping("/{garageId}/mecaniciens/{mIndex}/reparations")
    public ResponseEntity<Garage> addReparation(
            @PathVariable String garageId,
            @PathVariable int mIndex,
            @RequestBody Reparation reparation
    ) {
        return ResponseEntity.ok(garageService.addReparation(garageId, mIndex, reparation));
    }
}
