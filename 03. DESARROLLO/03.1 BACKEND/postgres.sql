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