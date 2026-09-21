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