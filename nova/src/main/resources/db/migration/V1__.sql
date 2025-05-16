CREATE TABLE carrito
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    fecha_compra datetime              NULL,
    finalizado   BIT(1)                NULL,
    id_cliente   BIGINT                NULL,
    CONSTRAINT pk_carrito PRIMARY KEY (id)
);

CREATE TABLE cliente
(
    id       BIGINT       NOT NULL,
    nombre   VARCHAR(255) NULL,
    apellido VARCHAR(255) NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

CREATE TABLE contiene
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    cantidad    INT                   NULL,
    id_carrito  BIGINT                NOT NULL,
    id_producto BIGINT                NOT NULL,
    CONSTRAINT pk_contiene PRIMARY KEY (id)
);

CREATE TABLE distribuidor
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    nombre            VARCHAR(255)          NULL,
    nif               VARCHAR(255)          NULL,
    direccion         VARCHAR(255)          NULL,
    telefono          VARCHAR(255)          NULL,
    email             VARCHAR(255)          NULL,
    nombre_repartidor VARCHAR(255)          NULL,
    id_proveedor      BIGINT                NULL,
    CONSTRAINT pk_distribuidor PRIMARY KEY (id)
);

CREATE TABLE producto
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    nombre          VARCHAR(255)          NULL,
    descripcion     VARCHAR(255)          NULL,
    precio          DOUBLE                NULL,
    entregado       BIT(1)                NULL,
    id_distribuidor BIGINT                NULL,
    CONSTRAINT pk_producto PRIMARY KEY (id)
);

CREATE TABLE proveedor
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    nombre    VARCHAR(255)          NULL,
    nif       VARCHAR(255)          NULL,
    direccion VARCHAR(255)          NULL,
    telefono  VARCHAR(255)          NULL,
    email     VARCHAR(255)          NULL,
    CONSTRAINT pk_proveedor PRIMARY KEY (id)
);

CREATE TABLE rol
(
    id     INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_rol PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id     INT    NOT NULL,
    user_codigo BIGINT NOT NULL
);

CREATE TABLE usuario
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    username           VARCHAR(255)          NOT NULL,
    password           VARCHAR(255)          NOT NULL,
    dni                VARCHAR(255)          NOT NULL,
    fecha_ultimo_login datetime              NULL,
    activo             BIT(1)                NULL,
    creation_date      datetime              NOT NULL,
    email              VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE distribuidor
    ADD CONSTRAINT uc_distribuidor_id_proveedor UNIQUE (id_proveedor);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_email UNIQUE (email);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_username UNIQUE (username);

ALTER TABLE carrito
    ADD CONSTRAINT FK_CARRITO_ON_ID_CLIENTE FOREIGN KEY (id_cliente) REFERENCES cliente (id);

ALTER TABLE cliente
    ADD CONSTRAINT FK_CLIENTE_ON_ID FOREIGN KEY (id) REFERENCES usuario (id);

ALTER TABLE contiene
    ADD CONSTRAINT FK_CONTIENE_ON_ID_CARRITO FOREIGN KEY (id_carrito) REFERENCES carrito (id);

ALTER TABLE contiene
    ADD CONSTRAINT FK_CONTIENE_ON_ID_PRODUCTO FOREIGN KEY (id_producto) REFERENCES producto (id);

ALTER TABLE distribuidor
    ADD CONSTRAINT FK_DISTRIBUIDOR_ON_ID_PROVEEDOR FOREIGN KEY (id_proveedor) REFERENCES proveedor (id);

ALTER TABLE producto
    ADD CONSTRAINT FK_PRODUCTO_ON_ID_DISTRIBUIDOR FOREIGN KEY (id_distribuidor) REFERENCES distribuidor (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES rol (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_usuario FOREIGN KEY (user_codigo) REFERENCES usuario (id);