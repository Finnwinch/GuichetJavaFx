package leo.cirpaci.guichet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ressource.Utilisateur;
import ressource.compte.Cheque;
import ressource.compte.Epargne;
import ressource.compte.Hypothecaire;
import ressource.system.Configuration;
import ressource.system.Utilitaire;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGestionBlockCompte implements Initializable {
    @FXML private ChoiceBox utilisateurSelection ;
    @FXML private ToggleGroup typesCompte ;
    @Override public void initialize(URL location, ResourceBundle resources) {
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur instanceof Administrateur) {
                continue;
            }
            utilisateurSelection.getItems().add(utilisateur) ;
        }
    }
    @FXML protected void AnnulerDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) utilisateurSelection.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(300) ;
        stage.setHeight(250) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void confirmerDoClick(){
        Client client ;
        client = (Client) utilisateurSelection.getValue() ;
        if (client==null) { return; }
        String message = "" ;
        if (client.getRestrient()) {
            message = "débloquer" ;
        } else {
            message = "bloquer" ;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Le Client " + client + "vas être " + message);
        ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(ouiButtonType);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait().ifPresent(buttonType -> {}) ;
        client.toogleRestrient();
        client.addTentativeDeConnexion(0);
        int count = 0 ;
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur.equals(client)) {
                Utilitaire.utilisateurs.set(count,client) ;
            }
            count++ ;
        }
    }
}
