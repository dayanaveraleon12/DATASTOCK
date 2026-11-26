import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { IUser } from 'app/shared/model/user.model';

export interface ICuenta {
  id?: number;
  numeroDocumento?: string;
  primerNombre?: string;
  segundoNombre?: string | null;
  primerApellido?: string;
  segundoApellido?: string | null;
  numeroCelular?: string;
  eps?: string;
  contactoEmergencia?: string;
  parentezcoEmergencia?: string;
  nombreEmergencia?: string;
  user?: IUser;
  tipoDocumento?: ITipoDocumento;
}

export const defaultValue: Readonly<ICuenta> = {};
