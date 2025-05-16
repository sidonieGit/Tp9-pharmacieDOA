package com.syspharma.projet;

import com.syspharma.projet.dao.MedicamentDAO;
import com.syspharma.projet.domaine.model.Categorie;
import com.syspharma.projet.domaine.model.Medicament;
import com.syspharma.projet.exceptions.DataBaseException;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MedicamentDAO medicamentDAO = new MedicamentDAO();

        System.out.println("=== SystPharma | Gestion des Médicaments ===");
        System.out.print("Souhaitez-vous (1) Insérer ou (2) Rechercher un médicament ? [1/2] : ");
        String choix = scanner.nextLine();

        try {
            if (choix.equals("1")) {
                // 👇 Insertion
                System.out.println("\n--- Insertion d'un médicament ---");

                System.out.print("Désignation : ");
                String designation = scanner.nextLine();

                System.out.print("Prix : ");
                double prix = Double.parseDouble(scanner.nextLine());

                System.out.print("Description : ");
                String description = scanner.nextLine();

                System.out.print("Nom de l'image (ex: paracetamol.jpg) : ");
                String image = scanner.nextLine();

                System.out.print("Catégorie : ");
                String nomCategorie = scanner.nextLine();

                Categorie categorie = new Categorie(nomCategorie);
                Medicament medicament = new Medicament(designation, prix, description, image, categorie);

                boolean resultat = medicamentDAO.insererMedicament(medicament);

                if (resultat) {
                    System.out.println("✅ Médicament inséré avec succès !");
                } else {
                    System.out.println("❌ Échec de l'insertion.");
                }

            } else if (choix.equals("2")) {
                // 👇 Recherche
                System.out.println("\n--- Recherche de médicament par nom ---");
                System.out.print("Entrez le nom ou une partie du nom : ");
                String rechercheNom = scanner.nextLine();

                List<Medicament> resultats = medicamentDAO.rechercherParNom(rechercheNom);
                if (resultats.isEmpty()) {
                    System.out.println("⚠️ Aucun médicament trouvé.");
                } else {
                    System.out.println("🔍 Médicaments trouvés :");
                    for (Medicament m : resultats) {
                        System.out.println("- " + m.getDesignation() + " | " + m.getPrix() + " FCFA | " + m.getCategorie().getDesignation());
                    }
                }
            } else {
                System.out.println("❌ Choix invalide.");
            }
        } catch (DataBaseException e) {
            System.err.println("🚨 Erreur lors de l'accès à la base de données : " + e.getMessage());
        }

        scanner.close();
    }
}
