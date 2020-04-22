import { IChat } from 'app/shared/model/chat.model';
import { IProductoImagen } from 'app/shared/model/producto-imagen.model';
import { IUsuarioImagen } from 'app/shared/model/usuario-imagen.model';
import { IParametrosAplicacion } from 'app/shared/model/parametros-aplicacion.model';

export interface IAdjunto {
  id?: number;
  contentType?: string;
  size?: number;
  fileName?: string;
  fileContentType?: string;
  file?: any;
  chats?: IChat[];
  productoImagens?: IProductoImagen[];
  usuarioImagens?: IUsuarioImagen[];
  parametrosAplicacions?: IParametrosAplicacion[];
}

export class Adjunto implements IAdjunto {
  constructor(
    public id?: number,
    public contentType?: string,
    public size?: number,
    public fileName?: string,
    public fileContentType?: string,
    public file?: any,
    public chats?: IChat[],
    public productoImagens?: IProductoImagen[],
    public usuarioImagens?: IUsuarioImagen[],
    public parametrosAplicacions?: IParametrosAplicacion[]
  ) {}
}
