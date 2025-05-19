CREATE TABLE USERS (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    role varchar(50) NOT NULL
);

CREATE TABLE MESAS (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre varchar(50) NOT NULL,
    capacidad INTEGER NOT NULL,
    estado varchar(50) NOT NULL
);

CREATE TABLE RESERVAS(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    mesa_id INTEGER NOT NULL,
    data_reserva DATETIME2 NOT NULL,
    status varchar(50) NOT NULL
);

-- Insertar USERS and admin

INSERT INTO USERS (nombre, email, password, role) VALUES ('Charles Arruda', 'charlesArruda@hotmail.com', '1234', 'admin');

INSERT INTO USERS (nombre, email, password, role) VALUES ('Ana Solera', 'anasolera@gmail.com', '1234', 'client');
INSERT INTO USERS (nombre, email, password, role) VALUES ('Carlos Pérez', 'carlosperez@gmail.com', 'abcd', 'client');
INSERT INTO USERS (nombre, email, password, role) VALUES ('Lucía Gómez', 'luciagomez@gmail.com', 'pass123', 'client');
INSERT INTO USERS (nombre, email, password, role) VALUES ('Miguel Torres', 'migueltorres@gmail.com', 'secure456', 'client');
INSERT INTO USERS (nombre, email, password, role) VALUES ('Sofía López', 'sofialopez@gmail.com', 'qwerty', 'client');

-- Insertar MESAS

INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 1', 4, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 2', 2, 'reservada');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 3', 6, 'reservada');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 4', 8, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 5', 4, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 6', 2, 'reservada');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 7', 6, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 8', 8, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 9', 4, 'disponible');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 10', 2, 'inativa');
INSERT INTO MESAS (nombre, capacidad, estado) VALUES ('Mesa 11', 6, 'disponible');


-- Insertar RESERVAS

INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (1, 1, '2023-10-01 12:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (2, 2, '2023-10-02 13:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (3, 3, '2023-10-03 14:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (4, 4, '2023-10-04 15:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (5, 5, '2023-10-05 16:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (6, 6, '2023-10-06 17:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (7, 7, '2023-10-07 18:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (8, 8, '2023-10-08 19:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (9, 9, '2023-10-09 20:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (10, 10, '2023-10-10 21:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (1, 11, '2023-10-11 22:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (2, 1, '2023-10-12 23:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (3, 2, '2023-10-13 12:00:00', 'cancelado');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (4, 3, '2023-10-14 13:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (5, 4, '2023-10-15 14:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (6, 5, '2023-10-16 15:00:00', 'ativo');
INSERT INTO RESERVAS (usuario_id, mesa_id, data_reserva, status) VALUES (7, 6, '2023-10-17 16:00:00', 'cancelado');