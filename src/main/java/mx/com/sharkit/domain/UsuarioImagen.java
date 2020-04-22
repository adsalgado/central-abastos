package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UsuarioImagen.
 */
@Entity
@Table(name = "usuario_imagen")
public class UsuarioImagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("usuarioImagens")
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

    public UsuarioImagen fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public User getUsuario() {
        return usuario;
    }

    public UsuarioImagen usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public UsuarioImagen adjunto(Adjunto adjunto) {
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
        if (!(o instanceof UsuarioImagen)) {
            return false;
        }
        return id != null && id.equals(((UsuarioImagen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsuarioImagen{" +
            "id=" + getId() +
            ", fechaAlta='" + getFechaAlta() + "'" +
            "}";
    }
}
