import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './historial-error.reducer';

export const HistorialErrorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const historialErrorEntity = useAppSelector(state => state.historialError.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="historialErrorDetailsHeading">Historial Error</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{historialErrorEntity.id}</dd>
          <dt>
            <span id="nombreError">Nombre Error</span>
          </dt>
          <dd>{historialErrorEntity.nombreError}</dd>
          <dt>
            <span id="mensaje">Mensaje</span>
          </dt>
          <dd>{historialErrorEntity.mensaje}</dd>
          <dt>
            <span id="fechaHora">Fecha Hora</span>
          </dt>
          <dd>
            {historialErrorEntity.fechaHora ? (
              <TextFormat value={historialErrorEntity.fechaHora} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Cuenta</dt>
          <dd>{historialErrorEntity.cuenta ? historialErrorEntity.cuenta.numeroDocumento : ''}</dd>
        </dl>
        <Button as={Link as any} to="/historial-error" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/historial-error/${historialErrorEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HistorialErrorDetail;
