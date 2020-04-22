package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A RecolectorTarifa.
 */
@Entity
@Table(name = "recolector_tarifa")
public class RecolectorTarifa implements Serializable {

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
    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;

    @ManyToOne
    @JsonIgnoreProperties("recolectorTarifas")
    private Recolector recolector;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRangoMinimo() {
        return rangoMinimo;
    }

    public RecolectorTarifa rangoMinimo(BigDecimal rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
        return this;
    }

    public void setRangoMinimo(BigDecimal rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
    }

    public BigDecimal getRangoMaximo() {
        return rangoMaximo;
    }

    public RecolectorTarifa rangoMaximo(BigDecimal rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
        return this;
    }

    public void setRangoMaximo(BigDecimal rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public RecolectorTarifa precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Recolector getRecolector() {
        return recolector;
    }

    public RecolectorTarifa recolector(Recolector recolector) {
        this.recolector = recolector;
        return this;
    }

    public void setRecolector(Recolector recolector) {
        this.recolector = recolector;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecolectorTarifa)) {
            return false;
        }
        return id != null && id.equals(((RecolectorTarifa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RecolectorTarifa{" +
            "id=" + getId() +
            ", rangoMinimo=" + getRangoMinimo() +
            ", rangoMaximo=" + getRangoMaximo() +
            ", precio=" + getPrecio() +
            "}";
    }
}
