package com.sido.syspharma.database;

import com.sido.syspharma.exceptions.DataBaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Permet de créer les tables nécessaires si elles n'existent pas encore.
 */
public class TableCreator {

    public static void createTableClientIfNotExists() throws DataBaseException {
        String sql = """
            CREATE TABLE IF NOT EXISTS client (
                id INT PRIMARY KEY AUTO_INCREMENT,
                nom VARCHAR(50),
                prenom VARCHAR(50),
                email VARCHAR(100) UNIQUE,
                password VARCHAR(100),
                adresse VARCHAR(100),
                telephone VARCHAR(20)
            )
        """;
        try (Connection conn = ConnexionDB.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataBaseException("❌ Création table client échouée", e);
        }
    }

    public static void createTableMedicamentIfNotExists() throws DataBaseException {
        String sql = """
            CREATE TABLE IF NOT EXISTS medicament (
                id INT PRIMARY KEY AUTO_INCREMENT,
                designation VARCHAR(100),
                prix DOUBLE,
                description TEXT,
                image VARCHAR(255),
                categorie VARCHAR(50)
            )
        """;
        try (Connection conn = ConnexionDB.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataBaseException("❌ Création table medicament échouée", e);
        }
    }
}
