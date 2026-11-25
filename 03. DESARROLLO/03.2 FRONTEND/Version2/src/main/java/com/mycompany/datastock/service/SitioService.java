package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.SitioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Sitio}.
 */
public interface SitioService {
    /**
     * Save a sitio.
     *
     * @param sitioDTO the entity to save.
     * @return the persisted entity.
     */
    SitioDTO save(SitioDTO sitioDTO);

    /**
     * Updates a sitio.
     *
     * @param sitioDTO the entity to update.
     * @return the persisted entity.
     */
    SitioDTO update(SitioDTO sitioDTO);

    /**
     * Partially updates a sitio.
     *
     * @param sitioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SitioDTO> partialUpdate(SitioDTO sitioDTO);

    /**
     * Get all the sitios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SitioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sitio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SitioDTO> findOne(Long id);

    /**
     * Delete the "id" sitio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
