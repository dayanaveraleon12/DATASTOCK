import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './remision.reducer';

export const RemisionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const remisionEntity = useAppSelector(state => state.remision.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="remisionDetailsHeading">Remision</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{remisionEntity.id}</dd>
          <dt>
            <span id="cantidadRemitida">Cantidad Remitida</span>
          </dt>
          <dd>{remisionEntity.cantidadRemitida}</dd>
          <dt>Producto Sitio</dt>
          <dd>{remisionEntity.productoSitio ? remisionEntity.productoSitio.id : ''}</dd>
          <dt>Codigo</dt>
          <dd>{remisionEntity.codigo ? remisionEntity.codigo.id : ''}</dd>
          <dt>Sitio Destino</dt>
          <dd>{remisionEntity.sitioDestino ? remisionEntity.sitioDestino.nombre : ''}</dd>
        </dl>
        <Button as={Link as any} to="/remision" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/remision/${remisionEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RemisionDetail;
