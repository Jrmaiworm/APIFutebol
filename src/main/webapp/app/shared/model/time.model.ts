import dayjs from 'dayjs';
import { IJogador } from 'app/shared/model/jogador.model';
import { EstadoOrigem } from 'app/shared/model/enumerations/estado-origem.model';

export interface ITime {
  id?: number;
  emblemaContentType?: string | null;
  emblema?: string | null;
  uniformeContentType?: string | null;
  uniforme?: string | null;
  nomeClube?: string | null;
  titulosBrasileiro?: number | null;
  titulosEstadual?: number | null;
  titulosLibertadores?: number | null;
  titulosMundial?: number | null;
  maiorArtilheiro?: string | null;
  estadoOrigem?: EstadoOrigem | null;
  treinador?: string | null;
  presidente?: string | null;
  anoFundacao?: string | null;
  jogadors?: IJogador[] | null;
}

export const defaultValue: Readonly<ITime> = {};
