package com.sido.syspharma.service;

import com.sido.syspharma.domaine.commande.ArticlePanier;
import com.sido.syspharma.domaine.commande.Commande;
import com.sido.syspharma.domaine.model.Medicament;
import com.sido.syspharma.domaine.model.Statistique;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour calculer les statistiques globales ou par pharmacie
 * à partir d'une liste de commandes.
 */
public class ServiceStatistique {

    /**
     * Calcule un objet Statistique pour une période donnée.
     *
     * @param commandes liste complète des commandes
     * @param debut     date de début de la période
     * @param fin       date de fin de la période
     * @return une instance de Statistique
     */
    public Statistique calculerStatistiques(List<Commande> commandes, LocalDate debut, LocalDate fin) {
        // Filtrer les commandes dans la période
        List<Commande> commandesFiltrees = commandes.stream()
                .filter(c -> !c.getDateCommande().isBefore(debut) && !c.getDateCommande().isAfter(fin))
                .toList();

        int nombreCommandes = commandesFiltrees.size();

        // Calcul du chiffre d’affaires total
        double chiffreAffaire = commandesFiltrees.stream()
                .mapToDouble(c -> c.getPanier().calculerMontantTotal())
                .sum();

        // Nombre total de produits vendus
        int nombreProduitsVendus = commandesFiltrees.stream()
                .flatMap(c -> c.getPanier().getArticles().stream())
                .mapToInt(ArticlePanier::getQuantite)
                .sum();

        // Nombre de clients distincts
        int nombreClients = (int) commandesFiltrees.stream()
                .map(c -> c.getClient().getEmail()) // identifier par email
                .distinct()
                .count();

        // Médicaments les plus commandés
        Map<Medicament, Integer> topMedocs = calculerMedicamentsLesPlusCommandes(commandesFiltrees);

        return new Statistique(
                nombreCommandes,
                nombreClients,
                chiffreAffaire,
                nombreProduitsVendus,
                topMedocs
        );
    }

    /**
     * Calcule la fréquence de chaque médicament commandé.
     *
     * @param commandes liste de commandes
     * @return map des médicaments et leur quantité totale
     */
    public Map<Medicament, Integer> calculerMedicamentsLesPlusCommandes(List<Commande> commandes) {
        Map<Medicament, Integer> compteur = new HashMap<>();

        for (Commande commande : commandes) {
            for (ArticlePanier article : commande.getPanier().getArticles()) {
                Medicament medoc = article.getMedicament();
                compteur.put(medoc, compteur.getOrDefault(medoc, 0) + article.getQuantite());
            }
        }

        // Tri par ordre décroissant de quantité
        return compteur.entrySet().stream()
                .sorted(Map.Entry.<Medicament, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
