import { ICuenta } from 'app/shared/model/cuenta.model';
import { ISitio } from 'app/shared/model/sitio.model';

export interface ICuentaSitio {
  id?: number;
  cuenta?: ICuenta;
  sitio?: ISitio;
}

export const defaultValue: Readonly<ICuentaSitio> = {};
