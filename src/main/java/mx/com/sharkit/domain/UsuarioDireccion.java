package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UsuarioDireccion.
 */
@Entity
@Table(name = "usuario_direccion")
public class UsuarioDireccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, insertable = false, updatable = false)
    private User usuario;
    
    @Column(name = "usuario_id")
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "direccion_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("usuarioDireccions")
    private Direccion direccion;
    
    @Column(name = "direccion_id")
    private Long direccionId;


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

    public UsuarioDireccion fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public User getUsuario() {
        return usuario;
    }

    public UsuarioDireccion usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

   
    public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Long getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Long direccionId) {
		this.direccionId = direccionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDireccion)) {
            return false;
        }
        return id != null && id.equals(((UsuarioDireccion) o).id);
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
