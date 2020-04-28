package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "nombre", length = 256, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 512)
    @Column(name = "descripcion", length = 512, nullable = false)
    private String descripcion;

    @NotNull
    @Size(max = 512)
    @Column(name = "caracteristicas", length = 512, nullable = false)
    private String caracteristicas;

    @NotNull
    @Column(name = "precio_sin_iva", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioSinIva;

    @NotNull
    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "adjunto_id", insertable = false, updatable = false)
    private Adjunto adjunto;

    @Column(name = "adjunto_id")
    private Long adjuntoId;

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

    @ManyToOne
    @JoinColumn(name = "tipo_articulo_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("productos")
    private TipoArticulo tipoArticulo;

    @Column(name = "tipo_articulo_id")
    private Long tipoArticuloId;

    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("productos")
    private Estatus estatus;
    
    @Column(name = "estatus_id")
    private Long estatusId;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("productos")
    private UnidadMedida unidadMedida;

    @Column(name = "unidad_medida_id")
    private Long unidadMedidaId;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
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

	public Long getTipoArticuloId() {
		return tipoArticuloId;
	}

	public void setTipoArticuloId(Long tipoArticuloId) {
		this.tipoArticuloId = tipoArticuloId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public Long getUnidadMedidaId() {
		return unidadMedidaId;
	}

	public void setUnidadMedidaId(Long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}

	public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public Producto caracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public BigDecimal getPrecioSinIva() {
        return precioSinIva;
    }

    public Producto precioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
        return this;
    }

    public void setPrecioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Producto precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Producto fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Producto fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public Producto adjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
        return this;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Producto usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public Producto usuarioModificacion(User user) {
        this.usuarioModificacion = user;
        return this;
    }

    public void setUsuarioModificacion(User user) {
        this.usuarioModificacion = user;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    public Producto tipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
        return this;
    }

    public void setTipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public Producto estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public Producto unidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
        return this;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", caracteristicas='" + getCaracteristicas() + "'" +
            ", precioSinIva=" + getPrecioSinIva() +
            ", precio=" + getPrecio() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
