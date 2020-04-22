package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CarritoHistorico.
 */
@Entity
@Table(name = "carrito_historico")
public class CarritoHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @OneToMany(mappedBy = "carritoHistorico")
    private Set<CarritoHistoricoDetalle> carritoHistoricoDetalles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("carritoHistoricos")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public CarritoHistorico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public CarritoHistorico fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<CarritoHistoricoDetalle> getCarritoHistoricoDetalles() {
        return carritoHistoricoDetalles;
    }

    public CarritoHistorico carritoHistoricoDetalles(Set<CarritoHistoricoDetalle> carritoHistoricoDetalles) {
        this.carritoHistoricoDetalles = carritoHistoricoDetalles;
        return this;
    }

    public CarritoHistorico addCarritoHistoricoDetalle(CarritoHistoricoDetalle carritoHistoricoDetalle) {
        this.carritoHistoricoDetalles.add(carritoHistoricoDetalle);
        carritoHistoricoDetalle.setCarritoHistorico(this);
        return this;
    }

    public CarritoHistorico removeCarritoHistoricoDetalle(CarritoHistoricoDetalle carritoHistoricoDetalle) {
        this.carritoHistoricoDetalles.remove(carritoHistoricoDetalle);
        carritoHistoricoDetalle.setCarritoHistorico(null);
        return this;
    }

    public void setCarritoHistoricoDetalles(Set<CarritoHistoricoDetalle> carritoHistoricoDetalles) {
        this.carritoHistoricoDetalles = carritoHistoricoDetalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CarritoHistorico cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarritoHistorico)) {
            return false;
        }
        return id != null && id.equals(((CarritoHistorico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CarritoHistorico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            "}";
    }
}
