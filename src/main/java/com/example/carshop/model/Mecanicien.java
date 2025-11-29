package com.example.carshop.model;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mecanicien {
    private String nom;
    private Integer experience;
    private List<Reparation> reparations;
}
