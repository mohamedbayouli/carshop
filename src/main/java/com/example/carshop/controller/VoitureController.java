package com.example.carshop.controller;

import com.example.carshop.model.*;
import com.example.carshop.service.VoitureService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voitures")
@RequiredArgsConstructor
public class VoitureController {
    private final VoitureService voitureService;

    @GetMapping
    public List<Voiture> all() { return voitureService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Voiture> get(@PathVariable String id) {
        return voitureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Voiture> create(@Validated @RequestBody Voiture voiture) {
        Voiture created = voitureService.create(voiture);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voiture> update(@PathVariable String id, @Validated @RequestBody Voiture voiture) {
        return ResponseEntity.ok(voitureService.update(id, voiture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        voitureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/proprietaires/{pIndex}/entretiens")
    public ResponseEntity<Voiture> addEntretien(@PathVariable String id, @PathVariable int pIndex, @RequestBody Entretien entretien) {
        return ResponseEntity.ok(voitureService.addEntretien(id, pIndex, entretien));
    }

    @GetMapping("/search")
    public List<Voiture> search(@RequestParam(required = false) String marque,
                                @RequestParam(required = false) String proprietaireNom,
                                @RequestParam(required = false) String model
                                ) {
        if (marque != null) return voitureService.findByMarque(marque);
        if (proprietaireNom != null) return voitureService.findByProprietaireNom(proprietaireNom);
        if (model !=null) return voitureService.findByMoteurTypeIgnoreCase(model);
        return voitureService.findAll();
    }
}

