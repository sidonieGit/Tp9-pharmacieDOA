package com.syspharma.projet.dao;

import com.syspharma.projet.database.ConnexionDB;
import com.syspharma.projet.domaine.model.Categorie;
import com.syspharma.projet.domaine.model.Medicament;
import com.syspharma.projet.exceptions.DataBaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    /**
     * Insère un médicament en base de données.
     *
     * @param medicament le médicament à insérer
     * @return true si insertion réussie
     * @throws DataBaseException si erreur SQL
     */
    public boolean insererMedicament(Medicament medicament) throws DataBaseException {
        String sql = "INSERT INTO medicament (designation, prix, description, image, categorie) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medicament.getDesignation());
            stmt.setDouble(2, medicament.getPrix());
            stmt.setString(3, medicament.getDescription());
            stmt.setString(4, medicament.getImage());
            stmt.setString(5, medicament.getCategorie().getDesignation());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    /**
     * Recherche les médicaments correspondant au nom donné.
     *
     * @param nom nom partiel ou complet du médicament
     * @return liste des médicaments correspondants
     * @throws DataBaseException si erreur d'accès à la base
     */
    public List<Medicament> rechercherParNom(String nom) throws DataBaseException {
        List<Medicament> resultats = new ArrayList<>();
        String sql = "SELECT * FROM medicament WHERE designation LIKE ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nom + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medicament m = new Medicament(
                        rs.getString("designation"),
                        rs.getDouble("prix"),
                        rs.getString("description"),
                        rs.getString("image"),
                        new Categorie(rs.getString("categorie"))
                );
                resultats.add(m);
            }

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

        return resultats;
    }
}
