package com.sido.syspharma.presentation;

import com.sido.syspharma.dao.impl.ClientDAOImpl;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.BusinessException;
import com.sido.syspharma.service.ServiceClient;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 🔁 Injection de dépendance manuelle
        ServiceClient serviceClient = new ServiceClient(new ClientDAOImpl());

        System.out.println("=== 📦 SystPharma - Connexion Client ===");
        System.out.print("1️⃣ Créer un compte ou 2️⃣ Se connecter ? [1/2] : ");
        String choix = scanner.nextLine();

        try {
            if (choix.equals("1")) {
                System.out.println("📝 Création de compte");
                System.out.print("Nom : "); String nom = scanner.nextLine();
                System.out.print("Prénom : "); String prenom = scanner.nextLine();
                System.out.print("Email : "); String email = scanner.nextLine();
                System.out.print("Adresse : "); String adresse = scanner.nextLine();
                System.out.print("Téléphone : "); String tel = scanner.nextLine();
                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();

                Client client = new Client(nom, prenom, email, adresse, tel, pwd, null);
                if (serviceClient.creerCompte(client)) {
                    System.out.println("✅ Compte client créé !");
                }

            } else if (choix.equals("2")) {
                System.out.print("Email : "); String email = scanner.nextLine();
                System.out.print("Mot de passe : "); String pwd = scanner.nextLine();

                if (serviceClient.seConnecter(email, pwd)) {
                    System.out.println("🔓 Connexion réussie !");
                } else {
                    System.out.println("❌ Échec de connexion.");
                }
            } else {
                System.out.println("⛔ Choix invalide.");
            }

        } catch (BusinessException e) {
            logger.fatal("💥 Erreur métier : " + e.getMessage());
            System.out.println("❌ Erreur : " + e.getMessage());
        }

        scanner.close();
    }
}
