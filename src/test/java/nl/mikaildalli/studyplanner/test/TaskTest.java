package nl.mikaildalli.studyplanner.test;

import nl.mikaildalli.studyplanner.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
//   Unit tests (JUnit)
// Aantonen dat model object correct wordt opgebouwd en waarden teruggeeft
//    Zonder database (sneller)
    @Test
    void task_constructor_sets_values() {
        LocalDate d = LocalDate.of(2026, 2, 15);


        Task t = new Task(1, "BP300", "Test", d, 1, null, "TODO", null);

        assertEquals(1, t.getId());
        assertEquals("BP300", t.getTitle());
        assertEquals("Test", t.getDescription());
        assertEquals(d, t.getDeadline());

        assertEquals(1, t.getStatusId());
        assertNull(t.getCategoryId());
        assertEquals("TODO", t.getStatusName());
        assertNull(t.getCategoryName());
    }







    @Test
    void deadline_can_be_null() {
        Task t = new Task(1, "X", "", null, 1, null, "TODO", null);
        assertNull(t.getDeadline());
    }
}