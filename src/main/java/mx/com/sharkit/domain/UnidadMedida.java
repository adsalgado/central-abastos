package mx.com.sharkit.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UnidadMedida.
 */
@Entity
@Table(name = "unidad_medida")
public class UnidadMedida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 56)
    @Column(name = "nombre", length = 56, nullable = false)
    private String nombre;

    @Size(max = 256)
    @Column(name = "descripcion", length = 256)
    private String descripcion;

    @OneToMany(mappedBy = "unidadMedida")
    private Set<Producto> productos = new HashSet<>();

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

    public UnidadMedida nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public UnidadMedida descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public UnidadMedida productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public UnidadMedida addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setUnidadMedida(this);
        return this;
    }

    public UnidadMedida removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setUnidadMedida(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadMedida)) {
            return false;
        }
        return id != null && id.equals(((UnidadMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
