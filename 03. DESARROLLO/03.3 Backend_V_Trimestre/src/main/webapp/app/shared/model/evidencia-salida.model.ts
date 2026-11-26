import dayjs from 'dayjs';

import { ICuenta } from 'app/shared/model/cuenta.model';
import { ISitio } from 'app/shared/model/sitio.model';

export interface IEvidenciaSalida {
  id?: number;
  archivoExcel?: string;
  nombreExcel?: string;
  fechaCarga?: dayjs.Dayjs;
  cuenta?: ICuenta;
  sitioSubida?: ISitio;
}

export const defaultValue: Readonly<IEvidenciaSalida> = {};
