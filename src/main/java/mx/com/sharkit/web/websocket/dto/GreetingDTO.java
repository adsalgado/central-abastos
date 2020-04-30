package mx.com.sharkit.web.websocket.dto;

public class GreetingDTO {

	private String content;

	public GreetingDTO() {
	}

	public GreetingDTO(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
