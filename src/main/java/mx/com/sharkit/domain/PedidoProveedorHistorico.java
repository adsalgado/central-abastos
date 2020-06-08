package mx.com.sharkit.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido_proveedor_historico")
public class PedidoProveedorHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    @Column(name = "pedido_proveedor_id")
    private Long pedidoProveedorId;

    @Column(name = "estatus_id")
    private Long estatusId;

    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    private Estatus estatus;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "fecha")
    private LocalDateTime fecha;
    
    @Column(name = "descripcion")
    private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoProveedorHistorico other = (PedidoProveedorHistorico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PedidoProveedorHistorico [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", estatusId="
				+ estatusId + ", estatus=" + estatus + ", usuarioId=" + usuarioId + ", fecha=" + fecha
				+ ", descripcion=" + descripcion + "]";
	}
    
}
