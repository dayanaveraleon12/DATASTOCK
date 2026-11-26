import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lote.reducer';

export const LoteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const loteEntity = useAppSelector(state => state.lote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="loteDetailsHeading">Lote</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{loteEntity.id}</dd>
          <dt>
            <span id="fechaIngreso">Fecha Ingreso</span>
          </dt>
          <dd>
            {loteEntity.fechaIngreso ? <TextFormat value={loteEntity.fechaIngreso} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fechaVencimiento">Fecha Vencimiento</span>
          </dt>
          <dd>
            {loteEntity.fechaVencimiento ? (
              <TextFormat value={loteEntity.fechaVencimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="codigoLote">Codigo Lote</span>
          </dt>
          <dd>{loteEntity.codigoLote}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{loteEntity.descripcion}</dd>
          <dt>
            <span id="facturaProveedor">Factura Proveedor</span>
          </dt>
          <dd>{loteEntity.facturaProveedor}</dd>
          <dt>
            <span id="cantidadIngresada">Cantidad Ingresada</span>
          </dt>
          <dd>{loteEntity.cantidadIngresada}</dd>
          <dt>Producto</dt>
          <dd>{loteEntity.producto ? loteEntity.producto.nombreEspecificacion : ''}</dd>
        </dl>
        <Button as={Link as any} to="/lote" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/lote/${loteEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LoteDetail;
