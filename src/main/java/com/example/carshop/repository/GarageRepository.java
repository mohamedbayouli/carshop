package com.example.carshop.repository;

import com.example.carshop.model.Garage;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GarageRepository extends MongoRepository<Garage, String> {
    List<Garage> findByVilleIgnoreCase(String ville);
    Garage findByNomIgnoreCase(String nom);
}