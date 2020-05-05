package mx.com.sharkit.web.websocket.dto;

import java.io.Serializable;

public class MessageChatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String from;
	
	private String to;
	
	private String text;
	
	private Long pedidoProveedorId;
	
	private Long tipoChatId;
	
	private Long tipoUsuarioId;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
	}

	public Long getTipoChatId() {
		return tipoChatId;
	}

	public void setTipoChatId(Long tipoChatId) {
		this.tipoChatId = tipoChatId;
	}

	public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	@Override
	public String toString() {
		return "MessageChatDTO [from=" + from + ", to=" + to + ", text=" + text + ", pedidoProveedorId="
				+ pedidoProveedorId + ", tipoChatId=" + tipoChatId + ", tipoUsuarioId=" + tipoUsuarioId + "]";
	}

}
