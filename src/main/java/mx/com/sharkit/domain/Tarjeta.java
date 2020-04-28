package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Tarjeta.
 */
@Entity
@Table(name = "usuario_tarjeta")
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    @Column(name = "alias", length = 45, nullable = false)
    private String alias;

    @NotNull
    @Size(max = 20)
    @Column(name = "numero_tarjeta", length = 20, nullable = false)
    private String numeroTarjeta;

    @NotNull
    @Size(max = 10)
    @Column(name = "fecha_caducidad", length = 10, nullable = false)
    private String fechaCaducidad;

    @NotNull
    @Size(max = 3)
    @Column(name = "numero_seguridad", length = 3, nullable = false)
    private String numeroSeguridad;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, insertable = false, updatable = false)
    private User usuario;
    
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "fecha_alta")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getNumeroSeguridad() {
		return numeroSeguridad;
	}

	public void setNumeroSeguridad(String numeroSeguridad) {
		this.numeroSeguridad = numeroSeguridad;
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

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarjeta)) {
            return false;
        }
        return id != null && id.equals(((Tarjeta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Tarjeta [id=" + id + ", alias=" + alias + ", numeroTarjeta=" + numeroTarjeta + ", fechaCaducidad="
				+ fechaCaducidad + ", numeroSeguridad=" + numeroSeguridad + ", usuarioId=" + usuarioId + ", fechaAlta="
				+ fechaAlta + "]";
	}

}
