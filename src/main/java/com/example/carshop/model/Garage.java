package com.example.carshop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "garage")
public class Garage {
    @Id
    private String id;
    private String nom;
    private String ville;
    private List<Mecanicien> mecaniciens;
}
