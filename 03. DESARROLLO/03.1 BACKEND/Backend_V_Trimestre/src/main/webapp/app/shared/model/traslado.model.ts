import { ICodigo } from 'app/shared/model/codigo.model';
import { ICuentaSitio } from 'app/shared/model/cuenta-sitio.model';
import { ISitio } from 'app/shared/model/sitio.model';

export interface ITraslado {
  id?: number;
  descripcion?: string | null;
  cuentaSitio?: ICuentaSitio;
  sitioDestino?: ISitio;
  codigo?: ICodigo;
}

export const defaultValue: Readonly<ITraslado> = {};
