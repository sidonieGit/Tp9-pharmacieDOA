package com.sido.syspharma.service;

import com.sido.syspharma.domaine.model.Assurance;
import com.sido.syspharma.domaine.model.Pharmacie;

import java.util.List;

public class serviceAssurance {
    // 🔍 Rechercher une assurance dans une pharmacie par son nom
    public Assurance rechercherAssuranceParNomEtPharmacie(String nomAssurance, Pharmacie pharmacie) {
        return pharmacie.getAssurances().stream()
                .filter(a -> a.getDesignation().equalsIgnoreCase(nomAssurance))
                .findFirst()
                .orElse(null);
    }

    // 🔍 Rechercher une assurance par nom uniquement
    public Assurance rechercherAssuranceParNom(String nomAssurance, List<Pharmacie> pharmacies) {
        for (Pharmacie pharmacie : pharmacies) {
            for (Assurance assurance : pharmacie.getAssurances()) {
                if (assurance.getDesignation().equalsIgnoreCase(nomAssurance)) {
                    return assurance;
                }
            }
        }
        return null;
    }
}
