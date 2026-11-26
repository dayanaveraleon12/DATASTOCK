import categoria from 'app/entities/categoria/categoria.reducer';
import trasladoProductoSitio from 'app/entities/traslado-producto-sitio/traslado-producto-sitio.reducer';
import cuentaSitio from 'app/entities/cuenta-sitio/cuenta-sitio.reducer';
import codigo from 'app/entities/codigo/codigo.reducer';
import cuenta from 'app/entities/cuenta/cuenta.reducer';
import estadoProducto from 'app/entities/estado-producto/estado-producto.reducer';
import remision from 'app/entities/remision/remision.reducer';
import evidenciaSalida from 'app/entities/evidencia-salida/evidencia-salida.reducer';
import historialError from 'app/entities/historial-error/historial-error.reducer';
import lote from 'app/entities/lote/lote.reducer';
import marca from 'app/entities/marca/marca.reducer';
import motivoSalida from 'app/entities/motivo-salida/motivo-salida.reducer';
import presentacion from 'app/entities/presentacion/presentacion.reducer';
import producto from 'app/entities/producto/producto.reducer';
import productoSitio from 'app/entities/producto-sitio/producto-sitio.reducer';
import salida from 'app/entities/salida/salida.reducer';
import sitio from 'app/entities/sitio/sitio.reducer';
import tipoDocumento from 'app/entities/tipo-documento/tipo-documento.reducer';
import traslado from 'app/entities/traslado/traslado.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  tipoDocumento,
  cuenta,
  historialError,
  sitio,
  marca,
  categoria,
  presentacion,
  producto,
  lote,
  estadoProducto,
  productoSitio,
  trasladoProductoSitio,
  cuentaSitio,
  codigo,
  remision,
  traslado,
  motivoSalida,
  evidenciaSalida,
  salida,
  // jhipster-needle-add-reducer-combine - JHipster will add reducer here
};

export default entitiesReducers;
