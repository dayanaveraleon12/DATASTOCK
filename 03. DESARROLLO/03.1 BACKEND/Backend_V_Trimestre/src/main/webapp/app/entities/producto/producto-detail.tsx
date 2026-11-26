import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto.reducer';

export const ProductoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const productoEntity = useAppSelector(state => state.producto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoDetailsHeading">Producto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productoEntity.id}</dd>
          <dt>
            <span id="nombreEspecificacion">Nombre Especificacion</span>
          </dt>
          <dd>{productoEntity.nombreEspecificacion}</dd>
          <dt>
            <span id="estadoDisponibilidad">Estado Disponibilidad</span>
          </dt>
          <dd>{productoEntity.estadoDisponibilidad}</dd>
          <dt>Categoria</dt>
          <dd>{productoEntity.categoria ? productoEntity.categoria.nombre : ''}</dd>
          <dt>Presentacion</dt>
          <dd>{productoEntity.presentacion ? productoEntity.presentacion.tipoPresentacion : ''}</dd>
          <dt>Marca</dt>
          <dd>{productoEntity.marca ? productoEntity.marca.marca : ''}</dd>
        </dl>
        <Button as={Link as any} to="/producto" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/producto/${productoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoDetail;
