package com.syspharma.projet.service;

import com.syspharma.projet.domaine.commande.Commande;
import com.syspharma.projet.domaine.commande.Paiement;
import com.syspharma.projet.domaine.enums.ModePaiement;
import com.syspharma.projet.domaine.enums.StatutCommande;

/**
 * Service dédié à la gestion des paiements.
 */
public class ServicePaiement {

    /**
     * Effectue un paiement sur une commande donnée.
     *
     * @param commande     La commande à payer
     * @param modePaiement Le mode de paiement utilisé (ESPECE, MOMO, OMO)
     * @return le paiement enregistré
     */
    public Paiement effectuerPaiement(Commande commande, ModePaiement modePaiement) {
        double montant = commande.getPanier().calculerMontantTotal();

        Paiement paiement = new Paiement(montant, modePaiement, commande);
        commande.setPaiement(paiement);

        // Mise à jour du statut selon le mode
        if (modePaiement == ModePaiement.ESPECE) {
            commande.setStatut(StatutCommande.EN_ATTENTE_PAIEMENT);
        } else {
            commande.setStatut(StatutCommande.PAYE_EN_LIGNE);
        }

        return paiement;
    }

    /**
     * Calcule le montant total d’une commande.
     *
     * @param commande la commande concernée
     * @return le montant total
     */
    public double calculerMontant(Commande commande) {
        return commande.getPanier().calculerMontantTotal();
    }
}
