create extension if not exists pgcrypto;

-- Package "actores" / rol /1 / Day
create table rol(
	id integer not null,
	nombre varchar(50) not null,
	constraint pk_rol primary key (id),
	constraint uk_nombre unique (nombre)
);

comment on table rol is 'Roles asignados a cada actor';
comment on column rol.id is 'Llave primaria sustituta de la tabla';
comment on column rol.nombre is 'Nombre del rol creado';

-- Package "actores" / usuario /2 / Day
create table usuario(
	id integer not null,
	correo_electronico varchar(50) not null,
	contrasenia varchar(255) not null,
	token_cambio_contrasenia integer null,
	constraint pk_usuario primary key (id),
	constraint uk_iniciar_sesion unique (correo_electronico)
);

comment on table usuario is 'Usuario a crear/registrarse';
comment on column usuario.id is 'Llave primaria sustituta de la tabla';
comment on column usuario.correo_electronico is 'Correo del usuario';
comment on column usuario.contrasenia is 'Contraseña del usuario a crear';
comment on column usuario.token_cambio_contrasenia is 'Token o código de 6 digitos generado al correo registrado del usuario, para recuperar su contraseña y acceso';

-- Package "actores" / tipo_documento /3 / Day
create table tipo_documento(
	id integer not null,
	tipo_documento varchar(10) not null,
	constraint pk_tipo_documento primary key(id),
	constraint uk_tipo_documento unique(tipo_documento)
);

comment on table tipo_documento is 'Asignación de tipos de documentos al sistema';
comment on column tipo_documento.id is 'Llave primaria sustituta de la tabla';
comment on column tipo_documento.tipo_documento is 'Agregar tipo de documento que va a ser valido';

-- Package "mercancia" / marca / 4 / Day
create table marca(
	id integer not null,
	marca varchar(50) not null,
	constraint pk_marca primary key(id),
	constraint uk_marca unique(marca)
);

comment on table marca is 'Marca de productos predeterminadas del sistema y que se pueden agregar / modificar';
comment on column marca.id is 'Llave primaria sustituta de la tabla';
comment on column marca.marca is 'Nombre de las marcas a asignar/crear';

-- Package "mercancia" / categoria / 5 / Day
create table categoria(
	id integer not null,
	nombre varchar(100) not null,
	constraint pk_categoria primary key(id),
	constraint uk_categoria unique(nombre)
);

comment on table categoria is 'Categoria de productos predeterminadas del sistema y que se pueden agregar / modificar, para organizar estos';
comment on column categoria.id is 'Llave primaria sustituta de la tabla';
comment on column categoria.nombre is 'Nombre de las categorias que se van a poder asignar a productos';

-- Tabla 6 presentacion Esteban
create table presentacion(
    id integer not null,
    tipo_presentacion varchar(50) not null, 
    constraint pk_presentacion primary key(id),
    constraint uk_presentacion UNIQUE(tipo_presentacion)
);

COMMENT ON TABLE presentacion is 'Presentación en la que está empacado el producto.';
COMMENT ON COLUMN presentacion.id is 'Id de la tabla presentación.';
COMMENT ON COLUMN presentacion.tipo_presentacion is 'Se especifica cual es el tipo de presentación del producto.';

-- Tabla 7 estado_producto Esteban
create table estado_producto(
    id integer not null,
    tipo_estado varchar(50) not null,
    constraint pk_estado_producto PRIMARY KEY(id),
    constraint uk_estado_producto UNIQUE(tipo_estado)
);

COMMENT ON TABLE estado_producto is 'Estado en el que se recibe o entrega un producto.';
COMMENT ON COLUMN estado_producto.id is 'Id del estado del producto.';
COMMENT ON COLUMN estado_producto.tipo_estado is 'Se especifican los estados en los que podría estar un producto.';


-- Tabla 8 sitio Esteban 
create table sitio(
    id integer not null,
    nombre varchar(200) not null,
    constraint pk_sitio PRIMARY KEY(id),
    constraint uk_sitio UNIQUE(nombre)
);

COMMENT ON TABLE sitio is 'Puntos de venta y almacenamiento.';
COMMENT ON COLUMN sitio.id is 'Id del punto de venta o sitio.';
COMMENT ON COLUMN sitio.nombre is 'Nombres de los puntos de venta y almacenamiento.';


-- Tabla 9 motivo_salida Esteban
create table motivo_salida(
    id integer not null,
    tipo_motivo varchar(150) not null,
    constraint pk_motivo_salida PRIMARY KEY(id),
    constraint uk_motivo_salida UNIQUE(tipo_motivo)
);

