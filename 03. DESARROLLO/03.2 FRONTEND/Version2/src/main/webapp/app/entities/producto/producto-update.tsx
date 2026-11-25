import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCategorias } from 'app/entities/categoria/categoria.reducer';
import { getEntities as getMarcas } from 'app/entities/marca/marca.reducer';
import { getEntities as getPresentacions } from 'app/entities/presentacion/presentacion.reducer';
import { Estado } from 'app/shared/model/enumerations/estado.model';

import { createEntity, getEntity, reset, updateEntity } from './producto.reducer';

export const ProductoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categorias = useAppSelector(state => state.categoria.entities);
  const presentacions = useAppSelector(state => state.presentacion.entities);
  const marcas = useAppSelector(state => state.marca.entities);
  const productoEntity = useAppSelector(state => state.producto.entity);
  const loading = useAppSelector(state => state.producto.loading);
  const updating = useAppSelector(state => state.producto.updating);
  const updateSuccess = useAppSelector(state => state.producto.updateSuccess);
  const estadoValues = Object.keys(Estado);

  const handleClose = () => {
    navigate(`/producto${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCategorias({}));
    dispatch(getPresentacions({}));
    dispatch(getMarcas({}));
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
      ...productoEntity,
      ...values,
      categoria: categorias.find(it => it.id.toString() === values.categoria?.toString()),
      presentacion: presentacions.find(it => it.id.toString() === values.presentacion?.toString()),
      marca: marcas.find(it => it.id.toString() === values.marca?.toString()),
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
          estadoDisponibilidad: 'ACTIVO',
          ...productoEntity,
          categoria: productoEntity?.categoria?.id,
          presentacion: productoEntity?.presentacion?.id,
          marca: productoEntity?.marca?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.producto.home.createOrEditLabel" data-cy="ProductoCreateUpdateHeading">
            Crear o editar Producto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="producto-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Nombre Especificacion"
                id="producto-nombreEspecificacion"
                name="nombreEspecificacion"
                data-cy="nombreEspecificacion"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  maxLength: { value: 200, message: 'Este campo no puede superar más de 200 caracteres.' },
                }}
              />
              <ValidatedField
                label="Estado Disponibilidad"
                id="producto-estadoDisponibilidad"
                name="estadoDisponibilidad"
                data-cy="estadoDisponibilidad"
                type="select"
              >
                {estadoValues.map(estado => (
                  <option value={estado} key={estado}>
                    {estado}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="producto-categoria" name="categoria" data-cy="categoria" label="Categoria" type="select" required>
                <option value="" key="0" />
                {categorias
                  ? categorias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                id="producto-presentacion"
                name="presentacion"
                data-cy="presentacion"
                label="Presentacion"
                type="select"
                required
              >
                <option value="" key="0" />
                {presentacions
                  ? presentacions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoPresentacion}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="producto-marca" name="marca" data-cy="marca" label="Marca" type="select" required>
                <option value="" key="0" />
                {marcas
                  ? marcas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.marca}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto" replace variant="info">
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

export default ProductoUpdate;
