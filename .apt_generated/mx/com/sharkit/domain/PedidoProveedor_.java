package mx.com.sharkit.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PedidoProveedor.class)
public abstract class PedidoProveedor_ {

	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> distanciaEntregaKm;
	public static volatile SingularAttribute<PedidoProveedor, Long> proveedorId;
	public static volatile SingularAttribute<PedidoProveedor, Integer> calificacionServicio;
	public static volatile SingularAttribute<PedidoProveedor, Long> estatusId;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> totalSinIva;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> comisionTransportista;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> comisionPreparador;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> total;
	public static volatile SingularAttribute<PedidoProveedor, String> personaEntrega;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> porcentajeComisionProveedor;
	public static volatile SingularAttribute<PedidoProveedor, Pedido> pedido;
	public static volatile SingularAttribute<PedidoProveedor, LocalDateTime> fechaEntrega;
	public static volatile SingularAttribute<PedidoProveedor, String> depositadoProveedor;
	public static volatile SingularAttribute<PedidoProveedor, Long> id;
	public static volatile SingularAttribute<PedidoProveedor, String> comentarios;
	public static volatile SingularAttribute<PedidoProveedor, Long> chatProveedorid;
	public static volatile SingularAttribute<PedidoProveedor, LocalDateTime> fechaModificacion;
	public static volatile SingularAttribute<PedidoProveedor, Long> recolectorId;
	public static volatile SingularAttribute<PedidoProveedor, LocalDateTime> fechaAlta;
	public static volatile SingularAttribute<PedidoProveedor, Long> usuarioAltaId;
	public static volatile SingularAttribute<PedidoProveedor, String> depositadoTransportista;
	public static volatile SingularAttribute<PedidoProveedor, String> token;
	public static volatile SingularAttribute<PedidoProveedor, Long> usuarioModificacionId;
	public static volatile SingularAttribute<PedidoProveedor, LocalDateTime> fechaPreparacion;
	public static volatile SingularAttribute<PedidoProveedor, LocalDateTime> fechaEnvio;
	public static volatile SingularAttribute<PedidoProveedor, Long> pedidoId;
	public static volatile SingularAttribute<PedidoProveedor, Estatus> estatus;
	public static volatile SingularAttribute<PedidoProveedor, Long> chatTransportistaId;
	public static volatile SingularAttribute<PedidoProveedor, String> folio;
	public static volatile SingularAttribute<PedidoProveedor, Proveedor> proveedor;
	public static volatile SingularAttribute<PedidoProveedor, BigDecimal> comisionProveedor;
	public static volatile SingularAttribute<PedidoProveedor, Long> transportistaId;

	public static final String DISTANCIA_ENTREGA_KM = "distanciaEntregaKm";
	public static final String PROVEEDOR_ID = "proveedorId";
	public static final String CALIFICACION_SERVICIO = "calificacionServicio";
	public static final String ESTATUS_ID = "estatusId";
	public static final String TOTAL_SIN_IVA = "totalSinIva";
	public static final String COMISION_TRANSPORTISTA = "comisionTransportista";
	public static final String COMISION_PREPARADOR = "comisionPreparador";
	public static final String TOTAL = "total";
	public static final String PERSONA_ENTREGA = "personaEntrega";
	public static final String PORCENTAJE_COMISION_PROVEEDOR = "porcentajeComisionProveedor";
	public static final String PEDIDO = "pedido";
	public static final String FECHA_ENTREGA = "fechaEntrega";
	public static final String DEPOSITADO_PROVEEDOR = "depositadoProveedor";
	public static final String ID = "id";
	public static final String COMENTARIOS = "comentarios";
	public static final String CHAT_PROVEEDORID = "chatProveedorid";
	public static final String FECHA_MODIFICACION = "fechaModificacion";
	public static final String RECOLECTOR_ID = "recolectorId";
	public static final String FECHA_ALTA = "fechaAlta";
	public static final String USUARIO_ALTA_ID = "usuarioAltaId";
	public static final String DEPOSITADO_TRANSPORTISTA = "depositadoTransportista";
	public static final String TOKEN = "token";
	public static final String USUARIO_MODIFICACION_ID = "usuarioModificacionId";
	public static final String FECHA_PREPARACION = "fechaPreparacion";
	public static final String FECHA_ENVIO = "fechaEnvio";
	public static final String PEDIDO_ID = "pedidoId";
	public static final String ESTATUS = "estatus";
	public static final String CHAT_TRANSPORTISTA_ID = "chatTransportistaId";
	public static final String FOLIO = "folio";
	public static final String PROVEEDOR = "proveedor";
	public static final String COMISION_PROVEEDOR = "comisionProveedor";
	public static final String TRANSPORTISTA_ID = "transportistaId";

}

