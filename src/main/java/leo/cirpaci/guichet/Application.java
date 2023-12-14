package leo.cirpaci.guichet;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ressource.Utilisateur;
import ressource.compte.Cheque;
import ressource.compte.Epargne;
import ressource.compte.Hypothecaire;
import ressource.system.Guichet;
import ressource.system.PackageData;
import ressource.system.Utilitaire;
import ressource.utilisateur.Administrateur;
import ressource.utilisateur.Client;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 260, 280);
        stage.setTitle("Guichet");
        stage.setScene(scene);
        stage.setResizable(false) ;
        stage.show();
        Guichet.toogleOuvert();
        /*
        Administrateur admin = new Administrateur() ;
        admin.setMotDePasse("root");
        admin.setIdentifiant("root");
        Utilitaire.utilisateurs.add(admin) ;
         */
        PackageData.deserialize("Transactions.ser") ;
        PackageData.deserialize("Utilisateurs.ser") ;
        for (Utilisateur user : Utilitaire.utilisateurs) {
            user.addTentativeDeConnexion(0);
        }
        System.out.println(Utilitaire.utilisateurs);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("L'application est en train de se fermer...");
                PackageData.serialize( Utilitaire.utilisateurs,"Utilisateurs.ser");
                PackageData.serialize(Utilitaire.transactions,"Transactions.ser");
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}