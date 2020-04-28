package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link mx.com.sharkit.domain.CarritoHistoricoDetalle} entity.
 */
public class CarritoHistoricoDetalleDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal cantidad;

    private BigDecimal precio;

    private ProductoProveedorDTO productoProveedor;
    
    private Long productoProveedorId;

    private Long carritoHistoricoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

	public ProductoProveedorDTO getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedorDTO productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	public Long getCarritoHistoricoId() {
        return carritoHistoricoId;
    }

    public void setCarritoHistoricoId(Long carritoHistoricoId) {
        this.carritoHistoricoId = carritoHistoricoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoHistoricoDetalleDTO carritoHistoricoDetalleDTO = (CarritoHistoricoDetalleDTO) o;
        if (carritoHistoricoDetalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoHistoricoDetalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "CarritoHistoricoDetalleDTO [id=" + id + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", productoProveedorId=" + productoProveedorId + ", carritoHistoricoId=" + carritoHistoricoId + "]";
	}

}
