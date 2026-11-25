import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCodigos } from 'app/entities/codigo/codigo.reducer';
import { getEntities as getProductoSitios } from 'app/entities/producto-sitio/producto-sitio.reducer';
import { getEntities as getSitios } from 'app/entities/sitio/sitio.reducer';

import { createEntity, getEntity, reset, updateEntity } from './remision.reducer';

export const RemisionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productoSitios = useAppSelector(state => state.productoSitio.entities);
  const codigos = useAppSelector(state => state.codigo.entities);
  const sitios = useAppSelector(state => state.sitio.entities);
  const remisionEntity = useAppSelector(state => state.remision.entity);
  const loading = useAppSelector(state => state.remision.loading);
  const updating = useAppSelector(state => state.remision.updating);
  const updateSuccess = useAppSelector(state => state.remision.updateSuccess);

  const handleClose = () => {
    navigate(`/remision${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProductoSitios({}));
    dispatch(getCodigos({}));
    dispatch(getSitios({}));
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
    if (values.cantidadRemitida !== undefined && typeof values.cantidadRemitida !== 'number') {
      values.cantidadRemitida = Number(values.cantidadRemitida);
    }

    const entity = {
      ...remisionEntity,
      ...values,
      productoSitio: productoSitios.find(it => it.id.toString() === values.productoSitio?.toString()),
      codigo: codigos.find(it => it.id.toString() === values.codigo?.toString()),
      sitioDestino: sitios.find(it => it.id.toString() === values.sitioDestino?.toString()),
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
          ...remisionEntity,
          productoSitio: remisionEntity?.productoSitio?.id,
          codigo: remisionEntity?.codigo?.id,
          sitioDestino: remisionEntity?.sitioDestino?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.remision.home.createOrEditLabel" data-cy="RemisionCreateUpdateHeading">
            Crear o editar Remision
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="remision-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Cantidad Remitida"
                id="remision-cantidadRemitida"
                name="cantidadRemitida"
                data-cy="cantidadRemitida"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 0, message: 'Este campo debe ser mayor que 0.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                id="remision-productoSitio"
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
              <ValidatedField id="remision-codigo" name="codigo" data-cy="codigo" label="Codigo" type="select" required>
                <option value="" key="0" />
                {codigos
                  ? codigos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="remision-sitioDestino"
                name="sitioDestino"
                data-cy="sitioDestino"
                label="Sitio Destino"
                type="select"
                required
              >
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/remision" replace variant="info">
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

export default RemisionUpdate;
