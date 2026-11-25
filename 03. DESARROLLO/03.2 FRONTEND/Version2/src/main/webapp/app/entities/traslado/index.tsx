import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Traslado from './traslado';
import TrasladoDeleteDialog from './traslado-delete-dialog';
import TrasladoDetail from './traslado-detail';
import TrasladoUpdate from './traslado-update';

const TrasladoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Traslado />} />
    <Route path="new" element={<TrasladoUpdate />} />
    <Route path=":id">
      <Route index element={<TrasladoDetail />} />
      <Route path="edit" element={<TrasladoUpdate />} />
      <Route path="delete" element={<TrasladoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrasladoRoutes;
