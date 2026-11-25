import { ICodigo } from 'app/shared/model/codigo.model';
import { IProductoSitio } from 'app/shared/model/producto-sitio.model';
import { ISitio } from 'app/shared/model/sitio.model';

export interface IRemision {
  id?: number;
  cantidadRemitida?: number;
  productoSitio?: IProductoSitio;
  codigo?: ICodigo;
  sitioDestino?: ISitio;
}

export const defaultValue: Readonly<IRemision> = {};
