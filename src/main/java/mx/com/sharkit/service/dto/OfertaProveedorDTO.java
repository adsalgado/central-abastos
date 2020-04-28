package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.OfertaProveedor} entity.
 */
public class OfertaProveedorDTO implements Serializable {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es_MX")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "dd/MM/yyyy", locale = "es_MX")
    private LocalDate fechaFin;

    private ProductoProveedorDTO productoProveedor;

    private Long productoProveedorId;

    private EstatusDTO estatus;

    private Long estatusId;

    private TipoOfertaDTO tipoOferta;

    private Long tipoOfertaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public TipoOfertaDTO getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(TipoOfertaDTO tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfertaProveedorDTO ofertaProveedorDTO = (OfertaProveedorDTO) o;
        if (ofertaProveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ofertaProveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "OfertaProveedorDTO [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", productoProveedorId=" + productoProveedorId + ", estatusId=" + estatusId + ", tipoOfertaId="
				+ tipoOfertaId + "]";
	}

}
