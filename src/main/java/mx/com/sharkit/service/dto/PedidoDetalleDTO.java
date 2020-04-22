package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.PedidoDetalle} entity.
 */
public class PedidoDetalleDTO implements Serializable {

    private Long id;

    private BigDecimal cantidad;

    private BigDecimal totalSinIva;

    private BigDecimal total;


    private Long pedidoId;

    private Long productoId;

    private Long estatusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalSinIva() {
        return totalSinIva;
    }

    public void setTotalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDetalleDTO pedidoDetalleDTO = (PedidoDetalleDTO) o;
        if (pedidoDetalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDetalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDetalleDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", totalSinIva=" + getTotalSinIva() +
            ", total=" + getTotal() +
            ", pedido=" + getPedidoId() +
            ", producto=" + getProductoId() +
            ", estatus=" + getEstatusId() +
            "}";
    }
}
