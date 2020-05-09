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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario_id", insertable = false, updatable = false)
	private User usuario;

	@Column(name = "usuario_id")
	private Long usuarioId;

	@NotNull
	@Size(max = 256)
	@Column(name = "nombre", length = 256, nullable = false)
	private String nombre;

	@Column(name = "fecha_alta")
	private LocalDateTime fechaAlta;

	@Column(name = "fecha_modificacion")
	private LocalDateTime fechaModificacion;

	@ManyToOne
	@JoinColumn(name = "direccion_id", insertable = false, updatable = false)
	private Direccion direccion;

	@Column(name = "direccion_id")
	private Long direccionId;

	@Column(name = "usuario_alta_id")
	private Long usuarioAltaId;

	@Column(name = "usuario_modificacion_id")
	private Long usuarioModificacionId;

	@ManyToOne
	@JoinColumn(name = "empresa_id", insertable = false, updatable = false)
	@JsonIgnoreProperties("proveedors")
	private Empresa empresa;

	@Column(name = "empresa_id")
	private Long empresaId;

	@ManyToOne
	@JoinColumn(name = "transportista_id", insertable = false, updatable = false)
	@JsonIgnoreProperties("proveedors")
	private Transportista transportista;

	@Column(name = "transportista_id")
	private Long transportistaId;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public Proveedor nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public Proveedor fechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
		return this;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public Proveedor fechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
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

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long usuarioAltaId) {
		this.usuarioAltaId = usuarioAltaId;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long usuarioModificacionId) {
		this.usuarioModificacionId = usuarioModificacionId;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Proveedor empresa(Empresa empresa) {
		this.empresa = empresa;
		return this;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Transportista getTransportista() {
		return transportista;
	}

	public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}

	public Long getTransportistaId() {
		return transportistaId;
	}

	public void setTransportistaId(Long transportistaId) {
		this.transportistaId = transportistaId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Proveedor)) {
			return false;
		}
		return id != null && id.equals(((Proveedor) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", fechaModificacion="
				+ fechaModificacion + ", direccionId=" + direccionId + ", usuarioAltaId=" + usuarioAltaId
				+ ", usuarioId=" + usuarioId + ", usuarioModificacionId=" + usuarioModificacionId + ", empresaId="
				+ empresaId + ", transportistaId=" + transportistaId + "]";
	}

}
