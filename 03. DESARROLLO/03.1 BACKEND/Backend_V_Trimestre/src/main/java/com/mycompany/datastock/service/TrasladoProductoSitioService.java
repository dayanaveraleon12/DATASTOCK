package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.TrasladoProductoSitioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.TrasladoProductoSitio}.
 */
public interface TrasladoProductoSitioService {
    /**
     * Save a trasladoProductoSitio.
     *
     * @param trasladoProductoSitioDTO the entity to save.
     * @return the persisted entity.
     */
    TrasladoProductoSitioDTO save(TrasladoProductoSitioDTO trasladoProductoSitioDTO);

    /**
     * Updates a trasladoProductoSitio.
     *
     * @param trasladoProductoSitioDTO the entity to update.
     * @return the persisted entity.
     */
    TrasladoProductoSitioDTO update(TrasladoProductoSitioDTO trasladoProductoSitioDTO);

    /**
     * Partially updates a trasladoProductoSitio.
     *
     * @param trasladoProductoSitioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrasladoProductoSitioDTO> partialUpdate(TrasladoProductoSitioDTO trasladoProductoSitioDTO);

    /**
     * Get all the trasladoProductoSitios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrasladoProductoSitioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trasladoProductoSitio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrasladoProductoSitioDTO> findOne(Long id);

    /**
     * Delete the "id" trasladoProductoSitio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
