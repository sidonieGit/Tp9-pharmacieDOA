package com.sido.syspharma.service.interfaces;

import com.sido.syspharma.domaine.enums.StatutCompte;
import com.sido.syspharma.domaine.model.Compte;
import com.sido.syspharma.domaine.model.Utilisateur;

import java.util.List;

public interface IServiceCompte {
    void modifierStatutCompte(Compte compte, StatutCompte nouveauStatut);

    void desactiverCompteParIdentifiant(List<Utilisateur> utilisateurs, String email);
}
