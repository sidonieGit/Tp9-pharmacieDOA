package com.sido.syspharma.database;

import com.sido.syspharma.exceptions.DataBaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {

    public static Connection getConnection() throws DataBaseException {
        try {
            return DriverManager.getConnection(
                    AppConfig.get("db.url"),
                    AppConfig.get("db.user"),
                    AppConfig.get("db.password")
            );
        } catch (SQLException e) {
            // ✅ Message synthétisé pour l'utilisateur final
            throw new DataBaseException("⚠️ La connexion à la base de données a échoué. Vérifiez la configuration.", e);
        }
    }
}
