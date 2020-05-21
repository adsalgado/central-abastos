package mx.com.sharkit.excel.objectbinding.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductoCargaDTO extends Base {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5687170971727290075L;

	private String sku;

	private String nombre;

	private String descripcion;

	private BigDecimal precio;
	
	private Long tipoArticuloId;

	private Long unidadMedidaId;

	private BigDecimal inventario;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Long getTipoArticuloId() {
		return tipoArticuloId;
	}

	public void setTipoArticuloId(Long tipoArticuloId) {
		this.tipoArticuloId = tipoArticuloId;
	}

	public Long getUnidadMedidaId() {
		return unidadMedidaId;
	}

	public void setUnidadMedidaId(Long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}

	public BigDecimal getInventario() {
		return inventario;
	}

	public void setInventario(BigDecimal inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "ProductoCargaDTO [sku=" + sku + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio="
				+ precio + ", tipoArticuloId=" + tipoArticuloId + ", unidadMedidaId=" + unidadMedidaId + ", inventario="
				+ inventario + "]";
	}


}
