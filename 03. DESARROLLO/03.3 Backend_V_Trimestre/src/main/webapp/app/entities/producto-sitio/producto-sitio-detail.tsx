import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './producto-sitio.reducer';

export const ProductoSitioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const productoSitioEntity = useAppSelector(state => state.productoSitio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productoSitioDetailsHeading">Producto Sitio</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productoSitioEntity.id}</dd>
          <dt>
            <span id="cantidadSitio">Cantidad Sitio</span>
          </dt>
          <dd>{productoSitioEntity.cantidadSitio}</dd>
          <dt>Producto</dt>
          <dd>{productoSitioEntity.producto ? productoSitioEntity.producto.nombreEspecificacion : ''}</dd>
          <dt>Sitio</dt>
          <dd>{productoSitioEntity.sitio ? productoSitioEntity.sitio.nombre : ''}</dd>
          <dt>Lote</dt>
          <dd>{productoSitioEntity.lote ? productoSitioEntity.lote.codigoLote : ''}</dd>
          <dt>Estado Producto</dt>
          <dd>{productoSitioEntity.estadoProducto ? productoSitioEntity.estadoProducto.tipoEstado : ''}</dd>
          <dt>Salidas</dt>
          <dd>
            {productoSitioEntity.salidases
              ? productoSitioEntity.salidases.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productoSitioEntity.salidases && i === productoSitioEntity.salidases.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button as={Link as any} to="/producto-sitio" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/producto-sitio/${productoSitioEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductoSitioDetail;
