package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A Chat Detalle.
 */
@Entity
@Table(name = "chat_detalle")
public class ChatDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @ManyToOne
//    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
//    private Chat chat;

    @Column(name = "chat_id")
    private Long chatId;
    
    @Column(name = "usuario_emisor")
    private String usuarioEmisorLogin;

    @Column(name = "usuario_receptor")
    private String usuarioReceptorLogin;

    
    @NotNull
    @Size(max = 512)
    @Column(name = "mensaje", length = 512, nullable = false)
    private String mensaje;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "adjunto_id")
    private Long adjuntoId;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatDetalle)) {
            return false;
        }
        return id != null && id.equals(((ChatDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "ChatDetalle [id=" + id + ", chatId=" + chatId + ", usuarioEmisorLogin=" + usuarioEmisorLogin
				+ ", usuarioReceptorLogin=" + usuarioReceptorLogin + ", mensaje=" + mensaje + ", fecha=" + fecha
				+ ", adjuntoId=" + adjuntoId + "]";
	}

}
