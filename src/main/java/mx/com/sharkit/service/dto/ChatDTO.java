package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Chat} entity.
 */
public class ChatDTO implements Serializable {

    private Long id;
        
    private Long pedidoProveedorId;

    private TipoChatDTO tipoChat;
    
    private Long tipoChatId;
    
    private String usuarioEmisorLogin;

    private String usuarioReceptorLogin;
    
    private List<ChatDetalleDTO> chatDetalles = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public TipoChatDTO getTipoChat() {
		return tipoChat;
	}

	public void setTipoChat(TipoChatDTO tipoChat) {
		this.tipoChat = tipoChat;
	}

	public Long getTipoChatId() {
		return tipoChatId;
	}

	public void setTipoChatId(Long tipoChatId) {
		this.tipoChatId = tipoChatId;
	}

	public List<ChatDetalleDTO> getChatDetalles() {
		return chatDetalles;
	}

	public void setChatDetalles(List<ChatDetalleDTO> chatDetalles) {
		this.chatDetalles = chatDetalles;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChatDTO chatDTO = (ChatDTO) o;
        if (chatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ChatDTO [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", tipoChatId=" + tipoChatId
				+ ", usuarioEmisorLogin=" + usuarioEmisorLogin + ", usuarioReceptorLogin=" + usuarioReceptorLogin
				+ ", fecha=" + fecha + "]";
	}

}
