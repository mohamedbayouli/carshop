package com.example.carshop.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entretien {
    private String date; // you can change to LocalDate if you want parsing
    private String type;
    private Integer cout;
    private GarageRef garage;
}
