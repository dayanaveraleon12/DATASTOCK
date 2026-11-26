package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.TrasladoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Traslado}.
 */
public interface TrasladoService {
    /**
     * Save a traslado.
     *
     * @param trasladoDTO the entity to save.
     * @return the persisted entity.
     */
    TrasladoDTO save(TrasladoDTO trasladoDTO);

    /**
     * Updates a traslado.
     *
     * @param trasladoDTO the entity to update.
     * @return the persisted entity.
     */
    TrasladoDTO update(TrasladoDTO trasladoDTO);

    /**
     * Partially updates a traslado.
     *
     * @param trasladoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrasladoDTO> partialUpdate(TrasladoDTO trasladoDTO);

    /**
     * Get all the traslados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrasladoDTO> findAll(Pageable pageable);

    /**
     * Get all the traslados with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrasladoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" traslado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrasladoDTO> findOne(Long id);

    /**
     * Delete the "id" traslado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
