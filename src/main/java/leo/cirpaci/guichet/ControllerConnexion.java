package leo.cirpaci.guichet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ressource.Utilisateur;
import ressource.system.Guichet;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnexion implements Initializable {
    @FXML private TextField id ;
    @FXML private Label idLabel ;
    @FXML private PasswordField password ;
    @FXML private Label motDePasseLabel ;
    @FXML private Label tentativeConnexion ;
    @FXML private Button connectbtn ;
    private boolean quitter ;
    @FXML private Stage stage;
    @FXML public void connectionINP() throws IOException {
        if (!Guichet.getEstOuvert()) {return;}
        if (quitter) {
            tentativeConnexion.setText("") ;
            id.setDisable(false);
            idLabel.setText("Identifiant");
            password.setDisable(false);
            motDePasseLabel.setText("Mot de passe");
            connectbtn.setText("Connection");

            quitter = false ;
            return;
        }
        Guichet.essaieConnexion(id.getText(),password.getText());
        String page = null;
        if (Guichet.getConnecter()!=null) {
            if (Guichet.getConnecter() instanceof Client) {
                page = "Lobby.fxml" ;
            } else if (Guichet.getConnecter() instanceof Administrateur) {
                page = "Gestion.fxml" ;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load() ;
            Scene scene = new Scene(root) ;
            Stage stage = (Stage) id.getScene().getWindow() ;
            stage.setScene(scene) ;
            stage.setWidth((page=="Gestion.fxml")?300:300) ;
            stage.setHeight((page=="Gestion.fxml")?250:200) ;
            stage.setResizable(false) ;
            stage.centerOnScreen() ;
        } else {
            if (Guichet.getPreConnecter()!=null) {
                switch (Guichet.getPreConnecter().getTentativeDeConnexion()) {
                    case 1 : tentativeConnexion.setText("2/3 tentative avant désactivation") ;
                        idLabel.setText("Identifiant") ;
                        motDePasseLabel.setText("Mot de passe invalide");
                        System.out.println(Guichet.getPreConnecter().getMotDePasse());
                    break ;
                    case 2 : tentativeConnexion.setText("3/3 tentative avant désactivation") ;
                        idLabel.setText("Identifiant") ;
                        motDePasseLabel.setText("Mot de passe invalide");
                        System.out.println(Guichet.getPreConnecter().getMotDePasse());
                    break ;
                    default :
                        password.setDisable(true);
                        id.setDisable(true);
                        motDePasseLabel.setText("Bloquer");
                        tentativeConnexion.setText("");
                        idLabel.setText("");
                        connectbtn.setText("ReConnection");
                        quitter = true ;
                        break ;
                }
            } else {
                idLabel.setText("Identifiant invalide");
                tentativeConnexion.setText("");
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tentativeConnexion.setText("") ;
        id.setDisable(false);
        idLabel.setText("Identifiant");
        password.setDisable(false);
        motDePasseLabel.setText("Mot de passe");
        connectbtn.setText("Connection");
        quitter = false ;
    }
}