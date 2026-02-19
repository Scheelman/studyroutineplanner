package nl.mikaildalli.studyplanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        AppView root = new AppView();                 // ✅ start de app met sidebar
        Scene scene = new Scene(root, 1100, 650);

        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Study Planner BP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);                                  // ✅ alleen launch hier
    }
}
