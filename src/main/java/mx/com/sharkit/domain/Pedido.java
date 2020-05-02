package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 10)
    @Column(name = "folio", length = 10)
    private String folio;

    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    private Estatus estatus;
    
    @Column(name = "estatus_id")
    private Long estatusId;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("pedidos")
    private User cliente;
    
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;

    @Column(name = "comision_transportista", precision = 21, scale = 2)
    private BigDecimal comisionTransportista;

    @Column(name = "comision_preparador", precision = 21, scale = 2)
    private BigDecimal comisionPreparador;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "fecha_preparacion")
    private LocalDate fechaPreparacion;

    @Column(name = "fecha_cobro")
    private LocalDate fechaCobro;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

//    @OneToMany(mappedBy = "pedido")
//    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @OneToMany(mappedBy = "pedido")
    private Set<HistoricoPedido> historicoPedidos = new HashSet<>();

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
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

	public Set<HistoricoPedido> getHistoricoPedidos() {
		return historicoPedidos;
	}

	public void setHistoricoPedidos(Set<HistoricoPedido> historicoPedidos) {
		this.historicoPedidos = historicoPedidos;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", folio=" + folio + ", estatusId=" + estatusId + ", clienteId=" + clienteId + ", total=" + total
				+ ", totalSinIva=" + totalSinIva + ", comisionTransportista=" + comisionTransportista
				+ ", comisionPreparador=" + comisionPreparador + ", fechaPedido=" + fechaPedido + ", fechaPreparacion="
				+ fechaPreparacion + ", fechaCobro=" + fechaCobro + ", fechaEntrega=" + fechaEntrega
				+ ", historicoPedidos=" + historicoPedidos + ", usuarioAltaId=" + usuarioAltaId + ", fechaAlta="
				+ fechaAlta + "]";
	}

}
