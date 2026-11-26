import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiItemCount, JhiPagination, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities } from './producto-sitio.reducer';

export const ProductoSitio = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const productoSitioList = useAppSelector(state => state.productoSitio.entities);
  const loading = useAppSelector(state => state.productoSitio.loading);
  const totalItems = useAppSelector(state => state.productoSitio.totalItems);

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
      <h2 id="producto-sitio-heading" data-cy="ProductoSitioHeading">
        Producto Sitios
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/producto-sitio/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Producto Sitio
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productoSitioList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('cantidadSitio')}>
                  Cantidad Sitio <FontAwesomeIcon icon={getSortIconByFieldName('cantidadSitio')} />
                </th>
                <th>
                  Producto <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Sitio <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Lote <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Estado Producto <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productoSitioList.map(productoSitio => (
                <tr key={`entity-${productoSitio.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/producto-sitio/${productoSitio.id}`} variant="link" size="sm">
                      {productoSitio.id}
                    </Button>
                  </td>
                  <td>{productoSitio.cantidadSitio}</td>
                  <td>
                    {productoSitio.producto ? (
                      <Link to={`/producto/${productoSitio.producto.id}`}>{productoSitio.producto.nombreEspecificacion}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{productoSitio.sitio ? <Link to={`/sitio/${productoSitio.sitio.id}`}>{productoSitio.sitio.nombre}</Link> : ''}</td>
                  <td>{productoSitio.lote ? <Link to={`/lote/${productoSitio.lote.id}`}>{productoSitio.lote.codigoLote}</Link> : ''}</td>
                  <td>
                    {productoSitio.estadoProducto ? (
                      <Link to={`/estado-producto/${productoSitio.estadoProducto.id}`}>{productoSitio.estadoProducto.tipoEstado}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        as={Link as any}
                        to={`/producto-sitio/${productoSitio.id}`}
                        variant="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/producto-sitio/${productoSitio.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (globalThis.location.href = `/producto-sitio/${productoSitio.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
          !loading && <div className="alert alert-warning">Ningún Producto Sitios encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={productoSitioList && productoSitioList.length > 0 ? '' : 'd-none'}>
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

export default ProductoSitio;
