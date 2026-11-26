import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './salida.reducer';

export const SalidaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const salidaEntity = useAppSelector(state => state.salida.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="salidaDetailsHeading">Salida</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{salidaEntity.id}</dd>
          <dt>
            <span id="respaldoImagen">Respaldo Imagen</span>
          </dt>
          <dd>{salidaEntity.respaldoImagen}</dd>
          <dt>
            <span id="cantidadASacar">Cantidad A Sacar</span>
          </dt>
          <dd>{salidaEntity.cantidadASacar}</dd>
          <dt>
            <span id="observaciones">Observaciones</span>
          </dt>
          <dd>{salidaEntity.observaciones}</dd>
          <dt>Evidencia Salida</dt>
          <dd>{salidaEntity.evidenciaSalida ? salidaEntity.evidenciaSalida.id : ''}</dd>
          <dt>Cuenta Sitio</dt>
          <dd>{salidaEntity.cuentaSitio ? salidaEntity.cuentaSitio.id : ''}</dd>
          <dt>Motivo Salida</dt>
          <dd>{salidaEntity.motivoSalida ? salidaEntity.motivoSalida.tipoMotivo : ''}</dd>
          <dt>Producto Sitios</dt>
          <dd>
            {salidaEntity.productoSitioses
              ? salidaEntity.productoSitioses.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {salidaEntity.productoSitioses && i === salidaEntity.productoSitioses.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button as={Link as any} to="/salida" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/salida/${salidaEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SalidaDetail;
