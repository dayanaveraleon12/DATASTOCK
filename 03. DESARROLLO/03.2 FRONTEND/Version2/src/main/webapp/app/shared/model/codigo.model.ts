import dayjs from 'dayjs';

import { ICuentaSitio } from 'app/shared/model/cuenta-sitio.model';
import { EstadoOperacion } from 'app/shared/model/enumerations/estado-operacion.model';

export interface ICodigo {
  id?: number;
  fechaRemision?: dayjs.Dayjs;
  codigo?: string;
  estadoOperacion?: keyof typeof EstadoOperacion;
  cuentaSitio?: ICuentaSitio;
}

export const defaultValue: Readonly<ICodigo> = {};
