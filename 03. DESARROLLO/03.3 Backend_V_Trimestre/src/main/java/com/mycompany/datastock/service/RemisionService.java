package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.RemisionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Remision}.
 */
public interface RemisionService {
    /**
     * Save a remision.
     *
     * @param remisionDTO the entity to save.
     * @return the persisted entity.
     */
    RemisionDTO save(RemisionDTO remisionDTO);

    /**
     * Updates a remision.
     *
     * @param remisionDTO the entity to update.
     * @return the persisted entity.
     */
    RemisionDTO update(RemisionDTO remisionDTO);

    /**
     * Partially updates a remision.
     *
     * @param remisionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RemisionDTO> partialUpdate(RemisionDTO remisionDTO);

    /**
     * Get all the remisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RemisionDTO> findAll(Pageable pageable);

    /**
     * Get all the remisions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RemisionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" remision.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RemisionDTO> findOne(Long id);

    /**
     * Delete the "id" remision.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
