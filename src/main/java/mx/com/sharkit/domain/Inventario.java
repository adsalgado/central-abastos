package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    private ProductoProveedor productoProveedor;

    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @OneToMany(mappedBy = "inventario")
    private Set<InventarioHistorico> inventarioHistoricos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Inventario total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<InventarioHistorico> getInventarioHistoricos() {
        return inventarioHistoricos;
    }

    public Inventario inventarioHistoricos(Set<InventarioHistorico> inventarioHistoricos) {
        this.inventarioHistoricos = inventarioHistoricos;
        return this;
    }

    public Inventario addInventarioHistorico(InventarioHistorico inventarioHistorico) {
        this.inventarioHistoricos.add(inventarioHistorico);
        inventarioHistorico.setInventario(this);
        return this;
    }

    public Inventario removeInventarioHistorico(InventarioHistorico inventarioHistorico) {
        this.inventarioHistoricos.remove(inventarioHistorico);
        inventarioHistorico.setInventario(null);
        return this;
    }

    public void setInventarioHistoricos(Set<InventarioHistorico> inventarioHistoricos) {
        this.inventarioHistoricos = inventarioHistoricos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
		return "Inventario [id=" + id + ", total=" + total + ", productoProveedorId=" + productoProveedorId + "]";
	}

}
