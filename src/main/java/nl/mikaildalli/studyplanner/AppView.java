package nl.mikaildalli.studyplanner.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppView extends BorderPane {

    public AppView() {
        VBox side = new VBox(10);
        side.setPadding(new Insets(16));
        side.setPrefWidth(200);

        Button dash = new Button("Dashboard");
        Button tasks = new Button("Tasks");
        Button notes = new Button("Notes");

        dash.setMaxWidth(Double.MAX_VALUE);
        tasks.setMaxWidth(Double.MAX_VALUE);
        notes.setMaxWidth(Double.MAX_VALUE);

        side.getChildren().addAll(dash, tasks, notes);
        setLeft(side);

        nl.mikaildalli.studyplanner.ui.DashboardView dashboardView = new nl.mikaildalli.studyplanner.ui.DashboardView();
        TaskView taskView = new TaskView();
        nl.mikaildalli.studyplanner.ui.NotesView notesView = new nl.mikaildalli.studyplanner.ui.NotesView();

        dash.setOnAction(e -> setCenter(dashboardView));
        tasks.setOnAction(e -> setCenter(taskView));
        notes.setOnAction(e -> setCenter(notesView));

        setCenter(dashboardView); // start
    }
}
