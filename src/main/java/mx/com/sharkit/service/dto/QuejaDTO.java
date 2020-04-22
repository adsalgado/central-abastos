package mx.com.sharkit.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Queja} entity.
 */
public class QuejaDTO implements Serializable {

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

        QuejaDTO quejaDTO = (QuejaDTO) o;
        if (quejaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quejaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuejaDTO{" +
            "id=" + getId() +
            "}";
    }
}
