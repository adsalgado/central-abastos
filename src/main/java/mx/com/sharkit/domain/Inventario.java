package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Inventario.
 */
@Entity
@Table(name = "inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    private ProductoProveedor productoProveedor;

    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "inventario_minimo", precision = 21, scale = 2)
    private BigDecimal inventarioMinimo;

    @Column(name = "inventario_maximo", precision = 21, scale = 2)
    private BigDecimal inventarioMaximo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getInventarioMinimo() {
		return inventarioMinimo;
	}

	public void setInventarioMinimo(BigDecimal inventarioMinimo) {
		this.inventarioMinimo = inventarioMinimo;
	}

	public BigDecimal getInventarioMaximo() {
		return inventarioMaximo;
	}

	public void setInventarioMaximo(BigDecimal inventarioMaximo) {
		this.inventarioMaximo = inventarioMaximo;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventario)) {
            return false;
        }
        return id != null && id.equals(((Inventario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", productoProveedor=" + productoProveedor + ", productoProveedorId="
				+ productoProveedorId + ", total=" + total + ", inventarioMinimo=" + inventarioMinimo
				+ ", inventarioMaximo=" + inventarioMaximo + "]";
	}

}
