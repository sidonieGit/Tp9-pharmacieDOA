package com.sido.syspharma.service;

import com.sido.syspharma.dao.interfaces.IClientDAO;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.DataBaseException;
import org.apache.log4j.Logger;

import java.util.Optional;

public class ServiceClient {

    private final IClientDAO clientDAO;
    private static final Logger logger = Logger.getLogger(ServiceClient.class);

    public ServiceClient(IClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    /**
     * ✅ Créer un compte client (si email n’est pas encore utilisé)
     */
    public boolean creerCompte(Client client) throws DataBaseException {
        logger.debug("Tentative de création de compte : " + client.getEmail());

        Optional<Client> existant = clientDAO.trouverParEmail(client.getEmail());

        if (existant.isPresent()) {
            logger.warn("⚠️ Email déjà utilisé : " + client.getEmail());
            return false;
        }

        return clientDAO.inserer(client);
    }

    /**
     * 🔐 Vérifie login (email + mot de passe)
     */
    public boolean seConnecter(String email, String password) throws DataBaseException {
        Optional<Client> optional = clientDAO.trouverParEmail(email);

        return optional
                .filter(c -> c.getPassword().equals(password)) // lambda ici
                .map(c -> {
                    logger.info("Connexion OK pour : " + c.getEmail());
                    return true;
                })
                .orElse(false);
    }
}
