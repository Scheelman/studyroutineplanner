Study Planner en Routine Planner (JavaFX en MySQL)

Introductie
Study Planner en Routine Planner BP2 is een JavaFX desktop applicatie waarmee je taken kunt beheren.
Je kunt taken aanmaken, bekijken, aanpassen en verwijderen (CRUD). De data wordt opgeslagen in een MySQL
Doel van dit project: aantonen dat ik een werkende Java applicatie kan bouwen, gekoppeld aan een database, inclusief acceptatietesten.
___
Instructies om te runnen: 
Download JavaFX SDK

Run command: java --module-path "...\lib" --add-modules javafx.controls,javafx.fxml -jar studyplannerbp2.jar”
___
Implementatieplan
1. Huidige staat februari 2026

De applicatie is functioneel en werkt lokaal via IntelliJ.
De database draait via XAMPP MySql

Op dit moment zijn de volgende onderdelen afgerond:

CRUD-functionaliteit voor taken (Create, Read, Update, Delete)

TableView gekoppeld aan database via JOIN query

Status en categorie worden als naam getoond in plaats van ID

Validatie bij invoer (titel en status verplicht)

Bevestiging bij verwijderen van een taak

Unit tests voor het Task-model

Acceptatietest uitgevoerd met bijbehorend testrapport

Database-structuur beschikbaar in db/structure.sql


___
2. Realisatieproces 
Fase 1  Database ontwerp en implementatie
10 februari 2026


Tabellen aangemaakt: task, status, category

Primary keys en foreign keys ingesteld

Relaties gecontroleerd

structure.sql export gemaakt

Testdata ingevoerd

Doel van deze fase: een stabiele en correcte databasebasis maken.



___
Fase 2 – JavaFX basisstructuur

12 februari 2026

MainApp opgezet

AppView met BorderPane layout gemaakt

Sidebar navigatie toegevoegd (Dashboard / Tasks / Notes)

Schermen gescheiden in aparte classes

Doel van deze fase: duidelijke scheiding tussen UI-onderdelen.






___
Fase 3 – CRUD implementatie

13–15 februari 2026

Database.java aangemaakt alle queries in een class

PreparedStatements gebruikt voor veilige SQL

getAllTasksWithJoin() gemaakt met JOIN query

TableView gekoppeld aan Task-model

Dialog toegevoegd voor toevoegen/bewerken

Delete met bevestiging (Alert)

Refresh functie geïmplementeerd

Doel van deze fase: volledige werkende takenmodule.




___
Fase 4 – Testen en afronding

16–17 februari 2026

Testgevallen uitgevoerd (Excel)

Testrapport ingevuld

Unit tests toegevoegd (JUnit 5)

Fresh database test uitgevoerd

README uitgebreid met installatie- en run-stappen

Artefact gebouwd via Build Artifact

Doel van deze fase: controle van het eind resultaat

3. Stappen richting oplevering

Voor de definitieve oplevering zijn de volgende controles uitgevoerd:

Controle projectstructuur:

src/

pom.xml

README.md

db/structure.sql

docs/

.gitignore gecontroleerd


Fresh install test:

Database verwijderd

structure.sql opnieuw geimporteerd

Applicatie gestart

CRUD volledig getest

Artefact:

Build Artifact uitgevoerd

JAR getest via IntelliJ en module-path

Laatste commit en push naar GitHub uitgevoerd.

4. Verificatie en validatie

De werking van de applicatie is gecontroleerd door:

Handmatige tests van CRUD

Acceptatietest volgens testgevallen

Unit tests voor het Task-model

5. Projectstatus

Takenmodule: afgerond en volledig getest

Routine/RoutineLog: database voorbereid, maar buiten scope van deze realisatie

Code voorzien van comments

Unit tests aanwezig

Artefact beschikbaar

