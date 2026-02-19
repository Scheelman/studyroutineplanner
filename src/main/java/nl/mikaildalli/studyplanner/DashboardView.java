package nl.mikaildalli.studyplanner.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {
    public DashboardView() {
        setPadding(new Insets(16));
        setSpacing(10);
        getChildren().addAll(
                new Label("Dashboard"),
                new Label("Hier komt later: upcoming tasks / deadlines / notes.")
        );
    }
}