COMMENT ON TABLE motivo_salida is 'Puntos de venta y almacenamiento.';
COMMENT ON COLUMN motivo_salida.id is 'Id del punto de venta o sitio.';
COMMENT ON COLUMN motivo_salida.tipo_motivo is 'Nombres de los puntos de venta y almacenamiento.';


-- Tabla 10 estado Esteban
create table estado(
    id INTEGER not null,
    nombre varchar(50) not null,
    constraint pk_estado PRIMARY KEY(id),
    constraint uk_estado UNIQUE(nombre)
);

COMMENT ON TABLE estado is 'Estado de remisión o traslado después de ser creado.';
COMMENT ON COLUMN estado.id is 'Id del estado.';
COMMENT ON COLUMN estado.nombre is 'Tipos de estado de una solicitud.';



--tabla 11 Jhon Mateus
create table autorizacion(
    id_rol integer not null,
    id_usuario integer not null,
    constraint pk_autorizacion primary key (id_rol,id_usuario),
    constraint fk_rol__autorizacion foreign key (id_rol) references rol(id),
    constraint fk_usuario__autorizacion foreign key (id_usuario) references usuario(id)
);
comment on table autorizacion is 'permisos que tiene el usuario para modificar';
comment on COLUMN autorizacion.id_rol is 'llave primaria Asignacion de rol al usuario';
comment on COLUMN autorizacion.id_usuario is 'Habilitar el usuario segun corresponda el id';


-- tabla 12 Jhon Mateus 
create table cuenta(
    id integer not null,
    id_usuario integer not null,
    id_tipo_documento integer not null,
    numero_documento varchar (10) not null,
    primer_nombre varchar (20) not null,
    segundo_nombre varchar (20),
    primer_apellido varchar (20) not null,
    segundo_apellido varchar (20),
    numero_celular varchar (20) not null,
    eps varchar (30) not null,
    numero_contacto_emergencia varchar (20) not null,
    nombre_contacto_emergencia varchar (20) not null,
    parentezco_contacto_emergencia varchar (20) not null,
    constraint uk_id_usuario unique (id_usuario),
    constraint uk_documento unique (id_tipo_documento,numero_documento),
    constraint pk_cuenta primary key (id),
    constraint fk_tipo_documento__producto foreign key (id_tipo_documento) references tipo_documento (id),
    constraint fk_usuario__producto foreign key (id_usuario) references usuario (id)  
);
comment on table cuenta is 'Datos del usuario';
comment on COLUMN cuenta.id is 'Id cuenta';
comment on column cuenta.id_tipo_documento is 'Id del tipo de documento';
comment on column cuenta.numero_documento is 'Numero de documento';
comment on column cuenta.primer_nombre is 'Ingreso de primer nombre';
comment on column cuenta.segundo_nombre is 'Ingreso segundo nombre opcional';
comment on column cuenta.primer_apellido is 'Ingreso de primer apellido';
comment on column cuenta.segundo_apellido is 'Ingreso de segundo apellido opcional';
comment on column cuenta.numero_celular is 'Ingreso de numero de contacto';
comment on column cuenta.eps is 'Ingreso de eps en la que esta vinculado el usuario';
comment on column cuenta.numero_contacto_emergencia is 'Ingreso de numero de contacto en caso de emergencia';
comment on column cuenta.nombre_contacto_emergencia is 'Ingreso de nombre de la persona de contacto en caso de emergencia';
comment on column cuenta.parentezco_contacto_emergencia is 'Ingreso de tipo de parentezco del contacto de emergencia con el usuario';

--tabla 13 Jhon Mateus
create table  historial_error(
    id integer not null,
    nombre_error varchar (150) not null,
    mensaje varchar (255) not null,
    fecha_hora timestamp not null,
    id_cuenta integer not null,
    constraint pk_historial_error primary key (id),
    constraint fk_cuenta__historial_error foreign key (id_cuenta) references cuenta (id)
);
comment on table historial_error is 'Guardar errores que ocurran en el sistema';
comment on column historial_error.id is 'Identificador de error';
comment on column historial_error.nombre_error is 'Nombre para el error que sucedio en el sistema';
comment on column historial_error.mensaje is 'Mensaje que sale de acuerdo al codigo de error';
comment on column historial_error.fecha_hora is 'Fecha y hora a la que ocurre el error';
comment on column historial_error.id_cuenta is 'Id de la cuenta en la que aparece el error';


--tabla 14 Jhon Mateus

