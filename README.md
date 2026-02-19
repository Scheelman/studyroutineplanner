Study Planner en Routine Planner (JavaFX en MySQL)

Introductie
Study Planner en Routine Planner BP2 is een JavaFX desktop applicatie waarmee je taken kunt beheren.
Je kunt taken aanmaken, bekijken, aanpassen en verwijderen (CRUD). De data wordt opgeslagen in een MySQL
Doel van dit project: aantonen dat ik een werkende Java applicatie kan bouwen, gekoppeld aan een database, inclusief acceptatietesten.



Inhoudsopgave
- [Functionaliteiten]
- [Technologieën]
- [Installatie en Run]
- [Database import]
- [Implementatieplan
- [Testen]
- [Projectstatus]
- [Bronnen]

---

Functionaliteiten
Taken (CRUD)
- Overzicht van taken in een TableView
- Nieuwe taak toevoegen (titel, omschrijving, deadline, status, categorie)
- Taak bewerken
- Taak verwijderen (met bevestiging)
- Refresh (opnieuw laden vanuit database)

Database
- Tabellen: `task`, `status`, `category` (+ voorbereid: `routine`, `routine_log`)
- PK/FK relaties + indexes
- JOIN query zodat status/categorie als naam wordt getoond

---

Technologieën
- Java
- JavaFX
- Maven
- MySQL (via XAMPP/phpMyAdmin)
- JDBC



Installatie en Run
Vereisten
- Java JDK geïnstalleerd
- Maven via IntelliJ
- XAMPP met MySQL aan

Run via IntelliJ
1. Open project in IntelliJ
2. Import Maven dependencies (pom.xml)
3. Start MySQL (XAMPP)
4. Run `MainApp`

Run via Maven (optioneel)

Database impor
1. Start XAMPP en zet MySQL aan
2. Open phpMyAdmin
3. Maak database aan: `studyplanner_bp2`
4. Importeer `db/structure.sql`
5. Controle: tabellen bestaan (`task`, `status`, `category', etc.)



Implementatieplan

1. Huidige staat
- Applicatie werkt lokaal via IntelliJ..
- Database draait lokaal via XAMPP/MySQL.
- Taken CRUD is getest.
- Database-structuur staat in `db/structure.sql`.
- Testdocumenten staan in `docs/`.

2. Stappen naar oplevering
1. controleren: `src/`, `pom.xml`, `README.md`, `db/structure.sql`, `docs/` aanwezig.
2. `.gitignore` check: `target/` en `.idea/` niet uploaden.
3. Fresh DB test:
    - drop database
    - opnieuw import `structure.sql`
    - app starten en CRUD uitvoeren
4. Oplevering:
    - laatste push naar GitHub
    - controleer dat run-stappen in README kloppen

3. Tijdlijn (planning)
- Dag 1: README + implementatieplan afronden
- Dag 2: bewijs-screenshots toevoegen in `docs/test-evidence/` + final test
- Dag 3: laatste check + inleveren

4. Communicatie + evaluatie/verificatie
   Communicatie
- Alles staat in GitHub repo. README bevat installatie- en run-stappen.

Evaluatie / verificatie
- Acceptatietest uitgevoerd met testgevallen spreadsheet.
- Resultaten samengevat in testrapport.
- Verificatie door “fresh install” test (DB opnieuw importeren + CRUD testen).

---

Testen
- Testgevallen: `docs/Testgevallen_BP2_StudyPlanner.xlsx`
- Testrapport: `docs/Testrapport_BP2_StudyPlanner_v2.docx`

---

Projectstatus
- Taken (CRUD): afgerond en getest.
- Routines/RoutineLog: voorbereid in database, buiten scope van huidige testse


