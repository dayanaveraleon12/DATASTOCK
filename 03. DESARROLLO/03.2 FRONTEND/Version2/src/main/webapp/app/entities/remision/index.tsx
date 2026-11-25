import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Remision from './remision';
import RemisionDeleteDialog from './remision-delete-dialog';
import RemisionDetail from './remision-detail';
import RemisionUpdate from './remision-update';

const RemisionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Remision />} />
    <Route path="new" element={<RemisionUpdate />} />
    <Route path=":id">
      <Route index element={<RemisionDetail />} />
      <Route path="edit" element={<RemisionUpdate />} />
      <Route path="delete" element={<RemisionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RemisionRoutes;