create table producto(
    id integer not null,
    nombre_especificacion varchar (200) not null,
    id_categoria integer not null,
    id_presentacion integer not null,
    id_estado_producto integer not null,
    id_marca integer not null,
    constraint uk_nombre_especificacion unique (nombre_especificacion),
    constraint pk_producto primary key (id),
    constraint fk_marca__producto foreign key (id_marca) references marca (id),
    constraint fk_estado_producto__producto foreign key (id_estado_producto) references estado_producto (id),
    constraint fk_presentacion__producto foreign key (id_presentacion) references presentacion (id),
    constraint fk_categoria__producto foreign key (id_categoria) references categoria (id)
);
comment on table producto is 'Especificacion del producto';
comment on column producto.id is 'Codigo identificador del producto';
comment on column producto.nombre_especificacion is 'Nombre exacto del producto';
comment on column producto.id_categoria is 'Categoria a la cual corresponde el producto';
comment on column producto.id_presentacion is 'Tipo de "presentaciones"(bulto,caja,bolsa,sachet..) del producto';
comment on column producto.id_estado_producto is 'Identificador de las condiciones del producto';
comment on column producto.id_marca is 'Identificador de la marca del producto';

--tabla 15 Jhon Mateus

create table lote(
    id integer not null,
    id_producto integer not null,
    fecha_ingreso date not null,
    fecha_vencimiento date not null,
    codigo_lote varchar (100) not null,
    observaciones varchar (100),
    factura_proveedor varchar (255) not null,
    cantidad_ingresada integer not null,
    constraint pk_lote primary key (id),
    constraint uk_producto_codigo_lote unique (id_producto,codigo_lote),
    constraint fk_producto__lote foreign key (id_producto) references producto (id)
);
comment on table lote is 'Lote del producto';
comment on column lote.id is 'Identificador de lote del producto';
comment on column lote.id_producto is 'Identificador del producto';
comment on column lote.fecha_ingreso is 'Fecha en la que ingresa el lote';
comment on column lote.fecha_vencimiento is 'Fecha en la que vence el lote';
comment on column lote.codigo_lote is 'Codigo del lote del producto que viene marcado en el producto';
comment on column lote.observaciones is 'observaciones si del lote del producto';
comment on column lote.factura_proveedor is 'Factura del proveedor del lote del producto';
comment on column lote.cantidad_ingresada is 'Cantidad de producto que viene en el lote';


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

--tabla 21 / Marce
create table traslado(
    id integer not null,
    id_cuenta_sitio integer not null,
    id_sitio_destino integer not null,
    id_codigo integer not null,
    descripcion varchar(150),
    constraint pk_traslado primary key (id),
    constraint fk_cuenta_sitio__traslado foreign key (id_cuenta_sitio) references cuenta_sitio(id),
    constraint fk_codigo_traslado_producto_sitio_lote foreign key (id_codigo) references codigo(id),
    constraint fk_sitio__traslado_destino foreign key (id_sitio_destino) references sitio(id)
);

comment on table traslado is 'Registra los traslados de las cuentas desde un sitio de origen hacia un sitio de destino';
comment on column traslado.id is 'Llave primaria sustituta de la tabla';
comment on column traslado.id_cuenta_sitio is 'Id de la cuenta y del sitio de origen asociados al traslado';
comment on column traslado.id_sitio_destino is 'Id del sitio de destino del traslado';
comment on column traslado.id_codigo is 'Id del codigo asociado al traslado';
comment on column traslado.descripcion is 'Descripcion del traslado';

--tabla 22 / Marce
create table salida (
    id integer not null,
    id_cuenta_sitio integer not null,
    id_motivo_salida integer not null,
    id_evidencia_salida integer not null,
    respaldo_imagen varchar(255),
    cantidad_a_sacar integer not null,
    observaciones varchar(150),
    constraint pk_salida primary key (id),
    constraint fk_motivo_salida__salida foreign key (id_motivo_salida) references motivo_salida(id),
    constraint fk_cuenta_sitio__salida foreign key (id_cuenta_sitio) references cuenta_sitio(id),
    constraint fk_evidencia_salida__salida foreign key (id_evidencia_salida) references evidencia_salida(id)
);

comment on table salida is 'Registra las salidas de productos';
comment on column salida.id is 'Llave primaria sustituta de la tabla';
comment on column salida.id_cuenta_sitio is 'Id de la cuenta asociada a la salida';
comment on column salida.id_motivo_salida is 'Id del motivo asociado a la salida';
comment on column salida.id_evidencia_salida is 'Id de la evidencia que respalda la salida';
comment on column salida.respaldo_imagen is 'Ruta o direccion del archivo de imagen que sirve como evidencia';
comment on column salida.cantidad_a_sacar is 'Cantidad de los productos a retirar';
comment on column salida.observaciones is 'Notas adicionales sobre la salida';

