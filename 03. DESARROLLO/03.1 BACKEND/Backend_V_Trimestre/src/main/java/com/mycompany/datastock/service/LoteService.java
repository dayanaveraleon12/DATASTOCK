package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.LoteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Lote}.
 */
public interface LoteService {
    /**
     * Save a lote.
     *
     * @param loteDTO the entity to save.
     * @return the persisted entity.
     */
    LoteDTO save(LoteDTO loteDTO);

    /**
     * Updates a lote.
     *
     * @param loteDTO the entity to update.
     * @return the persisted entity.
     */
    LoteDTO update(LoteDTO loteDTO);

    /**
     * Partially updates a lote.
     *
     * @param loteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoteDTO> partialUpdate(LoteDTO loteDTO);

    /**
     * Get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoteDTO> findAll(Pageable pageable);

    /**
     * Get all the lotes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" lote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoteDTO> findOne(Long id);

    /**
     * Delete the "id" lote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
