import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './codigo.reducer';

export const CodigoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const codigoEntity = useAppSelector(state => state.codigo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="codigoDetailsHeading">Codigo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{codigoEntity.id}</dd>
          <dt>
            <span id="fechaRemision">Fecha Remision</span>
          </dt>
          <dd>
            {codigoEntity.fechaRemision ? (
              <TextFormat value={codigoEntity.fechaRemision} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="codigo">Codigo</span>
          </dt>
          <dd>{codigoEntity.codigo}</dd>
          <dt>
            <span id="estadoOperacion">Estado Operacion</span>
          </dt>
          <dd>{codigoEntity.estadoOperacion}</dd>
          <dt>Cuenta Sitio</dt>
          <dd>{codigoEntity.cuentaSitio ? codigoEntity.cuentaSitio.id : ''}</dd>
        </dl>
        <Button as={Link as any} to="/codigo" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/codigo/${codigoEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CodigoDetail;
