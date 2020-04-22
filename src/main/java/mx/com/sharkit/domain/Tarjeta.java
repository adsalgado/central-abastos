package mx.com.sharkit.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tarjeta.
 */
@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @ManyToOne
    @JsonIgnoreProperties("tarjetas")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public Tarjeta numeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        return this;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public Tarjeta fechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
        return this;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getNumeroSeguridad() {
        return numeroSeguridad;
    }

    public Tarjeta numeroSeguridad(String numeroSeguridad) {
        this.numeroSeguridad = numeroSeguridad;
        return this;
    }

    public void setNumeroSeguridad(String numeroSeguridad) {
        this.numeroSeguridad = numeroSeguridad;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Tarjeta fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tarjeta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "Tarjeta{" +
            "id=" + getId() +
            ", numeroTarjeta='" + getNumeroTarjeta() + "'" +
            ", fechaCaducidad='" + getFechaCaducidad() + "'" +
            ", numeroSeguridad='" + getNumeroSeguridad() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            "}";
    }
}
