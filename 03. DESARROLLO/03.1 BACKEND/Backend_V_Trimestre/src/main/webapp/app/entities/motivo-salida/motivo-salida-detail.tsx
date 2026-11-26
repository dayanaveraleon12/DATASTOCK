import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Link, useParams } from 'react-router';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './motivo-salida.reducer';

export const MotivoSalidaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const motivoSalidaEntity = useAppSelector(state => state.motivoSalida.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="motivoSalidaDetailsHeading">Motivo Salida</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{motivoSalidaEntity.id}</dd>
          <dt>
            <span id="tipoMotivo">Tipo Motivo</span>
          </dt>
          <dd>{motivoSalidaEntity.tipoMotivo}</dd>
        </dl>
        <Button as={Link as any} to="/motivo-salida" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/motivo-salida/${motivoSalidaEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MotivoSalidaDetail;
