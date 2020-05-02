package mx.com.sharkit.service.dto;

public class PedidoPagoDTO {

	private Long pedidoId;
	
	private String token;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
