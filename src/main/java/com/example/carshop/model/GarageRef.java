package com.example.carshop.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarageRef {
    private String nom;
    private String ville;
}
