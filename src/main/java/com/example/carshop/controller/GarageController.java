package com.example.carshop.controller;

import com.example.carshop.model.Garage;
import com.example.carshop.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

//    @GetMapping
//    public String getAllGarages(Model model) {
//        model.addAttribute("garages", garageService.findAll());
//        return "garages/index";
//    }

    @GetMapping("/{id}")
    public String getGarageById(@PathVariable String id, Model model) {
        Optional<Garage> garage = garageService.findById(id);
        if (garage.isPresent()) {
            model.addAttribute("garage", garage.get());
            return "garages/details";
        } else {
            return "redirect:/garages";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("garage", new Garage());
        return "garages/create";
    }

    @PostMapping
    public String createGarage(@ModelAttribute Garage garage) {
        garageService.save(garage);
        return "redirect:/garages";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Garage> garage = garageService.findById(id);
        if (garage.isPresent()) {
            model.addAttribute("garage", garage.get());
            return "garages/edit";
        } else {
            return "redirect:/garages";
        }
    }

    @PostMapping("/update/{id}")
    public String updateGarage(@PathVariable String id, @ModelAttribute Garage garage) {
        garage.setId(id);
        garageService.save(garage);
        return "redirect:/garages";
    }

    @GetMapping("/delete/{id}")
    public String deleteGarage(@PathVariable String id) {
        garageService.deleteById(id);
        return "redirect:/garages";
    }

    @GetMapping
    public String getAllGarages(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String sort,
            Model model) {

        List<Garage> garages = garageService.findAll();

        // Apply search filter
        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.toLowerCase();
            garages = garages.stream()
                    .filter(garage ->
                            garage.getNom().toLowerCase().contains(searchLower) ||
                                    garage.getVille().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        // Apply ville filter
        if (ville != null && !ville.trim().isEmpty()) {
            garages = garages.stream()
                    .filter(garage -> ville.equals(garage.getVille()))
                    .collect(Collectors.toList());
        }

        // Apply sorting
        if (sort != null) {
            switch (sort) {
                case "nom":
                    garages.sort((g1, g2) -> g1.getNom().compareToIgnoreCase(g2.getNom()));
                    break;
                case "nom_desc":
                    garages.sort((g1, g2) -> g2.getNom().compareToIgnoreCase(g1.getNom()));
                    break;
                case "ville":
                    garages.sort((g1, g2) -> g1.getVille().compareToIgnoreCase(g2.getVille()));
                    break;
                case "ville_desc":
                    garages.sort((g1, g2) -> g2.getVille().compareToIgnoreCase(g1.getVille()));
                    break;
            }
        }

        // Get unique villes for filter dropdown
        List<String> villes = garageService.findAll().stream()
                .map(Garage::getVille)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // Calculate statistics
        long totalMecaniciens = garages.stream()
                .mapToLong(this::countMecaniciens)
                .sum();

        long villesCount = garages.stream()
                .map(Garage::getVille)
                .distinct()
                .count();

        long garagesAvecMecaniciens = garages.stream()
                .filter(this::hasMecaniciens)
                .count();

        model.addAttribute("garages", garages);
        model.addAttribute("villes", villes);
        model.addAttribute("totalMecaniciens", totalMecaniciens);
        model.addAttribute("villesCount", villesCount);
        model.addAttribute("garagesAvecMecaniciens", garagesAvecMecaniciens);

        return "garages/index";
    }

    private long countMecaniciens(Garage garage) {
        if (!hasMecaniciens(garage)) return 0;
        if (garage.isSingleMecanicien()) return 1;
        if (garage.isMultipleMecaniciens()) return garage.getMecaniciensList().size();
        return 0;
    }

    private boolean hasMecaniciens(Garage garage) {
        return garage.hasMecaniciens();
    }
}