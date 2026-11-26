import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';

import { createEntity, getEntity, reset, updateEntity } from './lote.reducer';

export const LoteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productos = useAppSelector(state => state.producto.entities);
  const loteEntity = useAppSelector(state => state.lote.entity);
  const loading = useAppSelector(state => state.lote.loading);
  const updating = useAppSelector(state => state.lote.updating);
  const updateSuccess = useAppSelector(state => state.lote.updateSuccess);

  const handleClose = () => {
    navigate(`/lote${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProductos({}));
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
    if (values.cantidadIngresada !== undefined && typeof values.cantidadIngresada !== 'number') {
      values.cantidadIngresada = Number(values.cantidadIngresada);
    }

    const entity = {
      ...loteEntity,
      ...values,
      producto: productos.find(it => it.id.toString() === values.producto?.toString()),
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
          ...loteEntity,
          producto: loteEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.lote.home.createOrEditLabel" data-cy="LoteCreateUpdateHeading">
            Crear o editar Lote
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="lote-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Fecha Ingreso"
                id="lote-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Fecha Vencimiento"
                id="lote-fechaVencimiento"
                name="fechaVencimiento"
                data-cy="fechaVencimiento"
                type="date"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Codigo Lote"
                id="lote-codigoLote"
                name="codigoLote"
                data-cy="codigoLote"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 100, message: 'Este campo no puede superar más de 100 caracteres.' },
                }}
              />
              <ValidatedField
                label="Descripcion"
                id="lote-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'Este campo no puede superar más de 10 caracteres.' },
                }}
              />
              <ValidatedField
                label="Factura Proveedor"
                id="lote-facturaProveedor"
                name="facturaProveedor"
                data-cy="facturaProveedor"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Cantidad Ingresada"
                id="lote-cantidadIngresada"
                name="cantidadIngresada"
                data-cy="cantidadIngresada"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 0, message: 'Este campo debe ser mayor que 0.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField id="lote-producto" name="producto" data-cy="producto" label="Producto" type="select" required>
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/lote" replace variant="info">
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

export default LoteUpdate;
