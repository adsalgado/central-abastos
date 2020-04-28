package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A CarritoHistoricoDetalle.
 */
@Entity
@Table(name = "carrito_historico_detalle")
public class CarritoHistoricoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cantidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "precio", precision = 21, scale = 2)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("carritoCompraDetalles")
    private ProductoProveedor productoProveedor;
    
    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @ManyToOne
    @JoinColumn(name = "carrito_historico_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("carritoHistoricoDetalles")
    private CarritoHistorico carritoHistorico;

    @Column(name = "carrito_historico_id")
    private Long carritoHistoricoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public CarritoHistoricoDetalle cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public CarritoHistoricoDetalle precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public CarritoHistorico getCarritoHistorico() {
        return carritoHistorico;
    }

    public CarritoHistoricoDetalle carritoHistorico(CarritoHistorico carritoHistorico) {
        this.carritoHistorico = carritoHistorico;
        return this;
    }

    public void setCarritoHistorico(CarritoHistorico carritoHistorico) {
        this.carritoHistorico = carritoHistorico;
    }
    
	public Long getCarritoHistoricoId() {
		return carritoHistoricoId;
	}

	public void setCarritoHistoricoId(Long carritoHistoricoId) {
		this.carritoHistoricoId = carritoHistoricoId;
	}
	

	public ProductoProveedor getProductoProveedor() {
		return productoProveedor;
	}

	public void setProductoProveedor(ProductoProveedor productoProveedor) {
		this.productoProveedor = productoProveedor;
	}

	public Long getProductoProveedorId() {
		return productoProveedorId;
	}

	public void setProductoProveedorId(Long productoProveedorId) {
		this.productoProveedorId = productoProveedorId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarritoHistoricoDetalle)) {
            return false;
        }
        return id != null && id.equals(((CarritoHistoricoDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "CarritoHistoricoDetalle [id=" + id + ", cantidad=" + cantidad + ", precio=" + precio
				+ ", productoProveedorId=" + productoProveedorId + ", carritoHistoricoId=" + carritoHistoricoId + "]";
	}


}
