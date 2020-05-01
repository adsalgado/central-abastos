package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Pedido} entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private EstatusDTO estatus;
    
    private Long estatusId;
    
    private UserDTO cliente;
    
    private Long clienteId;

    private BigDecimal total;

    private BigDecimal totalSinIva;

    private BigDecimal comisionTransportista;

    private BigDecimal comisionPreparador;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDate fechaPedido;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDate fechaPreparacion;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDate fechaCobro;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDate fechaEntrega;

//    @OneToMany(mappedBy = "pedido")
//    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
    private LocalDateTime fechaAlta;

    @OneToMany(mappedBy = "pedido")
    private Set<HistoricoPedidoDTO> historicoPedidos = new HashSet<>();
    
    @Transient
    private List<PedidoProveedorDTO> pedidoProveedores = new ArrayList<>();

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public UserDTO getCliente() {
		return cliente;
	}

	public void setCliente(UserDTO cliente) {
		this.cliente = cliente;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Set<HistoricoPedidoDTO> getHistoricoPedidos() {
		return historicoPedidos;
	}

	public void setHistoricoPedidos(Set<HistoricoPedidoDTO> historicoPedidos) {
		this.historicoPedidos = historicoPedidos;
	}

	public List<PedidoProveedorDTO> getPedidoProveedores() {
		return pedidoProveedores;
	}

	public void setPedidoProveedores(List<PedidoProveedorDTO> pedidoProveedores) {
		this.pedidoProveedores = pedidoProveedores;
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
		return "PedidoDTO [id=" + id + ", estatusId=" + estatusId + ", clienteId=" + clienteId + ", total=" + total
				+ ", totalSinIva=" + totalSinIva + ", comisionTransportista=" + comisionTransportista
				+ ", comisionPreparador=" + comisionPreparador + ", fechaPedido=" + fechaPedido + ", fechaPreparacion="
				+ fechaPreparacion + ", fechaCobro=" + fechaCobro + ", fechaEntrega=" + fechaEntrega
				+ ", usuarioAltaId=" + usuarioAltaId + ", fechaAlta=" + fechaAlta + ", historicoPedidos="
				+ historicoPedidos + "]";
	}

}
