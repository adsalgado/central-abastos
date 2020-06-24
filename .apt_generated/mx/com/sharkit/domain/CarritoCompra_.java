package mx.com.sharkit.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CarritoCompra.class)
public abstract class CarritoCompra_ {

	public static volatile SingularAttribute<CarritoCompra, User> cliente;
	public static volatile SingularAttribute<CarritoCompra, BigDecimal> precio;
	public static volatile SingularAttribute<CarritoCompra, LocalDateTime> fechaAlta;
	public static volatile SingularAttribute<CarritoCompra, Long> clienteId;
	public static volatile SingularAttribute<CarritoCompra, Long> productoProveedorId;
	public static volatile SingularAttribute<CarritoCompra, Long> id;
	public static volatile SingularAttribute<CarritoCompra, BigDecimal> cantidad;
	public static volatile SingularAttribute<CarritoCompra, ProductoProveedor> productoProveedor;

	public static final String CLIENTE = "cliente";
	public static final String PRECIO = "precio";
	public static final String FECHA_ALTA = "fechaAlta";
	public static final String CLIENTE_ID = "clienteId";
	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String ID = "id";
	public static final String CANTIDAD = "cantidad";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";

}

