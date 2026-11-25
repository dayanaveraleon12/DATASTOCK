import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TrasladoProductoSitio from './traslado-producto-sitio';
import TrasladoProductoSitioDeleteDialog from './traslado-producto-sitio-delete-dialog';
import TrasladoProductoSitioDetail from './traslado-producto-sitio-detail';
import TrasladoProductoSitioUpdate from './traslado-producto-sitio-update';

const TrasladoProductoSitioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TrasladoProductoSitio />} />
    <Route path="new" element={<TrasladoProductoSitioUpdate />} />
    <Route path=":id">
      <Route index element={<TrasladoProductoSitioDetail />} />
      <Route path="edit" element={<TrasladoProductoSitioUpdate />} />
      <Route path="delete" element={<TrasladoProductoSitioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrasladoProductoSitioRoutes;
