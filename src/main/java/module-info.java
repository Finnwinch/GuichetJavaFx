module leo.cirpaci.guichet {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens leo.cirpaci.guichet to javafx.fxml;
    exports leo.cirpaci.guichet;
}