package mx.com.sharkit.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoOferta.
 */
@Entity
@Table(name = "tipo_oferta")
public class TipoOferta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Size(max = 256)
    @Column(name = "descripcion", length = 256)
    private String descripcion;

    @OneToMany(mappedBy = "tipoOferta")
    private Set<OfertaProveedor> ofertaProveedors = new HashSet<>();

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

    public TipoOferta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoOferta descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<OfertaProveedor> getOfertaProveedors() {
        return ofertaProveedors;
    }

    public TipoOferta ofertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
        return this;
    }

    public TipoOferta addOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.add(ofertaProveedor);
        ofertaProveedor.setTipoOferta(this);
        return this;
    }

    public TipoOferta removeOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.remove(ofertaProveedor);
        ofertaProveedor.setTipoOferta(null);
        return this;
    }

    public void setOfertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoOferta)) {
            return false;
        }
        return id != null && id.equals(((TipoOferta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoOferta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