--tabla 23 / Marce
create table producto_sitio_salida(
    id integer not null,
    id_producto_sitio integer not null,
    id_salida integer not null,
    constraint pk_producto_sitio_salida primary key (id),
    constraint fk_producto_sitio__producto_sitio_salida foreign key (id_producto_sitio) references producto_sitio(id),
    constraint fk__salida_producto_sitio_salida foreign key (id_salida) references salida(id)
);

comment on table producto_sitio_salida is 'Registra la relacion entre los productos de un sitio y las salidas realizadas';
comment on column producto_sitio_salida.id is 'Llave primaria sustituta de la tabla';
comment on column producto_sitio_salida.id_producto_sitio is 'Id del producto asociado al sitio';
comment on column producto_sitio_salida.id_salida is 'Id de la salida registrada';

--tabla 24 / Marce
create table traslado_producto_sitio(
    id integer not null,
    id_traslado integer not null,
    id_producto_sitio integer not null,
    cantidad_traslado integer not null,
    constraint pk_traslado_producto_sitio primary key (id),
    constraint fk_traslado__traslado_producto_sitio foreign key (id_traslado) references traslado(id),
    constraint fk_producto_sitio__traslado_producto_sitio foreign key (id_producto_sitio) references producto_sitio(id)
);

comment on table traslado_producto_sitio is 'Registra el detalle de los productos y cantidades asociados a un traslado';
comment on column traslado_producto_sitio.id is 'Llave primaria sustituta de la tabla';
comment on column traslado_producto_sitio.id_traslado is 'Id del traslado correspondiente';
comment on column traslado_producto_sitio.id_producto_sitio is 'Id del producto asociado al sitio';
comment on column traslado_producto_sitio.cantidad_traslado is 'Cantidad de productos que se van a trasladar';

-- A PARTIR DE AQUÍ ABAJO VAN LOS INSERT DE LAS TABLAS
-- DEBEN IR EN ORDEN YA QUE TAMBIÉN LOS DATOS DEPENDEN LOS UNOS DE LOS OTROS.

-- Insert / "actores" / rol / 1 / Day
insert into rol values(1, 'Empresario');
insert into rol values(2, 'Administrador inventario');
insert into rol values(3, 'Empleado');
insert into rol values(4, 'PendienteE');

-- Insert / "actores" / usuario / 2 / Day
insert into usuario values(1, 'day@gmail.com', crypt('D12345', gen_salt('bf')), 123456);
insert into usuario values(2, 'juan@gmail.com', crypt('J12345', gen_salt('bf')), 654321);
insert into usuario values(3, 'carl@gmail.com', crypt('C12345', gen_salt('bf')));
insert into usuario values(4, 'ana@gmail.com', crypt('A12345', gen_salt('bf')));
insert into usuario values(5, 'sofia@gmail.com', crypt('S12345', gen_salt('bf')));
insert into usuario values(6,'jeffer@gmail.com', crypt('J12345', gen_salt('bf')));
insert into usuario values(7, 'laura@gmail.com', crypt('L12345', gen_salt('bf')));
insert into usuario values(8, 'lili@gmail.com', crypt('L12345', gen_salt('bf')));
insert into usuario values(9, 'sara@gmail.com', crypt('Sara12', gen_salt('bf')));
insert into usuario values(10, 'neyder@gmail.com', crypt('N12345', gen_salt('bf')), 987654);

-- Insert / "actores" / tipo_documento / 3 / Day
insert into tipo_documento values(1, 'CC');
insert into tipo_documento values(2, 'PAS');
insert into tipo_documento values(3, 'TI');
insert into tipo_documento values(4, 'RC');
insert into tipo_documento values(5, 'NIT');
insert into tipo_documento values(6, 'nose');

-- Insert / "mercancia" / marca / 4 / Day
insert into marca values(1, 'Alqueria');
insert into marca values(2, 'Ramo');
insert into marca values(3, 'Colanta');
insert into marca values(4, 'Diana');
insert into marca values(5, 'Nutresa');
insert into marca values(6, 'Alpina');
insert into marca values(7, 'Bimbo');
insert into marca values(8, 'Postobon');
insert into marca values(9, 'CocaCola');
insert into marca values(10, 'Ariel');

-- Insert / "mercancia" / categoria / 5 / Day
insert into categoria values(1, 'Lacteos');
insert into categoria values(2, 'Congelados');
insert into categoria values(3, 'Granos');
insert into categoria values(4, 'Bebidas');
insert into categoria values(5, 'Cuidado personal');
insert into categoria values(6, 'Aseo hogar');
insert into categoria values(7, 'Jabones');
insert into categoria values(8, 'Alcohol');
insert into categoria values(9, 'Dulces');
insert into categoria values(10, 'Harinas');

