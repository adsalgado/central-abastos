package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Pedido} entity.
 */
public class PedidoProveedorDTO implements Serializable {

	private Long id;

	private String folio;

	private PedidoDTO pedido;

	private Long pedidoId;

	private ProveedorDTO proveedor;

	private Long proveedorId;

	private EstatusDTO estatus;

	private Long estatusId;

	private Long transportistaId;

	private Long recolectorId;

	private BigDecimal total;

	private BigDecimal totalSinIva;

	private BigDecimal comisionTransportista;

	private BigDecimal comisionPreparador;

	private Long usuarioAltaId;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fechaAlta;

	private Long usuarioModificacionId;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fechaModificacion;

	private List<PedidoDetalleDTO> pedidoDetalles = new ArrayList<>();

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fechaPreparacion;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fechaEnvio;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
	private LocalDateTime fechaEntrega;

	private String personaEntrega;

    private Long chatProveedorid;

    private Long chatTransportistaId;
    
    private String token;
    
    private Integer calificacionServicio;
    
    private String comentarios;
    
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

	public Long getPedidoId() {
		return pedidoId;
	}

	public PedidoDTO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
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

	public List<PedidoDetalleDTO> getPedidoDetalles() {
		return pedidoDetalles;
	}

	public void setPedidoDetalles(List<PedidoDetalleDTO> pedidoDetalles) {
		this.pedidoDetalles = pedidoDetalles;
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
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PedidoProveedorDTO pedidoDTO = (PedidoProveedorDTO) o;
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
		return "PedidoProveedorDTO [id=" + id + ", folio=" + folio + ", pedido=" + pedido + ", pedidoId=" + pedidoId
				+ ", proveedor=" + proveedor + ", proveedorId=" + proveedorId + ", estatus=" + estatus + ", estatusId="
				+ estatusId + ", transportistaId=" + transportistaId + ", recolectorId=" + recolectorId + ", total="
				+ total + ", totalSinIva=" + totalSinIva + ", comisionTransportista=" + comisionTransportista
				+ ", comisionPreparador=" + comisionPreparador + ", usuarioAltaId=" + usuarioAltaId + ", fechaAlta="
				+ fechaAlta + ", usuarioModificacionId=" + usuarioModificacionId + ", fechaModificacion="
				+ fechaModificacion + ", pedidoDetalles=" + pedidoDetalles + ", fechaPreparacion=" + fechaPreparacion
				+ ", fechaEnvio=" + fechaEnvio + ", fechaEntrega=" + fechaEntrega + ", personaEntrega=" + personaEntrega
				+ ", chatProveedorid=" + chatProveedorid + ", chatTransportistaId=" + chatTransportistaId + ", token="
				+ token + ", calificacionServicio=" + calificacionServicio + ", comentarios=" + comentarios
				+ ", distanciaEntregaKm=" + distanciaEntregaKm + "]";
	}

}
