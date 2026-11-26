import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/tipo-documento">
        Tipo Documento
      </MenuItem>
      <MenuItem icon="asterisk" to="/cuenta">
        Cuenta
      </MenuItem>
      <MenuItem icon="asterisk" to="/historial-error">
        Historial Error
      </MenuItem>
      <MenuItem icon="asterisk" to="/sitio">
        Sitio
      </MenuItem>
      <MenuItem icon="asterisk" to="/marca">
        Marca
      </MenuItem>
      <MenuItem icon="asterisk" to="/categoria">
        Categoria
      </MenuItem>
      <MenuItem icon="asterisk" to="/presentacion">
        Presentacion
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto">
        Producto
      </MenuItem>
      <MenuItem icon="asterisk" to="/lote">
        Lote
      </MenuItem>
      <MenuItem icon="asterisk" to="/estado-producto">
        Estado Producto
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-sitio">
        Producto Sitio
      </MenuItem>
      <MenuItem icon="asterisk" to="/traslado-producto-sitio">
        Traslado Producto Sitio
      </MenuItem>
      <MenuItem icon="asterisk" to="/cuenta-sitio">
        Cuenta Sitio
      </MenuItem>
      <MenuItem icon="asterisk" to="/codigo">
        Codigo
      </MenuItem>
      <MenuItem icon="asterisk" to="/remision">
        Remision
      </MenuItem>
      <MenuItem icon="asterisk" to="/traslado">
        Traslado
      </MenuItem>
      <MenuItem icon="asterisk" to="/motivo-salida">
        Motivo Salida
      </MenuItem>
      <MenuItem icon="asterisk" to="/evidencia-salida">
        Evidencia Salida
      </MenuItem>
      <MenuItem icon="asterisk" to="/salida">
        Salida
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
