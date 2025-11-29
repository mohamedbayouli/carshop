package com.example.carshop.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reparation {
    private String type;
    private String date;
    private Integer cout;
    private ReparationDetails details;
}
