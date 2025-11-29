package com.example.carshop.service;

import com.example.carshop.model.Garage;
import com.example.carshop.model.Mecanicien;
import com.example.carshop.model.Reparation;
import com.example.carshop.repository.GarageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService {

    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    public Garage getGarageById(String id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage not found with id: " + id));
    }

    public Garage addGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    public Garage updateGarage(String id, Garage garage) {
        Garage existing = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage not found with id: " + id));

        existing.setNom(garage.getNom());
        existing.setVille(garage.getVille());
        existing.setMecaniciens(garage.getMecaniciens());

        return garageRepository.save(existing);
    }

    public void deleteGarage(String id) {
        garageRepository.deleteById(id);
    }

    public Garage addMecanicien(String garageId, Mecanicien mecanicien) {
        Garage g = garageRepository.findById(garageId).orElseThrow(() -> new RuntimeException("Garage not found"));
        if (g.getMecaniciens() != null) {
            g.getMecaniciens().add(mecanicien);
        } else {
            g.setMecaniciens(List.of(mecanicien));
        }
        return garageRepository.save(g);
    }

    // -------------------- ADD RÉPARATION --------------------
    public Garage addReparation(String garageId, int mIndex, Reparation reparation) {
        Garage g = garageRepository.findById(garageId).orElseThrow(() -> new RuntimeException("Garage not found"));
        if (g.getMecaniciens() == null || mIndex >= g.getMecaniciens().size()) {
            throw new RuntimeException("Invalid mecanicien index");
        }
        Mecanicien m = g.getMecaniciens().get(mIndex);
        if (m.getReparations() != null) {
            m.getReparations().add(reparation);
        } else {
            m.setReparations(List.of(reparation));
        }
        return garageRepository.save(g);
    }
}
