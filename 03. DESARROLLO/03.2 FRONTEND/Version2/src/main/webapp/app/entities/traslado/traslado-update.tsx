import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCodigos } from 'app/entities/codigo/codigo.reducer';
import { getEntities as getCuentaSitios } from 'app/entities/cuenta-sitio/cuenta-sitio.reducer';
import { getEntities as getSitios } from 'app/entities/sitio/sitio.reducer';

import { createEntity, getEntity, reset, updateEntity } from './traslado.reducer';

export const TrasladoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cuentaSitios = useAppSelector(state => state.cuentaSitio.entities);
  const sitios = useAppSelector(state => state.sitio.entities);
  const codigos = useAppSelector(state => state.codigo.entities);
  const trasladoEntity = useAppSelector(state => state.traslado.entity);
  const loading = useAppSelector(state => state.traslado.loading);
  const updating = useAppSelector(state => state.traslado.updating);
  const updateSuccess = useAppSelector(state => state.traslado.updateSuccess);

  const handleClose = () => {
    navigate(`/traslado${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCuentaSitios({}));
    dispatch(getSitios({}));
    dispatch(getCodigos({}));
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

    const entity = {
      ...trasladoEntity,
      ...values,
      cuentaSitio: cuentaSitios.find(it => it.id.toString() === values.cuentaSitio?.toString()),
      sitioDestino: sitios.find(it => it.id.toString() === values.sitioDestino?.toString()),
      codigo: codigos.find(it => it.id.toString() === values.codigo?.toString()),
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
          ...trasladoEntity,
          cuentaSitio: trasladoEntity?.cuentaSitio?.id,
          sitioDestino: trasladoEntity?.sitioDestino?.id,
          codigo: trasladoEntity?.codigo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.traslado.home.createOrEditLabel" data-cy="TrasladoCreateUpdateHeading">
            Crear o editar Traslado
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="traslado-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Descripcion"
                id="traslado-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 150, message: 'Este campo no puede superar más de 150 caracteres.' },
                }}
              />
              <ValidatedField
                id="traslado-cuentaSitio"
                name="cuentaSitio"
                data-cy="cuentaSitio"
                label="Cuenta Sitio"
                type="select"
                required
              >
                <option value="" key="0" />
                {cuentaSitios
                  ? cuentaSitios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="traslado-sitioDestino"
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
              <ValidatedField id="traslado-codigo" name="codigo" data-cy="codigo" label="Codigo" type="select" required>
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/traslado" replace variant="info">
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

export default TrasladoUpdate;
