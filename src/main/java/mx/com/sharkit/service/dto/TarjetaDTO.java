package mx.com.sharkit.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.com.sharkit.domain.Tarjeta} entity.
 */
public class TarjetaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String numeroTarjeta;

    @NotNull
    @Size(max = 10)
    private String fechaCaducidad;

    @NotNull
    @Size(max = 3)
    private String numeroSeguridad;

    @NotNull
    private LocalDate fechaAlta;


    private Long clienteId;

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

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarjetaDTO tarjetaDTO = (TarjetaDTO) o;
        if (tarjetaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarjetaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarjetaDTO{" +
            "id=" + getId() +
            ", numeroTarjeta='" + getNumeroTarjeta() + "'" +
            ", fechaCaducidad='" + getFechaCaducidad() + "'" +
            ", numeroSeguridad='" + getNumeroSeguridad() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", cliente=" + getClienteId() +
            "}";
    }
}
