import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './estado-producto.reducer';

export const EstadoProductoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const estadoProductoEntity = useAppSelector(state => state.estadoProducto.entity);
  const loading = useAppSelector(state => state.estadoProducto.loading);
  const updating = useAppSelector(state => state.estadoProducto.updating);
  const updateSuccess = useAppSelector(state => state.estadoProducto.updateSuccess);

  const handleClose = () => {
    navigate(`/estado-producto${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...estadoProductoEntity,
      ...values,
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
          ...estadoProductoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.estadoProducto.home.createOrEditLabel" data-cy="EstadoProductoCreateUpdateHeading">
            Crear o editar Estado Producto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="estado-producto-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Tipo Estado"
                id="estado-producto-tipoEstado"
                name="tipoEstado"
                data-cy="tipoEstado"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 50, message: 'Este campo no puede superar más de 50 caracteres.' },
                }}
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/estado-producto" replace variant="info">
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

export default EstadoProductoUpdate;
