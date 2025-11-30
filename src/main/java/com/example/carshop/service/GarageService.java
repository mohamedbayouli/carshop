package com.example.carshop.service;

import com.example.carshop.model.Garage;
import com.example.carshop.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    public List<Garage> findAll() {
        return garageRepository.findAll();
    }

    public Optional<Garage> findById(String id) {
        return garageRepository.findById(id);
    }

    public Garage save(Garage garage) {
        return garageRepository.save(garage);
    }

    public void deleteById(String id) {
        garageRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return garageRepository.existsById(id);
    }
}