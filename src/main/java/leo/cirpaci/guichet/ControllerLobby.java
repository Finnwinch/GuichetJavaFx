package leo.cirpaci.guichet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable ;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ressource.Compte;
import ressource.compte.Cheque;
import ressource.compte.Epargne;
import ressource.compte.Hypothecaire;
import ressource.system.Guichet;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ControllerLobby implements Initializable {
    @FXML private Label identifiantDisplay ;
    @FXML private Label detteLabel ;
    @FXML private Label SoldeLabel ;
    @FXML private Label sessionLabel ;
    @FXML private ChoiceBox CompteListe ;
    @FXML private Button optRetrait ;
    @FXML private Button optDepot ;
    @FXML private Button optTransfert ;
    @FXML private Button optFacture ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        identifiantDisplay.setText(Guichet.getConnecter().getIdentifiant());
        Client client = (Client) Guichet.getConnecter();
        ObservableList<Compte> comptes = FXCollections.observableArrayList(client.getCompte());
        CompteListe.setItems(comptes);
        CompteListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Compte>() {
            @Override
            public void changed(ObservableValue<? extends Compte> observable, Compte oldValue, Compte newValue) {
                if (newValue != null) {
                    SoldeLabel.setText("Solde : "+Float.toString(newValue.getSolde())+'$');
                    CompteListe.setValue(newValue);
                    switch (newValue.getClass().getSimpleName()) {
                        case "Cheque" :
                            optRetrait.setDisable(false);
                            optTransfert.setDisable(false);
                            optFacture.setDisable(false);
                            break ;
                        case "Epargne" :
                            optRetrait.setDisable(false);
                            optTransfert.setDisable(true);
                            optFacture.setDisable(true);
                            break ;
                        case "Hypothecaire" :
                            optRetrait.setDisable(true);
                            optTransfert.setDisable(true);
                            optFacture.setDisable(true);
                            break ;
                    }
                }
            }
        });
        CompteListe.setValue(client.getCompte().get(0));
        detteLabel.setText("Dette : "+Float.toString(client.getMargeCredit())+'$');
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
    @FXML protected void depotDoClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Dépôt d'argent");
        dialog.setHeaderText("Veuillez entrer le montant à déposer :");
        dialog.setContentText("Montant:");
        dialog.showAndWait().ifPresent(montant -> {
            try {
                float montantFloat = Float.parseFloat(montant);
                if (montantFloat <= 0) {showErrorAlert("Erreur", "Vous devez mettre une valeur positif!"); return;}
                showInfoAlert("Dépôt réussi", "Le dépôt de " + montantFloat + "$ a été effectué avec succès.");
                Compte compte = null ;
                switch (CompteListe.getValue().getClass().getSimpleName()) {
                    case "Cheque" :
                        Cheque ch ;
                        ch = (Cheque) CompteListe.getValue() ;
                        ch.depot(montantFloat);
                        compte = (Cheque) ch ;
                        break ;
                    case "Epargne" :
                        Epargne ep ;
                        ep = (Epargne) CompteListe.getValue() ;
                        ep.depot(montantFloat);
                        compte = (Epargne) ep ;
                        break ;
                    case "Hypothecaire" :
                        Hypothecaire hyp;
                        hyp = (Hypothecaire) CompteListe.getValue() ;
                        hyp.depot(montantFloat);
                        compte = (Hypothecaire) hyp ;
                        break ;
                }
                CompteListe.setValue(null);
                CompteListe.setValue(compte);
            } catch (NumberFormatException e) {
                showErrorAlert("Erreur", "Veuillez entrer un montant valide.");
            }
        });
    }
    @FXML protected void retraitDoClick(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Retrait d'argent");
        dialog.setHeaderText("Veuillez entrer le montant à retirer :");
        dialog.setContentText("Montant:");
        dialog.showAndWait().ifPresent(montant -> {
            try {
                float montantFloat = Float.parseFloat(montant);
                if ((int)montantFloat%10 != 0) { showErrorAlert("Erreur", "Doit être un multiple de 10") ; return; }
                if (montantFloat <= 0) {showErrorAlert("Erreur", "Vous devez mettre une valeur positif!"); return;}
                if (Guichet.getMontant() - montantFloat < 0) { showErrorAlert("Erreur", "Manque de fonds dans le distributeur : différence de (" + Float.toString((Guichet.getMontant() - montantFloat) * -1 ) + "$)"); return; }
                Compte compte = null ;
                switch (CompteListe.getValue().getClass().getSimpleName()) {
                    case "Cheque" :
                        Cheque ch ;
                        ch = (Cheque) CompteListe.getValue() ;
                        ch.retrait(montantFloat);
                        compte = (Cheque) ch ;
                        break ;
                    case "Epargne" :
                        Epargne ep ;
                        ep = (Epargne) CompteListe.getValue() ;
                        ep.retrait(montantFloat);
                        compte = (Epargne) ep ;
                        break ;
                }
                CompteListe.setValue(null);
                CompteListe.setValue(compte);
                Client client = (Client) Guichet.getConnecter();
                detteLabel.setText("Dette : "+Float.toString(client.getMargeCredit())+'$');
            } catch (NumberFormatException e) {
                showErrorAlert("Erreur", "Veuillez entrer un montant valide.");
            }
        });
    }
    @FXML protected void transfertDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.transfert.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) identifiantDisplay.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(250) ;
        stage.setHeight(300) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void factureDoClick(){}
    @FXML protected void sessionDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Connexion.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) identifiantDisplay.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(260) ;
        stage.setHeight(280) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
}