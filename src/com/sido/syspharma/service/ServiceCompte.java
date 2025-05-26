package com.sido.syspharma.service.compte;

import com.sido.syspharma.domaine.enums.StatutCompte;
import com.sido.syspharma.domaine.model.Compte;
import com.sido.syspharma.domaine.model.Utilisateur;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Service pour la gestion des comptes utilisateurs.
 */
public class ServiceCompte implements com.sido.syspharma.service.interfaces.IServiceCompte {

    private static final Logger logger = Logger.getLogger(ServiceCompte.class);

    /**
     * Modifie le statut d’un compte utilisateur.
     */
    @Override
    public void modifierStatutCompte(Compte compte, StatutCompte nouveauStatut) {
        compte.setStatut(nouveauStatut);
        logger.info("🛠️ Statut changé pour " + compte.getIdentifiant() + " -> " + nouveauStatut);
    }

    /**
     * Désactive un compte à partir de son email.
     */
    @Override
    public void desactiverCompteParIdentifiant(List<Utilisateur> utilisateurs, String email) {
        utilisateurs.stream()
                .filter(u -> u.getCompte().getIdentifiant().equalsIgnoreCase(email))
                .findFirst()
                .ifPresent(u -> {
                    u.getCompte().setStatut(StatutCompte.DESACTIVE);
                    logger.info("❌ Compte désactivé : " + email);
                });
    }
}
