package com.syspharma.projet.service;

import com.syspharma.projet.domaine.commande.Commande;
import com.syspharma.projet.domaine.commande.Panier;
import com.syspharma.projet.domaine.enums.StatutCommande;
import com.syspharma.projet.domaine.model.Client;
import com.syspharma.projet.domaine.model.Medicament;
import com.syspharma.projet.domaine.model.Pharmacie;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCommande {

    public Commande passerCommande(Client client, Pharmacie pharmacie) {
        Panier panier = client.getPanier();
        panier.valider();
        Commande commande = new Commande(client, panier, pharmacie);
        client.ajouterCommande(commande);
        return commande;
    }

    public List<Commande> listerCommandesParPharmacie(List<Commande> commandes, Pharmacie pharmacie) {
        return commandes.stream()
                .filter(c -> c.getPharmacie().equals(pharmacie))
                .toList();
    }

    public List<Commande> listerCommandesParClient(Client client) {
        return client.getCommandes();
    }

    public Commande consulterCommandeParDate(Client client, LocalDate date) {
        return client.getCommandes().stream()
                .filter(c -> c.getDateCommande().equals(date))
                .findFirst().orElse(null);
    }

    public List<Medicament> listerMedicamentsCommandes(Client client, String numeroCommande) {
        return client.getCommandes().stream()
                .filter(c -> c.getNumeroCommande().equals(numeroCommande))
                .flatMap(c -> c.getPanier().getArticles().stream())
                .map(a -> a.getMedicament())
                .toList();
    }

    public void annulerCommande(Commande commande) {
        commande.setStatut(StatutCommande.ANNULEE);
    }

    public void confirmerLivraison(Commande commande) {
        commande.setStatut(StatutCommande.LIVREE);
    }
}
