package mx.com.sharkit.web.websocket.dto;

import java.io.Serializable;

public class HelloMessageDTO implements Serializable {
	
	private String name;

	public HelloMessageDTO() {
	}

	public HelloMessageDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
