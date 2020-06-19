package mx.com.sharkit.service.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * A DTO for the {@link mx.com.sharkit.domain.TrackingQueja} entity.
 */
public class TrackingQuejaDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String trackingMessage;
    
//    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", locale = "es_MX")
//    private LocalDateTime trackingDate;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime trackingDate;

    private Long userId;

    private String userLogin;

    private Long quejaId;
    
    private QuejaDTO queja;

    private UserDTO user;
    
    public Long getId() {
        return id;
    }

    public String getTrackingMessage() {
        return trackingMessage;
    }

    public void setTrackingMessage(String trackingMessage) {
        this.trackingMessage = trackingMessage;
    }

    public LocalDateTime getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(LocalDateTime trackingDate) {
        this.trackingDate = trackingDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getQuejaId() {
        return quejaId;
    }

    public void setQuejaId(Long quejaId) {
        this.quejaId = quejaId;
    }
    
    

    public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public QuejaDTO getQueja() {
		return queja;
	}

	public void setQueja(QuejaDTO queja) {
		this.queja = queja;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackingQuejaDTO)) {
            return false;
        }

        return id != null && id.equals(((TrackingQuejaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrackingQuejaDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", quejaId=" + getQuejaId() +
            ", trackingMessage='" + getTrackingMessage() + "'" +
            ", trackingDate='" + getTrackingDate() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", quejaId=" + getQuejaId() +
            "}";
    }
}
