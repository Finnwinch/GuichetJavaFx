package ressource.system;

import ressource.Compte;

import java.io.Serializable;

public class Transaction implements Serializable {
    private Compte debiteur;
    private Compte beneficiare;
    private float montant;

    public Transaction(Compte debiteur, Compte beneficiare, float montant) {
        this.debiteur = debiteur;
        this.beneficiare = beneficiare;
        this.montant = montant;
    }
    @Override
    public String toString() {
        return debiteur + " => " + beneficiare + " : " + Float.toString(montant);
    }
}