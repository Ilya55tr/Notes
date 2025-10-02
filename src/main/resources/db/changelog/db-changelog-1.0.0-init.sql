--liquibase formatted sql

--changeset ilyatr:1-create-sequences
CREATE SEQUENCE note_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE task_seq START WITH 1 INCREMENT BY 1;

--changeset ilyatr:2-create-note-table
CREATE TABLE note (
                      id INTEGER PRIMARY KEY,
                      name VARCHAR(255)
);

--changeset ilyatr:3-create-task-table
CREATE TABLE task (
                      id INTEGER PRIMARY KEY,
                      task_name VARCHAR(255),
                      text TEXT,
                      note_id INTEGER NOT NULL
);

--changeset ilyatr:4-add-foreign-key-task-note
ALTER TABLE task
    ADD CONSTRAINT fk_task_note
        FOREIGN KEY (note_id) REFERENCES note(id)
            ON DELETE CASCADE;