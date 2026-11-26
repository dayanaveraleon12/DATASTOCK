import { ICategoria } from 'app/shared/model/categoria.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { IMarca } from 'app/shared/model/marca.model';
import { IPresentacion } from 'app/shared/model/presentacion.model';

export interface IProducto {
  id?: number;
  nombreEspecificacion?: string;
  estadoDisponibilidad?: keyof typeof Estado;
  categoria?: ICategoria;
  presentacion?: IPresentacion;
  marca?: IMarca;
}

export const defaultValue: Readonly<IProducto> = {};
