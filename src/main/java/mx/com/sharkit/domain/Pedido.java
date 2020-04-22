package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_sin_iva", precision = 21, scale = 2)
    private BigDecimal totalSinIva;

    @Column(name = "comision_transportista", precision = 21, scale = 2)
    private BigDecimal comisionTransportista;

    @Column(name = "comision_preparador", precision = 21, scale = 2)
    private BigDecimal comisionPreparador;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "fecha_preparacion")
    private LocalDate fechaPreparacion;

    @Column(name = "fecha_cobro")
    private LocalDate fechaCobro;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @OneToMany(mappedBy = "pedido")
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @OneToMany(mappedBy = "pedido")
    private Set<HistoricoPedido> historicoPedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Estatus estatus;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Transportista transportista;

    @ManyToOne
    @JsonIgnoreProperties("pedidos")
    private Recolector recolector;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalSinIva() {
        return totalSinIva;
    }

    public Pedido totalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
        return this;
    }

    public void setTotalSinIva(BigDecimal totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public BigDecimal getComisionTransportista() {
        return comisionTransportista;
    }

    public Pedido comisionTransportista(BigDecimal comisionTransportista) {
        this.comisionTransportista = comisionTransportista;
        return this;
    }

    public void setComisionTransportista(BigDecimal comisionTransportista) {
        this.comisionTransportista = comisionTransportista;
    }

    public BigDecimal getComisionPreparador() {
        return comisionPreparador;
    }

    public Pedido comisionPreparador(BigDecimal comisionPreparador) {
        this.comisionPreparador = comisionPreparador;
        return this;
    }

    public void setComisionPreparador(BigDecimal comisionPreparador) {
        this.comisionPreparador = comisionPreparador;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Pedido total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public Pedido fechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
        return this;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaPreparacion() {
        return fechaPreparacion;
    }

    public Pedido fechaPreparacion(LocalDate fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
        return this;
    }

    public void setFechaPreparacion(LocalDate fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
    }

    public LocalDate getFechaCobro() {
        return fechaCobro;
    }

    public Pedido fechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
        return this;
    }

    public void setFechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public Pedido fechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public Pedido pedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
        return this;
    }

    public Pedido addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setPedido(this);
        return this;
    }

    public Pedido removePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.remove(pedidoDetalle);
        pedidoDetalle.setPedido(null);
        return this;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public Set<HistoricoPedido> getHistoricoPedidos() {
        return historicoPedidos;
    }

    public Pedido historicoPedidos(Set<HistoricoPedido> historicoPedidos) {
        this.historicoPedidos = historicoPedidos;
        return this;
    }

    public Pedido addHistoricoPedido(HistoricoPedido historicoPedido) {
        this.historicoPedidos.add(historicoPedido);
        historicoPedido.setPedido(this);
        return this;
    }

    public Pedido removeHistoricoPedido(HistoricoPedido historicoPedido) {
        this.historicoPedidos.remove(historicoPedido);
        historicoPedido.setPedido(null);
        return this;
    }

    public void setHistoricoPedidos(Set<HistoricoPedido> historicoPedidos) {
        this.historicoPedidos = historicoPedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public Pedido estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public Pedido transportista(Transportista transportista) {
        this.transportista = transportista;
        return this;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public Recolector getRecolector() {
        return recolector;
    }

    public Pedido recolector(Recolector recolector) {
        this.recolector = recolector;
        return this;
    }

    public void setRecolector(Recolector recolector) {
        this.recolector = recolector;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", totalSinIva=" + getTotalSinIva() +
            ", comisionTransportista=" + getComisionTransportista() +
            ", comisionPreparador=" + getComisionPreparador() +
            ", total=" + getTotal() +
            ", fechaPedido='" + getFechaPedido() + "'" +
            ", fechaPreparacion='" + getFechaPreparacion() + "'" +
            ", fechaCobro='" + getFechaCobro() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            "}";
    }
}
