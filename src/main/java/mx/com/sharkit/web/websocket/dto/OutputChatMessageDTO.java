package mx.com.sharkit.web.websocket.dto;

import java.io.Serializable;

public class OutputChatMessageDTO extends MessageChatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String time;

	public OutputChatMessageDTO(final String from, final String text, final String time) {
		setFrom(from);
		setText(text);
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "OutputChatMessageDTO [time=" + time + ", getFrom()=" + getFrom() + ", getTo()=" + getTo()
				+ ", getText()=" + getText() + "]";
	}

}
