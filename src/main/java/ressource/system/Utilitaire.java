package ressource.system;

import ressource.Compte;
import ressource.Utilisateur;
import ressource.system.Transaction;

import java.io.Serializable;
import java.util.ArrayList;

public interface Utilitaire extends Serializable {
    ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<Transaction>() ;
}