package mx.com.sharkit.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Empresa.class)
public abstract class Empresa_ {

	public static volatile SetAttribute<Empresa, Transportista> transportistas;
	public static volatile SetAttribute<Empresa, Proveedor> proveedors;
	public static volatile SetAttribute<Empresa, Recolector> recolectors;
	public static volatile SingularAttribute<Empresa, Long> id;
	public static volatile SetAttribute<Empresa, Cliente> clientes;
	public static volatile SingularAttribute<Empresa, String> nombre;
	public static volatile SetAttribute<Empresa, Seccion> seccions;

	public static final String TRANSPORTISTAS = "transportistas";
	public static final String PROVEEDORS = "proveedors";
	public static final String RECOLECTORS = "recolectors";
	public static final String ID = "id";
	public static final String CLIENTES = "clientes";
	public static final String NOMBRE = "nombre";
	public static final String SECCIONS = "seccions";

}