-- Insert tabla 6 presentación Esteban
insert into presentacion values(1, 'Unidad');
insert into presentacion values(2, 'Paquete');
insert into presentacion values(3, 'Bolsa');
insert into presentacion values(4, 'Botella');
insert into presentacion values(5, 'Lata');
insert into presentacion values(6, 'Vaso');
insert into presentacion values(7, 'Aerosol');
insert into presentacion values(8, 'Caja');
insert into presentacion values(9, 'Bulto');
insert into presentacion values(10, 'Sixpack');

-- Insert tabla 7 estado_producto Esteban
insert into estado_producto values(1, 'Normal');
insert into estado_producto values(2, 'Dañado');
insert into estado_producto values(3, 'No llego por pedido');

-- Insert tabla 8 sitio Esteban
insert into sitio values(1, 'Bodega 1');
insert into sitio values(2, 'Tienda 1');
insert into sitio values(3, 'Tienda 2');
insert into sitio values(4, 'Tienda 3');
insert into sitio values(5, 'Tienda 4');
insert into sitio values(6, 'Tienda 5');
insert into sitio values(7, 'Tienda 6');
insert into sitio values(8, 'Bodega 2');
insert into sitio values(9, 'Tienda 7');
insert into sitio values(10, 'Tienda 8');

-- Insert tabla 9 motivo_salida
insert into motivo_salida values(1, 'Daño');
insert into motivo_salida values(2, 'Robo');
insert into motivo_salida values(3, 'Rotura');
insert into motivo_salida values(4, 'Vencimiento');
insert into motivo_salida values(5, 'Consumo Interno');
insert into motivo_salida values(6, 'Devuelto a Proveedor');
insert into motivo_salida values(7, 'Venta');

-- Insert tabla 10 estado
insert into estado values(1, 'Aceptada');
insert into estado values(2, 'Rechazada');
insert into estado values(3, 'Pendiente');


--Insert tabla 11 autorizacion Jhon
insert into autorizacion values (1, 1);
insert into autorizacion values (2, 2);
insert into autorizacion values (3, 3);
insert into autorizacion values (3, 4);
insert into autorizacion values (3, 5);
insert into autorizacion values (3, 6);
insert into autorizacion values (3, 7);
insert into autorizacion values (4, 8);
insert into autorizacion values (4, 9);
insert into autorizacion values (4, 10);

--Insert tabla 12 cuenta Jhon

INSERT INTO cuenta(
    id,
    id_usuario,
    id_tipo_documento,
    numero_documento,
    primer_nombre,
    segundo_nombre,
    primer_apellido,
    segundo_apellido,
    numero_celular,
    eps,
    numero_contacto_emergencia,
    nombre_contacto_emergencia,
    parentezco_contacto_emergencia
) VALUES
(1,  1,  1, '1014238945', 'Carlos',    'Eduardo',   'Ramírez',   'Gómez',     '3104567890', 'Famisanar',    '3158901234', 'Martha Gómez',      'Madre'),
(2,  2,  2, '80462621',   'Andrea',    'Paola',     'Martínez',  'Rodríguez', '3207891234', 'Sura',         '3112345678', 'Jorge Martínez',    'Padre'),
(3,  3,  3, '1032489012', 'Mateo',     NULL,        'Vargas',    'Castro',    '3145678901', 'Sanitas',      '3176543210', 'Lucía Castro',      'Madre'),
(4,  4,  4, '52890432',   'Valentina', 'Sofía',     'López',     'Mendoza',   '3001234567', 'Compensar',    '3189012345', 'Pedro López',       'Hermano'),
(5,  5,  5, '1098765432', 'Juan',      'David',     'Herrera',   'Torres',    '3123456789', 'Salud Total',  '3167890123', 'Gloria Torres',     'Madre'),
(6,  6,  2, '79845123',   'Daniela',   NULL,        'Pérez',     'Morales',   '3162345678', 'Nueva EPS',    '3109876543', 'Andrés Pérez',      'Cónyuge'),
(7,  7,  3, '1018456789', 'Santiago',  'Andrés',    'García',    'Rojas',     '3184567892', 'Sura',         '3212858368', 'Patricia Rojas',    'Madre'),
(8,  8,  4, '1020478951', 'Camila',    'Alejandra', 'Silva',     'Díaz',      '3178901235', 'Sanitas',      '3134567890', 'Roberto Silva',     'Padre'),
(9,  9,  5, '80123456',   'Felipe',    NULL,        'Ortiz',     'Jiménez',   '3136789012', 'Famisanar',    '3198765432', 'Elena Jiménez',     'Hermana'),
(10, 10, 1, '1026589412', 'Mariana',   'Lucía',     'Cruz',      'Navarro',   '3157890123', 'Compensar',    '3223456789', 'Fernando Cruz',     'Padre');

