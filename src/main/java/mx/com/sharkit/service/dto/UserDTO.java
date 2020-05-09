package mx.com.sharkit.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import mx.com.sharkit.config.Constants;
import mx.com.sharkit.domain.Authority;
import mx.com.sharkit.domain.User;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    @Size(max = 50)
    private String motherLastName;

    @Size(max = 15)
    private String telefono;

    @Size(max = 1)
    private String genero;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;
    
    private Long tipoUsuarioId;
    
    private String token;

    private Long adjuntoId;
    
    private AdjuntoDTO adjunto;
    
    private Long tipoPersonaId;
    
    private String razonSocial;

    private DireccionDTO direccion;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
    	if (user != null) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.motherLastName = user.getMotherLastName();
            this.telefono = user.getTelefono();
            this.genero = user.getGenero();
            this.fechaNacimiento = user.getFechaNacimiento();
            this.email = user.getEmail();
            this.activated = user.getActivated();
            this.imageUrl = user.getImageUrl();
            this.langKey = user.getLangKey();
            this.createdBy = user.getCreatedBy();
            this.createdDate = user.getCreatedDate();
            this.lastModifiedBy = user.getLastModifiedBy();
            this.lastModifiedDate = user.getLastModifiedDate();
            this.token = user.getToken();
            this.tipoUsuarioId = user.getTipoUsuarioId();
            this.tipoPersonaId = user.getTipoPersonaId();
            this.adjuntoId = user.getAdjuntoId();
            this.authorities = user.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
    		
    	} 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotherLastName() {
		return motherLastName;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getAdjuntoId() {
		return adjuntoId;
	}

	public void setAdjuntoId(Long adjuntoId) {
		this.adjuntoId = adjuntoId;
	}

	public AdjuntoDTO getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(AdjuntoDTO adjunto) {
		this.adjunto = adjunto;
	}

	public Long getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(Long tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public DireccionDTO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDTO direccion) {
		this.direccion = direccion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", login=" + login + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", motherLastName=" + motherLastName + ", telefono=" + telefono + ", genero="
				+ genero + ", fechaNacimiento=" + fechaNacimiento + ", imageUrl=" + imageUrl + ", activated="
				+ activated + ", langKey=" + langKey + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + ", authorities="
				+ authorities + ", tipoUsuarioId=" + tipoUsuarioId + ", token=" + token + ", adjuntoId=" + adjuntoId
				+ ", adjunto=" + adjunto + ", tipoPersonaId=" + tipoPersonaId + ", razonSocial=" + razonSocial
				+ ", direccion=" + direccion + "]";
	}

}
