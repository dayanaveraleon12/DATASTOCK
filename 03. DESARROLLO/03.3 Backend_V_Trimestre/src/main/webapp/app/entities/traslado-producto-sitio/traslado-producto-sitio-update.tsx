import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getProductoSitios } from 'app/entities/producto-sitio/producto-sitio.reducer';
import { getEntities as getTraslados } from 'app/entities/traslado/traslado.reducer';

import { createEntity, getEntity, reset, updateEntity } from './traslado-producto-sitio.reducer';

export const TrasladoProductoSitioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const traslados = useAppSelector(state => state.traslado.entities);
  const productoSitios = useAppSelector(state => state.productoSitio.entities);
  const trasladoProductoSitioEntity = useAppSelector(state => state.trasladoProductoSitio.entity);
  const loading = useAppSelector(state => state.trasladoProductoSitio.loading);
  const updating = useAppSelector(state => state.trasladoProductoSitio.updating);
  const updateSuccess = useAppSelector(state => state.trasladoProductoSitio.updateSuccess);

  const handleClose = () => {
    navigate(`/traslado-producto-sitio${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTraslados({}));
    dispatch(getProductoSitios({}));
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
    if (values.cantidadTraslado !== undefined && typeof values.cantidadTraslado !== 'number') {
      values.cantidadTraslado = Number(values.cantidadTraslado);
    }

    const entity = {
      ...trasladoProductoSitioEntity,
      ...values,
      traslado: traslados.find(it => it.id.toString() === values.traslado?.toString()),
      productoSitio: productoSitios.find(it => it.id.toString() === values.productoSitio?.toString()),
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
          ...trasladoProductoSitioEntity,
          traslado: trasladoProductoSitioEntity?.traslado?.id,
          productoSitio: trasladoProductoSitioEntity?.productoSitio?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.trasladoProductoSitio.home.createOrEditLabel" data-cy="TrasladoProductoSitioCreateUpdateHeading">
            Crear o editar Traslado Producto Sitio
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && (
                <ValidatedField name="id" required readOnly id="traslado-producto-sitio-id" label="ID" validate={{ required: true }} />
              )}
              <ValidatedField
                label="Cantidad Traslado"
                id="traslado-producto-sitio-cantidadTraslado"
                name="cantidadTraslado"
                data-cy="cantidadTraslado"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 0, message: 'Este campo debe ser mayor que 0.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                id="traslado-producto-sitio-traslado"
                name="traslado"
                data-cy="traslado"
                label="Traslado"
                type="select"
                required
              >
                <option value="" key="0" />
                {traslados
                  ? traslados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="traslado-producto-sitio-productoSitio"
                name="productoSitio"
                data-cy="productoSitio"
                label="Producto Sitio"
                type="select"
                required
              >
                <option value="" key="0" />
                {productoSitios
                  ? productoSitios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button
                as={Link as any}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/traslado-producto-sitio"
                replace
                variant="info"
              >
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

export default TrasladoProductoSitioUpdate;
