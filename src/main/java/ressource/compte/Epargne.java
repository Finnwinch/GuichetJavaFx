package ressource.compte;

import ressource.Compte;
import ressource.system.Guichet;

public class Epargne extends Compte implements Retrait {
    public Epargne(){};
    @Override
    public void retrait(float Montant) {
        if (Guichet.getMontant() < Montant || getSolde() - Montant < 0) { return ; }
        setSolde(getSolde() - Montant) ;
        Guichet.setMontant(Guichet.getMontant()-Montant);

    }
    @Override
    public String toString(){
        return "Epargne."+getProprietaire().getIdentifiant()+Integer.toString(hashCode()) ;
    }
}
