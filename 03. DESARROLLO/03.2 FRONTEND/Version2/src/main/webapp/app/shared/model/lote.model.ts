import dayjs from 'dayjs';

import { IProducto } from 'app/shared/model/producto.model';

export interface ILote {
  id?: number;
  fechaIngreso?: dayjs.Dayjs;
  fechaVencimiento?: dayjs.Dayjs;
  codigoLote?: string;
  descripcion?: string | null;
  facturaProveedor?: string;
  cantidadIngresada?: number;
  producto?: IProducto;
}

export const defaultValue: Readonly<ILote> = {};
