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
 * A Chat.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_proveedor_id", insertable = false, updatable = false)
    private PedidoProveedor pedidoProveedor;
    
    @Column(name = "pedido_proveedor_id")
    private Long pedidoProveedorId;

    @ManyToOne
    @JoinColumn(name = "tipo_chat_id", insertable = false, updatable = false)
    private TipoChat tipoChat;
    
    @Column(name = "tipo_chat_id")
    private Long tipoChatId;

//    @ManyToOne
//    @JoinColumn(name = "usuario_emisor", referencedColumnName="login", insertable = false, updatable = false)
//    private User usuarioEmisor;
//    
    @Column(name = "usuario_emisor")
    private String usuarioEmisorLogin;

//    @ManyToOne
//    @JoinColumn(name = "usuario_receptor", referencedColumnName="login", insertable = false, updatable = false)
//    private User usuarioReceptor;

    @Column(name = "usuario_receptor")
    private String usuarioReceptorLogin;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuarioEmisorLogin() {
		return usuarioEmisorLogin;
	}

	public void setUsuarioEmisorLogin(String usuarioEmisorLogin) {
		this.usuarioEmisorLogin = usuarioEmisorLogin;
	}

	public String getUsuarioReceptorLogin() {
		return usuarioReceptorLogin;
	}

	public void setUsuarioReceptorLogin(String usuarioReceptorLogin) {
		this.usuarioReceptorLogin = usuarioReceptorLogin;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public PedidoProveedor getPedidoProveedor() {
		return pedidoProveedor;
	}

	public void setPedidoProveedor(PedidoProveedor pedidoProveedor) {
		this.pedidoProveedor = pedidoProveedor;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public TipoChat getTipoChat() {
		return tipoChat;
	}

	public void setTipoChat(TipoChat tipoChat) {
		this.tipoChat = tipoChat;
	}

	public Long getTipoChatId() {
		return tipoChatId;
	}

	public void setTipoChatId(Long tipoChatId) {
		this.tipoChatId = tipoChatId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Chat [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", tipoChatId=" + tipoChatId
				+ ", usuarioEmisorLogin=" + usuarioEmisorLogin + ", usuarioReceptorLogin=" + usuarioReceptorLogin
				+ ", fecha=" + fecha + "]";
	}

}
