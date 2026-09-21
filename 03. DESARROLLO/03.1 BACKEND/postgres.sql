create extension if not exists pgcrypto;

-- tabla 16 juan david
create table producto_sitio(
    id integer not null,
    id_producto integer not null,
    id_sitio integer not null,
    id_lote integer not null,
    cantidad_sitio integer,
    constraint pk_producto_sitio primary key(id),
    constraint fk_producto__producto_sitio FOREIGN key (id_producto) references producto(id),
    constraint fk_sitio__producto_sitio FOREIGN key (id_sitio) references sitio (id),
    constraint fk_lote__producto_sitio FOREIGN key (id_lote) references lote(id),
    constraint uk_producto_sitio UNIQUE(id_producto, id_sitio, id_lote)
);

comment on table producto_sitio is 'Registra los productos disponibles en cada sitio y lote para el control del inventario';
comment on column producto_sitio.id is 'Llave primaria sustituta de la tabla';
comment on column producto_sitio.id_producto is 'Id del producto asociado al sitio';
comment on column producto_sitio.id_sitio is 'Id del sitio donde se encuentra el producto';
comment on column producto_sitio.id_lote is 'Id del lote al que pertenece el producto';
comment on column producto_sitio.cantidad_sitio is 'Cantidad disponible del producto en el sitio';


-- tabla 17 juan david

create table cuenta_sitio(
    id INTEGER not null,
    id_cuenta integer not null,
    id_sitio integer not null,
    constraint pk_cuenta_sitio primary key(id),
    constraint fk_cuenta__cuenta_sitio foreign key (id_cuenta) REFERENCES cuenta(id),
    constraint fk_sitio__cuenta_sitio foreign key (id_sitio) REFERENCES sitio(id)
);

comment on table cuenta_sitio is 'Relaciona las cuentas de usuario con los sitios a los que tienen acceso';
comment on column cuenta_sitio.id is 'Llave primaria sustituta de la tabla';
comment on column cuenta_sitio.id_cuenta is 'Id de la cuenta asociada al sitio';
comment on column cuenta_sitio.id_sitio is 'Id del sitio asociado a la cuenta';

-- tabla 18 juan david

create table codigo(
    id integer not null,
    id_estado integer not null,
    id_cuenta_sitio integer not null,
    fecha_remision date not null,
    codigo varchar(255) not null,
    constraint pk_codigo primary key (id),
    constraint fk_estado__codigo foreign key (id_estado) references estado(id),
    constraint fk_cuenta_sitio__codigo foreign key (id_cuenta_sitio) references cuenta_sitio(id),
    constraint uk_codigo unique (codigo)
);

comment on table codigo is 'Registra los códigos asociados a las remisiones y su estado';
comment on column codigo.id is 'Llave primaria sustituta de la tabla';
comment on column codigo.id_estado is 'Id del estado actual del código de la remisión';
comment on column codigo.id_cuenta_sitio is 'Id de la cuenta asociada al sitio que genera la remisión';
comment on column codigo.fecha_remision is 'Fecha en la que se genera la remisión';
comment on column codigo.codigo is 'Código único asignado a la remisión';

-- tabla 19 juan david
create table evidencia_salida(
    id integer not null,
    id_cuenta integer not null,
    archivo_excel varchar(255) not null,
    nombre_excel varchar(255) not null,
    fecha_carga date not null,
    id_sitio_descarga integer not null,
    constraint pk_evidencia_salida primary key (id),
    constraint fk_cuenta__evidencia_salida foreign key (id_cuenta) references cuenta(id),
    constraint fk_sitio__evidencia_salida foreign key (id_sitio_descarga) references sitio (id)
);

comment on table evidencia_salida is 'Registra las evidencias en archivos Excel asociadas a las salidas de productos';
comment on column evidencia_salida.id is 'Llave primaria sustituta de la tabla';
comment on column evidencia_salida.id_cuenta is 'Id de la cuenta que registra la evidencia de salida';
comment on column evidencia_salida.archivo_excel is 'Ruta o direccion del archivo Excel que contiene la evidencia';
comment on column evidencia_salida.nombre_excel is 'Nombre del archivo Excel utilizado como evidencia';
comment on column evidencia_salida.fecha_carga is 'Fecha en la que se carga la evidencia';
comment on column evidencia_salida.id_sitio_descarga is 'Id del sitio donde se realiza la descarga de los productos';


-- tabla 20 juan david

create table remision(
    id integer not null,
    id_producto_sitio integer not null,
    id_codigo integer not null,
    id_sitio_destino integer not null,
    cantidad_remitida integer not null,
    constraint pk_remision primary key (id),
    constraint fk_producto_sitio__remision foreign key (id_producto_sitio) references producto_sitio (id),
    constraint fk_codigo__remision foreign key (id_codigo) references codigo (id),
    constraint fk_sitio__remision foreign key (id_sitio_destino) references sitio (id)
);

