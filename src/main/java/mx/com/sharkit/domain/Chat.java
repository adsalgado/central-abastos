package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 512)
    @Column(name = "mensaje", length = 512, nullable = false)
    private String mensaje;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioFuente;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuarioDestino;

    @ManyToOne
    @JsonIgnoreProperties("chats")
    private Adjunto adjunto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Chat mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Chat fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public User getUsuarioFuente() {
        return usuarioFuente;
    }

    public Chat usuarioFuente(User user) {
        this.usuarioFuente = user;
        return this;
    }

    public void setUsuarioFuente(User user) {
        this.usuarioFuente = user;
    }

    public User getUsuarioDestino() {
        return usuarioDestino;
    }

    public Chat usuarioDestino(User user) {
        this.usuarioDestino = user;
        return this;
    }

    public void setUsuarioDestino(User user) {
        this.usuarioDestino = user;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public Chat adjunto(Adjunto adjunto) {
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
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            ", mensaje='" + getMensaje() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
