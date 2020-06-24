package mx.com.sharkit.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Chat.class)
public abstract class Chat_ {

	public static volatile SingularAttribute<Chat, LocalDateTime> fecha;
	public static volatile SingularAttribute<Chat, Long> pedidoProveedorId;
	public static volatile SingularAttribute<Chat, String> usuarioEmisorLogin;
	public static volatile SingularAttribute<Chat, PedidoProveedor> pedidoProveedor;
	public static volatile SingularAttribute<Chat, String> usuarioReceptorLogin;
	public static volatile SingularAttribute<Chat, Long> id;
	public static volatile SingularAttribute<Chat, Long> tipoChatId;
	public static volatile SingularAttribute<Chat, TipoChat> tipoChat;

	public static final String FECHA = "fecha";
	public static final String PEDIDO_PROVEEDOR_ID = "pedidoProveedorId";
	public static final String USUARIO_EMISOR_LOGIN = "usuarioEmisorLogin";
	public static final String PEDIDO_PROVEEDOR = "pedidoProveedor";
	public static final String USUARIO_RECEPTOR_LOGIN = "usuarioReceptorLogin";
	public static final String ID = "id";
	public static final String TIPO_CHAT_ID = "tipoChatId";
	public static final String TIPO_CHAT = "tipoChat";

}

