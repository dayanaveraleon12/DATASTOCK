import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './traslado.reducer';

export const TrasladoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const trasladoEntity = useAppSelector(state => state.traslado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trasladoDetailsHeading">Traslado</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trasladoEntity.id}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{trasladoEntity.descripcion}</dd>
          <dt>Cuenta Sitio</dt>
          <dd>{trasladoEntity.cuentaSitio ? trasladoEntity.cuentaSitio.id : ''}</dd>
          <dt>Sitio Destino</dt>
          <dd>{trasladoEntity.sitioDestino ? trasladoEntity.sitioDestino.nombre : ''}</dd>
          <dt>Codigo</dt>
          <dd>{trasladoEntity.codigo ? trasladoEntity.codigo.id : ''}</dd>
        </dl>
        <Button as={Link as any} to="/traslado" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/traslado/${trasladoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrasladoDetail;
