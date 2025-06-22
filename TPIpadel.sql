--- CREACIÓN DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS sistemapadel;
USE sistemapadel;
-- drop database sistemapadel;
-- TABLA JUGADOR
CREATE TABLE IF NOT EXISTS jugador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreJugador VARCHAR(100) NOT NULL,
    dniJugador VARCHAR(20) NOT NULL UNIQUE,
    telefonoJugador VARCHAR(20)
);

-- TABLA TORNEO
CREATE TABLE IF NOT EXISTS torneo (
    idTorneo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    capacidad_maxima INT NOT NULL
);

-- TABLA GRUPO (clave primaria compuesta: idTorneo + idGrupo)
CREATE TABLE IF NOT EXISTS grupo (
    idTorneo INT,
    idGrupo CHAR(1),
    PRIMARY KEY (idTorneo, idGrupo),
    FOREIGN KEY (idTorneo) REFERENCES torneo(idTorneo)
);

-- TABLA PAREJA (referencia a grupo mediante clave compuesta)
CREATE TABLE IF NOT EXISTS pareja (
    idPareja INT AUTO_INCREMENT PRIMARY KEY,
    idJugador1 INT NOT NULL,
    idJugador2 INT NOT NULL,
    idTorneo INT NOT NULL,
    idGrupo CHAR(1) NOT NULL,
    FOREIGN KEY (idJugador1) REFERENCES jugador(id),
    FOREIGN KEY (idJugador2) REFERENCES jugador(id),
    FOREIGN KEY (idTorneo, idGrupo) REFERENCES grupo(idTorneo, idGrupo)
);

-- TABLA PARTIDO (también referencia a grupo mediante clave compuesta)
CREATE TABLE IF NOT EXISTS partido (
    idPartido INT AUTO_INCREMENT PRIMARY KEY,
    idPareja1 INT NOT NULL,
    idPareja2 INT NOT NULL,
    resultado VARCHAR(50),
    idTorneo INT NOT NULL,
    idGrupo CHAR(1) NOT NULL,
    FOREIGN KEY (idPareja1) REFERENCES pareja(idPareja),
    FOREIGN KEY (idPareja2) REFERENCES pareja(idPareja),
    FOREIGN KEY (idTorneo, idGrupo) REFERENCES grupo(idTorneo, idGrupo)
);

-- TABLA ESTADISTICA (referencia directa a pareja)
CREATE TABLE IF NOT EXISTS estadistica (
    idPareja INT PRIMARY KEY,
    partidosJugados INT DEFAULT 0,
    partidosGanados INT DEFAULT 0,
    partidosPerdidos INT DEFAULT 0,
    FOREIGN KEY (idPareja) REFERENCES pareja(idPareja)
);

INSERT INTO jugador (nombreJugador, dniJugador, telefonoJugador) VALUES
('Martín López',     '37.645.982', '011-4578-3291'),
('Lucía Fernández',  '28.374.615', '011-3345-7789'),
('Tomás González',   '19.583.741', '011-5567-1234'),
('Valentina Rossi',  '23.849.102', '011-6678-9901'),
('Santiago Pérez',   '31.274.899', '011-4455-6677'),
('Camila Sánchez',   '27.391.456', '011-7788-2233'),
('Joaquín Díaz',     '33.517.206', '011-8899-3344'),
('Antonella Gómez',  '29.648.315', '011-9900-5566'),
('Diego Herrera',    '21.907.432', '011-2233-4455'),
('María Villalba',   '26.105.829', '011-3344-6677'),
('Facundo Rojas',    '34.218.673', '011-5566-7788'),
('Victoria Castro',  '30.392.510', '011-6677-8899'),
('Nicolás Medina',   '32.184.759', '011-4455-9900'),
('Florencia Duarte', '24.517.803', '011-7788-1122'),
('Bruno Acosta',     '35.609.274', '011-8899-2233'),
('Camila Vega',      '22.734.981', '011-9900-3344'),
('Gonzalo Molina',   '29.105.467', '011-2233-5566'),
('Mariana Ruiz',     '27.918.635', '011-3344-7788'),
('Federico Ortiz',   '31.456.928', '011-5566-8899'),
('Belén Navarro',    '28.302.174', '011-6677-9900'),
('Matías Bravo',     '33.819.205', '011-7788-4455'),
('Sofía Morales',    '25.607.319', '011-8899-5566'),
('Ignacio Castro',   '30.214.578', '011-9900-6677'),
('Isabella Fuentes', '26.932.450', '011-2233-7788');

