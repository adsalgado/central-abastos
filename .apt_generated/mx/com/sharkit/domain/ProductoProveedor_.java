package mx.com.sharkit.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductoProveedor.class)
public abstract class ProductoProveedor_ {

	public static volatile SingularAttribute<ProductoProveedor, LocalDateTime> fechaModificacion;
	public static volatile SingularAttribute<ProductoProveedor, LocalDateTime> fechaAlta;
	public static volatile SingularAttribute<ProductoProveedor, Long> proveedorId;
	public static volatile SingularAttribute<ProductoProveedor, Long> usuarioAltaId;
	public static volatile SingularAttribute<ProductoProveedor, Long> productoId;
	public static volatile SingularAttribute<ProductoProveedor, Producto> producto;
	public static volatile SingularAttribute<ProductoProveedor, Long> estatusId;
	public static volatile SingularAttribute<ProductoProveedor, BigDecimal> precioSinIva;
	public static volatile SingularAttribute<ProductoProveedor, BigDecimal> precio;
	public static volatile SingularAttribute<ProductoProveedor, Long> usuarioModificacionId;
	public static volatile SingularAttribute<ProductoProveedor, Estatus> estatus;
	public static volatile SingularAttribute<ProductoProveedor, Proveedor> proveedor;
	public static volatile SingularAttribute<ProductoProveedor, Long> id;

	public static final String FECHA_MODIFICACION = "fechaModificacion";
	public static final String FECHA_ALTA = "fechaAlta";
	public static final String PROVEEDOR_ID = "proveedorId";
	public static final String USUARIO_ALTA_ID = "usuarioAltaId";
	public static final String PRODUCTO_ID = "productoId";
	public static final String PRODUCTO = "producto";
	public static final String ESTATUS_ID = "estatusId";
	public static final String PRECIO_SIN_IVA = "precioSinIva";
	public static final String PRECIO = "precio";
	public static final String USUARIO_MODIFICACION_ID = "usuarioModificacionId";
	public static final String ESTATUS = "estatus";
	public static final String PROVEEDOR = "proveedor";
	public static final String ID = "id";

}

