package mx.com.sharkit.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import mx.com.sharkit.domain.enumeration.TipoEstatus;

/**
 * A Estatus.
 */
@Entity
@Table(name = "estatus")
public class Estatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final Long ACTIVO = 1L;
    public static final Long INACTIVO = 2L;

    public static final Long PEDIDO_SOLICITADO = 7L;
    public static final Long PEDIDO_EN_CONFIRMACION = 8L;
    public static final Long PEDIDO_CONFIRMADO = 9L;
    public static final Long PEDIDO_CANCELADO = 10L;
    public static final Long PEDIDO_PAGADO = 11L;
    public static final Long PEDIDO_EN_PREPARACION = 12L;
    public static final Long PEDIDO_PREPARADO = 13L;
    public static final Long PEDIDO_ENVIADO = 14L;
    public static final Long PEDIDO_ENTREGADO = 15L;
    public static final Long PEDIDO_RECHAZADO = 16L;
    public static final Long PEDIDO_ERROR_PAGO = 17L;
    public static final Long ENVIADO_A_TRANSPORTISTA = 18L;

    public static final Long QUEJA_ABIERTA = 19L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estatus")
    private TipoEstatus tipoEstatus;

    @NotNull
    @Size(max = 128)
    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "estatus")
    private Set<Producto> productos = new HashSet<>();

    @OneToMany(mappedBy = "estatus")
    private Set<OfertaProveedor> ofertaProveedors = new HashSet<>();

    @OneToMany(mappedBy = "estatus")
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "estatus")
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "estatus")
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEstatus getTipoEstatus() {
        return tipoEstatus;
    }

    public Estatus tipoEstatus(TipoEstatus tipoEstatus) {
        this.tipoEstatus = tipoEstatus;
        return this;
    }

    public void setTipoEstatus(TipoEstatus tipoEstatus) {
        this.tipoEstatus = tipoEstatus;
    }

    public String getNombre() {
        return nombre;
    }

    public Estatus nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Estatus productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Estatus addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setEstatus(this);
        return this;
    }

    public Estatus removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setEstatus(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<OfertaProveedor> getOfertaProveedors() {
        return ofertaProveedors;
    }

    public Estatus ofertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
        return this;
    }

    public Estatus addOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.add(ofertaProveedor);
        ofertaProveedor.setEstatus(this);
        return this;
    }

    public Estatus removeOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.remove(ofertaProveedor);
        ofertaProveedor.setEstatus(null);
        return this;
    }

    public void setOfertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Estatus clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Estatus addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setEstatus(this);
        return this;
    }

    public Estatus removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setEstatus(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public Estatus pedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
        return this;
    }

    public Estatus addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEstatus(this);
        return this;
    }

    public Estatus removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEstatus(null);
        return this;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public Estatus pedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
        return this;
    }

    public Estatus addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setEstatus(this);
        return this;
    }

    public Estatus removePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.remove(pedidoDetalle);
        pedidoDetalle.setEstatus(null);
        return this;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estatus)) {
            return false;
        }
        return id != null && id.equals(((Estatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Estatus{" +
            "id=" + getId() +
            ", tipoEstatus='" + getTipoEstatus() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
