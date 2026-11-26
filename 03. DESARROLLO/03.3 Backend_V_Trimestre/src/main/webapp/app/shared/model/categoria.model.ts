import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  estadoCategoria?: keyof typeof Estado;
}

export const defaultValue: Readonly<ICategoria> = {};
