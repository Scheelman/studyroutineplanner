## Implementatieplan

Mikail Dalli

Studentnummer: 2203898

### 1. Huidige staat

De applicatie werkt lokaal via IntelliJ.  
De database draait via XAMPP/MySQL.

Op dit moment zijn de belangrijkste onderdelen afgerond:

- CRUD-functionaliteit voor taken
- TableView gekoppeld aan de database via een JOIN-query
- status en categorie worden als naam getoond
- validatie bij invoer
- bevestiging bij verwijderen
- OOP uitgebreid met `PlannerItem`, `Task` en `Routine`
- `TaskService` toegevoegd als tussenlaag
- minimaal 5 unit tests aanwezig
- acceptatietesten uitgevoerd
- database beschikbaar via `db/structure.sql`
- artefact/JAR getest

---

### 2. Realisatieproces met planning

#### Fase 1 — Database  
**10 februari 2026 — ongeveer 19:00–21:00**

In deze fase heb ik de database opgezet.

Gedaan:

- tabellen aangemaakt: `task`, `status`, `category`
- `routine` en `routine_log` voorbereid
- primary keys en foreign keys ingesteld
- indexen toegevoegd
- `structure.sql` export gemaakt
- testdata toegevoegd

Doel: een stabiele databasebasis maken.

---

#### Fase 2 — JavaFX basis  
**12 februari 2026 — ongeveer 18:30–21:30**

In deze fase heb ik de basis van de applicatie gemaakt.

Gedaan:

- `MainApp` opgezet
- `AppView` gemaakt met BorderPane
- navigatie toegevoegd naar Dashboard, Tasks en Notes
- schermen gescheiden in aparte classes

Doel: een duidelijke basisstructuur maken.

---

#### Fase 3 — CRUD  
**13 t/m 15 februari 2026 — avonden**

In deze fase heb ik de takenmodule gemaakt.

Gedaan:

- `Database.java` gemaakt
- SQL-methodes gemaakt voor CRUD
- PreparedStatements gebruikt
- JOIN-query gemaakt
- TableView gekoppeld aan `Task`
- dialog gemaakt voor toevoegen en bewerken
- verwijderen met bevestiging toegevoegd
- refresh toegevoegd

Doel: werkende CRUD-functionaliteit voor taken.

---

#### Fase 4 — Verbeteringen na feedback  
**Mei 2026 — meerdere avonden**

Na de feedback heb ik de Java-kant verbeterd.

Gedaan:

- `PlannerItem` toegevoegd
- `Task` en `Routine` laten erven van `PlannerItem`
- `getType()` overschreven voor polymorfisme
- `TaskService` toegevoegd als tussenlaag
- `TaskView` aangepast zodat deze `TaskService` gebruikt
- unit tests uitgebreid naar minimaal 5
- artefact opnieuw gebouwd en getest

Doel: beter aansluiten op de lesstof en feedback.

---

### 3. Controles richting oplevering

Voor de oplevering heb ik gecontroleerd:

- projectstructuur (`src/`, `pom.xml`, `README.md`, `db/`, `docs/`)
- `.gitignore`
- database opnieuw geïmporteerd via `structure.sql`
- CRUD volledig getest
- unit tests uitgevoerd
- artefact/JAR getest via PowerShell
- laatste commits gepusht naar GitHub

---

### 4. Verificatie en validatie

De werking is gecontroleerd door:

- handmatige CRUD-tests
- acceptatietesten
- unit tests
- fresh database test
- artifact test via PowerShell

---

### 5. Projectstatus

- Takenmodule: afgerond en getest
- Database: afgerond
- OOP: uitgebreid met overerving en polymorfisme
- TaskService: toegevoegd
- Unit tests: minimaal 5 aanwezig
- Artefact: beschikbaar en getest
- Routine/RoutineLog: voorbereid in database, buiten scope
