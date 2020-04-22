package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "inventario")
    private Set<InventarioHistorico> inventarioHistoricos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("inventarios")
    private Proveedor proveedor;

    @ManyToOne
    @JsonIgnoreProperties("inventarios")
    private Producto producto;

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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Inventario proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public Inventario producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "Inventario{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            "}";
    }
}
