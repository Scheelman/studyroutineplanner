-- Database structuur
-- Het maakt de database + tabellen aan

CREATE DATABASE IF NOT EXISTS studyplanner_bp2;
USE studyplanner_bp2;

-- ===== STATUS =====
-- Lookup tabel: status van een taak
CREATE TABLE IF NOT EXISTS status (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(30) NOT NULL UNIQUE
    );


-- Lookup tabel: categorie van een taak bijv School/Werk
CREATE TABLE IF NOT EXISTS category (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(50) NOT NULL UNIQUE
    );

-- ===== TASK =====
-- Hoofdtabel: taken die de gebruiker beheert.
CREATE TABLE IF NOT EXISTS task (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(120) NOT NULL,
    description TEXT NULL,
    deadline DATE NULL,
    status_id INT NOT NULL,
    category_id INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_task_status
    FOREIGN KEY (status_id) REFERENCES status(id),

    -- Foreign key naar category
    -- Als categorie wordt verwijderd, zetten we category_id op NULL (taak blijft bestaan).
    CONSTRAINT fk_task_category
    FOREIGN KEY (category_id) REFERENCES category(id)
    ON DELETE SET NULL
    );

-- Indexen: dit helpt met performance bij filteren/sorteren
CREATE INDEX idx_task_status ON task(status_id);
CREATE INDEX idx_task_category ON task(category_id);
CREATE INDEX idx_task_deadline ON task(deadline);

-- ===== ROUTINE =====
-- Tabel met routines (bijv. "Workout", "Studeren")
CREATE TABLE IF NOT EXISTS routine (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(80) NOT NULL,
    frequency ENUM('DAILY','WEEKLY') NOT NULL DEFAULT 'DAILY',
    is_active BOOLEAN NOT NULL DEFAULT TRUE
    );

-- ===== ROUTINE_LOG =====
-- Logt wanneer een routine is uitgevoerd.
-- UNIQUE (routine_id, log_date) zorgt dat je een routine maar 1 keer per dag kunt afvinken.
CREATE TABLE IF NOT EXISTS routine_log (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           routine_id INT NOT NULL,
                                           log_date DATE NOT NULL,
                                           notes VARCHAR(255) NULL,

    -- Als routine wordt verwijderd, mogen logs mee verwijderd worden
    CONSTRAINT fk_log_routine
    FOREIGN KEY (routine_id) REFERENCES routine(id)
    ON DELETE CASCADE,

    CONSTRAINT uq_log_once_per_day UNIQUE (routine_id, log_date)
    );

CREATE INDEX idx_log_routine ON routine_log(routine_id);
CREATE INDEX idx_log_date ON routine_log(log_date);

-- ===== SEED DATA =====
-- We zetten alvast basiswaarden zodat de app direct werkt.
INSERT IGNORE INTO status (name) VALUES ('TODO'), ('DOING'), ('DONE');
INSERT IGNORE INTO category (name) VALUES ('School'), ('Werk'), ('Gezondheid');
