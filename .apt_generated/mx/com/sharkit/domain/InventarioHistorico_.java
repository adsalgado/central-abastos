package mx.com.sharkit.domain;

import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.com.sharkit.domain.enumeration.TipoMovimiento;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InventarioHistorico.class)
public abstract class InventarioHistorico_ {

	public static volatile SingularAttribute<InventarioHistorico, BigDecimal> totalAnterior;
	public static volatile SingularAttribute<InventarioHistorico, Long> id;
	public static volatile SingularAttribute<InventarioHistorico, TipoMovimiento> tipoMovimiento;
	public static volatile SingularAttribute<InventarioHistorico, BigDecimal> cantidad;
	public static volatile SingularAttribute<InventarioHistorico, Instant> fechaMovimiento;
	public static volatile SingularAttribute<InventarioHistorico, BigDecimal> totalFinal;
	public static volatile SingularAttribute<InventarioHistorico, Inventario> inventario;
	public static volatile SingularAttribute<InventarioHistorico, User> usuarioMovimiento;

	public static final String TOTAL_ANTERIOR = "totalAnterior";
	public static final String ID = "id";
	public static final String TIPO_MOVIMIENTO = "tipoMovimiento";
	public static final String CANTIDAD = "cantidad";
	public static final String FECHA_MOVIMIENTO = "fechaMovimiento";
	public static final String TOTAL_FINAL = "totalFinal";
	public static final String INVENTARIO = "inventario";
	public static final String USUARIO_MOVIMIENTO = "usuarioMovimiento";

}

