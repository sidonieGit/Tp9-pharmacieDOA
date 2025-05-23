package com.sido.syspharma.dao.impl;

import com.sido.syspharma.dao.AbstractDAO;
import com.sido.syspharma.dao.interfaces.IClientDAO;
import com.sido.syspharma.domaine.enums.Role;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.DataBaseException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sido.syspharma.database.ConnexionDB.getConnection;

public class ClientDAOImpl extends AbstractDAO implements IClientDAO{

    private static final Logger logger = Logger.getLogger(ClientDAOImpl.class);

    public ClientDAOImpl() {
        try {
            creerTableSiNonExistante(); // 👈 Création automatique à l'initialisation
        } catch (DataBaseException e) {
            logger.fatal("💥 Impossible de vérifier/créer la table client", e);
        }
    }
    /**
     * ✅ Crée la table client si elle n'existe pas encore.
     */
    private void creerTableSiNonExistante() throws DataBaseException {
        String sql = """
                CREATE TABLE IF NOT EXISTS client (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nom VARCHAR(100),
                    prenom VARCHAR(100),
                    email VARCHAR(150) UNIQUE,
                    adresse VARCHAR(255),
                    telephone VARCHAR(20),
                    password VARCHAR(100),
                    role VARCHAR(20)
                )
                """;

        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            logger.info("🧱 Table 'client' vérifiée/créée avec succès.");
        } catch (SQLException e) {
            throw new DataBaseException("Erreur création table client", e);
        } finally {
            closeConnection();
        }
    }
    /**
     * 🔄 Insertion d’un nouveau client
     */
    @Override
    public boolean inserer(Client client) throws DataBaseException {
        String sql = "INSERT INTO client (nom, prenom, email, adresse, telephone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getAdresse());
            stmt.setString(5, client.getTelephone());
            stmt.setString(6, client.getPassword());
            stmt.setString(7, Role.CLIENT.name());

            int rows = stmt.executeUpdate();
            logger.info("✅ Client inséré : " + client.getEmail());
            return rows > 0;

        } catch (SQLException e) {
            throw new DataBaseException("❌ Insertion client échouée", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * 🔍 Rechercher client par email (login)
     */
    @Override
    public Optional<Client> trouverParEmail(String email) throws DataBaseException {
        String sql = "SELECT * FROM client WHERE email = ?";

        try (PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Client c = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("password"),
                        null // commande (pas utile ici)
                );
                return Optional.of(c);
            }

        } catch (SQLException e) {
            throw new DataBaseException("Erreur recherche client", e);
        } finally {
            closeConnection();
        }

        return Optional.empty();
    }

    /**
     * 🔁 Lister tous les clients
     */
    @Override
    public List<Client> listerTous() throws DataBaseException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (PreparedStatement stmt = prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clients.add(new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("password"),
                        null
                ));
            }

        } catch (SQLException e) {
            throw new DataBaseException("Erreur listage clients", e);
        } finally {
            closeConnection();
        }

        return clients;
    }
}
