package com.sido.syspharma.domaine.model;

import com.sido.syspharma.domaine.enums.Role;
import com.sido.syspharma.dao.interfaces.IConnexion;

/**
 * Classe de base abstraite pour les utilisateurs du système.
 */
public abstract class Utilisateur implements IConnexion {
    protected String nom;
    protected String prenom;
    protected String email;
    protected String adresse;
    protected String telephone;
    protected String password;
    protected Compte compte;

    public Utilisateur(String nom, String prenom, String email, String adresse, String telephone, String password, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.password = password;
        this.compte = new Compte(email, password, role); // ✅ rôle passé à Compte
    }

    // Getters
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
    public String getTelephone() { return telephone; }
    public String getPassword() { return password; }
    public Compte getCompte() { return compte; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setPassword(String password) { this.password = password; }
    public void setCompte(Compte compte) { this.compte = compte; }

    // Méthodes IConnexion
    @Override
    public boolean seConnecter(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    @Override
    public boolean seDeconnecter() {
        return true;
    }

    @Override
    public boolean miseAJourCompte(Compte compte) {
        this.compte.setMotDePasse(compte.getMotDePasse());
        return true;
    }

    @Override
    public boolean estActif() {
        return this.compte.getStatut().isActif(); // ✅ méthode bien en place maintenant
    }
}
