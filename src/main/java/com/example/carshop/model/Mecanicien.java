package com.example.carshop.model;

import java.util.List;

public class Mecanicien {
    private String nom;
    private Integer experience;
    private List<Reparation> reparations;

    // Constructors
    public Mecanicien() {}

    public Mecanicien(String nom, Integer experience) {
        this.nom = nom;
        this.experience = experience;
    }

    // Getters and Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public List<Reparation> getReparations() { return reparations; }
    public void setReparations(List<Reparation> reparations) { this.reparations = reparations; }
}