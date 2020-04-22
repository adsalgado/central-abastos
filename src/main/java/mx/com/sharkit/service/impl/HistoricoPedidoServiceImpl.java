package mx.com.sharkit.service.impl;

import mx.com.sharkit.service.HistoricoPedidoService;
import mx.com.sharkit.domain.HistoricoPedido;
import mx.com.sharkit.repository.HistoricoPedidoRepository;
import mx.com.sharkit.service.dto.HistoricoPedidoDTO;
import mx.com.sharkit.service.mapper.HistoricoPedidoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HistoricoPedido}.
 */
@Service
@Transactional
public class HistoricoPedidoServiceImpl implements HistoricoPedidoService {

    private final Logger log = LoggerFactory.getLogger(HistoricoPedidoServiceImpl.class);

    private final HistoricoPedidoRepository historicoPedidoRepository;

    private final HistoricoPedidoMapper historicoPedidoMapper;

    public HistoricoPedidoServiceImpl(HistoricoPedidoRepository historicoPedidoRepository, HistoricoPedidoMapper historicoPedidoMapper) {
        this.historicoPedidoRepository = historicoPedidoRepository;
        this.historicoPedidoMapper = historicoPedidoMapper;
    }

    /**
     * Save a historicoPedido.
     *
     * @param historicoPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistoricoPedidoDTO save(HistoricoPedidoDTO historicoPedidoDTO) {
        log.debug("Request to save HistoricoPedido : {}", historicoPedidoDTO);
        HistoricoPedido historicoPedido = historicoPedidoMapper.toEntity(historicoPedidoDTO);
        historicoPedido = historicoPedidoRepository.save(historicoPedido);
        return historicoPedidoMapper.toDto(historicoPedido);
    }

    /**
     * Get all the historicoPedidos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistoricoPedidoDTO> findAll() {
        log.debug("Request to get all HistoricoPedidos");
        return historicoPedidoRepository.findAll().stream()
            .map(historicoPedidoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one historicoPedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HistoricoPedidoDTO> findOne(Long id) {
        log.debug("Request to get HistoricoPedido : {}", id);
        return historicoPedidoRepository.findById(id)
            .map(historicoPedidoMapper::toDto);
    }

    /**
     * Delete the historicoPedido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoricoPedido : {}", id);
        historicoPedidoRepository.deleteById(id);
    }
}
