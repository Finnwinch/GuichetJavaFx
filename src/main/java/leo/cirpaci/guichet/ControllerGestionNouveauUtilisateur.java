package leo.cirpaci.guichet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ressource.Utilisateur;
import ressource.system.Guichet;
import ressource.system.Utilitaire;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGestionNouveauUtilisateur {
    @FXML private TextField inpNom ;
    @FXML private TextField inpPreNom ;
    @FXML private TextField inpAge ;
    @FXML private TextField inpMail ;
    @FXML private TextField inpTel ;
    @FXML private TextField inpPassword ;
    @FXML private ToggleGroup TypeUser ;
    @FXML protected void AnnulerDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) inpNom.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(300) ;
        stage.setHeight(250) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void confirmerDoClick() throws IOException {
        if (!(inpNom.getText().equals("") ||
                inpPreNom.getText().equals("") ||
                inpAge.getText().equals("") ||
                inpMail.getText().equals("") ||
                inpTel.getText().equals("")
        )) {
            switch (((RadioButton) TypeUser.getSelectedToggle()).getText()) {
                case "Administrateur" :
                    Administrateur admin = new Administrateur(inpNom.getText(),inpPreNom.getText(),Integer.parseInt(inpAge.getText()),inpMail.getText(),inpTel.getText(),inpNom.getText() + inpPreNom.getText(0,1)) ;
                    if (!(inpPassword.getText().equals(""))) {
                        admin.setMotDePasse(inpPassword.getText());
                    }
                    admin.addTentativeDeConnexion(0);
                    Utilitaire.utilisateurs.add(admin) ;
                    break ;
                default :
                    Client user = new Client(inpNom.getText(),inpPreNom.getText(),Integer.parseInt(inpAge.getText()),inpMail.getText(),inpTel.getText(),inpNom.getText() + inpPreNom.getText(0,1)) ;
                    if (!(inpPassword.getText().equals(""))) {
                        user.setMotDePasse(inpPassword.getText());
                    }
                    user.addTentativeDeConnexion(0);
                    Utilitaire.utilisateurs.add(user) ;
                    break ;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.fxml"));
            Parent root = loader.load() ;
            Scene scene = new Scene(root) ;
            Stage stage = (Stage) inpPassword.getScene().getWindow() ;
            stage.setScene(scene) ;
            stage.setWidth(300) ;
            stage.setHeight(250) ;
            stage.setResizable(false) ;
            stage.centerOnScreen() ;
        }
    }
}
