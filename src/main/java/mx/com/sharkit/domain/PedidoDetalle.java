package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A PedidoDetalle.
 */
@Entity
@Table(name = "pedido_detalle")
public class PedidoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad", precision = 21, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JsonIgnoreProperties("pedidoDetalles")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_proveedor_id", insertable = false, updatable = false)
    private ProductoProveedor productoProveedor;

    @Column(name = "producto_proveedor_id")
    private Long productoProveedorId;

    @ManyToOne
    @JoinColumn(name = "estatus_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("pedidoDetalles")
    private Estatus estatus;

    @Column(name = "estatus_id")
    private Long estatusId;

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

    public PedidoDetalle cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotalSinIva() {
        return totalSinIva;
    }

    public PedidoDetalle totalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
        return this;
    }

    public void setTotalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public PedidoDetalle total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public PedidoDetalle pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public PedidoDetalle estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
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

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDetalle)) {
            return false;
        }
        return id != null && id.equals(((PedidoDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "PedidoDetalle [id=" + id + ", cantidad=" + cantidad + ", totalSinIva=" + totalSinIva + ", total="
				+ total + ", pedido=" + pedido + ", productoProveedorId=" + productoProveedorId + ", estatusId="
				+ estatusId + "]";
	}


}
