package com.syspharma.projet.service;

import com.syspharma.projet.domaine.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ServiceClient {
    private List<Client> baseClients = new ArrayList<>();

    public boolean creerCompte(Client nouveauClient) {
        for (Client c : baseClients) {
            if (c.getEmail().equalsIgnoreCase(nouveauClient.getEmail())) {
                return false;
            }
        }
        baseClients.add(nouveauClient);
        return true;
    }

    public List<Client> getTousLesClients() {
        return baseClients;
    }

    public Client rechercherParEmail(String email) {
        return baseClients.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
