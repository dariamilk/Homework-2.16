-- liquibase formatted sql

-- changeset dchulpanova:1
create index students_name_idx on student (name);
create index faculty_name_color_idx on faculty (name, color);