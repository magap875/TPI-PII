-- CREACIÓN DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS sistemapadel;
USE sistemapadel;

-- TABLA JUGADOR
CREATE TABLE IF NOT EXISTS jugador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreJugador VARCHAR(100) NOT NULL,
    dniJugador VARCHAR(20) NOT NULL UNIQUE,
    telefonoJugador VARCHAR(20)
);

-- TABLA GRUPO
CREATE TABLE IF NOT EXISTS grupo (
    idGrupo CHAR(1) PRIMARY KEY
);

-- TABLA PAREJA
CREATE TABLE IF NOT EXISTS pareja (
    idPareja INT AUTO_INCREMENT PRIMARY KEY,
    idJugador1 INT NOT NULL,
    idJugador2 INT NOT NULL,
    idGrupo CHAR(1),
    FOREIGN KEY (idJugador1) REFERENCES jugador(id),
    FOREIGN KEY (idJugador2) REFERENCES jugador(id),
    FOREIGN KEY (idGrupo) REFERENCES grupo(idGrupo)
);

-- TABLA PARTIDO
CREATE TABLE IF NOT EXISTS partido (
    idPartido INT AUTO_INCREMENT PRIMARY KEY,
    idPareja1 INT NOT NULL,
    idPareja2 INT NOT NULL,
    resultado VARCHAR(50),
    idGrupo CHAR(1),
    FOREIGN KEY (idPareja1) REFERENCES pareja(idPareja),
    FOREIGN KEY (idPareja2) REFERENCES pareja(idPareja),
    FOREIGN KEY (idGrupo) REFERENCES grupo(idGrupo)
);

-- TABLA ESTADISTICA
CREATE TABLE IF NOT EXISTS estadistica (
    idPareja INT PRIMARY KEY,
    partidosJugados INT DEFAULT 0,
    partidosGanados INT DEFAULT 0,
    partidosPerdidos INT DEFAULT 0,
    FOREIGN KEY (idPareja) REFERENCES pareja(idPareja)
);


-- TABLA TORNEO
CREATE TABLE IF NOT EXISTS torneo (
    idTorneo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    capacidad_maxima INT NOT NULL
);

-- INSERTAR 24 JUGADORES
INSERT INTO jugador (nombreJugador, dniJugador, telefonoJugador) VALUES
('Jugador 1', 'DNI001', '111-1111'),
('Jugador 2', 'DNI002', '111-1112'),
('Jugador 3', 'DNI003', '111-1113'),
('Jugador 4', 'DNI004', '111-1114'),
('Jugador 5', 'DNI005', '111-1115'),
('Jugador 6', 'DNI006', '111-1116'),
('Jugador 7', 'DNI007', '111-1117'),
('Jugador 8', 'DNI008', '111-1118'),
('Jugador 9', 'DNI009', '111-1119'),
('Jugador 10', 'DNI010', '111-1120'),
('Jugador 11', 'DNI011', '111-1121'),
('Jugador 12', 'DNI012', '111-1122');





