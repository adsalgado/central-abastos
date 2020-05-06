package mx.com.sharkit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.domain.Chat;


/**
 * Spring Data  repository for the Chat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	Optional<Chat> findOneByUsuarioEmisorLoginAndUsuarioReceptorLogin(String usuarioEmisor, String usuarioReceptor);

	Optional<Chat> findOneByPedidoProveedorIdAndTipoChatId(Long pedidoProveedorId, Long tipoChatId);

}
