import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CuentaSitio from './cuenta-sitio';
import CuentaSitioDeleteDialog from './cuenta-sitio-delete-dialog';
import CuentaSitioDetail from './cuenta-sitio-detail';
import CuentaSitioUpdate from './cuenta-sitio-update';

const CuentaSitioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CuentaSitio />} />
    <Route path="new" element={<CuentaSitioUpdate />} />
    <Route path=":id">
      <Route index element={<CuentaSitioDetail />} />
      <Route path="edit" element={<CuentaSitioUpdate />} />
      <Route path="delete" element={<CuentaSitioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CuentaSitioRoutes;
