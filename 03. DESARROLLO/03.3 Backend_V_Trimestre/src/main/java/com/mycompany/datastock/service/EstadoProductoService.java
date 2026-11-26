package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.EstadoProductoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.EstadoProducto}.
 */
public interface EstadoProductoService {
    /**
     * Save a estadoProducto.
     *
     * @param estadoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoProductoDTO save(EstadoProductoDTO estadoProductoDTO);

    /**
     * Updates a estadoProducto.
     *
     * @param estadoProductoDTO the entity to update.
     * @return the persisted entity.
     */
    EstadoProductoDTO update(EstadoProductoDTO estadoProductoDTO);

    /**
     * Partially updates a estadoProducto.
     *
     * @param estadoProductoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoProductoDTO> partialUpdate(EstadoProductoDTO estadoProductoDTO);

    /**
     * Get all the estadoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoProductoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" estadoProducto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoProductoDTO> findOne(Long id);

    /**
     * Delete the "id" estadoProducto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
