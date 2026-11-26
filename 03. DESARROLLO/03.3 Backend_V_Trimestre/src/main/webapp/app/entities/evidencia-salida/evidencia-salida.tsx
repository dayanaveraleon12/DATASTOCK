import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiItemCount, JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities } from './evidencia-salida.reducer';

export const EvidenciaSalida = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const evidenciaSalidaList = useAppSelector(state => state.evidenciaSalida.entities);
  const loading = useAppSelector(state => state.evidenciaSalida.loading);
  const totalItems = useAppSelector(state => state.evidenciaSalida.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const { order } = paginationState;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="evidencia-salida-heading" data-cy="EvidenciaSalidaHeading">
        Evidencia Salidas
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/evidencia-salida/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Evidencia Salida
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {evidenciaSalidaList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('archivoExcel')}>
                  Archivo Excel <FontAwesomeIcon icon={getSortIconByFieldName('archivoExcel')} />
                </th>
                <th className="hand" onClick={sort('nombreExcel')}>
                  Nombre Excel <FontAwesomeIcon icon={getSortIconByFieldName('nombreExcel')} />
                </th>
                <th className="hand" onClick={sort('fechaCarga')}>
                  Fecha Carga <FontAwesomeIcon icon={getSortIconByFieldName('fechaCarga')} />
                </th>
                <th>
                  Cuenta <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Sitio Subida <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {evidenciaSalidaList.map(evidenciaSalida => (
                <tr key={`entity-${evidenciaSalida.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/evidencia-salida/${evidenciaSalida.id}`} variant="link" size="sm">
                      {evidenciaSalida.id}
                    </Button>
                  </td>
                  <td>{evidenciaSalida.archivoExcel}</td>
                  <td>{evidenciaSalida.nombreExcel}</td>
                  <td>
                    {evidenciaSalida.fechaCarga ? (
                      <TextFormat type="date" value={evidenciaSalida.fechaCarga} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {evidenciaSalida.cuenta ? (
                      <Link to={`/cuenta/${evidenciaSalida.cuenta.id}`}>{evidenciaSalida.cuenta.numeroDocumento}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {evidenciaSalida.sitioSubida ? (
                      <Link to={`/sitio/${evidenciaSalida.sitioSubida.id}`}>{evidenciaSalida.sitioSubida.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        as={Link as any}
                        to={`/evidencia-salida/${evidenciaSalida.id}`}
                        variant="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/evidencia-salida/${evidenciaSalida.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (globalThis.location.href = `/evidencia-salida/${evidenciaSalida.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        variant="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Evidencia Salidas encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={evidenciaSalidaList && evidenciaSalidaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default EvidenciaSalida;
