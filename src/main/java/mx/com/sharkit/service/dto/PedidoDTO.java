package mx.com.sharkit.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Pedido} entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private BigDecimal totalSinIva;

    private BigDecimal comisionTransportista;

    private BigDecimal comisionPreparador;

    private BigDecimal total;

    private LocalDate fechaPedido;

    private LocalDate fechaPreparacion;

    private LocalDate fechaCobro;

    private LocalDate fechaEntrega;


    private Long clienteId;

    private Long estatusId;

    private Long transportistaId;

    private Long recolectorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalSinIva() {
        return totalSinIva;
    }

    public void setTotalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public BigDecimal getComisionTransportista() {
        return comisionTransportista;
    }

    public void setComisionTransportista(BigDecimal comisionTransportista) {
        this.comisionTransportista = comisionTransportista;
    }

    public BigDecimal getComisionPreparador() {
        return comisionPreparador;
    }

    public void setComisionPreparador(BigDecimal comisionPreparador) {
        this.comisionPreparador = comisionPreparador;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaPreparacion() {
        return fechaPreparacion;
    }

    public void setFechaPreparacion(LocalDate fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
    }

    public LocalDate getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Long estatusId) {
        this.estatusId = estatusId;
    }

    public Long getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Long transportistaId) {
        this.transportistaId = transportistaId;
    }

    public Long getRecolectorId() {
        return recolectorId;
    }

    public void setRecolectorId(Long recolectorId) {
        this.recolectorId = recolectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", totalSinIva=" + getTotalSinIva() +
            ", comisionTransportista=" + getComisionTransportista() +
            ", comisionPreparador=" + getComisionPreparador() +
            ", total=" + getTotal() +
            ", fechaPedido='" + getFechaPedido() + "'" +
            ", fechaPreparacion='" + getFechaPreparacion() + "'" +
            ", fechaCobro='" + getFechaCobro() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", cliente=" + getClienteId() +
            ", estatus=" + getEstatusId() +
            ", transportista=" + getTransportistaId() +
            ", recolector=" + getRecolectorId() +
            "}";
    }
}
