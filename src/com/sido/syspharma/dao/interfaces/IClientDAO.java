package com.sido.syspharma.dao.interfaces;

import com.sido.syspharma.dao.impl.ClientDAOImpl;
import com.sido.syspharma.domaine.model.Client;
import com.sido.syspharma.exceptions.DataBaseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.sido.syspharma.database.ConnexionDB.getConnection;

public interface IClientDAO {


    boolean inserer(Client client) throws DataBaseException;

    Optional<Client> trouverParEmail(String email) throws DataBaseException;

    List<Client> listerTous() throws DataBaseException;
}
