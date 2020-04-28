package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A ProductoImagen.
 */
@Entity
@Table(name = "producto_imagen")
public class ProductoImagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioAlta;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("productoImagens")
    private ProductoProveedor productoProveedor;

    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @ManyToOne
    @JoinColumn(name = "adjunto_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("productoImagens")
    private Adjunto adjunto;

    @Column(name = "adjunto_id")
    private Long adjuntoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public ProductoImagen fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public ProductoImagen usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public ProductoImagen adjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
        return this;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }
    
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
        if (!(o instanceof ProductoImagen)) {
            return false;
        }
        return id != null && id.equals(((ProductoImagen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "ProductoImagen [id=" + id + ", fechaAlta=" + fechaAlta + ", usuarioAlta=" + usuarioAlta
				+ ", productoProveedorId=" + productoProveedorId + ", adjuntoId=" + adjuntoId + "]";
	}

}
