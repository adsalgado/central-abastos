package mx.com.sharkit.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A CarritoCompra.
 */
@Entity
@Table(name = "carrito_compra")
public class CarritoCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "precio", precision = 21, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "fecha_alta")
    @JsonFormat(pattern="dd/MM/yyyy hh:mm:ss", locale="es_MX")
    private LocalDateTime fechaAlta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("carritoCompras")
    private Cliente cliente;
    
    @Column(name = "cliente_id")
    private Long clienteId;

    @ManyToOne
    @JoinColumn(name = "producto_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("carritoCompras")
    private Producto producto;

    @Column(name = "producto_id")
    private Long productoId;

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

    public CarritoCompra cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public CarritoCompra precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CarritoCompra cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public CarritoCompra producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarritoCompra)) {
            return false;
        }
        return id != null && id.equals(((CarritoCompra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CarritoCompra{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            "}";
    }
}
