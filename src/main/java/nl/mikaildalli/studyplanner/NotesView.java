package nl.mikaildalli.studyplanner.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

// Notes View = notities van het scherm
public class NotesView extends VBox {
    public NotesView() {
        setPadding(new Insets(16));
        setSpacing(10);

        TextArea area = new TextArea();
        area.setPromptText("Notities...");

// Button voor het opslaan
        Button save = new Button("Opslaan");
        save.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "Opgeslagen (demo).").showAndWait());

        getChildren().addAll(new Label("Notes"), area, save);
    }
}
