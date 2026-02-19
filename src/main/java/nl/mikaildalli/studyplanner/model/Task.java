package nl.mikaildalli.studyplanner.model;

import java.time.LocalDate;

/**
 * Task model:
 * - IDs (statusId/categoryId) zijn nodig voor UPDATE/DELETE
 * - namen (statusName/categoryName) zijn handig voor TableView (JOIN)
 */
public class Task {
    private final int id;
    private final String title;
    private final String description;
    private final LocalDate deadline;

    private final int statusId;
    private final Integer categoryId;

    private final String statusName;
    private final String categoryName;

    public Task(int id, String title, String description, LocalDate deadline,
                int statusId, Integer categoryId,
                String statusName, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.statusId = statusId;
        this.categoryId = categoryId;
        this.statusName = statusName;
        this.categoryName = categoryName;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDeadline() { return deadline; }

    public int getStatusId() { return statusId; }
    public Integer getCategoryId() { return categoryId; }

    public String getStatusName() { return statusName; }
    public String getCategoryName() { return categoryName; }
}
