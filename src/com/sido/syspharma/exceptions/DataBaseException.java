package com.sido.syspharma.exceptions;

/**
 * Exception personnalisée pour signaler les erreurs d’accès BD.
 */
public class DataBaseException extends Exception {

    // ✅ Constructeur par défaut
    public DataBaseException(Throwable cause) {
        super("ERREUR ACCES BASE DE DONNEES", cause);
    }

    // ✅ Constructeur personnalisé avec message
    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "ERREUR ACCES BASE DE DONNEES: " + super.getMessage();
    }
}
