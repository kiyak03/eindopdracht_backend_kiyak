/*
Spring runt dit SQL-bestand automatisch. Spring zoekt namelijk naar een bestand dat data.sql heet (in deze folder) en
voert de SQL commando's voor je uit. Dit is een van de manier om de database te vullen. Om dit te laten werken, is het
volgende aan application.properties toegevoegd:

spring.datasource.initialization-mode=always

Zoals gezegd, dit is een van de manieren. De huidige opzet avn deze applicatie heeft een vast aantal user-rollen. Deze
kunnen niet door eindgebruikers, moderators of admins toegevoegd worden. Alleen de programmeur kan user-rollen
toevoegen. Daarom is er ook geen Service & repo voor de user-rollen geprogrammeerd. De enige manier om dan iets in de
database te krijgen is via SQL statements in dit bestand.

 */
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_ADMIN');

-- INSERT INTO app_user (username, email, password) VALUES ('diablo', 'diablo@hexagon.com', 'diablo123');
-- INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

-- INSERT INTO files (content_type, data, name, size, user_id) VALUES ('','' ,'4796f2ba-d56f-49e3-88dd-f7796585d877.mp3', '', 1);

