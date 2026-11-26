import React, { useEffect } from 'react';
import { Button, Col, FormText, Row } from 'react-bootstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getCuentaSitios } from 'app/entities/cuenta-sitio/cuenta-sitio.reducer';
import { getEntities as getEvidenciaSalidas } from 'app/entities/evidencia-salida/evidencia-salida.reducer';
import { getEntities as getMotivoSalidas } from 'app/entities/motivo-salida/motivo-salida.reducer';
import { getEntities as getProductoSitios } from 'app/entities/producto-sitio/producto-sitio.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';

import { createEntity, getEntity, reset, updateEntity } from './salida.reducer';

export const SalidaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const evidenciaSalidas = useAppSelector(state => state.evidenciaSalida.entities);
  const cuentaSitios = useAppSelector(state => state.cuentaSitio.entities);
  const motivoSalidas = useAppSelector(state => state.motivoSalida.entities);
  const productoSitios = useAppSelector(state => state.productoSitio.entities);
  const salidaEntity = useAppSelector(state => state.salida.entity);
  const loading = useAppSelector(state => state.salida.loading);
  const updating = useAppSelector(state => state.salida.updating);
  const updateSuccess = useAppSelector(state => state.salida.updateSuccess);

  const handleClose = () => {
    navigate(`/salida${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEvidenciaSalidas({}));
    dispatch(getCuentaSitios({}));
    dispatch(getMotivoSalidas({}));
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
    if (values.cantidadASacar !== undefined && typeof values.cantidadASacar !== 'number') {
      values.cantidadASacar = Number(values.cantidadASacar);
    }

    const entity = {
      ...salidaEntity,
      ...values,
      evidenciaSalida: evidenciaSalidas.find(it => it.id.toString() === values.evidenciaSalida?.toString()),
      cuentaSitio: cuentaSitios.find(it => it.id.toString() === values.cuentaSitio?.toString()),
      motivoSalida: motivoSalidas.find(it => it.id.toString() === values.motivoSalida?.toString()),
      productoSitioses: mapIdList(values.productoSitioses),
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
          ...salidaEntity,
          evidenciaSalida: salidaEntity?.evidenciaSalida?.id,
          cuentaSitio: salidaEntity?.cuentaSitio?.id,
          motivoSalida: salidaEntity?.motivoSalida?.id,
          productoSitioses: salidaEntity?.productoSitioses?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datastockApp.salida.home.createOrEditLabel" data-cy="SalidaCreateUpdateHeading">
            Crear o editar Salida
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && <ValidatedField name="id" required readOnly id="salida-id" label="ID" validate={{ required: true }} />}
              <ValidatedField
                label="Respaldo Imagen"
                id="salida-respaldoImagen"
                name="respaldoImagen"
                data-cy="respaldoImagen"
                type="text"
                validate={{
                  maxLength: { value: 255, message: 'Este campo no puede superar más de 255 caracteres.' },
                }}
              />
              <ValidatedField
                label="Cantidad A Sacar"
                id="salida-cantidadASacar"
                name="cantidadASacar"
                data-cy="cantidadASacar"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  min: { value: 1, message: 'Este campo debe ser mayor que 1.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Observaciones"
                id="salida-observaciones"
                name="observaciones"
                data-cy="observaciones"
                type="text"
                validate={{
                  maxLength: { value: 150, message: 'Este campo no puede superar más de 150 caracteres.' },
                }}
              />
              <ValidatedField
                id="salida-evidenciaSalida"
                name="evidenciaSalida"
                data-cy="evidenciaSalida"
                label="Evidencia Salida"
                type="select"
                required
              >
                <option value="" key="0" />
                {evidenciaSalidas
                  ? evidenciaSalidas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField id="salida-cuentaSitio" name="cuentaSitio" data-cy="cuentaSitio" label="Cuenta Sitio" type="select" required>
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
                id="salida-motivoSalida"
                name="motivoSalida"
                data-cy="motivoSalida"
                label="Motivo Salida"
                type="select"
                required
              >
                <option value="" key="0" />
                {motivoSalidas
                  ? motivoSalidas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoMotivo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>Este campo es obligatorio.</FormText>
              <ValidatedField
                label="Producto Sitios"
                id="salida-productoSitios"
                data-cy="productoSitios"
                type="select"
                multiple
                name="productoSitioses"
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
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/salida" replace variant="info">
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

export default SalidaUpdate;
