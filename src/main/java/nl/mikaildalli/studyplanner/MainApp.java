package nl.mikaildalli.studyplanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// MainApp begin van de javafx applicatie
// Start de Javafx en maakt een scene aan
// Appview layout me sidebnar
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
//        Appview : bevat dashboard, tasks en notes
        AppView root = new AppView();
//        Grootte van de venster
        Scene scene = new Scene(root, 1100, 650);
//        CSS laden uit de resources map
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Study Planner BP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
