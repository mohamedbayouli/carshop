package com.example.carshop.model;

public class Details {
    private Voiture voiture;
    private Integer duree;

    // Constructors
    public Details() {}

    public Details(Voiture voiture, Integer duree) {
        this.voiture = voiture;
        this.duree = duree;
    }

    // Getters and Setters
    public Voiture getVoiture() { return voiture; }
    public void setVoiture(Voiture voiture) { this.voiture = voiture; }

    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }
}