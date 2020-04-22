package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProductoImagen.
 */
@Entity
@Table(name = "producto_imagen")
public class ProductoImagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioAlta;

    @ManyToOne
    @JsonIgnoreProperties("productoImagens")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("productoImagens")
    private Adjunto adjunto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public ProductoImagen fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public User getUsuarioAlta() {
        return usuarioAlta;
    }

    public ProductoImagen usuarioAlta(User user) {
        this.usuarioAlta = user;
        return this;
    }

    public void setUsuarioAlta(User user) {
        this.usuarioAlta = user;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProductoImagen producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public ProductoImagen adjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
        return this;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoImagen)) {
            return false;
        }
        return id != null && id.equals(((ProductoImagen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoImagen{" +
            "id=" + getId() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            "}";
    }
}
