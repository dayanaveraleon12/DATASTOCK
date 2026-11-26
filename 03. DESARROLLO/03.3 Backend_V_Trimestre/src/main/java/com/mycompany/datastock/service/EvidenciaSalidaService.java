package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.EvidenciaSalidaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.EvidenciaSalida}.
 */
public interface EvidenciaSalidaService {
    /**
     * Save a evidenciaSalida.
     *
     * @param evidenciaSalidaDTO the entity to save.
     * @return the persisted entity.
     */
    EvidenciaSalidaDTO save(EvidenciaSalidaDTO evidenciaSalidaDTO);

    /**
     * Updates a evidenciaSalida.
     *
     * @param evidenciaSalidaDTO the entity to update.
     * @return the persisted entity.
     */
    EvidenciaSalidaDTO update(EvidenciaSalidaDTO evidenciaSalidaDTO);

    /**
     * Partially updates a evidenciaSalida.
     *
     * @param evidenciaSalidaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvidenciaSalidaDTO> partialUpdate(EvidenciaSalidaDTO evidenciaSalidaDTO);

    /**
     * Get all the evidenciaSalidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvidenciaSalidaDTO> findAll(Pageable pageable);

    /**
     * Get all the evidenciaSalidas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvidenciaSalidaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" evidenciaSalida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvidenciaSalidaDTO> findOne(Long id);

    /**
     * Delete the "id" evidenciaSalida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
