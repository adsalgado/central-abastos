package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Pagos} entity.
 */
public class PagosDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagosDTO pagosDTO = (PagosDTO) o;
        if (pagosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagosDTO{" +
            "id=" + getId() +
            "}";
    }
}
