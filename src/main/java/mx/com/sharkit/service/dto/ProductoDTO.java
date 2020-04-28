package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

	private Long id;

	@NotNull
	@Size(max = 256)
	private String nombre;

	@NotNull
	@Size(max = 512)
	private String descripcion;

	@NotNull
	@Size(max = 512)
	private String caracteristicas;

	@NotNull
	private BigDecimal precioSinIva;

	@NotNull
	private BigDecimal precio;

	private Instant fechaAlta;

	private Instant fechaModificacion;

	private Long adjuntoId;

	private Long usuarioAltaId;

	private Long usuarioModificacionId;

	private Long tipoArticuloId;

	private Long estatusId;

	private Long unidadMedidaId;

	private TipoArticuloDTO tipoArticulo;

	private EstatusDTO estatus;

	private UnidadMedidaDTO unidadMedida;

	private List<AdjuntoDTO> imagenes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public BigDecimal getPrecioSinIva() {
		return precioSinIva;
	}

	public void setPrecioSinIva(BigDecimal precioSinIva) {
		this.precioSinIva = precioSinIva;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Instant getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Instant fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Instant getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Instant fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	public Long getUsuarioAltaId() {
		return usuarioAltaId;
	}

	public void setUsuarioAltaId(Long userId) {
		this.usuarioAltaId = userId;
	}

	public Long getUsuarioModificacionId() {
		return usuarioModificacionId;
	}

	public void setUsuarioModificacionId(Long userId) {
		this.usuarioModificacionId = userId;
	}

	public Long getTipoArticuloId() {
		return tipoArticuloId;
	}

	public void setTipoArticuloId(Long tipoArticuloId) {
		this.tipoArticuloId = tipoArticuloId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public Long getUnidadMedidaId() {
		return unidadMedidaId;
	}

	public void setUnidadMedidaId(Long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}

	public TipoArticuloDTO getTipoArticulo() {
		return tipoArticulo;
	}

	public void setTipoArticulo(TipoArticuloDTO tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	public EstatusDTO getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	public UnidadMedidaDTO getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedidaDTO unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public List<AdjuntoDTO> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<AdjuntoDTO> imagenes) {
		this.imagenes = imagenes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ProductoDTO productoDTO = (ProductoDTO) o;
		if (productoDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), productoDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ProductoDTO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", caracteristicas="
				+ caracteristicas + ", precioSinIva=" + precioSinIva + ", precio=" + precio + ", fechaAlta=" + fechaAlta
				+ ", fechaModificacion=" + fechaModificacion + ", adjuntoId=" + adjuntoId + ", usuarioAltaId="
				+ usuarioAltaId + ", usuarioModificacionId=" + usuarioModificacionId + ", tipoArticuloId="
				+ tipoArticuloId + ", estatusId=" + estatusId + ", unidadMedidaId=" + unidadMedidaId + "]";
	}

}
