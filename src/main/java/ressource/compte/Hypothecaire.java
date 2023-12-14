package ressource.compte;

import ressource.Compte;

public class Hypothecaire extends Compte {
    public Hypothecaire(){
        super();
    }
    @Override
    public String toString(){
        return "Hypothecaire."+getProprietaire().getIdentifiant()+Integer.toString(hashCode()) ;
    }
}
