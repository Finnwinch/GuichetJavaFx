package ressource;

import java.io.Serializable;

import static ressource.system.Configuration.MontantDepart;

public abstract class Compte implements Serializable {
    private float solde = MontantDepart ;
    private String nip ;
    private Utilisateur proprietaire ;
    private String identifiant ;
    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire ;
    }
    public Utilisateur getProprietaire(){
        return this.proprietaire ;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setNip(String nip){
        this.nip = nip ;
    }
    public String getNip(){
        return nip ;
    }
    public float getSolde(){
        return this.solde ;
    }
    public void setSolde(float Montant) {
        this.solde = Montant ;
    }
    public void depot(float montant) {
        solde+=montant ;
    }
}