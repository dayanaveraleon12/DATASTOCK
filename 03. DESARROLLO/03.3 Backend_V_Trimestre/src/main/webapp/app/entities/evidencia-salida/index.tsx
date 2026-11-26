import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EvidenciaSalida from './evidencia-salida';
import EvidenciaSalidaDeleteDialog from './evidencia-salida-delete-dialog';
import EvidenciaSalidaDetail from './evidencia-salida-detail';
import EvidenciaSalidaUpdate from './evidencia-salida-update';

const EvidenciaSalidaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EvidenciaSalida />} />
    <Route path="new" element={<EvidenciaSalidaUpdate />} />
    <Route path=":id">
      <Route index element={<EvidenciaSalidaDetail />} />
      <Route path="edit" element={<EvidenciaSalidaUpdate />} />
      <Route path="delete" element={<EvidenciaSalidaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EvidenciaSalidaRoutes;
