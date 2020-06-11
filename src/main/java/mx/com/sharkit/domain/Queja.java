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
import javax.validation.constraints.NotNull;

/**
 * A Queja.
 */
@Entity
@Table(name = "queja")
public class Queja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
	@Column(name = "tipo_usuario_id", nullable = false)
    private Long tipoUsuarioId;
    
    @ManyToOne
	@JoinColumn(name = "tipo_usuario_id", insertable = false, updatable = false)
	private TipoUsuario tipoUsuario;

    @NotNull
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    
    @ManyToOne
	@JoinColumn(name = "usuario_id", insertable = false, updatable = false)
	private User usuario;

    @NotNull
    @Column(name = "pedido_proveedor_id", nullable = false)
    private Long pedidoProveedorId;
    
    @ManyToOne
	@JoinColumn(name = "pedido_proveedor_id", insertable = false, updatable = false)
	private PedidoProveedor pedidoProveedor;

    @Column(name = "estatus_id")
    private Long estatusId;
    
    @ManyToOne
	@JoinColumn(name = "estatus_id", insertable = false, updatable = false)
	private Estatus estatus;
    
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public PedidoProveedor getPedidoProveedor() {
		return pedidoProveedor;
	}

	public void setPedidoProveedor(PedidoProveedor pedidoProveedor) {
		this.pedidoProveedor = pedidoProveedor;
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
        if (!(o instanceof Queja)) {
            return false;
        }
        return id != null && id.equals(((Queja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Queja [id=" + id + ", tipoUsuarioId=" + tipoUsuarioId + ", tipoUsuario=" + tipoUsuario + ", usuarioId="
				+ usuarioId + ", usuario=" + usuario + ", pedidoProveedorId=" + pedidoProveedorId + ", pedidoProveedor="
				+ pedidoProveedor + ", estatusId=" + estatusId + ", estatus=" + estatus + ", fechaAlta=" + fechaAlta
				+ "]";
	}

}
