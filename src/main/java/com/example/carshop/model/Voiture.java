package com.example.carshop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "voiture")
public class Voiture {
    @Id
    private String id; // maps the _id from JSON

    private String marque;
    private String modele;
    private Integer annee;

    private Moteur moteur;

    private List<Proprietaire> proprietaire;
}
