import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './traslado-producto-sitio.reducer';

export const TrasladoProductoSitioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const trasladoProductoSitioEntity = useAppSelector(state => state.trasladoProductoSitio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trasladoProductoSitioDetailsHeading">Traslado Producto Sitio</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trasladoProductoSitioEntity.id}</dd>
          <dt>
            <span id="cantidadTraslado">Cantidad Traslado</span>
          </dt>
          <dd>{trasladoProductoSitioEntity.cantidadTraslado}</dd>
          <dt>Traslado</dt>
          <dd>{trasladoProductoSitioEntity.traslado ? trasladoProductoSitioEntity.traslado.id : ''}</dd>
          <dt>Producto Sitio</dt>
          <dd>{trasladoProductoSitioEntity.productoSitio ? trasladoProductoSitioEntity.productoSitio.id : ''}</dd>
        </dl>
        <Button as={Link as any} to="/traslado-producto-sitio" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/traslado-producto-sitio/${trasladoProductoSitioEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrasladoProductoSitioDetail;
