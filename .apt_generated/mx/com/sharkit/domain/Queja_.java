package mx.com.sharkit.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Queja.class)
public abstract class Queja_ {

	public static volatile SingularAttribute<Queja, Long> pedidoProveedorId;
	public static volatile SingularAttribute<Queja, Estatus> estatus;
	public static volatile SingularAttribute<Queja, LocalDateTime> fechaAlta;
	public static volatile SingularAttribute<Queja, PedidoProveedor> pedidoProveedor;
	public static volatile SingularAttribute<Queja, TipoUsuario> tipoUsuario;
	public static volatile SingularAttribute<Queja, User> usuario;
	public static volatile SingularAttribute<Queja, Long> id;
	public static volatile SingularAttribute<Queja, Long> estatusId;
	public static volatile SingularAttribute<Queja, Long> tipoUsuarioId;
	public static volatile SingularAttribute<Queja, Long> usuarioId;

	public static final String PEDIDO_PROVEEDOR_ID = "pedidoProveedorId";
	public static final String ESTATUS = "estatus";
	public static final String FECHA_ALTA = "fechaAlta";
	public static final String PEDIDO_PROVEEDOR = "pedidoProveedor";
	public static final String TIPO_USUARIO = "tipoUsuario";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String ESTATUS_ID = "estatusId";
	public static final String TIPO_USUARIO_ID = "tipoUsuarioId";
	public static final String USUARIO_ID = "usuarioId";

}

