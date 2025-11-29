package com.example.carshop.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proprietaire {
    private String nom;
    private Integer age;
    private String pays;
    private List<Entretien> entretiens;
}
