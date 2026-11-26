import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HistorialError from './historial-error';
import HistorialErrorDeleteDialog from './historial-error-delete-dialog';
import HistorialErrorDetail from './historial-error-detail';
import HistorialErrorUpdate from './historial-error-update';

const HistorialErrorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HistorialError />} />
    <Route path="new" element={<HistorialErrorUpdate />} />
    <Route path=":id">
      <Route index element={<HistorialErrorDetail />} />
      <Route path="edit" element={<HistorialErrorUpdate />} />
      <Route path="delete" element={<HistorialErrorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HistorialErrorRoutes;
