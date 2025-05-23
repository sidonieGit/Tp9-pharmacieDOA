package com.sido.syspharma.presentation;

import com.sido.syspharma.dao.impl.ClientDAOImpl;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.DataBaseException;
import com.sido.syspharma.service.ServiceClient;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceClient service = new ServiceClient(new ClientDAOImpl());

        System.out.println("=== 📦 SystPharma - Client ===");
        System.out.print("1️⃣ Créer un compte ou 2️⃣ Se connecter ? [1/2] : ");
        String choix = scanner.nextLine();

        try {
            if ("1".equals(choix)) {
                // Création de compte
                System.out.println("📝 Création d’un compte client");
                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Prénom : "); String prenom = scanner.nextLine();
                System.out.print("Email : "); String email = scanner.nextLine();
                System.out.print("Adresse : "); String adresse = scanner.nextLine();
                System.out.print("Téléphone : "); String tel = scanner.nextLine();
                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();

                Client client = new Client(nom, prenom, email, adresse, tel, pwd, null);

                if (service.creerCompte(client)) {
                    System.out.println("✅ Compte créé avec succès !");
                } else {
                    System.out.println("⚠️ Email déjà utilisé !");
                }

            } else if ("2".equals(choix)) {
                // Connexion
                System.out.print("Email : "); String email = scanner.nextLine();
                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();

                if (service.seConnecter(email, pwd)) {
                    System.out.println("🔓 Connexion réussie !");
                } else {
                    System.out.println("❌ Identifiants invalides.");
                }
            } else {
                System.out.println("⛔ Choix invalide.");
            }

        } catch (DataBaseException e) {
            logger.fatal("💥 Erreur critique BD : " + e.getMessage());
            System.out.println("❗ Erreur de base de données : " + e.getMessage());
        }

        scanner.close();
    }
}
