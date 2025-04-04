CREATE TABLE carrito
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    fecha_compra datetime NULL,
    finalizado   BIT(1) NULL,
    id_cliente   BIGINT NULL,
    CONSTRAINT pk_carrito PRIMARY KEY (id)
);

CREATE TABLE cliente
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    nombre   VARCHAR(255) NULL,
    apellido VARCHAR(255) NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

CREATE TABLE contiene
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    cantidad   INT NULL,
    id_carrito BIGINT NOT NULL,
    CONSTRAINT pk_contiene PRIMARY KEY (id)
);

CREATE TABLE distribuidor
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    nombre            VARCHAR(255) NULL,
    nif               VARCHAR(255) NULL,
    direccion         VARCHAR(255) NULL,
    telefono          VARCHAR(255) NULL,
    email             VARCHAR(255) NULL,
    nombre_repartidor VARCHAR(255) NULL,
    id_proveedor      BIGINT NULL,
    CONSTRAINT pk_distribuidor PRIMARY KEY (id)
);

CREATE TABLE producto
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    nombre          VARCHAR(255) NULL,
    descripcion     VARCHAR(255) NULL,
    precio DOUBLE NULL,
    entregado       BIT(1) NULL,
    id_contiene     BIGINT NULL,
    id_distribuidor BIGINT NULL,
    CONSTRAINT pk_producto PRIMARY KEY (id)
);

CREATE TABLE proveedor
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    nombre    VARCHAR(255) NULL,
    nif       VARCHAR(255) NULL,
    direccion VARCHAR(255) NULL,
    telefono  VARCHAR(255) NULL,
    email     VARCHAR(255) NULL,
    CONSTRAINT pk_proveedor PRIMARY KEY (id)
);

ALTER TABLE distribuidor
    ADD CONSTRAINT uc_distribuidor_id_proveedor UNIQUE (id_proveedor);

ALTER TABLE carrito
    ADD CONSTRAINT FK_CARRITO_ON_ID_CLIENTE FOREIGN KEY (id_cliente) REFERENCES cliente (id);

ALTER TABLE contiene
    ADD CONSTRAINT FK_CONTIENE_ON_ID_CARRITO FOREIGN KEY (id_carrito) REFERENCES carrito (id);

ALTER TABLE distribuidor
    ADD CONSTRAINT FK_DISTRIBUIDOR_ON_ID_PROVEEDOR FOREIGN KEY (id_proveedor) REFERENCES proveedor (id);

ALTER TABLE producto
    ADD CONSTRAINT FK_PRODUCTO_ON_ID_CONTIENE FOREIGN KEY (id_contiene) REFERENCES contiene (id);

ALTER TABLE producto
    ADD CONSTRAINT FK_PRODUCTO_ON_ID_DISTRIBUIDOR FOREIGN KEY (id_distribuidor) REFERENCES distribuidor (id);