import { ITime } from 'app/shared/model/time.model';
import { Posicao } from 'app/shared/model/enumerations/posicao.model';

export interface IJogador {
  id?: number;
  nome?: string | null;
  fotoContentType?: string | null;
  foto?: string | null;
  idade?: number | null;
  posicao?: Posicao | null;
  camisa?: number | null;
  numerodeGols?: number | null;
  time?: ITime | null;
}

export const defaultValue: Readonly<IJogador> = {};
