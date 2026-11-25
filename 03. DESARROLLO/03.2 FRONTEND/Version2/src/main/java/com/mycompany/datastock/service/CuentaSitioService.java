package com.mycompany.datastock.service;

import com.mycompany.datastock.service.dto.CuentaSitioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.datastock.domain.CuentaSitio}.
 */
public interface CuentaSitioService {
    /**
     * Save a cuentaSitio.
     *
     * @param cuentaSitioDTO the entity to save.
     * @return the persisted entity.
     */
    CuentaSitioDTO save(CuentaSitioDTO cuentaSitioDTO);

    /**
     * Updates a cuentaSitio.
     *
     * @param cuentaSitioDTO the entity to update.
     * @return the persisted entity.
     */
    CuentaSitioDTO update(CuentaSitioDTO cuentaSitioDTO);

    /**
     * Partially updates a cuentaSitio.
     *
     * @param cuentaSitioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CuentaSitioDTO> partialUpdate(CuentaSitioDTO cuentaSitioDTO);

    /**
     * Get all the cuentaSitios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CuentaSitioDTO> findAll(Pageable pageable);

    /**
     * Get all the cuentaSitios with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CuentaSitioDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" cuentaSitio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CuentaSitioDTO> findOne(Long id);

    /**
     * Delete the "id" cuentaSitio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
