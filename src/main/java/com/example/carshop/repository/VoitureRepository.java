package com.example.carshop.repository;
import com.example.carshop.model.Voiture;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VoitureRepository extends MongoRepository<Voiture, String> {
    List<Voiture> findByMarqueIgnoreCase(String marque);
    List<Voiture> findByMoteurTypeIgnoreCase(String moteurType);
    // find by proprietor name (nested) - Spring Data supports nested fields:
    List<Voiture> findByProprietaireNomIgnoreCase(String nom);
}
