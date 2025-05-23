package com.sido.syspharma;

import com.sido.syspharma.dao.impl.MedicamentDAOImpl;
import com.sido.syspharma.dao.interfaces.IMedicamentDAO;
import com.sido.syspharma.domaine.model.Categorie;
import com.sido.syspharma.domaine.model.Medicament;
import com.sido.syspharma.exceptions.DataBaseException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class Main {

    // ✅ Le logger doit être déclaré ici (dans la classe, pas dans une méthode)
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("🚀 Log4j est bien configuré !");
        logger.debug("🔍 Ceci est un message de DEBUG");
        logger.error("❌ Ceci est un message d'ERREUR (exemple)");

        Scanner scanner = new Scanner(System.in);
        IMedicamentDAO dao = new MedicamentDAOImpl();

        System.out.println("=== SystPharma | Gestion des Médicaments ===");
        System.out.print("Souhaitez-vous (1) Insérer ou (2) Rechercher un médicament ? [1/2] : ");
        String choix = scanner.nextLine();

        try {
            if (choix.equals("1")) {
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

                boolean resultat = dao.insererMedicament(medicament);

                if (resultat) {
                    System.out.println("✅ Médicament inséré avec succès !");
                    logger.info("✅ Médicament inséré : " + designation);
                } else {
                    System.out.println("❌ Échec de l'insertion.");
                    logger.error("❌ Échec insertion médicament : " + designation);
                }

            } else if (choix.equals("2")) {
                System.out.println("\n--- Recherche de médicament par nom ---");
                System.out.print("Entrez le nom ou une partie du nom : ");
                String rechercheNom = scanner.nextLine();

                List<Medicament> resultats = dao.rechercherParNom(rechercheNom);
                if (resultats.isEmpty()) {
                    System.out.println("⚠️ Aucun médicament trouvé.");
                    logger.warn("⚠️ Aucun médicament trouvé pour : " + rechercheNom);
                } else {
                    System.out.println("🔍 Médicaments trouvés :");
                    for (Medicament m : resultats) {
                        System.out.println("- " + m.getDesignation() + " | " + m.getPrix() + " FCFA | " + m.getCategorie().getDesignation());
                    }
                    logger.info("🔍 Médicaments trouvés pour : " + rechercheNom);
                }
            } else {
                System.out.println("❌ Choix invalide.");
                logger.warn("❌ Choix utilisateur invalide : " + choix);
            }
        } catch (DataBaseException e) {
            System.err.println("🚨 Erreur lors de l'accès à la base de données : " + e.getMessage());
            logger.fatal("❌ Erreur critique DB : ", e);
        }

        scanner.close();
    }
}
