package nl.mikaildalli.studyplanner.ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.mikaildalli.studyplanner.model.Task;
import nl.mikaildalli.studyplanner.util.Database;

import java.time.LocalDate;
import java.util.List;

// Task View is de hoofscherm voor takenbeheer (CRUD)
//Tableview met PropertyValueFactory (koppelt kolommen aan getters)
//Buttons met setOnAction
//Gridpane

//Database
//JOIN query  zodat status en categorie namen in tabel staan
public class TaskView extends BorderPane {

// TableView toont Task objecten
    private final TableView<Task> table = new TableView<>();
//    Databse helper class (CRUD en select queries)
    private final Database db = new Database();

    public TaskView() {
        setPadding(new Insets(16));
// UI knoppen
        Button nieuw = new Button("Nieuw");
        Button bewerk = new Button("Bewerk");
        Button verwijder = new Button("Verwijder");
        Button refresh = new Button("Refresh");
// Event handlers
        nieuw.setOnAction(e -> openDialog(null));
        bewerk.setOnAction(e -> {
            Task t = table.getSelectionModel().getSelectedItem();
            if (t == null) info("Selecteer eerst een taak.");
            else openDialog(t);
        });
        verwijder.setOnAction(e -> deleteSelected());
        refresh.setOnAction(e -> load());
// Bovenbalk met knoppen
        setTop(new HBox(10, new Label("Taken"), nieuw, bewerk, verwijder, refresh));
// TableView instellen
        setupTable();
        setCenter(table);
// Laden van de data
        load();
    }
// Kolommen
//    PropertyValueFactory zoekt kolommen
    private void setupTable() {
        TableColumn<Task, String> c1 = new TableColumn<>("Titel");
        c1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, String> c2 = new TableColumn<>("Status");
        c2.setCellValueFactory(new PropertyValueFactory<>("statusName"));

        TableColumn<Task, String> c3 = new TableColumn<>("Categorie");
        c3.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        TableColumn<Task, LocalDate> c4 = new TableColumn<>("Deadline");
        c4.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        table.getColumns().addAll(c1, c2, c3, c4);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }
// Laadt taken opnieuw uit de databser en zet ze in de TableView
    private void load() {
        table.setItems(FXCollections.observableArrayList(db.getAllTasksWithJoin()));
    }
// Delete met bevestiging
    private void deleteSelected() {
        Task t = table.getSelectionModel().getSelectedItem();
        if (t == null) { info("Selecteer eerst een taak."); return; }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Verwijderen");
        a.setHeaderText("Taak verwijderen?");
        a.setContentText(t.getTitle());

        a.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                db.deleteTaskById(t.getId());
                load();
            }
        });
    }
// Tekst voor:
//   nieuwe taak, bestaande taken bijwerken
//    Validatie
    private void openDialog(Task existing) {
        boolean edit = existing != null;

        Dialog<Void> d = new Dialog<>();
        d.setTitle(edit ? "Taak bewerken" : "Nieuwe taak");
        ButtonType save = new ButtonType(edit ? "Opslaan" : "Toevoegen", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);

// Form velden
        TextField tfTitle = new TextField();
        TextArea taDesc = new TextArea();
        taDesc.setPrefRowCount(4);
        DatePicker dp = new DatePicker();
// ComboBoxes komen uit de database
        ComboBox<Database.IdName> cbStatus = new ComboBox<>();
        ComboBox<Database.IdName> cbCat = new ComboBox<>();

        List<Database.IdName> statuses = db.getAllStatuses();
        List<Database.IdName> cats = db.getAllCategories();
        cbStatus.setItems(FXCollections.observableArrayList(statuses));
        cbCat.setItems(FXCollections.observableArrayList(cats));

//        Layout form met GridPane
        GridPane g = new GridPane();
        g.setHgap(10); g.setVgap(10); g.setPadding(new Insets(10));
        g.add(new Label("Titel:"), 0, 0); g.add(tfTitle, 1, 0);
        g.add(new Label("Omschrijving:"), 0, 1); g.add(taDesc, 1, 1);
        g.add(new Label("Deadline:"), 0, 2); g.add(dp, 1, 2);
        g.add(new Label("Status:"), 0, 3); g.add(cbStatus, 1, 3);
        g.add(new Label("Categorie:"), 0, 4); g.add(cbCat, 1, 4);

//        Edit : zet bestaande waardes terug in form
        if (edit) {
            tfTitle.setText(existing.getTitle());
            taDesc.setText(existing.getDescription());
            dp.setValue(existing.getDeadline());
            cbStatus.getSelectionModel().select(findById(statuses, existing.getStatusId()));
            if (existing.getCategoryId() != null) {
                cbCat.getSelectionModel().select(findById(cats, existing.getCategoryId()));
            }
//            Status selecteren als er minstens 1 bestaat
        } else {
            if (!statuses.isEmpty()) cbStatus.getSelectionModel().select(0);
        }

        d.getDialogPane().setContent(g);

// Knop van de tekst
        Button saveNode = (Button) d.getDialogPane().lookupButton(save);
        saveNode.addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
            String title = tfTitle.getText().trim();
            Database.IdName st = cbStatus.getSelectionModel().getSelectedItem();
//            Validatie: titel is verplicht
            if (title.isEmpty()) { info("Titel is verplicht."); ev.consume(); return; }
//            Validatie: status verplicht
            if (st == null) { info("Kies een status."); ev.consume(); return; }

            String desc = taDesc.getText().trim();
            LocalDate deadline = dp.getValue();
            Database.IdName cat = cbCat.getSelectionModel().getSelectedItem();
            Integer catId = (cat == null) ? null : cat.getId();
// CRUD voor update en insert
            if (edit) db.updateTask(existing.getId(), title, desc, deadline, st.getId(), catId);
            else db.insertTask(title, desc, deadline, st.getId(), catId);
// Na opslaan: tabel opnieuw laden
            load();
        });

        d.showAndWait();
    }
// Helper
    private Database.IdName findById(List<Database.IdName> list, int id) {
        for (Database.IdName x : list) if (x.getId() == id) return x;
        return null;
    }
// informatie popup
    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
