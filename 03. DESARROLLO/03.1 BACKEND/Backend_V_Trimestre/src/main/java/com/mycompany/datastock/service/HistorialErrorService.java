package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.HistorialErrorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.HistorialError}.
 */
public interface HistorialErrorService {
    /**
     * Save a historialError.
     *
     * @param historialErrorDTO the entity to save.
     * @return the persisted entity.
     */
    HistorialErrorDTO save(HistorialErrorDTO historialErrorDTO);

    /**
     * Updates a historialError.
     *
     * @param historialErrorDTO the entity to update.
     * @return the persisted entity.
     */
    HistorialErrorDTO update(HistorialErrorDTO historialErrorDTO);

    /**
     * Partially updates a historialError.
     *
     * @param historialErrorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HistorialErrorDTO> partialUpdate(HistorialErrorDTO historialErrorDTO);

    /**
     * Get all the historialErrors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HistorialErrorDTO> findAll(Pageable pageable);

    /**
     * Get all the historialErrors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HistorialErrorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" historialError.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistorialErrorDTO> findOne(Long id);

    /**
     * Delete the "id" historialError.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
