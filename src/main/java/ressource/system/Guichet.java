package ressource.system;
import ressource.Utilisateur;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;
import java.util.ArrayList;
import java.util.Objects;

import static ressource.system.Configuration.TentativeConnexion;

public class Guichet implements Configuration, Utilitaire {
    private static Utilisateur connecter = null;
    private static Utilisateur preConnecter = null ;
    private static float montant = Configuration.MontantDepartGuichet;
    private static boolean estOuvert = false;
    public static boolean getEstOuvert() { return estOuvert ;}
    public static void setMontant(float valeur) {
        montant = valeur;
    }
    public static float getMontant(){ return montant ; }
    public static void toogleOuvert(){
        estOuvert = !estOuvert ;
    }
    public static Utilisateur getConnecter() { return connecter ; }
    public static Utilisateur getPreConnecter() { return preConnecter ; }
    public static void essaieConnexion(String identifiant, String motDePasse) {
        if (!estOuvert) { return;}
        connecter = null ;
        preConnecter = null ;
        for (Utilisateur user : utilisateurs) {
            if (user.detectIdentifiant(identifiant)) {
                preConnecter = user ;
                break ;
            }
        }
        if (preConnecter!=null) {
            if (!preConnecter.getRestrient() && preConnecter.getTentativeDeConnexion()<=TentativeConnexion) {
                preConnecter.addTentativeDeConnexion();
            }
            if (preConnecter.getTentativeDeConnexion()>TentativeConnexion) {
                preConnecter.toogleRestrient();
            }
            if(preConnecter.verification(identifiant,motDePasse)) {
                connecter=preConnecter ;
                preConnecter.addTentativeDeConnexion(0);
            }
        }
    }
    public static void deconection(){
        connecter = null ;
    }
}