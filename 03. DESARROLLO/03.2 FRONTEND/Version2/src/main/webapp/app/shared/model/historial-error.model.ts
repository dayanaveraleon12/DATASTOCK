import dayjs from 'dayjs';

import { ICuenta } from 'app/shared/model/cuenta.model';

export interface IHistorialError {
  id?: number;
  nombreError?: string;
  mensaje?: string;
  fechaHora?: dayjs.Dayjs;
  cuenta?: ICuenta | null;
}

export const defaultValue: Readonly<IHistorialError> = {};
