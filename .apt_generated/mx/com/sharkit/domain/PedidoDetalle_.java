package mx.com.sharkit.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PedidoDetalle.class)
public abstract class PedidoDetalle_ {

	public static volatile SingularAttribute<PedidoDetalle, Long> productoProveedorId;
	public static volatile SingularAttribute<PedidoDetalle, Long> estatusId;
	public static volatile SingularAttribute<PedidoDetalle, BigDecimal> totalSinIva;
	public static volatile SingularAttribute<PedidoDetalle, ProductoProveedor> productoProveedor;
	public static volatile SingularAttribute<PedidoDetalle, BigDecimal> precioSinIva;
	public static volatile SingularAttribute<PedidoDetalle, BigDecimal> total;
	public static volatile SingularAttribute<PedidoDetalle, BigDecimal> precio;
	public static volatile SingularAttribute<PedidoDetalle, Long> pedidoProveedorId;
	public static volatile SingularAttribute<PedidoDetalle, Estatus> estatus;
	public static volatile SingularAttribute<PedidoDetalle, PedidoProveedor> pedidoProveedor;
	public static volatile SingularAttribute<PedidoDetalle, String> folio;
	public static volatile SingularAttribute<PedidoDetalle, Long> id;
	public static volatile SingularAttribute<PedidoDetalle, BigDecimal> cantidad;

	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String ESTATUS_ID = "estatusId";
	public static final String TOTAL_SIN_IVA = "totalSinIva";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";
	public static final String PRECIO_SIN_IVA = "precioSinIva";
	public static final String TOTAL = "total";
	public static final String PRECIO = "precio";
	public static final String PEDIDO_PROVEEDOR_ID = "pedidoProveedorId";
	public static final String ESTATUS = "estatus";
	public static final String PEDIDO_PROVEEDOR = "pedidoProveedor";
	public static final String FOLIO = "folio";
	public static final String ID = "id";
	public static final String CANTIDAD = "cantidad";

}

