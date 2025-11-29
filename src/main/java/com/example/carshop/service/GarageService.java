package com.example.carshop.service;



import com.example.carshop.repository.GarageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService {

    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public List<Garage> getAllGarages() {
        return garageRepository.findAll();
    }

    @Override
    public Garage getGarageById(String id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage not found with id: " + id));
    }

    @Override
    public Garage addGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    @Override
    public Garage updateGarage(String id, Garage garage) {
        Garage existing = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage not found with id: " + id));

        existing.setNom(garage.getNom());
        existing.setVille(garage.getVille());
        existing.setMecaniciens(garage.getMecaniciens());

        return garageRepository.save(existing);
    }

    @Override
    public void deleteGarage(String id) {
        garageRepository.deleteById(id);
    }
}
