import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Presentacion from './presentacion';
import PresentacionDeleteDialog from './presentacion-delete-dialog';
import PresentacionDetail from './presentacion-detail';
import PresentacionUpdate from './presentacion-update';

const PresentacionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Presentacion />} />
    <Route path="new" element={<PresentacionUpdate />} />
    <Route path=":id">
      <Route index element={<PresentacionDetail />} />
      <Route path="edit" element={<PresentacionUpdate />} />
      <Route path="delete" element={<PresentacionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PresentacionRoutes;
