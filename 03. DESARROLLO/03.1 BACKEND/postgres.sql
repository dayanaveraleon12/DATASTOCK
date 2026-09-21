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