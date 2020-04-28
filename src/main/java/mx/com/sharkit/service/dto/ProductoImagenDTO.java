package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.ProductoImagen} entity.
 */
public class ProductoImagenDTO implements Serializable {

    private Long id;

    private Instant fechaAlta;


    private Long usuarioAltaId;

    private ProductoProveedorDTO productoProveedor;

    private Long productoProveedorId;

    private Long adjuntoId;
    
    private AdjuntoDTO adjunto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getUsuarioAltaId() {
        return usuarioAltaId;
    }

    public void setUsuarioAltaId(Long userId) {
        this.usuarioAltaId = userId;
    }

    public Long getAdjuntoId() {
        return adjuntoId;
    }

    public void setAdjuntoId(Long adjuntoId) {
        this.adjuntoId = adjuntoId;
    }

    public AdjuntoDTO getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(AdjuntoDTO adjunto) {
		this.adjunto = adjunto;
	}

	public ProductoProveedorDTO getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedorDTO productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoImagenDTO productoImagenDTO = (ProductoImagenDTO) o;
        if (productoImagenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoImagenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ProductoImagenDTO [id=" + id + ", fechaAlta=" + fechaAlta + ", usuarioAltaId=" + usuarioAltaId
				+ ", productoProveedorId=" + productoProveedorId + ", adjuntoId=" + adjuntoId + ", adjunto=" + adjunto
				+ "]";
	}

}
