package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A DTO for the {@link mx.com.sharkit.domain.ChatDetalle} entity.
 */
public class ChatDetalleDTO implements Serializable {

	private Long id;

	private Long chatId;

	private String usuarioEmisorLogin;

	private String usuarioReceptorLogin;

	private String mensaje;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime fecha;


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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ChatDetalleDTO chatDTO = (ChatDetalleDTO) o;
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
		return "ChatDetalleDTO [id=" + id + ", chatId=" + chatId + ", usuarioEmisorLogin=" + usuarioEmisorLogin
				+ ", usuarioReceptorLogin=" + usuarioReceptorLogin + ", mensaje=" + mensaje + ", fecha=" + fecha + "]";
	}

}
