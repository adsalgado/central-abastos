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
    
    @Column(name = "total_sin_comision", precision = 21, scale = 2)
	private BigDecimal totalSinComision;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;

    @Column(name = "comision_transportista", precision = 21, scale = 2)
    private BigDecimal comisionTransportista;

    @Column(name = "comision_preparador", precision = 21, scale = 2)
    private BigDecimal comisionPreparador;
    
    @Column(name = "comision_stripe", precision = 21, scale = 2)
    private BigDecimal comisionStripe;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "fecha_preparacion")
    private LocalDate fechaPreparacion;

    @Column(name = "fecha_cobro")
    private LocalDate fechaCobro;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "correo_contacto")
    private String correoContacto;
    
    @ManyToOne
    @JoinColumn(name = "direccion_contacto_id", insertable = false, updatable = false)
    private Direccion direccionContacto;
    
    @Column(name = "direccion_contacto_id")
    private Long direccionContactoId;


//    @OneToMany(mappedBy = "pedido")
//    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @OneToMany(mappedBy = "pedido")
    private Set<HistoricoPedido> historicoPedidos = new HashSet<>();

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "status_pago")
    private String statusPago;

    @Column(name = "balance_transaction")
    private String balanceTransaction;

    @Column(name = "charge_id")
    private String chargeId;

    @Column(name = "receipt_url")
    private String receiptUrl;


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

	public BigDecimal getComisionStripe() {
		return comisionStripe;
	}

	public void setComisionStripe(BigDecimal comisionStripe) {
		this.comisionStripe = comisionStripe;
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

	
	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getCorreoContacto() {
		return correoContacto;
	}

	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}

	public Direccion getDireccionContacto() {
		return direccionContacto;
	}

	public void setDireccionContacto(Direccion direccionContacto) {
		this.direccionContacto = direccionContacto;
	}

	public Long getDireccionContactoId() {
		return direccionContactoId;
	}

	public void setDireccionContactoId(Long direccionContactoId) {
		this.direccionContactoId = direccionContactoId;
	}

	public String getStatusPago() {
		return statusPago;
	}

	public void setStatusPago(String statusPago) {
		this.statusPago = statusPago;
	}

	public String getBalanceTransaction() {
		return balanceTransaction;
	}

	public void setBalanceTransaction(String balanceTransaction) {
		this.balanceTransaction = balanceTransaction;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	public BigDecimal getTotalSinComision() {
		return totalSinComision;
	}

	public void setTotalSinComision(BigDecimal totalSinComision) {
		this.totalSinComision = totalSinComision;
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
		return "Pedido [id=" + id + ", folio=" + folio + ", estatus=" + estatus + ", estatusId=" + estatusId
				+ ", cliente=" + cliente + ", clienteId=" + clienteId + ", total=" + total + ", totalSinComision="
				+ totalSinComision + ", totalSinIva=" + totalSinIva + ", comisionTransportista=" + comisionTransportista
				+ ", comisionPreparador=" + comisionPreparador + ", comisionStripe=" + comisionStripe + ", fechaPedido="
				+ fechaPedido + ", fechaPreparacion=" + fechaPreparacion + ", fechaCobro=" + fechaCobro
				+ ", fechaEntrega=" + fechaEntrega + ", nombreContacto=" + nombreContacto + ", telefonoContacto="
				+ telefonoContacto + ", correoContacto=" + correoContacto + ", direccionContacto=" + direccionContacto
				+ ", direccionContactoId=" + direccionContactoId + ", historicoPedidos=" + historicoPedidos
				+ ", usuarioAltaId=" + usuarioAltaId + ", fechaAlta=" + fechaAlta + ", statusPago=" + statusPago
				+ ", balanceTransaction=" + balanceTransaction + ", chargeId=" + chargeId + ", receiptUrl=" + receiptUrl
				+ "]";
	}

}
