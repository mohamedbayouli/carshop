package com.example.carshop.controller;

import com.example.carshop.model.Garage;
import com.example.carshop.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import java.util.*;

@Controller
@RequestMapping("/mecaniciens")
public class MecanicienController {

    @Autowired
    private GarageService garageService;

    @GetMapping("/{garageId}/{mecanicienName}")
    public String getMecanicienDetails(@PathVariable String garageId,
                                       @PathVariable String mecanicienName,
                                       Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null) {
                model.addAttribute("garage", garage);
                model.addAttribute("mecanicien", mecanicien);
                model.addAttribute("mecanicienName", mecanicienName);
                return "mecaniciens/details";
            }
        }

        return "redirect:/garages";
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findMecanicien(Garage garage, String mecanicienName) {
        if (garage.isSingleMecanicien()) {
            Map<String, Object> singleMecano = garage.getSingleMecanicien();
            if (singleMecano != null && mecanicienName.equals(singleMecano.get("nom"))) {
                return singleMecano;
            }
        } else if (garage.isMultipleMecaniciens()) {
            for (Object mecanoObj : garage.getMecaniciensList()) {
                if (mecanoObj instanceof Map) {
                    Map<String, Object> mecano = (Map<String, Object>) mecanoObj;
                    if (mecanicienName.equals(mecano.get("nom"))) {
                        return mecano;
                    }
                }
            }
        }
        return null;
    }

    @GetMapping("/new/{garageId}")
    public String showCreateMecanicienForm(@PathVariable String garageId, Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);
        if (garageOpt.isPresent()) {
            model.addAttribute("garage", garageOpt.get());
            model.addAttribute("mecanicienData", new HashMap<String, Object>());
            return "mecaniciens/create";
        }
        return "redirect:/garages";
    }

    @PostMapping("/create/{garageId}")
    public String createMecanicien(@PathVariable String garageId,
                                   @RequestParam String nom,
                                   @RequestParam Integer experience,
                                   Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();

            // Create new mechanic map
            Map<String, Object> newMecanicien = new HashMap<>();
            newMecanicien.put("nom", nom);
            newMecanicien.put("experience", experience);
            newMecanicien.put("reparations", new ArrayList<>());

            // Add to garage
            if (garage.getMecaniciens() == null) {
                // First mechanic - create as single object
                garage.setMecaniciens(newMecanicien);
            } else if (garage.isSingleMecanicien()) {
                // Convert single mechanic to list and add new one
                Map<String, Object> existingMecano = garage.getSingleMecanicien();
                List<Map<String, Object>> mecaniciensList = new ArrayList<>();
                mecaniciensList.add(existingMecano);
                mecaniciensList.add(newMecanicien);
                garage.setMecaniciens(mecaniciensList);
            } else if (garage.isMultipleMecaniciens()) {
                // Add to existing list
                List<Object> mecaniciensList = garage.getMecaniciensList();
                mecaniciensList.add(newMecanicien);
            }

            // Save the updated garage
            garageService.save(garage);

            return "redirect:/garages/" + garageId;
        }

        return "redirect:/garages";
    }

    @GetMapping("/edit/{garageId}/{mecanicienName}")
    public String showEditMecanicienForm(@PathVariable String garageId,
                                         @PathVariable String mecanicienName,
                                         Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null) {
                model.addAttribute("garage", garage);
                model.addAttribute("mecanicien", mecanicien);
                model.addAttribute("mecanicienName", mecanicienName);
                return "mecaniciens/edit";
            }
        }

        return "redirect:/garages";
    }

    @PostMapping("/update/{garageId}/{mecanicienName}")
    public String updateMecanicien(@PathVariable String garageId,
                                   @PathVariable String mecanicienName,
                                   @RequestParam String newNom,
                                   @RequestParam Integer experience,
                                   Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null) {
                // Update the mechanic
                mecanicien.put("nom", newNom);
                mecanicien.put("experience", experience);

                // Save the updated garage
                garageService.save(garage);
            }
        }

        return "redirect:/mecaniciens/" + garageId + "/" + newNom;
    }

    @GetMapping("/delete/{garageId}/{mecanicienName}")
    public String deleteMecanicien(@PathVariable String garageId,
                                   @PathVariable String mecanicienName) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();

            if (garage.isSingleMecanicien()) {
                // Remove the single mechanic
                garage.setMecaniciens(null);
            } else if (garage.isMultipleMecaniciens()) {
                // Remove from list
                List<Object> mecaniciensList = garage.getMecaniciensList();
                mecaniciensList.removeIf(mecanoObj -> {
                    if (mecanoObj instanceof Map) {
                        Map<String, Object> mecano = (Map<String, Object>) mecanoObj;
                        return mecanicienName.equals(mecano.get("nom"));
                    }
                    return false;
                });

                // If only one mechanic remains, convert to single object
                if (mecaniciensList.size() == 1) {
                    garage.setMecaniciens(mecaniciensList.get(0));
                }
            }

            // Save the updated garage
            garageService.save(garage);
        }

        return "redirect:/garages/" + garageId;
    }

    @GetMapping("/{garageId}/{mecanicienName}/reparations/new")
    public String showCreateReparationForm(@PathVariable String garageId,
                                           @PathVariable String mecanicienName,
                                           Model model) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null) {
                model.addAttribute("garage", garage);
                model.addAttribute("mecanicien", mecanicien);
                model.addAttribute("mecanicienName", mecanicienName);
                return "reparations/create";
            }
        }

        return "redirect:/garages";
    }

    @PostMapping("/{garageId}/{mecanicienName}/reparations/create")
    public String createReparation(@PathVariable String garageId,
                                   @PathVariable String mecanicienName,
                                   @RequestParam String type,
                                   @RequestParam String date,
                                   @RequestParam Double cout,
                                   @RequestParam String marque,
                                   @RequestParam String modele,
                                   @RequestParam Integer duree) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null) {
                // Create new repair
                Map<String, Object> newReparation = new LinkedHashMap<>();
                newReparation.put("type", type);
                newReparation.put("date", date);
                newReparation.put("cout", cout);

                // Create details
                Map<String, Object> details = new LinkedHashMap<>();
                Map<String, Object> voiture = new LinkedHashMap<>();
                voiture.put("marque", marque);
                voiture.put("modele", modele);
                details.put("voiture", voiture);
                details.put("duree", duree);

                newReparation.put("details", details);

                // Add to mechanic's repairs
                if (!mecanicien.containsKey("reparations")) {
                    mecanicien.put("reparations", new ArrayList<Map<String, Object>>());
                }

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> reparations = (List<Map<String, Object>>) mecanicien.get("reparations");
                reparations.add(newReparation);

                // Save the updated garage
                garageService.save(garage);
            }
        }

        return "redirect:/mecaniciens/" + garageId + "/" + mecanicienName;
    }

    @GetMapping("/{garageId}/{mecanicienName}/reparations/delete/{index}")
    public String deleteReparation(@PathVariable String garageId,
                                   @PathVariable String mecanicienName,
                                   @PathVariable int index) {
        Optional<Garage> garageOpt = garageService.findById(garageId);

        if (garageOpt.isPresent()) {
            Garage garage = garageOpt.get();
            Map<String, Object> mecanicien = findMecanicien(garage, mecanicienName);

            if (mecanicien != null && mecanicien.containsKey("reparations")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> reparations = (List<Map<String, Object>>) mecanicien.get("reparations");

                if (index >= 0 && index < reparations.size()) {
                    reparations.remove(index);

                    // Save the updated garage
                    garageService.save(garage);
                }
            }
        }

        return "redirect:/mecaniciens/" + garageId + "/" + mecanicienName;
    }
}