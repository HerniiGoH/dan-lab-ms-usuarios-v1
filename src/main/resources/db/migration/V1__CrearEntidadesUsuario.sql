CREATE TABLE tipo_usuario
(
    id   INTEGER UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE usuario
(
    id              INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usuario         VARCHAR(20) UNIQUE NOT NULL,
    password        VARCHAR(50)        NOT NULL,
    tipo_usuario_id INTEGER UNSIGNED,
    CONSTRAINT fk_tipo_usuario_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario (id)
);

CREATE TABLE empleado
(
    id         INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(50)      NOT NULL,
    usuario_id INTEGER UNSIGNED NOT NULL,
    CONSTRAINT fk_usuario_empleado FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE cliente
(
    id                      INTEGER UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    razon_social            VARCHAR(100)       NOT NULL,
    cuit                    VARCHAR(11) UNIQUE NOT NULL,
    mail                    VARCHAR(50) UNIQUE NOT NULL,
    maximo_cuenta_corriente NUMERIC(19, 2),
    habilitado_online       BOOLEAN            NOT NULL DEFAULT FALSE,
    usuario_id              INTEGER UNSIGNED   NOT NULL,
    CONSTRAINT fk_usuario_cliente FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE tipo_obra
(
    id   INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE obra
(
    id           INTEGER UNSIGNED   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    descripcion  VARCHAR(255)       NOT NULL,
    direccion    VARCHAR(100)       NOT NULL UNIQUE,
    latitud      REAL               NOT NULL,
    longitud     REAL               NOT NULL,
    superficie   INTEGER            NOT NULL,
    tipo_obra_id INTEGER UNSIGNED   NOT NULL,
    cliente_id   INTEGER UNSIGNED   NOT NULL,
    CONSTRAINT fk_cliente_obra FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    CONSTRAINT fk_tipo_obra_obra FOREIGN KEY (tipo_obra_id) REFERENCES tipo_obra (id)
);

INSERT INTO tipo_obra (tipo)
VALUES ('Reforma'),
       ('Casa'),
       ('Edificio'),
       ('Vial');

INSERT INTO tipo_usuario (tipo)
VALUES ('Cliente'),
       ('Vendedor');