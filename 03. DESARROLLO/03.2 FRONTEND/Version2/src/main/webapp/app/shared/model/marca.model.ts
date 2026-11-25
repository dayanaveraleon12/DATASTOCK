import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IMarca {
  id?: number;
  marca?: string;
  estadoMarca?: keyof typeof Estado;
}

export const defaultValue: Readonly<IMarca> = {};
