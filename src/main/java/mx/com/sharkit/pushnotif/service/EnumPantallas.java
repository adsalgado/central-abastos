package mx.com.sharkit.pushnotif.service;

public enum EnumPantallas {
	
	SOLICITUD_PEDIDO(1);
	
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
