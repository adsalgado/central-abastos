package mx.com.sharkit.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CarritoHistoricoDetalle.class)
public abstract class CarritoHistoricoDetalle_ {

	public static volatile SingularAttribute<CarritoHistoricoDetalle, BigDecimal> precio;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, CarritoHistorico> carritoHistorico;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, Long> productoProveedorId;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, Long> id;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, BigDecimal> cantidad;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, Long> carritoHistoricoId;
	public static volatile SingularAttribute<CarritoHistoricoDetalle, ProductoProveedor> productoProveedor;

	public static final String PRECIO = "precio";
	public static final String CARRITO_HISTORICO = "carritoHistorico";
	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String ID = "id";
	public static final String CANTIDAD = "cantidad";
	public static final String CARRITO_HISTORICO_ID = "carritoHistoricoId";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";

}

