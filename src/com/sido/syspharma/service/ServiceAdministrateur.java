package com.sido.syspharma.service;

import com.sido.syspharma.domaine.enums.StatutCompte;
import com.sido.syspharma.domaine.model.AgentPharmacie;
import com.sido.syspharma.domaine.model.Compte;
import com.sido.syspharma.domaine.model.Pharmacie;
import com.sido.syspharma.domaine.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Service dédié à l’administration : gestion des pharmacies et utilisateurs.
 */
public class ServiceAdministrateur {

    private List<Pharmacie> pharmacies = new ArrayList<>();
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    // ---------- Gestion des pharmacies ----------

    public void ajouterPharmacie(Pharmacie p) {
        pharmacies.add(p);
    }

    public void supprimerPharmacie(Pharmacie p) {
        pharmacies.remove(p);
    }

    public void modifierPharmacie(Pharmacie p, String nouvelleAdresse) {
        p.setAdresse(nouvelleAdresse);
    }

    public List<Pharmacie> listerPharmacies() {
        return pharmacies;
    }

    // ---------- Gestion des comptes utilisateurs ----------

    public void creerCompteAgent(String nom, String prenom, String email, String adresse, String telephone, String password, String matricule, Pharmacie pharmacie) {
        AgentPharmacie agent = new AgentPharmacie(nom, prenom, email, adresse, telephone, password, matricule);
        pharmacie.setAgentResponsable(agent);
        utilisateurs.add(agent);
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurs;
    }

    public void modifierStatutCompte(Compte compte, StatutCompte nouveauStatut) {
        compte.setStatut(nouveauStatut);
    }

    public void desactiverCompteParIdentifiant(String email) {
        utilisateurs.stream()
                .filter(u -> u.getCompte().getIdentifiant().equalsIgnoreCase(email))
                .findFirst()
                .ifPresent(u -> u.getCompte().setStatut(StatutCompte.DESACTIVE));
    }
}
