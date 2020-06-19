package mx.com.sharkit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A TrackingQueja.
 */
@Entity
@Table(name = "tracking_queja")
public class TrackingQueja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "queja_id")
    private Long quejaId;

    @Column(name = "tracking_message")
    private String trackingMessage;

    @Column(name = "tracking_date")
    private LocalDateTime trackingDate;

    @OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queja_id", insertable = false, updatable = false)
    private Queja queja;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

//    public TrackingQueja userId(Long userId) {
//        this.userId = userId;
//        return this;
//    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuejaId() {
        return quejaId;
    }

//    public TrackingQueja quejaId(Long quejaId) {
//        this.quejaId = quejaId;
//        return this;
//    }

    public void setQuejaId(Long quejaId) {
        this.quejaId = quejaId;
    }

    public String getTrackingMessage() {
        return trackingMessage;
    }

//    public TrackingQueja trackingMessage(String trackingMessage) {
//        this.trackingMessage = trackingMessage;
//        return this;
//    }

    public void setTrackingMessage(String trackingMessage) {
        this.trackingMessage = trackingMessage;
    }

    public LocalDateTime getTrackingDate() {
        return trackingDate;
    }

//    public TrackingQueja trackingDate(LocalDateTime trackingDate) {
//        this.trackingDate = trackingDate;
//        return this;
//    }

    public void setTrackingDate(LocalDateTime trackingDate) {
        this.trackingDate = trackingDate;
    }

    public User getUser() {
        return user;
    }

//    public TrackingQueja user(User user) {
//        this.user = user;
//        return this;
//    }

    public void setUser(User user) {
        this.user = user;
    }

    public Queja getQueja() {
        return queja;
    }

//    public TrackingQueja queja(Queja queja) {
//        this.queja = queja;
//        return this;
//    }

    public void setQueja(Queja queja) {
        this.queja = queja;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackingQueja)) {
            return false;
        }
        return id != null && id.equals(((TrackingQueja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrackingQueja{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", quejaId=" + getQuejaId() +
            ", trackingMessage='" + getTrackingMessage() + "'" +
            ", trackingDate='" + getTrackingDate() + "'" +
            "}";
    }
}
