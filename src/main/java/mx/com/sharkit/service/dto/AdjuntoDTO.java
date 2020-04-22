package mx.com.sharkit.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Adjunto} entity.
 */
public class AdjuntoDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String contentType;

    private Long size;

    @Size(max = 128)
    private String fileName;

    @Lob
    private byte[] file;

    private String fileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdjuntoDTO adjuntoDTO = (AdjuntoDTO) o;
        if (adjuntoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adjuntoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdjuntoDTO{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", size=" + getSize() +
            ", fileName='" + getFileName() + "'" +
            ", file='" + getFile() + "'" +
            "}";
    }
}
