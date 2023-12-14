package leo.cirpaci.guichet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ressource.Compte;
import ressource.Utilisateur;
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

public class ControllerGestion implements Initializable {
    @FXML private Label identifiantDisplay ;
    @Override public void initialize(URL location, ResourceBundle resources) {
        identifiantDisplay.setText(Guichet.getConnecter().getIdentifiant());
        System.out.println(Guichet.getConnecter().toString());
    }
    @FXML protected void NouveauUtilisateurDoClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.nouveauUtilisateur.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) identifiantDisplay.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(250) ;
        stage.setHeight(300) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void NouveauCompteDoClick() throws IOException {
        boolean detectClient = false ;
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur instanceof Client) {
                detectClient = true ;
                break ;
            }
        }
        if (!detectClient) { return; }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.nouveauCompte.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) identifiantDisplay.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(250) ;
        stage.setHeight(300) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void FermerGuichetDoClick() throws IOException {
        Guichet.toogleOuvert();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Le Guichet est fermé, vous allez être rediriger vers la page de connexion qui sera désactiver!");
        ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(ouiButtonType);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait().ifPresent(buttonType -> {}) ;
        DeconnexionDoClick();
    }
    @FXML protected void DeconnexionDoClick() throws IOException {
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
    @FXML protected void PayerInteretEpargne(){
        StringBuilder str = new StringBuilder() ;
        for (Utilisateur user : Utilitaire.utilisateurs) {
            if (user instanceof Administrateur) { ; continue; }
            Client client ;
            client = (Client) user ;
            for (Compte compte : client.getCompte()) {
                if (compte instanceof Epargne) {
                    str.append(user+"\n") ;
                    compte.setSolde(compte.getSolde() + compte.getSolde() * Configuration.TauxEpargne);
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Paiement des comptes Épargnes suivent à " + Configuration.TauxEpargne * 100 + "% : \n" + str);
        ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(ouiButtonType);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait().ifPresent(buttonType -> {}) ;
    }
    @FXML protected void GetTransactionDoClick(){
        StringBuilder str = new StringBuilder() ;
        for (Transaction tr : Utilitaire.transactions) {
            str.append(tr+"\n") ;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Liste des transactions\n" + str);
        ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(ouiButtonType);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait().ifPresent(buttonType -> {}) ;
    }
    @FXML protected void PreleverHypo(){
        StringBuilder str = new StringBuilder() ;
        TextInputDialog dialog = new TextInputDialog("test");
        dialog.setTitle("Traitement Prelevement Hypothéque");
        dialog.setHeaderText("Veuillez entrer le montant à prelever :");
        dialog.setContentText("Montant:");
        dialog.showAndWait().ifPresent(montant -> {
            try {
                float montantFloat = Float.parseFloat(montant);
                if (montantFloat < 0) {
                    showErrorAlert("Erreur","Veuillez saisire un nombre valide") ;
                    return ;
                }
                for (Utilisateur user : Utilitaire.utilisateurs) {
                    if (user instanceof Administrateur) { ; continue; }
                    Client client ;
                    client = (Client) user ;
                    for (Compte compte : client.getCompte()) {
                        if (compte instanceof Hypothecaire) {
                            str.append(user+"\n") ;
                            compte.setSolde(compte.getSolde()-montantFloat);
                        }
                    }
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Prélèvement des comptes Hypothécaire suivent (" + Float.toString(montantFloat) + "$)\n" + str);
                ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(ouiButtonType);
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait().ifPresent(buttonType -> {}) ;
            } catch (NumberFormatException e) {
                showErrorAlert("Erreur","Veuillez saisire un nombre") ;
            }
        });
    }
    @FXML protected void BlockCompte() throws IOException {
        boolean detectClient = false ;
        for (Utilisateur utilisateur : Utilitaire.utilisateurs) {
            if (utilisateur instanceof Client) {
                detectClient = true ;
                break ;
            }
        }
        if (!detectClient) { return; }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gestion.BlockCompte.fxml"));
        Parent root = loader.load() ;
        Scene scene = new Scene(root) ;
        Stage stage = (Stage) identifiantDisplay.getScene().getWindow() ;
        stage.setScene(scene) ;
        stage.setWidth(250) ;
        stage.setHeight(300) ;
        stage.setResizable(false) ;
        stage.centerOnScreen() ;
    }
    @FXML protected void AugmenterCredit(){
        StringBuilder str = new StringBuilder() ;
        for (Utilisateur user : Utilitaire.utilisateurs) {
            if (user instanceof Administrateur) {continue;}
            Client client;
            client = (Client) user;
            str.append(client) ;
            client.setMargeCredit(client.getMargeCredit() + client.getMargeCredit() * Configuration.TauxMargeCredit);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Augmentation des crédits suivent à " + Configuration.TauxMargeCredit * 100 + "% : \n" + str);
        ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(ouiButtonType);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait().ifPresent(buttonType -> {}) ;
    }
    @FXML protected void RemplireDistributeur() {
        TextInputDialog dialog = new TextInputDialog("test");
        dialog.setTitle("Traitement Remplire Distributeur");
        dialog.setHeaderText("Veuillez entrer le montant à remplire :");
        dialog.setContentText("Montant:");
        dialog.showAndWait().ifPresent(montant -> {
            try {
                float montantFloat = Float.parseFloat(montant);
                if (montantFloat < 0) {
                    showErrorAlert("Erreur","Veuillez saisire un nombre valide") ;
                    return ;
                } else if (Guichet.getMontant() + montantFloat > Configuration.maximumMontantGuichet) {
                    showErrorAlert("Erreur","Le Guichet ne peux pas recevoir autant d'argent : "+
                            Float.toString((Guichet.getMontant())) + " / " +
                            Float.toString(Configuration.maximumMontantGuichet)) ;
                    return ;
                }
                Guichet.setMontant(Guichet.getMontant()+montantFloat);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Le distributeur à était rempli de "+montantFloat+"$ ("+
                        Float.toString((Guichet.getMontant())) + "$ / " +
                        Float.toString(Configuration.maximumMontantGuichet)+"$)") ;
                ButtonType ouiButtonType = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(ouiButtonType);
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait().ifPresent(buttonType -> {}) ;
            } catch (NumberFormatException e) {
                showErrorAlert("Erreur","Veuillez saisire un nombre") ;
            }
        });
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
