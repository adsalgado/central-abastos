package mx.com.sharkit.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductoImagen.class)
public abstract class ProductoImagen_ {

	public static volatile SingularAttribute<ProductoImagen, Instant> fechaAlta;
	public static volatile SingularAttribute<ProductoImagen, Long> productoProveedorId;
	public static volatile SingularAttribute<ProductoImagen, Long> adjuntoId;
	public static volatile SingularAttribute<ProductoImagen, Adjunto> adjunto;
	public static volatile SingularAttribute<ProductoImagen, Long> id;
	public static volatile SingularAttribute<ProductoImagen, User> usuarioAlta;
	public static volatile SingularAttribute<ProductoImagen, ProductoProveedor> productoProveedor;

	public static final String FECHA_ALTA = "fechaAlta";
	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String ADJUNTO_ID = "adjuntoId";
	public static final String ADJUNTO = "adjunto";
	public static final String ID = "id";
	public static final String USUARIO_ALTA = "usuarioAlta";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";

}

