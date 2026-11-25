import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './estado-producto.reducer';

export const EstadoProductoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const estadoProductoEntity = useAppSelector(state => state.estadoProducto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="estadoProductoDetailsHeading">Estado Producto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{estadoProductoEntity.id}</dd>
          <dt>
            <span id="tipoEstado">Tipo Estado</span>
          </dt>
          <dd>{estadoProductoEntity.tipoEstado}</dd>
        </dl>
        <Button as={Link as any} to="/estado-producto" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/estado-producto/${estadoProductoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EstadoProductoDetail;
