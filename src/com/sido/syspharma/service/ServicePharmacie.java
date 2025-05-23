package com.sido.syspharma.service;

import com.sido.syspharma.domaine.model.Pharmacie;

import java.util.List;
import java.util.stream.Collectors;

public class ServicePharmacie {
    // 🔍 Rechercher les pharmacies qui vendent un médicament donné
    public List<Pharmacie> rechercherPharmaciesParNomMedicament(String nomMedoc, List<Pharmacie> pharmacies) {
        return pharmacies.stream()
                .filter(p -> p.getStock().stream()
                        .anyMatch(m -> m.getDesignation().equalsIgnoreCase(nomMedoc)))
                .collect(Collectors.toList());
    }
}
