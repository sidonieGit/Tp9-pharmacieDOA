package com.syspharma.projet.service;

import com.syspharma.projet.domaine.commande.ArticlePanier;
import com.syspharma.projet.domaine.commande.Panier;
import com.syspharma.projet.domaine.model.Medicament;

public class ServicePanier {

    public void ajouterArticle(Panier panier, Medicament medicament, int quantite) {
        panier.ajouterArticle(new ArticlePanier(medicament, quantite));
    }

    public void supprimerArticle(Panier panier, Medicament medicament) {
        panier.supprimerArticle(medicament);
    }

    public void viderPanier(Panier panier) {
        panier.vider();
    }

    public void incrementerQuantite(Panier panier, Medicament medicament) {
        panier.incrementerQuantite(medicament);
    }

    public void decrementerQuantite(Panier panier, Medicament medicament) {
        panier.decrementerQuantite(medicament);
    }

    public double calculerMontant(Panier panier) {
        return panier.calculerMontantTotal();
    }
}
