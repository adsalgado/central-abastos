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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "nombre", length = 256, nullable = false)
    private String nombre;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "direccion_id", insertable = false, updatable = false)
    private Direccion direccion;

    @Column(name = "direccion_id")
    private Long direccionId;

    @ManyToOne
    @JoinColumn(name = "usuario_alta_id", insertable = false, updatable = false)
    private User usuarioAlta;

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;
    
    @ManyToOne
    @JoinColumn(name = "usuario_modificacion_id", insertable = false, updatable = false)
    private User usuarioModificacion;

    @Column(name = "usuario_modificacion_id")
    private Long usuarioModificacionId;
    
    @OneToMany(mappedBy = "proveedor")
    private Set<OfertaProveedor> ofertaProveedors = new HashSet<>();

    @OneToMany(mappedBy = "proveedor")
    private Set<Inventario> inventarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("proveedors")
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

    public Proveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Proveedor fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Proveedor fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Proveedor usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public Proveedor usuarioModificacion(User user) {
        this.usuarioModificacion = user;
        return this;
    }

    public void setUsuarioModificacion(User user) {
        this.usuarioModificacion = user;
    }

    public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Long getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Long direccionId) {
		this.direccionId = direccionId;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

    public Set<OfertaProveedor> getOfertaProveedors() {
        return ofertaProveedors;
    }

    public Proveedor ofertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
        return this;
    }

    public Proveedor addOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.add(ofertaProveedor);
        ofertaProveedor.setProveedor(this);
        return this;
    }

    public Proveedor removeOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.remove(ofertaProveedor);
        ofertaProveedor.setProveedor(null);
        return this;
    }

    public void setOfertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
    }

    public Set<Inventario> getInventarios() {
        return inventarios;
    }

    public Proveedor inventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
        return this;
    }

    public Proveedor addInventario(Inventario inventario) {
        this.inventarios.add(inventario);
        inventario.setProveedor(this);
        return this;
    }

    public Proveedor removeInventario(Inventario inventario) {
        this.inventarios.remove(inventario);
        inventario.setProveedor(null);
        return this;
    }

    public void setInventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Proveedor empresa(Empresa empresa) {
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
        if (!(o instanceof Proveedor)) {
            return false;
        }
        return id != null && id.equals(((Proveedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
