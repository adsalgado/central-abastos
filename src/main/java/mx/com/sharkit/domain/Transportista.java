package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Transportista.
 */
@Entity
@Table(name = "transportista")
public class Transportista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioAlta;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioModificacion;

    @OneToMany(mappedBy = "transportista")
    private Set<TransportistaTarifa> transportistaTarifas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("transportistas")
    private Empresa empresa;

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

    public Transportista nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Transportista fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Transportista fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Transportista usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public Transportista usuarioModificacion(User user) {
        this.usuarioModificacion = user;
        return this;
    }

    public void setUsuarioModificacion(User user) {
        this.usuarioModificacion = user;
    }

    public Set<TransportistaTarifa> getTransportistaTarifas() {
        return transportistaTarifas;
    }

    public Transportista transportistaTarifas(Set<TransportistaTarifa> transportistaTarifas) {
        this.transportistaTarifas = transportistaTarifas;
        return this;
    }

    public Transportista addTransportistaTarifa(TransportistaTarifa transportistaTarifa) {
        this.transportistaTarifas.add(transportistaTarifa);
        transportistaTarifa.setTransportista(this);
        return this;
    }

    public Transportista removeTransportistaTarifa(TransportistaTarifa transportistaTarifa) {
        this.transportistaTarifas.remove(transportistaTarifa);
        transportistaTarifa.setTransportista(null);
        return this;
    }

    public void setTransportistaTarifas(Set<TransportistaTarifa> transportistaTarifas) {
        this.transportistaTarifas = transportistaTarifas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Transportista empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transportista)) {
            return false;
        }
        return id != null && id.equals(((Transportista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transportista{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
