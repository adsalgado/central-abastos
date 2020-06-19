package mx.com.sharkit.repository;

import mx.com.sharkit.domain.TrackingQueja;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrackingQueja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingQuejaRepository extends JpaRepository<TrackingQueja, Long> {
}