--Insert tabla 13 historial error Jhon
INSERT INTO historial_error (
    id,
    nombre_error,
    mensaje,
    fecha_hora,
    id_cuenta
) VALUES
(1,  'AuthenticationFailedException', 'Credenciales de acceso inválidas o usuario bloqueado',                '2026-06-15 08:30:12', 1),
(2,  'NullPointerException',          'Intento de acceso a un objeto no inicializado en el módulo de pagos',  '2026-06-15 09:14:45', 2),
(3,  'SQLException',                  'Violación de restricción de clave foránea al actualizar saldo',        '2026-06-16 11:05:20', 3),
(4,  'TimeoutException',              'Tiempo de espera agotado al conectar con el servidor externo',         '2026-06-16 14:22:01', 1),
(5,  'UnauthorizedAccessException',   'La cuenta no tiene permisos suficientes para ejecutar la acción',      '2026-06-17 10:11:33', 4),
(6,  'DataValidationException',       'El formato del correo electrónico proporcionado no es válido',        '2026-06-17 16:45:50', 2),
(7,  'HttpHostConnectException',      'Error al intentar conectar con la pasarela de pagos',                 '2026-06-18 12:00:15', 5),
(8,  'DuplicateKeyException',         'El número de documento ya se encuentra registrado en el sistema',     '2026-06-19 15:30:40', 3),
(9,  'SessionExpiredException',       'La sesión del usuario ha expirado por inactividad',                    '2026-06-20 18:02:11', 4),
(10, 'InternalServerError',           'Fallo inesperado al procesar la solicitud del reporte mensual',        '2026-06-20 20:10:05', 5);

-- Insert tabla 14 producto Jhon

INSERT INTO producto (id, nombre_especificacion, id_categoria, id_presentacion, id_estado_producto, id_marca) VALUES
(1,  'Leche Entera Alqueria 1100ml',         1,  3, 1, 1),   -- Lácteos / Bolsa / Normal / Alquería
(2,  'Chocorramo Tradicional 65g',           9,  2, 1, 2),   -- Dulces / Paquete / Normal / Ramo
(3,  'Queso Campesino Colanta 500g',         1,  2, 1, 3),   -- Lácteos / Paquete / Normal / Colanta
(4,  'Arroz Blanco Diana  1000g',            3,  3, 1, 4),   -- Granos / Bolsa / Normal / Diana
(5,  'Galletas Festival Vainilla 403g',      9,  2, 1, 5),   -- Dulces / Paquete / Normal / Nutresa
(6,  'Yogurt Fresa Alpina 1000g',            1,  4, 1, 6),   -- Lácteos / Botella / Normal / Alpina
(7,  'Pan Blanco Familiar Bimbo 600g',       10, 2, 1, 7),   -- Harinas / Paquete / Normal / Bimbo
(8,  'Gaseosa Manzana Postobon 1.5L',        4,  4, 1, 8),   -- Bebidas / Botella / Normal / Postobón
(9,  'Gaseosa CocaCola Original 400ml',      4,  5, 1, 9),   -- Bebidas / Lata / Normal / CocaCola
(10, 'Detergente en Polvo Ariel 2kg',        6,  3, 1, 10);  -- Aseo hogar / Bolsa / Normal / Ariel

-- Insert tabla 15 lote Jhon

INSERT INTO lote (id, id_producto, fecha_ingreso, fecha_vencimiento, codigo_lote, observaciones, factura_proveedor, cantidad_ingresada) VALUES
(1,  1,  '2026-01-10', '2026-04-10', 'LOT-ALQ-001', 'Llegó en óptimas condiciones de frío', 'FAC-PROV-9011', 200),
(2,  2,  '2026-01-15', '2026-06-30', 'LOT-RAM-002', 'Empaque sellado y completo',           'FAC-PROV-9012', 150),
(3,  3,  '2026-02-01', '2026-05-15', 'LOT-COL-003', 'Refrigeración verificada',             'FAC-PROV-9013', 180),
(4,  4,  '2026-02-05', '2027-02-05', 'LOT-DIA-004', 'Almacenado sobre estibas',             'FAC-PROV-9014', 300),
(5,  5,  '2026-02-10', '2026-11-20', 'LOT-NUT-005', 'Cajas sin averías',                    'FAC-PROV-9015', 250),
(6,  6,  '2026-02-12', '2026-05-01', 'LOT-ALP-006', 'Cadena de frío controlada',            'FAC-PROV-9016', 220),
(7,  7,  '2026-02-15', '2026-03-30', 'LOT-BIM-007', 'Fecha corta de rotación rápida',       'FAC-PROV-9017', 190),
(8,  8,  '2026-03-01', '2026-12-31', 'LOT-POS-008', 'Estibas completas de botellas',        'FAC-PROV-9018', 210),
(9,  9,  '2026-03-05', '2027-01-15', 'LOT-COC-009', 'Lote de latas perfecto',               'FAC-PROV-9019', 240),
(10, 10, '2026-03-10', '2028-03-10', 'LOT-ARI-010', 'Bolsas selladas de detergente',        'FAC-PROV-9020', 260);

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

