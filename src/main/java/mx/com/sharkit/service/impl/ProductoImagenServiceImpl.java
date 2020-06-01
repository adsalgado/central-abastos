package mx.com.sharkit.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.domain.ProductoImagen;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.repository.ProductoImagenRepository;
import mx.com.sharkit.service.ProductoImagenService;
import mx.com.sharkit.service.dto.ProductoImagenDTO;
import mx.com.sharkit.service.mapper.ProductoImagenMapper;

/**
 * Service Implementation for managing {@link ProductoImagen}.
 */
@Service
@Transactional
public class ProductoImagenServiceImpl implements ProductoImagenService {

    private final Logger log = LoggerFactory.getLogger(ProductoImagenServiceImpl.class);

    private final ProductoImagenRepository productoImagenRepository;

    private final ProductoImagenMapper productoImagenMapper;

	@Autowired
	private AdjuntoRepository adjuntoRepository;

    public ProductoImagenServiceImpl(ProductoImagenRepository productoImagenRepository, ProductoImagenMapper productoImagenMapper) {
        this.productoImagenRepository = productoImagenRepository;
        this.productoImagenMapper = productoImagenMapper;
    }

    /**
     * Save a productoImagen.
     *
     * @param productoImagenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoImagenDTO save(ProductoImagenDTO productoImagenDTO) {
        log.debug("Request to save ProductoImagen : {}", productoImagenDTO);
        ProductoImagen productoImagen = productoImagenMapper.toEntity(productoImagenDTO);
        productoImagen = productoImagenRepository.save(productoImagen);
        
		if (productoImagenDTO.getAdjunto() != null) {
			Adjunto adjunto = null;
			if (productoImagenDTO.getAdjunto().getId() != null) {
				adjunto = adjuntoRepository.findById(productoImagenDTO.getAdjunto().getId()).orElse(null);
				if (adjunto != null) {
					adjunto.setFileName(productoImagenDTO.getAdjunto().getFileName());
					adjunto.setFile(productoImagenDTO.getAdjunto().getFile());
					adjunto.setContentType(productoImagenDTO.getAdjunto().getContentType());
					adjunto.setFileContentType(productoImagenDTO.getAdjunto().getFileContentType());
					adjunto.setSize(productoImagenDTO.getAdjunto().getSize());
				}
			}

			if (adjunto == null) {
				adjunto = new Adjunto();
				adjunto.setFileName(productoImagenDTO.getAdjunto().getFileName());
				adjunto.setFile(productoImagenDTO.getAdjunto().getFile());
				adjunto.setContentType(productoImagenDTO.getAdjunto().getContentType());
				adjunto.setFileContentType(productoImagenDTO.getAdjunto().getFileContentType());
				adjunto.setSize(productoImagenDTO.getAdjunto().getSize());
				adjuntoRepository.save(adjunto);
			}

			productoImagen.setAdjuntoId(adjunto.getId());
		}

        return productoImagenMapper.toDto(productoImagen);
    }

    /**
     * Get all the productoImagens.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoImagenDTO> findAll() {
        log.debug("Request to get all ProductoImagens");
        return productoImagenRepository.findAll().stream()
            .map(productoImagenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productoImagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoImagenDTO> findOne(Long id) {
        log.debug("Request to get ProductoImagen : {}", id);
        return productoImagenRepository.findById(id)
            .map(productoImagenMapper::toDto);
    }

    /**
     * Delete the productoImagen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoImagen : {}", id);
        productoImagenRepository.deleteById(id);
    }

    /**
	 * Get all the productoImagens by productoProveedorId.
	 *
	 * @param productoProveedorId
	 * @return the list of entities.
	 */
	@Override
	public List<ProductoImagenDTO> findByProductoProveedorId(Long productoProveedorId) {
        log.debug("Request to get all ProductoImagens");
        return productoImagenRepository.findByProductoProveedorIdOrderByIdAsc(productoProveedorId).stream()
            .map(productoImagenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
	}
}
