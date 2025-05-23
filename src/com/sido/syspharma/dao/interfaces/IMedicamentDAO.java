package com.sido.syspharma.dao.interfaces;

import com.sido.syspharma.domaine.model.Medicament;
import com.sido.syspharma.exceptions.DataBaseException;

import java.util.List;

public interface IMedicamentDAO {
    boolean insererMedicament(Medicament medicament) throws DataBaseException;
    List<Medicament> rechercherParNom(String nom) throws DataBaseException;
}
