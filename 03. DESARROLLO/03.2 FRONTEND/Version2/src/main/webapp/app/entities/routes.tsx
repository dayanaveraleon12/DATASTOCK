import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';

import Categoria from './categoria';
import TrasladoProductoSitio from './traslado-producto-sitio';
import CuentaSitio from './cuenta-sitio';
import Codigo from './codigo';
import Cuenta from './cuenta';
import EstadoProducto from './estado-producto';
import Remision from './remision';
import EvidenciaSalida from './evidencia-salida';
import HistorialError from './historial-error';
import Lote from './lote';
import Marca from './marca';
import MotivoSalida from './motivo-salida';
import Presentacion from './presentacion';
import Producto from './producto';
import ProductoSitio from './producto-sitio';
import Salida from './salida';
import Sitio from './sitio';
import TipoDocumento from './tipo-documento';
import Traslado from './traslado';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/tipo-documento/*" element={<TipoDocumento />} />
        <Route path="/cuenta/*" element={<Cuenta />} />
        <Route path="/historial-error/*" element={<HistorialError />} />
        <Route path="/sitio/*" element={<Sitio />} />
        <Route path="/marca/*" element={<Marca />} />
        <Route path="/categoria/*" element={<Categoria />} />
        <Route path="/presentacion/*" element={<Presentacion />} />
        <Route path="/producto/*" element={<Producto />} />
        <Route path="/lote/*" element={<Lote />} />
        <Route path="/estado-producto/*" element={<EstadoProducto />} />
        <Route path="/producto-sitio/*" element={<ProductoSitio />} />
        <Route path="/traslado-producto-sitio/*" element={<TrasladoProductoSitio />} />
        <Route path="/cuenta-sitio/*" element={<CuentaSitio />} />
        <Route path="/codigo/*" element={<Codigo />} />
        <Route path="/remision/*" element={<Remision />} />
        <Route path="/traslado/*" element={<Traslado />} />
        <Route path="/motivo-salida/*" element={<MotivoSalida />} />
        <Route path="/evidencia-salida/*" element={<EvidenciaSalida />} />
        <Route path="/salida/*" element={<Salida />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
        <Route path="*" element={<PageNotFound />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};
