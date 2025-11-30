package com.example.carshop.model;

import java.time.LocalDate;

public class Reparation {
    private String type;
    private LocalDate date;
    private Double cout;
    private Details details;

    // Constructors
    public Reparation() {}

    public Reparation(String type, LocalDate date, Double cout, Details details) {
        this.type = type;
        this.date = date;
        this.cout = cout;
        this.details = details;
    }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getCout() { return cout; }
    public void setCout(Double cout) { this.cout = cout; }

    public Details getDetails() { return details; }
    public void setDetails(Details details) { this.details = details; }
}