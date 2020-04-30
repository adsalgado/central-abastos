package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private LocalDateTime fechaAlta;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, insertable = false, updatable = false)
    private User usuario;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    @Size(max = 1)
    @Column(name = "favorita", length = 1)
    private String favorita;

    @Size(max = 45)
    @Column(name = "alias", length = 45)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "direccion_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("usuarioDireccions")
    private Direccion direccion;
    
    @Column(name = "direccion_id")
    private Long direccionId;

    @ManyToOne
    @JoinColumn(name = "tipo_direccion_id", insertable = false, updatable = false)
    private TipoDireccion tipoDireccion;
    
    @Column(name = "tipo_direccion_id")
    private Long tipoDireccionId;

    @Column(name = "usuario_alta_id")
    private Long usuarioAltaId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getFavorita() {
		return favorita;
	}

	public void setFavorita(String favorita) {
		this.favorita = favorita;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public TipoDireccion getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(TipoDireccion tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public Long getTipoDireccionId() {
		return tipoDireccionId;
	}

	public void setTipoDireccionId(Long tipoDireccionId) {
		this.tipoDireccionId = tipoDireccionId;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
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
		return "UsuarioDireccion [id=" + id + ", fechaAlta=" + fechaAlta + ", usuarioId=" + usuarioId + ", favorita="
				+ favorita + ", direccionId=" + direccionId + ", tipoDireccionId=" + tipoDireccionId
				+ ", usuarioAltaId=" + usuarioAltaId + "]";
	}

}
