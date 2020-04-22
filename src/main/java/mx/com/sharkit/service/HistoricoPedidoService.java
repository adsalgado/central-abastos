package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.HistoricoPedidoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link mx.com.sharkit.domain.HistoricoPedido}.
 */
public interface HistoricoPedidoService {

    /**
     * Save a historicoPedido.
     *
     * @param historicoPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    HistoricoPedidoDTO save(HistoricoPedidoDTO historicoPedidoDTO);

    /**
     * Get all the historicoPedidos.
     *
     * @return the list of entities.
     */
    List<HistoricoPedidoDTO> findAll();


    /**
     * Get the "id" historicoPedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoricoPedidoDTO> findOne(Long id);

    /**
     * Delete the "id" historicoPedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
