package com.sido.syspharma.domaine.model;

import com.sido.syspharma.domaine.enums.Role;
import com.sido.syspharma.domaine.enums.StatutCompte;

import java.time.LocalDate;

/**
 * Représente un compte utilisateur (client, agent, admin).
 */
public class Compte {
    private String identifiant;
    private String motDePasse;
    private StatutCompte statut;
    private LocalDate dateCreation;
    private LocalDate derniereConnexion;
    private Role role; // ✅ Ajout de rôle

    public Compte(String identifiant, String motDePasse, Role role) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.statut = StatutCompte.ACTIF;
        this.dateCreation = LocalDate.now();
        this.derniereConnexion = LocalDate.now();
        this.role = role;
    }

    // Getters et Setters

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public StatutCompte getStatut() {
        return statut;
    }

    public void setStatut(StatutCompte statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(LocalDate derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
