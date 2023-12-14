package ressource.utilisateur;

import ressource.Compte;
import ressource.Utilisateur;

import java.util.ArrayList;

public class Client extends Utilisateur {
    private float margeCredit = 0 ;
    private ArrayList<Compte> comptes = new ArrayList<>() ;
    public Client() {}
    public Client(String nom, String prenom, int age, String courriel, String telephone, String identifiant){
        this.setNom(nom) ;
        this.setPrenom(prenom) ;
        this.setAge(age) ;
        this.setCourriel(courriel) ;
        this.setTelephone(telephone) ;
        this.setIdentifiant(identifiant) ;
    }
    public ArrayList<Compte> getCompte(){
        return comptes ;
    }
    public void addCompte(Compte compte){
        comptes.add(compte) ;
    }
    public float getMargeCredit(){
        return margeCredit ;
    }
    public void setMargeCredit(float marge) {
        margeCredit = marge ;
    }
}