--insert tabla 21 / Marce
INSERT INTO traslado (
    id,
    id_cuenta_sitio,
    id_sitio_destino,
    id_codigo,
    descripcion
)
VALUES 
    (1, 1, 1, 1, NULL),
    (2, 1, 8, 1, NULL),
    (3, 8, 1, 2, NULL),
    (4, 2, 8, 2, NULL),
    (5, 2, 2, 4, NULL),
    (6, 3, 3, 5, NULL),
    (7, 1, 4, 6, NULL),
    (8, 2, 5, 7, NULL),
    (9, 7, 6, 8, NULL),
    (10, 3, 6, 9, NULL);

--insert tabla 22 / Marce
INSERT INTO salida (
    id,
    id_cuenta_sitio,
    id_motivo_salida,
    id_evidencia_salida,
    respaldo_imagen,
    cantidad_a_sacar,
    observaciones
)
VALUES
    (1, 1, 1, 1, NULL, 2, NULL),
    (2, 2, 1, 1, NULL, 56, NULL),
    (3, 5, 1, 2, NULL, 58, NULL),
    (4, 8, 1, 3, NULL, 5, NULL),
    (5, 8, 2, 6, 'http', 23, 'dainado'),
    (6, 6, 3, 5, 'http', 54, 'no llego'),
    (7, 3, 2, 5, NULL, 2, NULL),
    (8, 5, 1, 9, NULL, 6, NULL),
    (9, 2, 2, 10, 'http', 2, 'dainado'),
    (10, 3, 2, 4, 'http', 29, 'dainado');

--insert tabla 23 / Marce
INSERT INTO producto_sitio_salida(
    id,
    id_producto_sitio,
    id_salida
)
VALUES
    (1, 1, 8),
    (2, 1, 9),
    (3, 2, 4),
    (4, 5, 7),
    (5, 6, 6),
    (6, 5, 6),
    (7, 2, 1),
    (8, 6, 1),
    (9, 6, 1),
    (10, 4, 2);

--insert tabla 24 / Marce
INSERT INTO traslado_producto_sitio(
    id,
    id_traslado,
    id_producto_sitio,
    cantidad_traslado
)
VALUES
    (1, 1, 1, 10),
    (2, 2, 1, 12),
    (3, 3, 1, 55),
    (4, 4, 7, 13),
    (5, 5, 7, 1),
    (6, 6, 10, 2),
    (7, 7, 1, 5),
    (8, 8, 10, 8),
    (9, 9, 1, 9),
    (10, 10, 7, 7);


-- A PARTIR DE AQUÍ ABAJO VAN LOS UPDATE
-- POR FAVOR, COLOCARLOS EN EL ORDEN POR PRECAUCIÓN

-- Actualizamos el nombre de un campo en la tabla sitio, columna nombre. Esteban.
UPDATE sitio SET nombre = 'Bodega 10' WHERE id = 10;

--Se actualiza el estado de observacion de un lote en la tabla 15 / Jhon
UPDATE lote SET observaciones = 'Revisión técnica aprobada: producto apto para distribución' WHERE id = 1;

--se cambia/actualiza el valor de "55" por el "60" en la tabla "20" juan david
update remision set cantidad_remitida = 60 where remision.cantidad_remitida = 55;

-- Se actualiza la descripcion en la tabla traslado (21) / Marce
UPDATE traslado SET descripcion = 'Traslado de tienda 1 a tienda 2 para acabar existencias en stock' WHERE id = 1;

-- Actualizamos el campo para que quede bien escrito :) / Day / Tabla 1 rol
update rol r set nombre = 'Pendiente' where r.nombre = 'PendienteE';


-- A PARTIR DE AQUÍ ABAJO VAN LOS DELETE
-- POR FAVOR, COLOCARLOS EN EL ORDEN POR PRECAUCIÓN

-- Delete tabla 10 estado Esteban
insert into estado values(4, 'jeje');
DELETE from estado where estado.nombre = 'jeje';

