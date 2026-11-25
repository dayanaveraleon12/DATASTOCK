import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCuentas } from 'app/entities/cuenta/cuenta.reducer';
import { getEntities as getSitios } from 'app/entities/sitio/sitio.reducer';

import { createEntity, getEntity, reset, updateEntity } from './cuenta-sitio.reducer';

export const CuentaSitioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cuentas = useAppSelector(state => state.cuenta.entities);
  const sitios = useAppSelector(state => state.sitio.entities);
  const cuentaSitioEntity = useAppSelector(state => state.cuentaSitio.entity);
  const loading = useAppSelector(state => state.cuentaSitio.loading);
  const updating = useAppSelector(state => state.cuentaSitio.updating);
  const updateSuccess = useAppSelector(state => state.cuentaSitio.updateSuccess);

  const handleClose = () => {
    navigate(`/cuenta-sitio${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCuentas({}));
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

    const entity = {
      ...cuentaSitioEntity,
      ...values,
      cuenta: cuentas.find(it => it.id.toString() === values.cuenta?.toString()),
      sitio: sitios.find(it => it.id.toString() === values.sitio?.toString()),
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
          ...cuentaSitioEntity,
          cuenta: cuentaSitioEntity?.cuenta?.id,
          sitio: cuentaSitioEntity?.sitio?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.cuentaSitio.home.createOrEditLabel" data-cy="CuentaSitioCreateUpdateHeading">
            Crear o editar Cuenta Sitio
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="cuenta-sitio-id" label="ID" validate={{ required: true }} />}
              <ValidatedField id="cuenta-sitio-cuenta" name="cuenta" data-cy="cuenta" label="Cuenta" type="select" required>
                <option value="" key="0" />
                {cuentas
                  ? cuentas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.numeroDocumento}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="cuenta-sitio-sitio" name="sitio" data-cy="sitio" label="Sitio" type="select" required>
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/cuenta-sitio" replace variant="info">
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

export default CuentaSitioUpdate;
