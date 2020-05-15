package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido_proveedor")
public class PedidoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 12)
    @Column(name = "folio", length = 12)
    private String folio;

    @ManyToOne
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false)
    private Pedido pedido;
    
    @Column(name = "pedido_id")
    private Long pedidoId;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", insertable = false, updatable = false)
    private Proveedor proveedor;
    
    @Column(name = "proveedor_id")
    private Long proveedorId;
    
    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    private Estatus estatus;
    
    @Column(name = "estatus_id")
    private Long estatusId;

    @Column(name = "transportista_id")
    private Long transportistaId;

    @Column(name = "recolector_id")
    private Long recolectorId;
    
    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;

    @Column(name = "comision_transportista", precision = 21, scale = 2)
    private BigDecimal comisionTransportista;

    @Column(name = "comision_preparador", precision = 21, scale = 2)
    private BigDecimal comisionPreparador;
    
    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "usuario_modificacion_id")
    private Long usuarioModificacionId;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    @Column(name = "fecha_preparacion")
	private LocalDateTime fechaPreparacion;

    @Column(name = "fecha_envio")
	private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega")
	private LocalDateTime fechaEntrega;

    @Column(name = "persona_entrega")
	private String personaEntrega;

    @Column(name = "chat_proveedor_id")
    private Long chatProveedorid;

    @Column(name = "chat_transportista_id")
    private Long chatTransportistaId;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "calificacion_servicio")
    private Integer calificacionServicio;
    
    @Size(max = 512)
    @Column(name = "comentarios", length = 512)
    private String comentarios;
    
    @Column(name = "distancia_entrega_km")
	private BigDecimal distanciaEntregaKm;

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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
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

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public LocalDateTime getFechaPreparacion() {
		return fechaPreparacion;
	}

	public void setFechaPreparacion(LocalDateTime fechaPreparacion) {
		this.fechaPreparacion = fechaPreparacion;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getPersonaEntrega() {
		return personaEntrega;
	}

	public void setPersonaEntrega(String personaEntrega) {
		this.personaEntrega = personaEntrega;
	}

	public Long getChatProveedorid() {
		return chatProveedorid;
	}

	public void setChatProveedorid(Long chatProveedorid) {
		this.chatProveedorid = chatProveedorid;
	}

	public Long getChatTransportistaId() {
		return chatTransportistaId;
	}

	public void setChatTransportistaId(Long chatTransportistaId) {
		this.chatTransportistaId = chatTransportistaId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getCalificacionServicio() {
		return calificacionServicio;
	}

	public void setCalificacionServicio(Integer calificacionServicio) {
		this.calificacionServicio = calificacionServicio;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public BigDecimal getDistanciaEntregaKm() {
		return distanciaEntregaKm;
	}

	public void setDistanciaEntregaKm(BigDecimal distanciaEntregaKm) {
		this.distanciaEntregaKm = distanciaEntregaKm;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoProveedor)) {
            return false;
        }
        return id != null && id.equals(((PedidoProveedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "PedidoProveedor [id=" + id + ", folio=" + folio + ", pedido=" + pedido + ", pedidoId=" + pedidoId
				+ ", proveedor=" + proveedor + ", proveedorId=" + proveedorId + ", estatus=" + estatus + ", estatusId="
				+ estatusId + ", transportistaId=" + transportistaId + ", recolectorId=" + recolectorId + ", total="
				+ total + ", totalSinIva=" + totalSinIva + ", comisionTransportista=" + comisionTransportista
				+ ", comisionPreparador=" + comisionPreparador + ", usuarioAltaId=" + usuarioAltaId + ", fechaAlta="
				+ fechaAlta + ", usuarioModificacionId=" + usuarioModificacionId + ", fechaModificacion="
				+ fechaModificacion + ", fechaPreparacion=" + fechaPreparacion + ", fechaEnvio=" + fechaEnvio
				+ ", fechaEntrega=" + fechaEntrega + ", personaEntrega=" + personaEntrega + ", chatProveedorid="
				+ chatProveedorid + ", chatTransportistaId=" + chatTransportistaId + ", token=" + token
				+ ", calificacionServicio=" + calificacionServicio + ", comentarios=" + comentarios
				+ ", distanciaEntregaKm=" + distanciaEntregaKm + "]";
	}

}
