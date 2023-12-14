package ressource;

import java.io.Serializable;

public abstract class Utilisateur implements Serializable {
    private String nom ;
    private String prenom ;
    private int age ;
    private String courriel ;
    private String telephone ;
    private String identifiant ;
    private String motDePasse = "default" ;
    private int tentativeDeConnexion = 0 ;
    private boolean restrient = false ;
    // Getter pour la propriété "nom"
    public String getNom() {
        return nom;
    }

    // Setter pour la propriété "nom"
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour la propriété "prenom"
    public String getPrenom() {
        return prenom;
    }

    // Setter pour la propriété "prenom"
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter pour la propriété "age"
    public int getAge() {
        return age;
    }

    // Setter pour la propriété "age"
    public void setAge(int age) {
        this.age = age;
    }

    // Getter pour la propriété "courriel"
    public String getCourriel() {
        return courriel;
    }

    // Setter pour la propriété "courriel"
    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    // Getter pour la propriété "telephone"
    public String getTelephone() {
        return telephone;
    }

    // Setter pour la propriété "telephone"
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Getter pour la propriété "identifiant"
    public String getIdentifiant() {
        return identifiant;
    }

    // Setter pour la propriété "identifiant"
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    // Getter pour la propriété "motDePasse"
    public String getMotDePasse() {
        return motDePasse;
    }

    // Setter pour la propriété "motDePasse"
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    // vérifier si l'identifiant et le mot de passe saisies est égal à l'utilisateur
    public boolean detectIdentifiant(String identifiant) {
        return getIdentifiant().equals(identifiant) ;
    }
    public boolean verification(String identifiant, String motDePasse) {
        return getIdentifiant().equals(identifiant) && getMotDePasse().equals(motDePasse) ;
    }
    public int getTentativeDeConnexion() { return this.tentativeDeConnexion ; }
    public void addTentativeDeConnexion() { this.tentativeDeConnexion++ ; }
    public void addTentativeDeConnexion(int val) { this.tentativeDeConnexion = val ; }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder() ;
        str.append(String.format("[%s] %s,%s\n\t",getIdentifiant(),getNom(),getPrenom())) ;
        str.append(String.format("%d ans\n\t",getAge())) ;
        str.append(String.format("Courriel : %s\n\t",getCourriel())) ;
        str.append(String.format("Téléphone : %s\n\t",getTelephone())) ;
        str.append(String.format("Mot de passe : %s\n",getMotDePasse())) ;
        str.append(getTelephone()) ;
        return str.toString() ;
    }
    public void toogleRestrient(){
        restrient = !restrient ;
    }
    public boolean getRestrient() { return this.restrient ; }
}
