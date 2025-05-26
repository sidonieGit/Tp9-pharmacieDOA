package com.sido.syspharma.service;

import com.sido.syspharma.dao.interfaces.IClientDAO;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.BusinessException;
import com.sido.syspharma.exceptions.DataBaseException;
import com.sido.syspharma.service.interfaces.IServiceClient;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional; // Très important pour gérer les valeurs potentiellement absentes

/**
 * Service métier lié aux clients : création, connexion, récupération.
 */
public class ServiceClient implements IServiceClient {


    private final IClientDAO clientDAO; // L'objet DAO dont le service a besoin
    private static final Logger logger = Logger.getLogger(ServiceClient.class); // Instance du logger

    public ServiceClient(IClientDAO clientDAO) { // Constructeur pour l'injection de dépendance
        this.clientDAO = clientDAO;
    }

    /**
     * Crée un compte client si l'email n'existe pas déjà.
     */
    @Override
    public boolean creerCompte(Client client) throws BusinessException {
        try {
            Optional<Client> existant = clientDAO.trouverParEmail(client.getEmail());
            if (existant.isPresent()) {
                throw new BusinessException("L'email est déjà utilisé !");
            }
            return clientDAO.inserer(client);
        } catch (DataBaseException e) {
            throw new BusinessException("Erreur lors de la création du client", e);
        }
    }

    /**
     * Vérifie les identifiants du client.
     */
    @Override
    public boolean seConnecter(String email, String motDePasse) throws BusinessException {
        try {
            Optional<Client> optional = clientDAO.trouverParEmail(email);
            return optional
                    .filter(c -> c.getPassword().equals(motDePasse))
                    .isPresent();
        } catch (DataBaseException e) {
            throw new BusinessException("Connexion impossible", e);
        }
    }

    /**
     * Récupère tous les clients.
     */
    @Override
    public List<Client> getTousLesClients() throws BusinessException {
        try {
            return clientDAO.listerTous();
        } catch (DataBaseException e) {
            throw new BusinessException("Erreur de récupération des clients", e);
        }
    }
}
