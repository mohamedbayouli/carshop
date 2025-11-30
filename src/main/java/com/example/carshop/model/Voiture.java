package com.example.carshop.model;

public class Voiture {
    private String marque;
    private String modele;

    // Constructors
    public Voiture() {}

    public Voiture(String marque, String modele) {
        this.marque = marque;
        this.modele = modele;
    }

    // Getters and Setters
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
}