import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EstadoProducto from './estado-producto';
import EstadoProductoDeleteDialog from './estado-producto-delete-dialog';
import EstadoProductoDetail from './estado-producto-detail';
import EstadoProductoUpdate from './estado-producto-update';

const EstadoProductoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EstadoProducto />} />
    <Route path="new" element={<EstadoProductoUpdate />} />
    <Route path=":id">
      <Route index element={<EstadoProductoDetail />} />
      <Route path="edit" element={<EstadoProductoUpdate />} />
      <Route path="delete" element={<EstadoProductoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EstadoProductoRoutes;
