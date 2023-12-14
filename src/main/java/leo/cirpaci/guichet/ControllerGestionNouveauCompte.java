package leo.cirpaci.guichet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ressource.Utilisateur;
import ressource.compte.Cheque;
import ressource.compte.Epargne;
import ressource.compte.Hypothecaire;
import ressource.system.Utilitaire;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGestionNouveauCompte implements Initializable {
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
        System.out.println(utilisateurSelection.getValue());
        Client client ;
        client = (Client) utilisateurSelection.getValue() ;
        if (client==null) { return; }
        switch (((RadioButton) typesCompte.getSelectedToggle()).getText()) {
            case "Épargne" :
                Epargne epargne = new Epargne() ;
                epargne.setProprietaire(client);
                client.addCompte(epargne);
                break ;
            case "Hypothécaire" :
                Hypothecaire hypothecaire = new Hypothecaire() ;
                hypothecaire.setProprietaire(client);
                client.addCompte(hypothecaire);
                break ;
            default :
                Cheque cheque = new Cheque() ;
                cheque.setProprietaire(client);
                client.addCompte(cheque);
                break ;
        }
        int count = 0 ;
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur.equals(client)) {
                Utilitaire.utilisateurs.set(count,client) ;
            }
            count++ ;
        }
    }
}