--Delete tabla 14 producto / Jhon
-- Insertamos un producto temporal de prueba
INSERT INTO producto (id, nombre_especificacion, id_categoria, id_presentacion, id_estado_producto, id_marca)
VALUES (11, 'Producto Temporal Para Hacer Delete', 1, 1, 2, 1);

-- Eliminamos el producto creado / Jhon / Tabla 14 producto
DELETE FROM producto WHERE id = 11;

-- se elimina en la tabla remision "20" la fila de cantidad remitida juan david
delete from remision where remision.cantidad_remitida = 55;

-- Delete en la tabla 24 / Marce
DELETE FROM traslado_producto_sitio WHERE id = 10;


-- A PARTIR DE AQUÍ ABAJO VAN LOS JOIN
-- POR FAVOR, COLOCARLOS EN EL ORDEN POR PRECAUCIÓN

--Inner Join de Esteban
-- Le indico que me muestre las 3 columnas a continuación.
SELECT
    p.nombre_especificacion,
    ca.nombre,
    ep.tipo_estado
FROM
    producto p -- Le indico que tome como tabla base de busqueda la que se llama "producto".
INNER JOIN estado_producto ep ON -- Le indico la tabla con la que quiero que compare "producto". En éste caso, "estado_producto".
    p.id_estado_producto = ep.id -- Comparo la llave foránea de "productos" con la de "estado_producto" para que me traiga la información exacta y correspondiente al id.
INNER JOIN categoria ca ON -- Le indico que me compara ahora la tabla "producto" con "categoria".
    p.id_categoria = ca.id -- Le indico que compare la llave foránea de "producto" con la llave primaria de "categoria". 
WHERE
    p.id = 2 -- Finalmente, le indico que el id que quiero que compare, es el número "2", en éste caso.
; -- Una vez finalizado el proceso, el sistema te traera las columnas colocadas al inicio del SELECT con la información correspondiente.


--Inner Join de Jhon tabla 12 cuenta 
SELECT
    ct.primer_nombre,
    ct.eps,
    ct.numero_contacto_emergencia,
    ct.nombre_contacto_emergencia,
    ct.parentezco_contacto_emergencia,
    us.correo_electronico
FROM cuenta ct
INNER JOIN usuario us ON ct.id_usuario = us.id;


--¿Qué productos se enviaron, a qué lugares, en qué fecha y bajo qué cuenta o código de registro? juan david
select
c.id_cuenta_sitio,
c.fecha_remision,
ps.id_producto,
ps.id_sitio
FROM
remision r
inner join codigo c on
c.id_cuenta_sitio= r.id
inner join producto_sitio ps ON
ps.id = r.id_producto_sitio;

-- Inner join de Marce -> muestra la descripcion y la cantidad de productos del traslado
SELECT 
    t.descripcion,
    tps.cantidad_traslado
FROM traslado_producto_sitio tps
INNER JOIN traslado t ON tps.id_traslado = t.id;



-- A PARTIR DE AQUÍ ABAJO VAN LAS CONSULTAS Y SUBCONSULTAS
-- POR FAVOR, COLOCARLOS EN EL ORDEN POR PRECAUCIÓN 

--¿Qué productos tienen existencias en los sitios y además han sido solicitados mediante una remisión? tabla producto_sitio y remision juan david
SELECT
    ps.id_producto,
    ps.id_sitio,
    ps.cantidad_sitio
FROM producto_sitio ps
WHERE ps.cantidad_sitio > 0
AND ps.id IN (
    SELECT r.id_producto_sitio
    FROM remision r
);

-- Vamos a ver los nombres de los productos junto con su tipo de presentacion siempre y cuando su presentación empiece por la letra "a".  
select nombre_especificacion 
from producto 
where producto.id_presentacion in 
(select id from presentacion where tipo_presentacion ilike 'b%');


--Consulta y subconsulta de Lote tabla 15 Jhon


SELECT  -- Queremos ver éstas columnas de la tabla Lote, solo cuando la llave foránea de lote coincida con la llave primaria de producto. 
    l.id AS id_lote,
    l.id_producto,
    l.codigo_lote,
    l.fecha_ingreso
FROM lote l
WHERE l.id_producto IN (
    SELECT p.id
    FROM producto p
);

-- Obtener la info de los traslados que tengan "Traslado de tienda 1 a tienda 2 para acabar existencias en stock" en la descripcion / Marce
SELECT 
    id, 
    id_producto_sitio, 
    cantidad_traslado
FROM traslado_producto_sitio
WHERE id_traslado IN(
    SELECT id 
    FROM traslado 
    WHERE descripcion ILIKE '%Traslado de tienda 1 a tienda 2 para acabar existencias en stock%'
);