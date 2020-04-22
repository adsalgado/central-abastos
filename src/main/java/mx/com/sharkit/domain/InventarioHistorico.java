package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import mx.com.sharkit.domain.enumeration.TipoMovimiento;

/**
 * A InventarioHistorico.
 */
@Entity
@Table(name = "inventario_historico")
public class InventarioHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @NotNull
    @Column(name = "cantidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @NotNull
    @Column(name = "total_anterior", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAnterior;

    @NotNull
    @Column(name = "total_final", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalFinal;

    @NotNull
    @Column(name = "fecha_movimiento", nullable = false)
    private Instant fechaMovimiento;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioMovimiento;

    @ManyToOne
    @JsonIgnoreProperties("inventarioHistoricos")
    private Inventario inventario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public InventarioHistorico tipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return this;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public InventarioHistorico cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalAnterior() {
        return totalAnterior;
    }

    public InventarioHistorico totalAnterior(BigDecimal totalAnterior) {
        this.totalAnterior = totalAnterior;
        return this;
    }

    public void setTotalAnterior(BigDecimal totalAnterior) {
        this.totalAnterior = totalAnterior;
    }

    public BigDecimal getTotalFinal() {
        return totalFinal;
    }

    public InventarioHistorico totalFinal(BigDecimal totalFinal) {
        this.totalFinal = totalFinal;
        return this;
    }

    public void setTotalFinal(BigDecimal totalFinal) {
        this.totalFinal = totalFinal;
    }

    public Instant getFechaMovimiento() {
        return fechaMovimiento;
    }

    public InventarioHistorico fechaMovimiento(Instant fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
        return this;
    }

    public void setFechaMovimiento(Instant fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public User getUsuarioMovimiento() {
        return usuarioMovimiento;
    }

    public InventarioHistorico usuarioMovimiento(User user) {
        this.usuarioMovimiento = user;
        return this;
    }

    public void setUsuarioMovimiento(User user) {
        this.usuarioMovimiento = user;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public InventarioHistorico inventario(Inventario inventario) {
        this.inventario = inventario;
        return this;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventarioHistorico)) {
            return false;
        }
        return id != null && id.equals(((InventarioHistorico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InventarioHistorico{" +
            "id=" + getId() +
            ", tipoMovimiento='" + getTipoMovimiento() + "'" +
            ", cantidad=" + getCantidad() +
            ", totalAnterior=" + getTotalAnterior() +
            ", totalFinal=" + getTotalFinal() +
            ", fechaMovimiento='" + getFechaMovimiento() + "'" +
            "}";
    }
}
