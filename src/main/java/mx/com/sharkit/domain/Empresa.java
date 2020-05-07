package mx.com.sharkit.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final Long CENTRAL_ABASTOS = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "nombre", length = 256, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "empresa")
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    private Set<Proveedor> proveedors = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    private Set<Recolector> recolectors = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    private Set<Transportista> transportistas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    private Set<Seccion> seccions = new HashSet<>();

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

    public Empresa nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Empresa clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Empresa addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setEmpresa(this);
        return this;
    }

    public Empresa removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setEmpresa(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Set<Proveedor> getProveedors() {
        return proveedors;
    }

    public Empresa proveedors(Set<Proveedor> proveedors) {
        this.proveedors = proveedors;
        return this;
    }

    public Empresa addProveedor(Proveedor proveedor) {
        this.proveedors.add(proveedor);
        proveedor.setEmpresa(this);
        return this;
    }

    public Empresa removeProveedor(Proveedor proveedor) {
        this.proveedors.remove(proveedor);
        proveedor.setEmpresa(null);
        return this;
    }

    public void setProveedors(Set<Proveedor> proveedors) {
        this.proveedors = proveedors;
    }

    public Set<Recolector> getRecolectors() {
        return recolectors;
    }

    public Empresa recolectors(Set<Recolector> recolectors) {
        this.recolectors = recolectors;
        return this;
    }

    public Empresa addRecolector(Recolector recolector) {
        this.recolectors.add(recolector);
        recolector.setEmpresa(this);
        return this;
    }

    public Empresa removeRecolector(Recolector recolector) {
        this.recolectors.remove(recolector);
        recolector.setEmpresa(null);
        return this;
    }

    public void setRecolectors(Set<Recolector> recolectors) {
        this.recolectors = recolectors;
    }

    public Set<Transportista> getTransportistas() {
        return transportistas;
    }

    public Empresa transportistas(Set<Transportista> transportistas) {
        this.transportistas = transportistas;
        return this;
    }

    public Empresa addTransportista(Transportista transportista) {
        this.transportistas.add(transportista);
        transportista.setEmpresa(this);
        return this;
    }

    public Empresa removeTransportista(Transportista transportista) {
        this.transportistas.remove(transportista);
        transportista.setEmpresa(null);
        return this;
    }

    public void setTransportistas(Set<Transportista> transportistas) {
        this.transportistas = transportistas;
    }

    public Set<Seccion> getSeccions() {
        return seccions;
    }

    public Empresa seccions(Set<Seccion> seccions) {
        this.seccions = seccions;
        return this;
    }

    public Empresa addSeccion(Seccion seccion) {
        this.seccions.add(seccion);
        seccion.setEmpresa(this);
        return this;
    }

    public Empresa removeSeccion(Seccion seccion) {
        this.seccions.remove(seccion);
        seccion.setEmpresa(null);
        return this;
    }

    public void setSeccions(Set<Seccion> seccions) {
        this.seccions = seccions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
