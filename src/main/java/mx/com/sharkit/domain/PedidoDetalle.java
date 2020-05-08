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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Size(max = 12)
    @Column(name = "folio", length = 12)
    private String folio;

    @ManyToOne
    @JoinColumn(name = "pedido_proveedor_id", insertable = false, updatable = false)
    private PedidoProveedor pedidoProveedor;

    @Column(name = "pedido_proveedor_id")
    private Long pedidoProveedorId;

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
    
    @Column(name = "cantidad", precision = 21, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;
    
    @Column(name = "precio_sin_iva", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioSinIva;

    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public PedidoProveedor getPedidoProveedor() {
		return pedidoProveedor;
	}

	public void setPedidoProveedor(PedidoProveedor pedidoProveedor) {
		this.pedidoProveedor = pedidoProveedor;
	}

	public Long getPedidoProveedorId() {
		return pedidoProveedorId;
	}

	public void setPedidoProveedorId(Long pedidoProveedorId) {
		this.pedidoProveedorId = pedidoProveedorId;
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

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalSinIva() {
		return totalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		this.totalSinIva = totalSinIva;
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
		return "PedidoDetalle [id=" + id + ", pedidoProveedorId=" + pedidoProveedorId + ", productoProveedorId="
				+ productoProveedorId + ", estatusId=" + estatusId + ", cantidad=" + cantidad + ", total=" + total
				+ ", folio=" + folio + ", totalSinIva=" + totalSinIva + ", precioSinIva=" + precioSinIva + ", precio=" + precio + "]";
	}


}
