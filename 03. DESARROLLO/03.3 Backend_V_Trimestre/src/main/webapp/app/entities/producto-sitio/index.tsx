import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProductoSitio from './producto-sitio';
import ProductoSitioDeleteDialog from './producto-sitio-delete-dialog';
import ProductoSitioDetail from './producto-sitio-detail';
import ProductoSitioUpdate from './producto-sitio-update';

const ProductoSitioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProductoSitio />} />
    <Route path="new" element={<ProductoSitioUpdate />} />
    <Route path=":id">
      <Route index element={<ProductoSitioDetail />} />
      <Route path="edit" element={<ProductoSitioUpdate />} />
      <Route path="delete" element={<ProductoSitioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductoSitioRoutes;
