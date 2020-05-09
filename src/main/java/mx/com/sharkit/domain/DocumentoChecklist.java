package mx.com.sharkit.domain;

import java.io.Serializable;

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

/**
 * A Documento.
 */
@Entity
@Table(name = "documento_checklist")
public class DocumentoChecklist implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	@JoinColumn(name = "tipo_usuario_id", insertable = false, updatable = false)
	private TipoUsuario tipoUsuario;

    @Column(name = "tipo_usuario_id")
    private Long tipoUsuarioId;

	@ManyToOne
	@JoinColumn(name = "documento_id", insertable = false, updatable = false)
	private Documento documento;

    @Column(name = "documento_id")
    private Long documentoId;
    
    @Size(max = 1)
    @Column(name = "requerido", length = 1)
    private String requerido;
    
 
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoChecklist)) {
            return false;
        }
        return id != null && id.equals(((DocumentoChecklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "DocumentoChecklist [id=" + id + ", tipoUsuario=" + tipoUsuario + ", tipoUsuarioId=" + tipoUsuarioId
				+ ", documento=" + documento + ", documentoId=" + documentoId + ", requerido=" + requerido + "]";
	}

}
