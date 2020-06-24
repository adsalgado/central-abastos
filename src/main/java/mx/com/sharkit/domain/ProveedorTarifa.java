package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A TransportistaTarifa.
 */
@Entity
@Table(name = "proveedor_tarifa")
public class ProveedorTarifa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rango_minimo", precision = 21, scale = 2, nullable = false)
    private BigDecimal rangoMinimo;

    @NotNull
    @Column(name = "rango_maximo", precision = 21, scale = 2, nullable = false)
    private BigDecimal rangoMaximo;

    @NotNull
    @Column(name = "porcentaje_comision", precision = 21, scale = 2, nullable = false)
    private BigDecimal porcentajeComision;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getRangoMinimo() {
		return rangoMinimo;
	}

	public void setRangoMinimo(BigDecimal rangoMinimo) {
		this.rangoMinimo = rangoMinimo;
	}

	public BigDecimal getRangoMaximo() {
		return rangoMaximo;
	}

	public void setRangoMaximo(BigDecimal rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

	public BigDecimal getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(BigDecimal porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProveedorTarifa)) {
            return false;
        }
        return id != null && id.equals(((ProveedorTarifa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "ProveedorTarifa [id=" + id + ", rangoMinimo=" + rangoMinimo + ", rangoMaximo=" + rangoMaximo
				+ ", porcentajeComision=" + porcentajeComision + "]";
	}

}
