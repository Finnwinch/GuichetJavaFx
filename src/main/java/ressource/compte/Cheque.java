package ressource.compte;
import javafx.scene.control.Alert;
import ressource.Compte;
import ressource.Utilisateur;
import ressource.system.Configuration;
import ressource.system.Guichet;
import ressource.utilisateur.Client;

public class Cheque extends Compte implements Configuration, PaiementFacture, Retrait {
    public Cheque(){}
    @Override
    public void retrait(float Montant) {
        if (Guichet.getMontant() < Montant) { return ; }
        if (getSolde() - Montant < 0) {
            showErrorAlert("Erreur", "Vous n'avez pas assez d'argent.\nNous regadons pour un emprunt! :"+Float.toString(Montant - getSolde())+'$') ;
            this.emprunt(Montant - getSolde());
        } else {
            setSolde(getSolde() - Montant) ;
        }
        Guichet.setMontant(Guichet.getMontant()-Montant);

    }
    public void transfert(Compte Destinataire, float Montant) {
        if (getSolde() - Montant < 0) {}
        else {
            setSolde(getSolde() - Montant);
            Destinataire.setSolde( Destinataire.getSolde() + Montant);
        }
    }
    public void emprunt(float Montant) {
        Client client ;
        client = (Client) getProprietaire() ;
        if (client.getMargeCredit() + Montant > Configuration.LimiteEndettement) {
            showErrorAlert("Erreur", "Vous ne pouvez pas emprunter. Vous dépasserais votre limite d'endettement!") ;
            return;
        }
        showInfoAlert("Emprunt","l'emprunt a était accepter !") ;
        client.setMargeCredit(client.getMargeCredit() + Montant);
        setSolde(0f);
        }
    public float getEmprunt(){
        Client client ;
        client = (Client) getProprietaire() ;
        return client.getMargeCredit() ;
    }
    @Override
    public void paiementFacture() {} ;
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @Override
    public String toString(){
        return "Cheque."+getProprietaire().getIdentifiant()+Integer.toString(hashCode()) ;
    }
}
