package mx.com.sharkit.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OfertaProveedor.class)
public abstract class OfertaProveedor_ {

	public static volatile SingularAttribute<OfertaProveedor, Estatus> estatus;
	public static volatile SingularAttribute<OfertaProveedor, LocalDate> fechaInicio;
	public static volatile SingularAttribute<OfertaProveedor, Long> productoProveedorId;
	public static volatile SingularAttribute<OfertaProveedor, Long> tipoOfertaId;
	public static volatile SingularAttribute<OfertaProveedor, Long> id;
	public static volatile SingularAttribute<OfertaProveedor, Long> estatusId;
	public static volatile SingularAttribute<OfertaProveedor, LocalDate> fechaFin;
	public static volatile SingularAttribute<OfertaProveedor, ProductoProveedor> productoProveedor;
	public static volatile SingularAttribute<OfertaProveedor, TipoOferta> tipoOferta;

	public static final String ESTATUS = "estatus";
	public static final String FECHA_INICIO = "fechaInicio";
	public static final String PRODUCTO_PROVEEDOR_ID = "productoProveedorId";
	public static final String TIPO_OFERTA_ID = "tipoOfertaId";
	public static final String ID = "id";
	public static final String ESTATUS_ID = "estatusId";
	public static final String FECHA_FIN = "fechaFin";
	public static final String PRODUCTO_PROVEEDOR = "productoProveedor";
	public static final String TIPO_OFERTA = "tipoOferta";

}

