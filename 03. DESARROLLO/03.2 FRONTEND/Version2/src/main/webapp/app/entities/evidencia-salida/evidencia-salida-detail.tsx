import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evidencia-salida.reducer';

export const EvidenciaSalidaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const evidenciaSalidaEntity = useAppSelector(state => state.evidenciaSalida.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="evidenciaSalidaDetailsHeading">Evidencia Salida</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{evidenciaSalidaEntity.id}</dd>
          <dt>
            <span id="archivoExcel">Archivo Excel</span>
          </dt>
          <dd>{evidenciaSalidaEntity.archivoExcel}</dd>
          <dt>
            <span id="nombreExcel">Nombre Excel</span>
          </dt>
          <dd>{evidenciaSalidaEntity.nombreExcel}</dd>
          <dt>
            <span id="fechaCarga">Fecha Carga</span>
          </dt>
          <dd>
            {evidenciaSalidaEntity.fechaCarga ? (
              <TextFormat value={evidenciaSalidaEntity.fechaCarga} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Cuenta</dt>
          <dd>{evidenciaSalidaEntity.cuenta ? evidenciaSalidaEntity.cuenta.numeroDocumento : ''}</dd>
          <dt>Sitio Subida</dt>
          <dd>{evidenciaSalidaEntity.sitioSubida ? evidenciaSalidaEntity.sitioSubida.nombre : ''}</dd>
        </dl>
        <Button as={Link as any} to="/evidencia-salida" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/evidencia-salida/${evidenciaSalidaEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EvidenciaSalidaDetail;
