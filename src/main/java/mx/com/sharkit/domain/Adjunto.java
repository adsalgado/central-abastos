package mx.com.sharkit.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * A Adjunto.
 */
@Entity
@Table(name = "adjunto")
public class Adjunto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 128)
    @Column(name = "content_type", length = 128)
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Size(max = 128)
    @Column(name = "file_name", length = 128)
    private String fileName;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @OneToMany(mappedBy = "adjunto")
    private Set<ProductoImagen> productoImagens = new HashSet<>();

    @OneToMany(mappedBy = "adjunto")
    private Set<UsuarioImagen> usuarioImagens = new HashSet<>();

    @OneToMany(mappedBy = "adjunto")
    private Set<ParametrosAplicacion> parametrosAplicacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public Adjunto contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public Adjunto size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public Adjunto fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public Adjunto file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Adjunto fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Set<ProductoImagen> getProductoImagens() {
        return productoImagens;
    }

    public Adjunto productoImagens(Set<ProductoImagen> productoImagens) {
        this.productoImagens = productoImagens;
        return this;
    }

    public Adjunto addProductoImagen(ProductoImagen productoImagen) {
        this.productoImagens.add(productoImagen);
        productoImagen.setAdjunto(this);
        return this;
    }

    public Adjunto removeProductoImagen(ProductoImagen productoImagen) {
        this.productoImagens.remove(productoImagen);
        productoImagen.setAdjunto(null);
        return this;
    }

    public void setProductoImagens(Set<ProductoImagen> productoImagens) {
        this.productoImagens = productoImagens;
    }

    public Set<UsuarioImagen> getUsuarioImagens() {
        return usuarioImagens;
    }

    public Adjunto usuarioImagens(Set<UsuarioImagen> usuarioImagens) {
        this.usuarioImagens = usuarioImagens;
        return this;
    }

    public Adjunto addUsuarioImagen(UsuarioImagen usuarioImagen) {
        this.usuarioImagens.add(usuarioImagen);
        usuarioImagen.setAdjunto(this);
        return this;
    }

    public Adjunto removeUsuarioImagen(UsuarioImagen usuarioImagen) {
        this.usuarioImagens.remove(usuarioImagen);
        usuarioImagen.setAdjunto(null);
        return this;
    }

    public void setUsuarioImagens(Set<UsuarioImagen> usuarioImagens) {
        this.usuarioImagens = usuarioImagens;
    }

    public Set<ParametrosAplicacion> getParametrosAplicacions() {
        return parametrosAplicacions;
    }

    public Adjunto parametrosAplicacions(Set<ParametrosAplicacion> parametrosAplicacions) {
        this.parametrosAplicacions = parametrosAplicacions;
        return this;
    }

    public Adjunto addParametrosAplicacion(ParametrosAplicacion parametrosAplicacion) {
        this.parametrosAplicacions.add(parametrosAplicacion);
        parametrosAplicacion.setAdjunto(this);
        return this;
    }

    public Adjunto removeParametrosAplicacion(ParametrosAplicacion parametrosAplicacion) {
        this.parametrosAplicacions.remove(parametrosAplicacion);
        parametrosAplicacion.setAdjunto(null);
        return this;
    }

    public void setParametrosAplicacions(Set<ParametrosAplicacion> parametrosAplicacions) {
        this.parametrosAplicacions = parametrosAplicacions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adjunto)) {
            return false;
        }
        return id != null && id.equals(((Adjunto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Adjunto{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", size=" + getSize() +
            ", fileName='" + getFileName() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
