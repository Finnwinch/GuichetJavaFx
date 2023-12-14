package leo.cirpaci.guichet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ressource.Compte;
import ressource.Utilisateur;
import ressource.compte.Cheque;
import ressource.compte.Epargne;
import ressource.compte.Hypothecaire;
import ressource.system.Configuration;
import ressource.system.Guichet;
import ressource.system.Transaction;
import ressource.system.Utilitaire;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLobbyTransfret implements Initializable {
    @FXML private ChoiceBox debiteurListe ;
    @FXML private ChoiceBox beneficiareListe ;
    @FXML private TextField montantSaisie ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur instanceof Administrateur) { continue; }
            Client user = (Client) utilisateur ;
            for (Compte compte : user.getCompte()) {
                if (utilisateur.equals(Guichet.getConnecter())) {
                    debiteurListe.getItems().add(compte) ;
                }
                beneficiareListe.getItems().add(compte) ;
            }
        }
        beneficiareListe.getItems().add("Remboursement") ;
    }
    @FXML protected void annulerDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) debiteurListe.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(260) ;
        stage.setHeight(250) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void transfertDoClick(){
        Cheque sender = (Cheque) debiteurListe.getValue();
        if(beneficiareListe.getValue().equals(debiteurListe.getValue())) {
            showErrorAlert("Erreur","Vous ne pouvez pas faire un transfert sur le même compte") ;
            return;
        }
        if(beneficiareListe.getValue() instanceof String && "Remboursement".equals(beneficiareListe.getValue())) {
            Client client = (Client) Guichet.getConnecter() ;
            if (sender.getSolde() - Integer.parseInt(montantSaisie.getText()) < 0) {
                showErrorAlert("Erreur","Vous n'avez pas assez de fonds>") ;
            } else {
                showInfoAlert("Transfert",montantSaisie.getText()+" à était déduit de " + sender + " vers recouvrement dette") ;
                client.setMargeCredit(client.getMargeCredit()-Integer.parseInt(montantSaisie.getText()));
                sender.setSolde(sender.getSolde()-Integer.parseInt(montantSaisie.getText()));
            }
            Utilitaire.transactions.add(new Transaction( (Compte) debiteurListe.getValue(), null,Integer.parseInt(montantSaisie.getText()))) ;
            return;
        }
        Compte reciver = (Compte) beneficiareListe.getValue() ;
        Client client = (Client) Guichet.getConnecter() ;
        sender.retrait(Integer.parseInt(montantSaisie.getText()));
        if (client.getMargeCredit() + Integer.parseInt(montantSaisie.getText()) > Configuration.LimiteEndettement) {
            return;
        }
        reciver.setSolde(reciver.getSolde()+Integer.parseInt(montantSaisie.getText()));
        showInfoAlert("Transfert",montantSaisie.getText()+" à était déduit de " + sender + " vers" + reciver) ;
        Utilitaire.transactions.add(new Transaction( (Compte) debiteurListe.getValue(), (Compte) beneficiareListe.getValue(),Integer.parseInt(montantSaisie.getText()))) ;
    }
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
}