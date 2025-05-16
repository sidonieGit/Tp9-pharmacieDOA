package com.syspharma.projet.domaine.interfaces;

import com.syspharma.projet.domaine.model.Compte;

public interface IConnexion {

    /**
     * Authentifie un utilisateur.
     * @param email identifiant
     * @param motDePasse mot de passe
     * @return true si les identifiants sont valides
     */
    boolean seConnecter(String email, String motDePasse);

    /**
     * Déconnecte l'utilisateur.
     * @return true si la déconnexion a réussi
     */
    boolean seDeconnecter();

    /**
     * Met à jour les informations du compte (ex : mot de passe).
     * @param nouveauCompte les nouvelles données
     * @return true si la mise à jour est réussie
     */
    boolean miseAJourCompte(Compte nouveauCompte);

    /**
     * Vérifie si un compte est actif.
     * @return true si actif, false sinon
     */
    boolean estActif();
}
