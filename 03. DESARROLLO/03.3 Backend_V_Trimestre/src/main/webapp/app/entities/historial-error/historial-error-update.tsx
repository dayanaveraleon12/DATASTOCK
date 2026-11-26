import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCuentas } from 'app/entities/cuenta/cuenta.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

import { createEntity, getEntity, reset, updateEntity } from './historial-error.reducer';

export const HistorialErrorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cuentas = useAppSelector(state => state.cuenta.entities);
  const historialErrorEntity = useAppSelector(state => state.historialError.entity);
  const loading = useAppSelector(state => state.historialError.loading);
  const updating = useAppSelector(state => state.historialError.updating);
  const updateSuccess = useAppSelector(state => state.historialError.updateSuccess);

  const handleClose = () => {
    navigate(`/historial-error${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCuentas({}));
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
    values.fechaHora = convertDateTimeToServer(values.fechaHora);

    const entity = {
      ...historialErrorEntity,
      ...values,
      cuenta: cuentas.find(it => it.id.toString() === values.cuenta?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaHora: displayDefaultDateTime(),
        }
      : {
          ...historialErrorEntity,
          fechaHora: convertDateTimeFromServer(historialErrorEntity.fechaHora),
          cuenta: historialErrorEntity?.cuenta?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.historialError.home.createOrEditLabel" data-cy="HistorialErrorCreateUpdateHeading">
            Crear o editar Historial Error
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="historial-error-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Nombre Error"
                id="historial-error-nombreError"
                name="nombreError"
                data-cy="nombreError"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 150, message: 'Este campo no puede superar más de 150 caracteres.' },
                }}
              />
              <ValidatedField
                label="Mensaje"
                id="historial-error-mensaje"
                name="mensaje"
                data-cy="mensaje"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Fecha Hora"
                id="historial-error-fechaHora"
                name="fechaHora"
                data-cy="fechaHora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="historial-error-cuenta" name="cuenta" data-cy="cuenta" label="Cuenta" type="select" required>
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/historial-error" replace variant="info">
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

export default HistorialErrorUpdate;
