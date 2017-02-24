DROP TABLE patient IF EXISTS;
DROP TABLE study IF EXISTS;
DROP TABLE doctor IF EXISTS;
DROP TABLE room IF EXISTS;
DROP TABLE procedure_schedule IF EXISTS;

CREATE TABLE patient (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name       VARCHAR(30) NOT NULL,
  last_name       VARCHAR(30) NOT NULL,
  sex       VARCHAR(30),
  birth_date DATE
);

CREATE TABLE study (
  id         INTEGER IDENTITY PRIMARY KEY,
  description       VARCHAR(80) NOT NULL
);

CREATE TABLE doctor (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);

CREATE TABLE room (
  id   INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(80)
);

CREATE TABLE procedure_schedule (
  id   INTEGER IDENTITY PRIMARY KEY,
  doctor_id    INTEGER NOT NULL,
  room_id    INTEGER NOT NULL,
  patient_id    INTEGER NOT NULL,
  study_id    INTEGER NOT NULL,
  status VARCHAR(80) NOT NULL,
  planned_start_time TIMESTAMP,
  estimated_end_time TIMESTAMP
);


/*ALTER TABLE procedure_schedule ADD CONSTRAINT IF NOT EXISTS fk_processSchedule_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (id);
ALTER TABLE procedure_schedule ADD CONSTRAINT fk_processSchedule_doctor FOREIGN KEY (room_id) REFERENCES room (id);
ALTER TABLE procedure_schedule ADD CONSTRAINT fk_processSchedule_doctor FOREIGN KEY (patient_id) REFERENCES patient (id);
ALTER TABLE procedure_schedule ADD CONSTRAINT fk_processSchedule_doctor FOREIGN KEY (study_id) REFERENCES study (id);
*/

