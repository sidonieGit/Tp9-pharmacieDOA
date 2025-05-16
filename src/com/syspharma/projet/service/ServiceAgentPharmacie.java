package com.syspharma.projet.service;

import com.syspharma.projet.domaine.commande.Commande;
import com.syspharma.projet.domaine.enums.StatutCommande;
import com.syspharma.projet.domaine.model.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service lié aux actions de l’agent de pharmacie : gestion des stocks et assurances.
 */
public class ServiceAgentPharmacie {

    // ---------- Gestion des médicaments ----------

    public void ajouterMedicament(Pharmacie pharmacie, Medicament medicament) {
        pharmacie.getStock().add(medicament);
    }

    public void supprimerMedicament(Pharmacie pharmacie, Medicament medicament) {
        pharmacie.getStock().remove(medicament);
    }

    public void modifierMedicament(Medicament medicament, String nouvelleDescription) {
        medicament.setDescription(nouvelleDescription);
    }

    public List<Medicament> listerMedicaments(Pharmacie pharmacie) {
        return pharmacie.getStock();
    }

    // ---------- Gestion des assurances ----------

    public void ajouterAssurance(Pharmacie pharmacie, Assurance assurance) {
        pharmacie.getAssurances().add(assurance);
    }

    public void supprimerAssurance(Pharmacie pharmacie, Assurance assurance) {
        pharmacie.getAssurances().remove(assurance);
    }

    // ---------- Interaction avec les services de commande ----------


    /**
     * Lister les clients ayant passé une commande dans une pharmacie,
     * avec ou sans filtre de statut.
     */
    public Set<Client> listerClientsAyantCommandeDansPharmacie(List<Commande> commandes, Pharmacie pharmacie, StatutCommande statut) {
        return commandes.stream()
                .filter(c -> c.getPharmacie().equals(pharmacie))
                .filter(c -> statut == null || c.getStatut().equals(statut))
                .map(Commande::getClient)
                .collect(Collectors.toSet());
    }

    /**
     * Vérifie dans quelle pharmacie une commande a été enregistrée.
     */
    public Pharmacie verifierPharmacieCommande(Client client, String numeroCommande) {
        return client.getCommandes().stream()
                .filter(c -> c.getNumeroCommande().equals(numeroCommande))
                .map(Commande::getPharmacie)
                .findFirst()
                .orElse(null);
    }
}
