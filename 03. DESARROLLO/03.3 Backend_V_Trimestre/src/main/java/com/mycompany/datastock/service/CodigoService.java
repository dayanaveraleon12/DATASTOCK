package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.CodigoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.Codigo}.
 */
public interface CodigoService {
    /**
     * Save a codigo.
     *
     * @param codigoDTO the entity to save.
     * @return the persisted entity.
     */
    CodigoDTO save(CodigoDTO codigoDTO);

    /**
     * Updates a codigo.
     *
     * @param codigoDTO the entity to update.
     * @return the persisted entity.
     */
    CodigoDTO update(CodigoDTO codigoDTO);

    /**
     * Partially updates a codigo.
     *
     * @param codigoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CodigoDTO> partialUpdate(CodigoDTO codigoDTO);

    /**
     * Get all the codigos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CodigoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" codigo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CodigoDTO> findOne(Long id);

    /**
     * Delete the "id" codigo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
