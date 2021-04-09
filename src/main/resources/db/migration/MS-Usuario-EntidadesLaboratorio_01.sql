CREATE TABLE tipo_usuario
(
    id   SERIAL PRIMARY KEY,
    tipo VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE usuario
(
    id              SERIAL PRIMARY KEY,
    usuario         VARCHAR(20) UNIQUE NOT NULL,
    password        VARCHAR(50)        NOT NULL,
    tipo_usuario_id INTEGER,
    CONSTRAINT fk_tipo_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario (id)
);

CREATE TABLE empleado
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(50) NOT NULL,
    usuario_id INTEGER     NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE cliente
(
    id                      SERIAL PRIMARY KEY,
    razon_social            VARCHAR(100)       NOT NULL,
    cuit                    VARCHAR(11) UNIQUE NOT NULL,
    mail                    VARCHAR(50) UNIQUE NOT NULL,
    maximo_cuenta_corriente NUMERIC(19, 2),
    habilitado_online       BOOLEAN            NOT NULL DEFAULT FALSE,
    usuario_id              INTEGER            NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE tipo_obra
(
    id   SERIAL PRIMARY KEY,
    tipo VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE obra
(
    id           SERIAL PRIMARY KEY,
    descripcion  VARCHAR(255) NOT NULL,
    direccion    VARCHAR(100) NOT NULL UNIQUE,
    latitud      REAL         NOT NULL,
    longitud     REAL         NOT NULL,
    superficie   INTEGER      NOT NULL,
    tipo_obra_id INTEGER      NOT NULL,
    cliente_id   INTEGER      NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    CONSTRAINT fk_tipo_obra FOREIGN KEY (tipo_obra_id) REFERENCES tipo_obra (id)
);

INSERT INTO tipo_obra (tipo)
VALUES ('Reforma'),
       ('Casa'),
       ('Edificio'),
       ('Vial');

INSERT INTO tipo_usuario (tipo)
VALUES ('Cliente'),
       ('Vendedor');