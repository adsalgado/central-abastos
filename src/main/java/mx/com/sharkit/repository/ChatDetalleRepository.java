package mx.com.sharkit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.ChatDetalle;


/**
 * Spring Data  repository for the Chat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatDetalleRepository extends JpaRepository<ChatDetalle, Long> {

	List<ChatDetalle> findByChatIdOrderById(Long chatId);
	
}
