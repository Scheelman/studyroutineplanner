package nl.mikaildalli.studyplanner.util;

import nl.mikaildalli.studyplanner.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database.java (stijl 1)
 * --------------------------------------------
 * In de oefenopdrachten staat vaak alle SQL in één class.
 * Dat is handig omdat je docent direct ziet:
 * - welke CRUD queries je gebruikt
 * - dat je PreparedStatements gebruikt (veilig en professioneel)
 * - dat je resources netjes sluit (try-with-resources)
 *
 * In deze class staan:
 * - READ (taken met JOIN)  ✅
 * - CREATE (taak toevoegen) ✅
 * - UPDATE (taak aanpassen) ✅
 * - DELETE (taak verwijderen) ✅
 * + extra READs voor status/category (voor ComboBoxes)
 */
public class Database {

    private static final String URL =
            "jdbc:mysql://localhost:3306/studyplanner_bp2?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // vul in als je een wachtwoord hebt

    /**
     * Maakt een nieuwe connectie aan.
     * We maken bewust geen "global" connection die altijd open blijft,
     * omdat dat vaak errors geeft. Met try-with-resources sluit alles netjes.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // =========================================================
    // READ: Taken ophalen (met JOIN)
    // =========================================================

    /**
     * Haalt alle taken op inclusief statusnaam en categorienaam.
     * Dit is een READ query met JOIN, zodat je in de UI geen losse ID's ziet,
     * maar gewoon "TODO" en "School".
     */
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

    // =========================================================
    // CREATE: Taak toevoegen
    // =========================================================

    /**
     * Voegt een nieuwe taak toe (CREATE).
     * We gebruiken PreparedStatement om SQL injection te voorkomen
     * en om netjes met null-waardes te kunnen werken.
     */
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

    // =========================================================
    // UPDATE: Taak aanpassen
    // =========================================================

    /**
     * Past een taak aan (UPDATE) op basis van id.
     */
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

    // =========================================================
    // DELETE: Taak verwijderen
    // =========================================================

    /**
     * Verwijdert een taak op basis van id (DELETE).
     */
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

    // =========================================================
    // Extra: Status + Category ophalen (voor ComboBox)
    // =========================================================

    /**
     * Haalt alle statussen op (id + name).
     * Handig voor een ComboBox in je "Nieuwe taak" scherm.
     */
    public List<IdName> getAllStatuses() {
        return getAllIdNames("SELECT id, name FROM status ORDER BY id");
    }

    /**
     * Haalt alle categorieën op (id + name).
     */
    public List<IdName> getAllCategories() {
        return getAllIdNames("SELECT id, name FROM category ORDER BY name");
    }

    /**
     * Helper method: maakt van een SELECT id,name een lijst IdName objecten.
     */
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

    /**
     * Klein hulpklasse voor ComboBox:
     * We bewaren id + naam, en tonen in de UI automatisch de naam via toString().
     */
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
