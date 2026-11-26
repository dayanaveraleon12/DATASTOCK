import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lote from './lote';
import LoteDeleteDialog from './lote-delete-dialog';
import LoteDetail from './lote-detail';
import LoteUpdate from './lote-update';

const LoteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lote />} />
    <Route path="new" element={<LoteUpdate />} />
    <Route path=":id">
      <Route index element={<LoteDetail />} />
      <Route path="edit" element={<LoteUpdate />} />
      <Route path="delete" element={<LoteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LoteRoutes;
