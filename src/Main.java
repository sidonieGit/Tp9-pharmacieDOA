

import com.syspharma.projet.dao.MedicamentDAO;
import com.syspharma.projet.domaine.model.Medicament;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MedicamentDAO dao = new MedicamentDAO();

        System.out.print("Entrez le nom du médicament recherché : ");
        String nom = scanner.nextLine();

        List<Medicament> resultats = dao.rechercherParNom(nom);

        if (resultats.isEmpty()) {
            System.out.println("Aucun médicament trouvé.");
        } else {
            System.out.println("📦 Médicaments trouvés :");
            for (Medicament m : resultats) {
                System.out.println("- " + m.getDesignation() + " (" + m.getPrix() + " €)");
            }
        }

        scanner.close();
    }
}
