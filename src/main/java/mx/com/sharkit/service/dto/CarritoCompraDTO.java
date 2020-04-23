package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A DTO for the {@link mx.com.sharkit.domain.CarritoCompra} entity.
 */
public class CarritoCompraDTO implements Serializable {

    private Long id;

    private BigDecimal cantidad;

    private BigDecimal precio;

    private LocalDateTime fechaAlta;

    private Long clienteId;

    private Long productoId;
    
    @JsonIgnore
    private transient UserDTO cliente;
    
    private ProductoDTO producto;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public UserDTO getCliente() {
		return cliente;
	}

	public void setCliente(UserDTO cliente) {
		this.cliente = cliente;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarritoCompraDTO carritoCompraDTO = (CarritoCompraDTO) o;
        if (carritoCompraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carritoCompraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarritoCompraDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            ", cliente=" + getClienteId() +
            ", producto=" + getProductoId() +
            ", fechaAlta=" + getFechaAlta() +
            "}";
    }
}
