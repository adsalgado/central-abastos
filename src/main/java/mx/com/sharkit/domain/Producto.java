package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "nombre", length = 256, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 512)
    @Column(name = "descripcion", length = 512, nullable = false)
    private String descripcion;

    @NotNull
    @Size(max = 512)
    @Column(name = "caracteristicas", length = 512, nullable = false)
    private String caracteristicas;

    @NotNull
    @Column(name = "precio_sin_iva", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioSinIva;

    @NotNull
    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @OneToOne
    @JoinColumn(unique = true)
    private Adjunto adjunto;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioAlta;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioModificacion;

    @OneToMany(mappedBy = "producto")
    private Set<OfertaProveedor> ofertaProveedors = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    private Set<CarritoCompra> carritoCompras = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    private Set<CarritoHistoricoDetalle> carritoCompraDetalles = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    private Set<Inventario> inventarios = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    private Set<ProductoImagen> productoImagens = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Proveedor proveedor;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private TipoArticulo tipoArticulo;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Seccion seccion;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Estatus estatus;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private UnidadMedida unidadMedida;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public Producto caracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public BigDecimal getPrecioSinIva() {
        return precioSinIva;
    }

    public Producto precioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
        return this;
    }

    public void setPrecioSinIva(BigDecimal precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Producto precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Producto fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public Producto fechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public Producto adjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
        return this;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public Producto usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public User getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public Producto usuarioModificacion(User user) {
        this.usuarioModificacion = user;
        return this;
    }

    public void setUsuarioModificacion(User user) {
        this.usuarioModificacion = user;
    }

    public Set<OfertaProveedor> getOfertaProveedors() {
        return ofertaProveedors;
    }

    public Producto ofertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
        return this;
    }

    public Producto addOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.add(ofertaProveedor);
        ofertaProveedor.setProducto(this);
        return this;
    }

    public Producto removeOfertaProveedor(OfertaProveedor ofertaProveedor) {
        this.ofertaProveedors.remove(ofertaProveedor);
        ofertaProveedor.setProducto(null);
        return this;
    }

    public void setOfertaProveedors(Set<OfertaProveedor> ofertaProveedors) {
        this.ofertaProveedors = ofertaProveedors;
    }

    public Set<CarritoCompra> getCarritoCompras() {
        return carritoCompras;
    }

    public Producto carritoCompras(Set<CarritoCompra> carritoCompras) {
        this.carritoCompras = carritoCompras;
        return this;
    }

    public Producto addCarritoCompra(CarritoCompra carritoCompra) {
        this.carritoCompras.add(carritoCompra);
        carritoCompra.setProducto(this);
        return this;
    }

    public Producto removeCarritoCompra(CarritoCompra carritoCompra) {
        this.carritoCompras.remove(carritoCompra);
        carritoCompra.setProducto(null);
        return this;
    }

    public void setCarritoCompras(Set<CarritoCompra> carritoCompras) {
        this.carritoCompras = carritoCompras;
    }

    public Set<CarritoHistoricoDetalle> getCarritoCompraDetalles() {
        return carritoCompraDetalles;
    }

    public Producto carritoCompraDetalles(Set<CarritoHistoricoDetalle> carritoHistoricoDetalles) {
        this.carritoCompraDetalles = carritoHistoricoDetalles;
        return this;
    }

    public Producto addCarritoCompraDetalle(CarritoHistoricoDetalle carritoHistoricoDetalle) {
        this.carritoCompraDetalles.add(carritoHistoricoDetalle);
        carritoHistoricoDetalle.setProducto(this);
        return this;
    }

    public Producto removeCarritoCompraDetalle(CarritoHistoricoDetalle carritoHistoricoDetalle) {
        this.carritoCompraDetalles.remove(carritoHistoricoDetalle);
        carritoHistoricoDetalle.setProducto(null);
        return this;
    }

    public void setCarritoCompraDetalles(Set<CarritoHistoricoDetalle> carritoHistoricoDetalles) {
        this.carritoCompraDetalles = carritoHistoricoDetalles;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public Producto pedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
        return this;
    }

    public Producto addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.add(pedidoDetalle);
        pedidoDetalle.setProducto(this);
        return this;
    }

    public Producto removePedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalles.remove(pedidoDetalle);
        pedidoDetalle.setProducto(null);
        return this;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public Set<Inventario> getInventarios() {
        return inventarios;
    }

    public Producto inventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
        return this;
    }

    public Producto addInventario(Inventario inventario) {
        this.inventarios.add(inventario);
        inventario.setProducto(this);
        return this;
    }

    public Producto removeInventario(Inventario inventario) {
        this.inventarios.remove(inventario);
        inventario.setProducto(null);
        return this;
    }

    public void setInventarios(Set<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public Set<ProductoImagen> getProductoImagens() {
        return productoImagens;
    }

    public Producto productoImagens(Set<ProductoImagen> productoImagens) {
        this.productoImagens = productoImagens;
        return this;
    }

    public Producto addProductoImagen(ProductoImagen productoImagen) {
        this.productoImagens.add(productoImagen);
        productoImagen.setProducto(this);
        return this;
    }

    public Producto removeProductoImagen(ProductoImagen productoImagen) {
        this.productoImagens.remove(productoImagen);
        productoImagen.setProducto(null);
        return this;
    }

    public void setProductoImagens(Set<ProductoImagen> productoImagens) {
        this.productoImagens = productoImagens;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Producto proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    public Producto tipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
        return this;
    }

    public void setTipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public Producto seccion(Seccion seccion) {
        this.seccion = seccion;
        return this;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public Producto estatus(Estatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public Producto unidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
        return this;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Producto empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", caracteristicas='" + getCaracteristicas() + "'" +
            ", precioSinIva=" + getPrecioSinIva() +
            ", precio=" + getPrecio() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
