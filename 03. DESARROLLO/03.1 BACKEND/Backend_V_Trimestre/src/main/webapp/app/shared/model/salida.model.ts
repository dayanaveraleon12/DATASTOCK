import { ICuentaSitio } from 'app/shared/model/cuenta-sitio.model';
import { IEvidenciaSalida } from 'app/shared/model/evidencia-salida.model';
import { IMotivoSalida } from 'app/shared/model/motivo-salida.model';
import { IProductoSitio } from 'app/shared/model/producto-sitio.model';

export interface ISalida {
  id?: number;
  respaldoImagen?: string | null;
  cantidadASacar?: number;
  observaciones?: string | null;
  evidenciaSalida?: IEvidenciaSalida;
  cuentaSitio?: ICuentaSitio;
  motivoSalida?: IMotivoSalida;
  productoSitioses?: IProductoSitio[];
}

export const defaultValue: Readonly<ISalida> = {};
