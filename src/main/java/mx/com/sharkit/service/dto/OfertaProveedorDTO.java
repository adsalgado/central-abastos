package mx.com.sharkit.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.OfertaProveedor} entity.
 */
public class OfertaProveedorDTO implements Serializable {

    private Long id;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;


    private Long proveedorId;

    private Long productoId;

    private Long estatusId;

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

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
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
        return "OfertaProveedorDTO{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", proveedor=" + getProveedorId() +
            ", producto=" + getProductoId() +
            ", estatus=" + getEstatusId() +
            ", tipoOferta=" + getTipoOfertaId() +
            "}";
    }
}
