package com.example.carshop.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReparationDetails {
    private VoitureInfo voiture;
    private Integer duree;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class VoitureInfo {
    private String marque;
    private String modele;
}
