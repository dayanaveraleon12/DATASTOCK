import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCuentaSitios } from 'app/entities/cuenta-sitio/cuenta-sitio.reducer';
import { EstadoOperacion } from 'app/shared/model/enumerations/estado-operacion.model';

import { createEntity, getEntity, reset, updateEntity } from './codigo.reducer';

export const CodigoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cuentaSitios = useAppSelector(state => state.cuentaSitio.entities);
  const codigoEntity = useAppSelector(state => state.codigo.entity);
  const loading = useAppSelector(state => state.codigo.loading);
  const updating = useAppSelector(state => state.codigo.updating);
  const updateSuccess = useAppSelector(state => state.codigo.updateSuccess);
  const estadoOperacionValues = Object.keys(EstadoOperacion);

  const handleClose = () => {
    navigate(`/codigo${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCuentaSitios({}));
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
      ...codigoEntity,
      ...values,
      cuentaSitio: cuentaSitios.find(it => it.id.toString() === values.cuentaSitio?.toString()),
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
          estadoOperacion: 'PENDIENTE',
          ...codigoEntity,
          cuentaSitio: codigoEntity?.cuentaSitio?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.codigo.home.createOrEditLabel" data-cy="CodigoCreateUpdateHeading">
            Crear o editar Codigo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="codigo-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Fecha Remision"
                id="codigo-fechaRemision"
                name="fechaRemision"
                data-cy="fechaRemision"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Codigo"
                id="codigo-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Estado Operacion"
                id="codigo-estadoOperacion"
                name="estadoOperacion"
                data-cy="estadoOperacion"
                type="select"
              >
                {estadoOperacionValues.map(estadoOperacion => (
                  <option value={estadoOperacion} key={estadoOperacion}>
                    {estadoOperacion}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="codigo-cuentaSitio" name="cuentaSitio" data-cy="cuentaSitio" label="Cuenta Sitio" type="select" required>
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/codigo" replace variant="info">
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

export default CodigoUpdate;
