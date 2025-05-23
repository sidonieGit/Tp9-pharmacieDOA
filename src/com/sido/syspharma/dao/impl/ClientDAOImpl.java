package com.sido.syspharma.dao.impl;

import com.sido.syspharma.dao.AbstractDAO;
import com.sido.syspharma.dao.interfaces.IClientDAO;
import com.sido.syspharma.domaine.enums.Role;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.DataBaseException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation DAO pour les opérations liées aux clients.
 */
public class ClientDAOImpl extends AbstractDAO implements IClientDAO {

    private static final Logger logger = Logger.getLogger(ClientDAOImpl.class);

    public ClientDAOImpl() {
        // Constructeur par défaut
    }

    /**
     * 🔄 Insertion d’un nouveau client dans la base de données.
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

            logger.info("✅ Client inséré avec succès : " + client.getEmail());
            return rows > 0;

        } catch (SQLException e) {
            logger.error("❌ Erreur lors de l'insertion du client : " + client.getEmail(), e);
            throw new DataBaseException("❌ Insertion client échouée", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * 🔍 Recherche un client par son email (utile pour la connexion).
     */
    @Override
    public Optional<Client> trouverParEmail(String email) throws DataBaseException {
        String sql = "SELECT * FROM client WHERE email = ?";

        try (PreparedStatement stmt = prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("password"),
                        null // Le numéro de commande n'est pas concerné ici
                );

                return Optional.of(client);
            }

        } catch (SQLException e) {
            logger.error("❌ Erreur lors de la recherche du client avec email : " + email, e);
            throw new DataBaseException("Erreur recherche client", e);
        } finally {
            closeConnection();
        }

        return Optional.empty();
    }

    /**
     * 🔁 Liste tous les clients présents dans la base.
     */
    @Override
    public List<Client> listerTous() throws DataBaseException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (PreparedStatement stmt = prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("password"),
                        null
                );
                clients.add(client);
            }

        } catch (SQLException e) {
            logger.error("❌ Erreur lors du listage des clients", e);
            throw new DataBaseException("Erreur listage clients", e);
        } finally {
            closeConnection();
        }

        return clients;
    }
}
