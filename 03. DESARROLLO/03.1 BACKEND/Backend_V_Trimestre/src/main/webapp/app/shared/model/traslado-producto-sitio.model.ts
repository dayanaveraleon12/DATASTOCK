import { IProductoSitio } from 'app/shared/model/producto-sitio.model';
import { ITraslado } from 'app/shared/model/traslado.model';

export interface ITrasladoProductoSitio {
  id?: number;
  cantidadTraslado?: number;
  traslado?: ITraslado;
  productoSitio?: IProductoSitio;
}

export const defaultValue: Readonly<ITrasladoProductoSitio> = {};
