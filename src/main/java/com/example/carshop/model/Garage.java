package com.example.carshop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "garage")
public class Garage {
    @Id
    private String id;
    private String nom;
    private String ville;

    // Use a flexible approach for mecaniciens
    private Object mecaniciens;

    // Additional properties map for any unexpected fields
    private Map<String, Object> otherProperties = new HashMap<>();

    // Constructors
    public Garage() {}

    public Garage(String id, String nom, String ville) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public Object getMecaniciens() { return mecaniciens; }
    public void setMecaniciens(Object mecaniciens) { this.mecaniciens = mecaniciens; }

    // Helper methods that safely handle the mecaniciens object
    public boolean hasMecaniciens() {
        return mecaniciens != null;
    }

    public boolean isSingleMecanicien() {
        if (mecaniciens == null) return false;

        // Check if it's a Map (which represents a single mechanic)
        if (mecaniciens instanceof Map) {
            Map<?, ?> mecanoMap = (Map<?, ?>) mecaniciens;
            return mecanoMap.containsKey("nom") || mecanoMap.containsKey("experience");
        }
        return false;
    }

    public boolean isMultipleMecaniciens() {
        if (mecaniciens == null) return false;
        return mecaniciens instanceof List;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getSingleMecanicien() {
        if (isSingleMecanicien()) {
            return (Map<String, Object>) mecaniciens;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Object> getMecaniciensList() {
        if (isMultipleMecaniciens()) {
            return (List<Object>) mecaniciens;
        }
        return null;
    }

    // Handle any additional properties that might come from MongoDB
    @JsonAnyGetter
    public Map<String, Object> getOtherProperties() {
        return otherProperties;
    }

    @JsonAnySetter
    public void setOtherProperty(String name, Object value) {
        otherProperties.put(name, value);
    }

}