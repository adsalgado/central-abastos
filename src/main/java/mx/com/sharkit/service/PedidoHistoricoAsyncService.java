package mx.com.sharkit.service;

import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;

public interface PedidoHistoricoAsyncService {

    /**
     * Save a PedidoHistorico.
     *
     * @param historicoDTO the entity to save.
     */
    void savePedidoProveedorHistorico(PedidoProveedorHistoricoDTO historicoDTO);

}
