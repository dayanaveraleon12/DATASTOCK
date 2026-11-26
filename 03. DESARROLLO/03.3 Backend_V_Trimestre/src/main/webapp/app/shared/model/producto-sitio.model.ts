import { IEstadoProducto } from 'app/shared/model/estado-producto.model';
import { ILote } from 'app/shared/model/lote.model';
import { IProducto } from 'app/shared/model/producto.model';
import { ISalida } from 'app/shared/model/salida.model';
import { ISitio } from 'app/shared/model/sitio.model';

export interface IProductoSitio {
  id?: number;
  cantidadSitio?: number;
  producto?: IProducto;
  sitio?: ISitio;
  lote?: ILote;
  estadoProducto?: IEstadoProducto;
  salidases?: ISalida[] | null;
}

export const defaultValue: Readonly<IProductoSitio> = {};
