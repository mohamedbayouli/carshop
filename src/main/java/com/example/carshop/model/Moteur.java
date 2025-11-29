package com.example.carshop.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moteur {
    private String type;
    private String cylindree;
    private Integer chevaux;
}
