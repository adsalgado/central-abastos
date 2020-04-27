package mx.com.sharkit.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
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

/**
 * A TipoArticulo.
 */
@Entity
@Table(name = "tipo_articulo")
public class TipoArticulo implements Serializable {

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

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @Column(name = "usuario_modificacion_id")
    private Long usuarioModificacionId;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    private Categoria categoria;
    
    @Column(name = "categoria_id")
    private Long categoriaId;
    
    @Column(name = "adjunto_id")
    private Long adjuntoId;

    @OneToMany(mappedBy = "tipoArticulo")
    private Set<Producto> productos = new HashSet<>();

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

    public TipoArticulo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public TipoArticulo productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public TipoArticulo addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setTipoArticulo(this);
        return this;
    }

    public TipoArticulo removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setTipoArticulo(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoArticulo)) {
            return false;
        }
        return id != null && id.equals(((TipoArticulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "TipoArticulo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", usuarioAltaId="
				+ usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId + ", fechaAlta=" + fechaAlta
				+ ", fechaModificacion=" + fechaModificacion + ", categoriaId=" + categoriaId + ", adjuntoId="
				+ adjuntoId + "]";
	}

   
}
