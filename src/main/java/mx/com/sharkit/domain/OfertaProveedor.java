package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A OfertaProveedor.
 */
@Entity
@Table(name = "oferta_proveedor")
public class OfertaProveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    private ProductoProveedor productoProveedor;

    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("ofertaProveedors")
    private Estatus estatus;

    @Column(name = "estatus_id")
    private Long estatusId;

    @ManyToOne
    @JoinColumn(name = "tipo_oferta_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("ofertaProveedors")
    private TipoOferta tipoOferta;

    @Column(name = "tipo_oferta_id")
    private Long tipoOfertaId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public OfertaProveedor fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public OfertaProveedor fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public OfertaProveedor estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public TipoOferta getTipoOferta() {
        return tipoOferta;
    }

    public OfertaProveedor tipoOferta(TipoOferta tipoOferta) {
        this.tipoOferta = tipoOferta;
        return this;
    }

    public void setTipoOferta(TipoOferta tipoOferta) {
        this.tipoOferta = tipoOferta;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ProductoProveedor getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedor productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public Long getTipoOfertaId() {
		return tipoOfertaId;
	}

	public void setTipoOfertaId(Long tipoOfertaId) {
		this.tipoOfertaId = tipoOfertaId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfertaProveedor)) {
            return false;
        }
        return id != null && id.equals(((OfertaProveedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "OfertaProveedor [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", productoProveedorId=" + productoProveedorId + ", estatusId=" + estatusId + ", tipoOfertaId="
				+ tipoOfertaId + "]";
	}

}
