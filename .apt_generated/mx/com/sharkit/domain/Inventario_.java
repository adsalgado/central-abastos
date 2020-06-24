package mx.com.sharkit.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Inventario.class)
public abstract class Inventario_ {

	public static volatile SingularAttribute<Inventario, BigDecimal> inventarioMinimo;
	public static volatile SingularAttribute<Inventario, BigDecimal> total;
	public static volatile SingularAttribute<Inventario, Long> productoProveedorId;
	public static volatile SingularAttribute<Inventario, BigDecimal> inventarioMaximo;
	public static volatile SingularAttribute<Inventario, Long> id;
	public static volatile SingularAttribute<Inventario, ProductoProveedor> productoProveedor;

	public static final String INVENTARIO_MINIMO = "inventarioMinimo";
	public static final String TOTAL = "total";
	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String INVENTARIO_MAXIMO = "inventarioMaximo";
	public static final String ID = "id";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";

}