comment on table remision is 'Registra los productos y cantidades que se envian desde la bodega hacia otro sitio';
comment on column remision.id is 'Llave primaria sustituta de la tabla';
comment on column remision.id_producto_sitio is 'Id del producto asociado al sitio desde donde se realiza la remisión';
comment on column remision.id_codigo is 'Id del código asociado a la remisión';
comment on column remision.id_sitio_destino is 'Id del sitio donde serán enviados los productos';
comment on column remision.cantidad_remitida is 'Cantidad de productos que se envian en la remisión';



-- insert  tabla 16 producto_sitio juan david

INSERT INTO producto_sitio VALUES(1, 1, 1, 1, 100);
INSERT INTO producto_sitio VALUES(2, 2, 2, 2, 90);
INSERT INTO producto_sitio VALUES(3, 3, 3, 3, 110);
INSERT INTO producto_sitio VALUES(4, 4, 2, 4, 120);
INSERT INTO producto_sitio VALUES(5, 5, 2, 5, 130);
INSERT INTO producto_sitio VALUES(6, 6, 2, 6, 140);
INSERT INTO producto_sitio VALUES(7, 7, 1, 7, 150);
INSERT INTO producto_sitio VALUES(8, 8, 3, 8, 160);
INSERT INTO producto_sitio VALUES(9, 9, 3, 9, 170);
INSERT INTO producto_sitio VALUES(10, 10, 1, 10, 180);
INSERT INTO producto_sitio VALUES(11, 1, 3, 9, 22);

-- insert  tabla 17 cuenta_sitio juan david

INSERT INTO cuenta_sitio VALUES(1, 1, 1);
INSERT INTO cuenta_sitio VALUES(2, 2, 1);
INSERT INTO cuenta_sitio VALUES(3, 2, 8);
INSERT INTO cuenta_sitio VALUES(4, 3, 8);
INSERT INTO cuenta_sitio VALUES(5, 4, 4);
INSERT INTO cuenta_sitio VALUES(6, 5, 5);
INSERT INTO cuenta_sitio VALUES(7, 5, 6);
INSERT INTO cuenta_sitio VALUES(8, 6, 7);
INSERT INTO cuenta_sitio VALUES(9, 7, 7);
INSERT INTO cuenta_sitio VALUES(10, 8, 9);

-- insert  tabla 18 codigo juan david

INSERT INTO codigo VALUES(1, 1, 1, '2026-03-14', 12346);
INSERT INTO codigo VALUES(2, 1, 2, '2025-06-30', 122);
INSERT INTO codigo VALUES(3, 2, 1, '2025-01-15', 15);
INSERT INTO codigo VALUES(4, 2, 3, '2024-05-25', 52);
INSERT INTO codigo VALUES(5, 3, 5, '2025-08-08', 44);
INSERT INTO codigo VALUES(6, 3, 3, '2025-12-12', 266);
INSERT INTO codigo VALUES(7, 3, 6, '2025-12-20', 754);
INSERT INTO codigo VALUES(8, 1, 8, '2025-12-20', 6555);
INSERT INTO codigo VALUES(9, 2, 5, '2025-12-20', 323);
INSERT INTO codigo VALUES(10, 3, 5, '2025-12-20', 51);

-- insert  tabla 19 evidencia_salida juan david

INSERT INTO evidencia_salida VALUES(1, 1, 'http', 'Tienda 1', '2025-12-01', 1);
INSERT INTO evidencia_salida VALUES(2, 1, 'http', 'Tienda 6', '2024-09-23', 8);
INSERT INTO evidencia_salida VALUES(3, 10, 'http', 'Tienda 8', '2025-02-02', 8);
INSERT INTO evidencia_salida VALUES(4, 1, 'http', 'Tienda 5', '2021-03-29', 8);
INSERT INTO evidencia_salida VALUES(5, 1, 'http', 'Tienda 1', '2025-07-21', 1);
INSERT INTO evidencia_salida VALUES(6, 1, 'http', 'Tienda 7', '2025-05-23', 1);
INSERT INTO evidencia_salida VALUES(7, 10, 'http', 'Tienda 7', '2025-08-30', 1);
INSERT INTO evidencia_salida VALUES(8, 1, 'http', 'Tienda 2', '2022-01-23', 8);
INSERT INTO evidencia_salida VALUES(9, 1, 'http', 'Tienda 3', '2026-02-23', 1);
INSERT INTO evidencia_salida VALUES(10, 1, 'http', 'Tienda 8', '2025-03-05', 8);

-- insert  tabla 20 remision juan david

INSERT INTO remision VALUES(1, 1, 1, 9, 55);
INSERT INTO remision VALUES(2, 2, 1, 9, 6);
INSERT INTO remision VALUES(3, 3, 1, 9, 22);
INSERT INTO remision VALUES(4, 5, 1, 9, 84);
INSERT INTO remision VALUES(5, 5, 2, 5, 7);
INSERT INTO remision VALUES(6, 5, 3, 5, 5);
INSERT INTO remision VALUES(7, 5, 5, 2, 36);
INSERT INTO remision VALUES(8, 4, 6, 2, 95);
INSERT INTO remision VALUES(9, 2, 6, 1, 51);
INSERT INTO remision VALUES(10, 3, 2, 7, 12);

