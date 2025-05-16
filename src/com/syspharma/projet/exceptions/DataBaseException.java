package com.syspharma.projet.exceptions;

public class DataBaseException extends Exception {
    @Override
    public String getMessage() {
        return "ERREUR ACCES BASE DE DONNEES";
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
