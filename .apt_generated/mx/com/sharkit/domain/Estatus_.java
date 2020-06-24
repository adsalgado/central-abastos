package mx.com.sharkit.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.com.sharkit.domain.enumeration.TipoEstatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estatus.class)
public abstract class Estatus_ {

	public static volatile SetAttribute<Estatus, OfertaProveedor> ofertaProveedors;
	public static volatile SetAttribute<Estatus, PedidoDetalle> pedidoDetalles;
	public static volatile SingularAttribute<Estatus, Long> id;
	public static volatile SetAttribute<Estatus, Pedido> pedidos;
	public static volatile SingularAttribute<Estatus, TipoEstatus> tipoEstatus;
	public static volatile SetAttribute<Estatus, Cliente> clientes;
	public static volatile SingularAttribute<Estatus, String> nombre;
	public static volatile SetAttribute<Estatus, Producto> productos;

	public static final String OFERTA_PROVEEDORS = "ofertaProveedors";
	public static final String PEDIDO_DETALLES = "pedidoDetalles";
	public static final String ID = "id";
	public static final String PEDIDOS = "pedidos";
	public static final String TIPO_ESTATUS = "tipoEstatus";
	public static final String CLIENTES = "clientes";
	public static final String NOMBRE = "nombre";
	public static final String PRODUCTOS = "productos";

}

