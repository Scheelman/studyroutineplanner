package nl.mikaildalli.studyplanner;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import nl.mikaildalli.studyplanner.ui.TaskView;

// Appview = hoofd layout van de applicatie
//BorderPane, sidebar en content in het midden
// Links navigatie voor dashboard, tasks en notes

public class AppView extends BorderPane {

    public AppView() {
//        Sidebar container meet knoppen
        VBox side = new VBox(10);
        side.setPadding(new Insets(16));
        side.setPrefWidth(200);

// Navigatie knoppen voor dashboard, tasks en notes
        Button dash = new Button("Dashboard");
        Button tasks = new Button("Tasks");
        Button notes = new Button("Notes");

//        Grootte van dashboard, tasks en notes
        dash.setMaxWidth(Double.MAX_VALUE);
        tasks.setMaxWidth(Double.MAX_VALUE);
        notes.setMaxWidth(Double.MAX_VALUE);

        side.getChildren().addAll(dash, tasks, notes);
        setLeft(side);

// Views naar het center plaatsen
        nl.mikaildalli.studyplanner.ui.DashboardView dashboardView = new nl.mikaildalli.studyplanner.ui.DashboardView();
        TaskView taskView = new TaskView();
        nl.mikaildalli.studyplanner.ui.NotesView notesView = new nl.mikaildalli.studyplanner.ui.NotesView();
// Navigatie vanuit center content
        dash.setOnAction(e -> setCenter(dashboardView));
        tasks.setOnAction(e -> setCenter(taskView));
        notes.setOnAction(e -> setCenter(notesView));

//        De startscherm
        setCenter(dashboardView); // start
    }
}
