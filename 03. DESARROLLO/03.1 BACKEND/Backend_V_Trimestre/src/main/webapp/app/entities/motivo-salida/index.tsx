import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MotivoSalida from './motivo-salida';
import MotivoSalidaDeleteDialog from './motivo-salida-delete-dialog';
import MotivoSalidaDetail from './motivo-salida-detail';
import MotivoSalidaUpdate from './motivo-salida-update';

const MotivoSalidaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MotivoSalida />} />
    <Route path="new" element={<MotivoSalidaUpdate />} />
    <Route path=":id">
      <Route index element={<MotivoSalidaDetail />} />
      <Route path="edit" element={<MotivoSalidaUpdate />} />
      <Route path="delete" element={<MotivoSalidaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MotivoSalidaRoutes;
