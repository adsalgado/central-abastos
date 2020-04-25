package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import mx.com.sharkit.domain.Estatus;
import mx.com.sharkit.domain.Proveedor;

public class ProductoProveedorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull
	private BigDecimal precioSinIva;

	@NotNull
	private BigDecimal precio;

	private Instant fechaAlta;

	private Instant fechaModificacion;

	private Long usuarioAltaId;

	private Long usuarioModificacionId;

	private ProductoDTO producto;

	private Long productoId;

	private Proveedor proveedor;

	private Long proveedorId;

	private Estatus estatus;

	private Long estatusId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrecioSinIva() {
		return precioSinIva;
	}

	public void setPrecioSinIva(BigDecimal precioSinIva) {
		this.precioSinIva = precioSinIva;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Instant getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Instant fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Instant getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Instant fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoProveedorDTO other = (ProductoProveedorDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductoProveedorDTO [id=" + id + ", precioSinIva=" + precioSinIva + ", precio=" + precio
				+ ", fechaAlta=" + fechaAlta + ", fechaModificacion=" + fechaModificacion + ", usuarioAltaId="
				+ usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId + ", productoId=" + productoId
				+ ", proveedorId=" + proveedorId + ", estatusId=" + estatusId + "]";
	}

}
