package mx.com.sharkit.pushnotif.service;

public enum EnumPantallas {
	
	SOLICITUD_PEDIDO(1),
	PEDIDO_CONFIRMADO_CLIENTE(2),
	PEDIDO_CONFIRMADO_TRANSPORTISTA(3),
	CHAT(10);
	
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
