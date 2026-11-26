import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Salida from './salida';
import SalidaDeleteDialog from './salida-delete-dialog';
import SalidaDetail from './salida-detail';
import SalidaUpdate from './salida-update';

const SalidaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Salida />} />
    <Route path="new" element={<SalidaUpdate />} />
    <Route path=":id">
      <Route index element={<SalidaDetail />} />
      <Route path="edit" element={<SalidaUpdate />} />
      <Route path="delete" element={<SalidaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SalidaRoutes;
