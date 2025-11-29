package com.example.carshop.service;

import com.example.carshop.model.*;
import com.example.carshop.model.Voiture;
import com.example.carshop.repository.GarageRepository;
import com.example.carshop.repository.VoitureRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoitureService {
    private final VoitureRepository voitureRepo;
    private final GarageRepository garageRepo;

    public List<Voiture> findAll() { return voitureRepo.findAll(); }
    public Optional<Voiture> findById(String id) { return voitureRepo.findById(id); }
    public Voiture create(Voiture v) { return voitureRepo.save(v); }
    public Voiture update(String id, Voiture updates) {
        return voitureRepo.findById(id).map(v -> {
            v.setMarque(updates.getMarque());
            v.setModele(updates.getModele());
            v.setAnnee(updates.getAnnee());
            v.setMoteur(updates.getMoteur());
            v.setProprietaire(updates.getProprietaire());
            return voitureRepo.save(v);
        });
    }
    public void delete(String id) { voitureRepo.deleteById(id); }

    // add an entretien to a proprietor (by owner name)
    public Voiture addEntretien(String voitureId, int proprietorIndex, Entretien entretien) {
        Voiture v = voitureRepo.findById(voitureId);
        if (v.getProprietaire() == null || proprietorIndex >= v.getProprietaire().size()) throw new IllegalArgumentException("Invalid proprietor index");
        v.getProprietaire().get(proprietorIndex).getEntretiens().add(entretien);
        return voitureRepo.save(v);
    }
}
