package com.example.carshop.repository;

import com.example.carshop.model.Garage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends MongoRepository<Garage, String> {
}