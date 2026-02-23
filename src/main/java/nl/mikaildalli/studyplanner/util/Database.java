package nl.mikaildalli.studyplanner.util;

import nl.mikaildalli.studyplanner.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Database.java
// /* READ (taken met JOIN)
// CREATE (Taken toevoegen)
// UPDATE (Taken aanpassen)
// DELETE (Taken verwijderen)
public class Database {

    private static final String URL =
            "jdbc:mysql://localhost:3306/studyplanner_bp2?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Haalt alle taken op inclusief statusnaam en categorienaam
    // Taken ophalen met JOIN
    public List<Task> getAllTasksWithJoin() {
        String sql = """
            SELECT t.id, t.title, t.description, t.deadline,
                   s.name AS status_name,
                   c.name AS category_name,
                   t.status_id,
                   t.category_id
            FROM task t
            JOIN status s ON s.id = t.status_id
            LEFT JOIN category c ON c.id = t.category_id
            ORDER BY t.deadline IS NULL, t.deadline ASC, t.id DESC
        """;

        List<Task> tasks = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date dl = rs.getDate("deadline");
                LocalDate deadline = (dl == null) ? null : dl.toLocalDate();

                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        deadline,
                        rs.getInt("status_id"),
                        rs.getObject("category_id") == null ? null : rs.getInt("category_id"),
                        rs.getString("status_name"),
                        rs.getString("category_name")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB fout bij getAllTasksWithJoin: " + e.getMessage(), e);
        }

        return tasks;
    }


    // CREATE: Taak toevoegen
//    nieuwe taken toevoegen
    public void insertTask(String title, String description, LocalDate deadline,
                           int statusId, Integer categoryId) {
        String sql = """
            INSERT INTO task (title, description, deadline, status_id, category_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);

            if (deadline == null) ps.setNull(3, Types.DATE);
            else ps.setDate(3, Date.valueOf(deadline));

            ps.setInt(4, statusId);

            if (categoryId == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, categoryId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB fout bij insertTask: " + e.getMessage(), e);
        }
    }

    // UPDATE: Taak aanpassen
    // Aanpassen van taken (UPDATE) basis van id
    public void updateTask(int id, String title, String description, LocalDate deadline,
                           int statusId, Integer categoryId) {
        String sql = """
            UPDATE task
            SET title = ?, description = ?, deadline = ?, status_id = ?, category_id = ?
            WHERE id = ?
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);

            if (deadline == null) ps.setNull(3, Types.DATE);
            else ps.setDate(3, Date.valueOf(deadline));

            ps.setInt(4, statusId);

            if (categoryId == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, categoryId);

            ps.setInt(6, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB fout bij updateTask: " + e.getMessage(), e);
        }
    }

    // DELETE: Taak verwijderen
// Verwijderen van taken op basiss van id
    public void deleteTaskById(int id) {
        String sql = "DELETE FROM task WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB fout bij deleteTaskById: " + e.getMessage(), e);
        }
    }


    //  Status + Category ophalen (voor ComboBox)
// Verwijderen van taken op basiss van id

    public List<IdName> getAllStatuses() {
        return getAllIdNames("SELECT id, name FROM status ORDER BY id");
    }


    public List<IdName> getAllCategories() {
        return getAllIdNames("SELECT id, name FROM category ORDER BY name");
    }


    // Helper method: maakt van SELECT idm name een lijst idname objecten
    private List<IdName> getAllIdNames(String sql) {
        List<IdName> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new IdName(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB fout bij getAllIdNames: " + e.getMessage(), e);
        }

        return list;
    }

    // Bewaren van id en naam en tonen in de UI automatisch
    public static class IdName {
        private final int id;
        private final String name;

        public IdName(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public String getName() { return name; }

        @Override
        public String toString() {
            // ComboBox toont dit als tekst
            return name;
        }
    }
}
