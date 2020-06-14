package mx.com.sharkit.pushnotif.service;

public enum EnumPantallas {
	
	SOLICITUD_PEDIDO(1),
	PEDIDO_CONFIRMADO_CLIENTE(2),
	PEDIDO_CONFIRMADO_TRANSPORTISTA(3),
	PEDIDO_ENTREGADO(4),
	PEDIDO_CALIFICADO(5),
	CHAT(10),
	CONTACT_CENTER(11),
	LLEGADA_TRANSPORTISTA(12);
	
	private int view;
	
	private EnumPantallas(int view) {
		this.view = view;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}
	
}
