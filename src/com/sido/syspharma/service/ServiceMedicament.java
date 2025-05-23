package com.sido.syspharma.service;

import com.sido.syspharma.domaine.model.Assurance;
import com.sido.syspharma.domaine.model.Medicament;
import com.sido.syspharma.domaine.model.Pharmacie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service de recherche métier pour médicaments, pharmacies et assurances.
 */
public class ServiceMedicament {

    // 🔍 Rechercher un médicament par nom dans toutes les pharmacies
    public Medicament rechercherMedicamentParNom(String nomMedoc, List<Pharmacie> pharmacies) {
        for (Pharmacie pharmacie : pharmacies) {
            for (Medicament medicament : pharmacie.getStock()) {
                if (medicament.getDesignation().equalsIgnoreCase(nomMedoc)) {
                    return medicament;
                }
            }
        }
        return null;
    }

    // 🔍 Rechercher un médicament par nom dans une pharmacie donnée
    public Medicament rechercherMedicamentParNomDansPharmacie(String nomMedoc, Pharmacie pharmacie) {
        return pharmacie.getStock().stream()
                .filter(m -> m.getDesignation().equalsIgnoreCase(nomMedoc))
                .findFirst()
                .orElse(null);
    }

    // 🔍 Rechercher un médicament par nom et catégorie
    public Medicament rechercherMedicamentParNomEtCategorie(String nomMedoc, String nomCategorie, List<Pharmacie> pharmacies) {
        return pharmacies.stream()
                .flatMap(p -> p.getStock().stream())
                .filter(m -> m.getDesignation().equalsIgnoreCase(nomMedoc))
                .filter(m -> m.getCategorie().getDesignation().equalsIgnoreCase(nomCategorie))
                .findFirst()
                .orElse(null);
    }

    // 🔍 Rechercher tous les médicaments d'une catégorie dans une pharmacie
    public List<Medicament> rechercherMedicamentsParCategorie(String categorie, Pharmacie pharmacie) {
        return pharmacie.getStock().stream()
                .filter(m -> m.getCategorie().getDesignation().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }

    // 🔍 Rechercher tous les médicaments disponibles dans une pharmacie
    public List<Medicament> rechercherMedicamentsParPharmacie(String nomPharmacie, List<Pharmacie> pharmacies) {
        Optional<Pharmacie> pharmacieOpt = pharmacies.stream()
                .filter(p -> p.getDesignation().equalsIgnoreCase(nomPharmacie))
                .findFirst();

        return pharmacieOpt.map(Pharmacie::getStock).orElse(List.of());
    }




}
