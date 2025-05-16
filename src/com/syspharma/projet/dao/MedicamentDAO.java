package com.syspharma.projet.dao;

import com.syspharma.projet.database.ConnexionDB;
import com.syspharma.projet.domaine.model.Categorie;
import com.syspharma.projet.domaine.model.Medicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    public List<Medicament> rechercherParNom(String nom) {
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
                        new Categorie(rs.getString("categorie")) // ✅ Convertir String → Categorie

                );
                resultats.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }
}
