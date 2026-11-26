import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Codigo from './codigo';
import CodigoDeleteDialog from './codigo-delete-dialog';
import CodigoDetail from './codigo-detail';
import CodigoUpdate from './codigo-update';

const CodigoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Codigo />} />
    <Route path="new" element={<CodigoUpdate />} />
    <Route path=":id">
      <Route index element={<CodigoDetail />} />
      <Route path="edit" element={<CodigoUpdate />} />
      <Route path="delete" element={<CodigoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CodigoRoutes;
