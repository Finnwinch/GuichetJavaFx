package ressource.utilisateur;
import ressource.Compte;
import ressource.Utilisateur;
import ressource.system.Configuration;
import ressource.system.Guichet;
import ressource.system.Utilitaire;
import ressource.system.Transaction;

import java.io.Serializable;

public class Administrateur extends Utilisateur implements Utilitaire, Configuration {
    public Administrateur(){}
    public Administrateur(String nom, String prenom, int age, String courriel, String telephone, String identifiant){
        this.setNom(nom) ;
        this.setPrenom(prenom) ;
        this.setAge(age) ;
        this.setCourriel(courriel) ;
        this.setTelephone(telephone) ;
        this.setIdentifiant(identifiant) ;
    }
}
