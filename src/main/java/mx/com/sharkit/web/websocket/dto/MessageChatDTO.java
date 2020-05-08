package mx.com.sharkit.web.websocket.dto;

import java.io.Serializable;

public class MessageChatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long chatId;

	private String from;
	
	private String to;
	
	private String text;

//	private Long pedidoProveedorId;
//	
//	private Long tipoChatId;
//	
//	private Long tipoUsuarioId;

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

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

	@Override
	public String toString() {
		return "MessageChatDTO [chatId=" + chatId + ", from=" + from + ", to=" + to + ", text=" + text + "]";
	}
		
}
