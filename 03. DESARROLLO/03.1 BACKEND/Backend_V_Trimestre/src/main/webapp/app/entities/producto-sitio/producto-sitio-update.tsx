import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getEstadoProductos } from 'app/entities/estado-producto/estado-producto.reducer';
import { getEntities as getLotes } from 'app/entities/lote/lote.reducer';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { getEntities as getSalidas } from 'app/entities/salida/salida.reducer';
import { getEntities as getSitios } from 'app/entities/sitio/sitio.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';

import { createEntity, getEntity, reset, updateEntity } from './producto-sitio.reducer';

export const ProductoSitioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productos = useAppSelector(state => state.producto.entities);
  const sitios = useAppSelector(state => state.sitio.entities);
  const lotes = useAppSelector(state => state.lote.entities);
  const estadoProductos = useAppSelector(state => state.estadoProducto.entities);
  const salidas = useAppSelector(state => state.salida.entities);
  const productoSitioEntity = useAppSelector(state => state.productoSitio.entity);
  const loading = useAppSelector(state => state.productoSitio.loading);
  const updating = useAppSelector(state => state.productoSitio.updating);
  const updateSuccess = useAppSelector(state => state.productoSitio.updateSuccess);

  const handleClose = () => {
    navigate(`/producto-sitio${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProductos({}));
    dispatch(getSitios({}));
    dispatch(getLotes({}));
    dispatch(getEstadoProductos({}));
    dispatch(getSalidas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.cantidadSitio !== undefined && typeof values.cantidadSitio !== 'number') {
      values.cantidadSitio = Number(values.cantidadSitio);
    }

    const entity = {
      ...productoSitioEntity,
      ...values,
      producto: productos.find(it => it.id.toString() === values.producto?.toString()),
      sitio: sitios.find(it => it.id.toString() === values.sitio?.toString()),
      lote: lotes.find(it => it.id.toString() === values.lote?.toString()),
      estadoProducto: estadoProductos.find(it => it.id.toString() === values.estadoProducto?.toString()),
      salidases: mapIdList(values.salidases),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...productoSitioEntity,
          producto: productoSitioEntity?.producto?.id,
          sitio: productoSitioEntity?.sitio?.id,
          lote: productoSitioEntity?.lote?.id,
          estadoProducto: productoSitioEntity?.estadoProducto?.id,
          salidases: productoSitioEntity?.salidases?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.productoSitio.home.createOrEditLabel" data-cy="ProductoSitioCreateUpdateHeading">
            Crear o editar Producto Sitio
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="producto-sitio-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Cantidad Sitio"
                id="producto-sitio-cantidadSitio"
                name="cantidadSitio"
                data-cy="cantidadSitio"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 0, message: 'Este campo debe ser mayor que 0.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField id="producto-sitio-producto" name="producto" data-cy="producto" label="Producto" type="select" required>
                <option value="" key="0" />
                {productos
                  ? productos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombreEspecificacion}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="producto-sitio-sitio" name="sitio" data-cy="sitio" label="Sitio" type="select" required>
                <option value="" key="0" />
                {sitios
                  ? sitios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="producto-sitio-lote" name="lote" data-cy="lote" label="Lote" type="select" required>
                <option value="" key="0" />
                {lotes
                  ? lotes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codigoLote}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="producto-sitio-estadoProducto"
                name="estadoProducto"
                data-cy="estadoProducto"
                label="Estado Producto"
                type="select"
                required
              >
                <option value="" key="0" />
                {estadoProductos
                  ? estadoProductos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoEstado}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField label="Salidas" id="producto-sitio-salidas" data-cy="salidas" type="select" multiple name="salidases">
                <option value="" key="0" />
                {salidas
                  ? salidas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto-sitio" replace variant="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button variant="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProductoSitioUpdate;
