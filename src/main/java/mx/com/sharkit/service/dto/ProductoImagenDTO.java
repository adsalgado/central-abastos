package mx.com.sharkit.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.ProductoImagen} entity.
 */
public class ProductoImagenDTO implements Serializable {

    private Long id;

    private Instant fechaAlta;


    private Long usuarioAltaId;

    private Long productoId;

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

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
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
        return "ProductoImagenDTO{" +
            "id=" + getId() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", usuarioAlta=" + getUsuarioAltaId() +
            ", producto=" + getProductoId() +
            ", adjunto=" + getAdjuntoId() +
            ", adjunto=" + getAdjunto() +
            "}";
    }
}
