import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCuentas } from 'app/entities/cuenta/cuenta.reducer';
import { getEntities as getSitios } from 'app/entities/sitio/sitio.reducer';

import { createEntity, getEntity, reset, updateEntity } from './evidencia-salida.reducer';

export const EvidenciaSalidaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cuentas = useAppSelector(state => state.cuenta.entities);
  const sitios = useAppSelector(state => state.sitio.entities);
  const evidenciaSalidaEntity = useAppSelector(state => state.evidenciaSalida.entity);
  const loading = useAppSelector(state => state.evidenciaSalida.loading);
  const updating = useAppSelector(state => state.evidenciaSalida.updating);
  const updateSuccess = useAppSelector(state => state.evidenciaSalida.updateSuccess);

  const handleClose = () => {
    navigate(`/evidencia-salida${location.search}`);
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
      ...evidenciaSalidaEntity,
      ...values,
      cuenta: cuentas.find(it => it.id.toString() === values.cuenta?.toString()),
      sitioSubida: sitios.find(it => it.id.toString() === values.sitioSubida?.toString()),
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
          ...evidenciaSalidaEntity,
          cuenta: evidenciaSalidaEntity?.cuenta?.id,
          sitioSubida: evidenciaSalidaEntity?.sitioSubida?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.evidenciaSalida.home.createOrEditLabel" data-cy="EvidenciaSalidaCreateUpdateHeading">
            Crear o editar Evidencia Salida
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="evidencia-salida-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Archivo Excel"
                id="evidencia-salida-archivoExcel"
                name="archivoExcel"
                data-cy="archivoExcel"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Nombre Excel"
                id="evidencia-salida-nombreExcel"
                name="nombreExcel"
                data-cy="nombreExcel"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Fecha Carga"
                id="evidencia-salida-fechaCarga"
                name="fechaCarga"
                data-cy="fechaCarga"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="evidencia-salida-cuenta" name="cuenta" data-cy="cuenta" label="Cuenta" type="select" required>
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
              <ValidatedField
                id="evidencia-salida-sitioSubida"
                name="sitioSubida"
                data-cy="sitioSubida"
                label="Sitio Subida"
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/evidencia-salida" replace variant="info">
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

export default EvidenciaSalidaUpdate;
