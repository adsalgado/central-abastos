package mx.com.sharkit.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DefinicionParametrosDTO {

	private Integer id;
	
	private Integer parentId;
	
	private Integer tipoParametroId;
	
	private String claveParametro;
	
	private String descripcion;
	
	private String caracteristica1;
	
	private String caracteristica2;
	
	private String caracteristica3;
	
	private String caracteristica4;
	
	private String caracteristica5;
	
	private String configuracion;
	
	private String usuarioAlta;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
	private LocalDateTime fechaAlta;
	
	private String usuarioUltModificacion;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", locale="es_MX")
	private LocalDateTime fechaUltModificacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getTipoParametroId() {
		return tipoParametroId;
	}

	public void setTipoParametroId(Integer tipoParametroId) {
		this.tipoParametroId = tipoParametroId;
	}

	public String getClaveParametro() {
		return claveParametro;
	}

	public void setClaveParametro(String claveParametro) {
		this.claveParametro = claveParametro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracteristica1() {
		return caracteristica1;
	}

	public void setCaracteristica1(String caracteristica1) {
		this.caracteristica1 = caracteristica1;
	}

	public String getCaracteristica2() {
		return caracteristica2;
	}

	public void setCaracteristica2(String caracteristica2) {
		this.caracteristica2 = caracteristica2;
	}

	public String getCaracteristica3() {
		return caracteristica3;
	}

	public void setCaracteristica3(String caracteristica3) {
		this.caracteristica3 = caracteristica3;
	}

	public String getCaracteristica4() {
		return caracteristica4;
	}

	public void setCaracteristica4(String caracteristica4) {
		this.caracteristica4 = caracteristica4;
	}

	public String getCaracteristica5() {
		return caracteristica5;
	}

	public void setCaracteristica5(String caracteristica5) {
		this.caracteristica5 = caracteristica5;
	}

	public String getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(String configuracion) {
		this.configuracion = configuracion;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUsuarioUltModificacion() {
		return usuarioUltModificacion;
	}

	public void setUsuarioUltModificacion(String usuarioUltModificacion) {
		this.usuarioUltModificacion = usuarioUltModificacion;
	}

	public LocalDateTime getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(LocalDateTime fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefinicionParametrosDTO other = (DefinicionParametrosDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefinicionParametrosDTO [id=" + id + ", parentId=" + parentId + ", tipoParametroId=" + tipoParametroId
				+ ", claveParametro=" + claveParametro + ", descripcion=" + descripcion + ", caracteristica1="
				+ caracteristica1 + ", caracteristica2=" + caracteristica2 + ", caracteristica3=" + caracteristica3
				+ ", caracteristica4=" + caracteristica4 + ", caracteristica5=" + caracteristica5 + ", configuracion="
				+ configuracion + ", usuarioAlta=" + usuarioAlta + ", fechaAlta=" + fechaAlta
				+ ", usuarioUltModificacion=" + usuarioUltModificacion + ", fechaUltModificacion="
				+ fechaUltModificacion + "]";
	}
	
}
