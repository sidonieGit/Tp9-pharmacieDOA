// src/com/sido/syspharma/presentation/Main.java
package com.sido.syspharma.presentation;

import com.sido.syspharma.database.TableCreator;
import com.sido.syspharma.dao.impl.ClientDAOImpl;
import com.sido.syspharma.dao.impl.MedicamentDAOImpl;
import com.sido.syspharma.dao.interfaces.IMedicamentDAO;
import com.sido.syspharma.domaine.model.Categorie;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.domaine.model.Medicament;
import com.sido.syspharma.exceptions.DataBaseException;
import com.sido.syspharma.service.ServiceClient;
import com.sido.syspharma.util.ValidateurFormulaire;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        try {
            // ✅ Création automatique des tables si elles n’existent pas
            TableCreator.createTableClientIfNotExists();
            TableCreator.createTableMedicamentIfNotExists();
        } catch (DataBaseException e) {
            logger.fatal("🚨 Erreur lors de la création des tables : " + e.getMessage());
            System.out.println("❌ Impossible de démarrer : problème de structure BDD.");
            return;
        }


        Scanner scanner = new Scanner(System.in);
        ServiceClient serviceClient = new ServiceClient(new ClientDAOImpl());
        IMedicamentDAO medocDAO = new MedicamentDAOImpl();

        System.out.println("=== 📦 SystPharma ===");
        System.out.print("1️⃣ Créer un compte ou 2️⃣ Se connecter ? [1/2] : ");
        String choix = scanner.nextLine();

        try {
            if ("1".equals(choix)) {
                System.out.println("📝 Création d’un compte client");

                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Prénom : "); String prenom = scanner.nextLine();

                System.out.print("Email : "); String email = scanner.nextLine();
                if (!ValidateurFormulaire.emailValide(email)) {
                    System.out.println("❌ Email invalide !");
                    return;
                }

                System.out.print("Adresse : "); String adresse = scanner.nextLine();

                System.out.print("Téléphone : "); String tel = scanner.nextLine();
                if (!ValidateurFormulaire.telephoneValide(tel)) {
                    System.out.println("❌ Numéro invalide !");
                    return;
                }

                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();
                if (!ValidateurFormulaire.motDePasseValide(pwd)) {
                    System.out.println("❌ Mot de passe trop court !");
                    return;
                }

                Client client = new Client(nom, prenom, email, adresse, tel, pwd, null);

                if (serviceClient.creerCompte(client)) {
                    System.out.println("✅ Compte créé !");
                } else {
                    System.out.println("⚠️ Email déjà utilisé !");
                }

            } else if ("2".equals(choix)) {
                System.out.print("Email : "); String email = scanner.nextLine();
                if (!ValidateurFormulaire.emailValide(email)) {
                    System.out.println("❌ Email invalide !");
                    return;
                }

                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();

                if (serviceClient.seConnecter(email, pwd)) {
                    System.out.println("🔓 Connexion réussie !");
                    logger.info("Utilisateur connecté : " + email);

                    Optional<Client> clientOpt = serviceClient.rechercherParEmail(email);

                    // ➕ Action sur médicament
                    System.out.print("Souhaitez-vous (1) Insérer ou (2) Rechercher un médicament ? [1/2] : ");
                    String action = scanner.nextLine();

                    if ("1".equals(action)) {
                        System.out.println("📝 Insertion médicament");

                        System.out.print("Désignation : "); String designation = scanner.nextLine();
                        System.out.print("Prix : "); double prix = Double.parseDouble(scanner.nextLine());
                        System.out.print("Description : "); String description = scanner.nextLine();
                        System.out.print("Image : "); String image = scanner.nextLine();
                        System.out.print("Catégorie : "); String cat = scanner.nextLine();

                        Medicament medicament = new Medicament(designation, prix, description, image, new Categorie(cat));
                        boolean inserted = medocDAO.insererMedicament(medicament);

                        if (inserted) {
                            System.out.println("✅ Médicament inséré !");
                        } else {
                            System.out.println("❌ Insertion échouée.");
                        }

                    } else if ("2".equals(action)) {
                        System.out.print("Nom médicament à rechercher : ");
                        String nomRecherche = scanner.nextLine();
                        List<Medicament> resultats = medocDAO.rechercherParNom(nomRecherche);

                        if (resultats.isEmpty()) {
                            System.out.println("⚠️ Aucun médicament trouvé.");
                        } else {
                            System.out.println("📦 Médicaments trouvés :");
                            resultats.forEach(m -> System.out.println("- " + m.getDesignation() + " | " + m.getPrix() + " FCFA | " + m.getCategorie().getDesignation()));
                        }
                    }

                } else {
                    System.out.println("❌ Identifiants incorrects.");
                }
            } else {
                System.out.println("❌ Choix invalide.");
            }

        } catch (DataBaseException e) {
            System.out.println("💥 Erreur de base de données : " + e.getMessage());
            logger.fatal("Erreur critique BDD", e);
        }

        scanner.close();
    }
}
