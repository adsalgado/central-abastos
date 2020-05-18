package mx.com.sharkit.domain;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A CarritoHistorico.
 */
@Entity
@Table(name = "carrito_historico")
public class CarritoHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "fecha_alta", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", locale = "es_MX")
    private LocalDateTime fechaAlta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    @JsonIgnoreProperties("carritoHistoricos")
    private User cliente;
    
    @Column(name = "cliente_id")
    private Long clienteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public CarritoHistorico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public CarritoHistorico fechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public Long getClienteId() {
 		return clienteId;
 	}

 	public void setClienteId(Long clienteId) {
 		this.clienteId = clienteId;
 	}

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarritoHistorico)) {
            return false;
        }
        return id != null && id.equals(((CarritoHistorico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "CarritoHistorico [id=" + id + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", cliente=" + cliente
				+ ", clienteId=" + clienteId + "]";
	}

}
