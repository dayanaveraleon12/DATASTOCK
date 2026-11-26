package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.SalidaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Salida}.
 */
public interface SalidaService {
    /**
     * Save a salida.
     *
     * @param salidaDTO the entity to save.
     * @return the persisted entity.
     */
    SalidaDTO save(SalidaDTO salidaDTO);

    /**
     * Updates a salida.
     *
     * @param salidaDTO the entity to update.
     * @return the persisted entity.
     */
    SalidaDTO update(SalidaDTO salidaDTO);

    /**
     * Partially updates a salida.
     *
     * @param salidaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SalidaDTO> partialUpdate(SalidaDTO salidaDTO);

    /**
     * Get all the salidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SalidaDTO> findAll(Pageable pageable);

    /**
     * Get all the salidas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SalidaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" salida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalidaDTO> findOne(Long id);

    /**
     * Delete the "id" salida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
